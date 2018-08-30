package com.gs.kinesis.demo.service;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

@Service
public class KinesisProducerServiceImpl implements IKinesisProducerService {
    private final Logger logger = Logger.getLogger(KinesisProducerServiceImpl.class.getName());

    @Value("${kinesis.stream.name}")
    private String streamName;

    @Autowired
    private KinesisProducer kinesisProducer;

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;


    private static final Random RANDOM = new Random();

    @Override
    public void publish(final String data) {

        final ListenableFuture<UserRecordResult> f =
                kinesisProducer.addUserRecord(streamName, String.valueOf(System.currentTimeMillis()),
                        null, ByteBuffer.wrap(data.getBytes()));

        Futures.addCallback(f, callback);
    }


    private void producerWithApi() {
        String data = new String("Hello");
        AmazonKinesisClient amazonKinesisClient = new AmazonKinesisClient(awsCredentialsProvider.getCredentials());
        amazonKinesisClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        PutRecordRequest putRecord = new PutRecordRequest();
        putRecord.setStreamName(streamName);
        putRecord.setPartitionKey(String.valueOf(System.currentTimeMillis()));
        putRecord.setData(ByteBuffer.wrap(data.getBytes()));

        try {
            logger.info(amazonKinesisClient.putRecord(putRecord).toString());
        } catch (AmazonClientException ex) {
            logger.warning("Error sending record to Amazon Kinesis." + ex);
        }
    }

    private String randomExplicitHashKey() {
        return new BigInteger(128, RANDOM).toString(10);
    }

    private FutureCallback<UserRecordResult> callback = new FutureCallback<UserRecordResult>() {
        public void onFailure(Throwable t) {
        }

        public void onSuccess(UserRecordResult result) {
            logger.info(result.getSequenceNumber());
        }
    };
}

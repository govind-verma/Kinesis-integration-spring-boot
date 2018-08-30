package com.gs.kinesis.demo.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.gs.kinesis.demo.processor.RecordProcessorFactoryImpl;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class KinesisDemoConfig {

    @Value("${kinesis.stream.name}")
    private String streamName;

    private AWSCredentialsProvider awsCredentialsProvider;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }


    @Bean
    public KinesisClientLibConfiguration kinesisClientLibConfiguration() {
        System.out.println("awsCredentialsProvider" + awsCredentialsProvider);

        final KinesisClientLibConfiguration kinesisClientLibConfiguration =
                new KinesisClientLibConfiguration("kinesisDemoApp", streamName, awsCredentialsProvider, "DemoWorker");


        kinesisClientLibConfiguration.withRegionName(Region.getRegion(Regions.US_WEST_2).toString());
        kinesisClientLibConfiguration.withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);

        return kinesisClientLibConfiguration;
    }

    @Bean
    public KinesisProducer getKinesisProducer() {
        KinesisProducerConfiguration config = new KinesisProducerConfiguration();
        config.setRegion(Region.getRegion(Regions.US_WEST_2).toString());
        config.setCredentialsProvider(new DefaultAWSCredentialsProviderChain());
        config.setMaxConnections(1);
        config.setRequestTimeout(60000);
        config.setRecordMaxBufferedTime(100);
        KinesisProducer producer = new KinesisProducer(config);

        return producer;
    }

    @Bean
    public Worker workerConsumer() {
        return new Worker(new RecordProcessorFactoryImpl(), kinesisClientLibConfiguration());
    }

    @Bean
    public ExecutorService workerExecutorService() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    public DefaultAWSCredentialsProviderChain defaultAWSCredentialsProviderChain() {
        return new DefaultAWSCredentialsProviderChain();
    }
}

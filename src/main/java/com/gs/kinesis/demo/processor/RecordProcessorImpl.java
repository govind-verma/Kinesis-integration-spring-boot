package com.gs.kinesis.demo.processor;

import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;

public class RecordProcessorImpl implements IRecordProcessor {
    Logger logger = Logger.getLogger(RecordProcessorImpl.class.getName());

    public void initialize(final String s) {
        logger.info("initializing record processor with shard : " + s);
    }

    public void processRecords(final List<Record> records, final IRecordProcessorCheckpointer iRecordProcessorCheckpointer) {

        for (Record record : records) {
            String data = "Partition Key: " + record.getPartitionKey() +
                    ", Data record : " + new String(record.getData().array());

            logger.info(data);
        }

    }

    public void shutdown(final IRecordProcessorCheckpointer iRecordProcessorCheckpointer, final ShutdownReason shutdownReason) {
        logger.info("shutting down record processor ");
    }
}

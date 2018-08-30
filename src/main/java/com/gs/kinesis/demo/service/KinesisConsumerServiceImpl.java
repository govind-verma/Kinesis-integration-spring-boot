package com.gs.kinesis.demo.service;

import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;

@Service
public class KinesisConsumerServiceImpl implements IKinesisConsumerService {
    private final Logger logger = Logger.getLogger(KinesisConsumerServiceImpl.class.getName());
    @Autowired
    private Worker workerConsumer;

    @Autowired
    private ExecutorService workerExecutorService;

    @Override
    public void start() {
        logger.info("Starting kinesis consumer...");

        workerExecutorService.execute(workerConsumer);
    }
}

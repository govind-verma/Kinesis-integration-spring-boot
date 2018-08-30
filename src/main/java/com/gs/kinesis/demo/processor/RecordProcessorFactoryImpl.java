package com.gs.kinesis.demo.processor;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;


public class RecordProcessorFactoryImpl implements IRecordProcessorFactory {

    public IRecordProcessor createProcessor() {
        return new RecordProcessorImpl();
    }
}

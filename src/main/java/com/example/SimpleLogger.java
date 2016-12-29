package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.ngmon.logger.Logger;
import org.ngmon.logger.enums.EventLevel;
import org.ngmon.logger.injection.LogEvent;
import org.ngmon.logger.serialize.CachingLogRecordGenerator;

public class SimpleLogger implements Logger {

    private final CachingLogRecordGenerator holder = new CachingLogRecordGenerator();

    public SimpleLogger() throws JsonMappingException {
    }

    @Override
    public void log(EventLevel level, LogEvent logEvent, String signature) {
        try {
            String record = this.holder.getRecord(level, logEvent, signature);
            System.out.println(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

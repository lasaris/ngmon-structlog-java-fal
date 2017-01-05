package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ngmon.logger.Logger;
import org.ngmon.logger.enums.EventLevel;
import org.ngmon.logger.injection.LogEvent;
import org.ngmon.logger.serialize.CachingSchemaGenerator;
import org.ngmon.logger.serialize.EventWrapper;

public class SimpleLogger implements Logger {

    private final ObjectMapper mapper = new ObjectMapper();

    public SimpleLogger() throws JsonMappingException {
    }

    @Override
    public void log(EventLevel level, LogEvent logEvent, String signature) {
        try {
            long timestamp = System.currentTimeMillis();
            String payload = this.mapper.writeValueAsString(logEvent.getValueMap());
            String record = this.mapper.writeValueAsString(new EventWrapper(signature, timestamp, level, payload));
            System.out.println(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

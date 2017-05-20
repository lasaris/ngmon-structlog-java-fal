package com.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ngmon.structlog.Logger;
import org.ngmon.structlog.enums.EventLevel;
import org.ngmon.structlog.injection.LogEvent;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class SimpleLogger implements Logger {

    private JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());

    public SimpleLogger() throws JsonMappingException {}

    @Override
    public void log(EventLevel level, LogEvent logEvent, String signature) {
        System.out.println(getEventJson("events", signature, logEvent.getValueMap(), level));
    }

    private String getEventJson(String fqnNS, String event, Map<String, Object> payload, EventLevel level) {
        StringWriter writer = new StringWriter();
        try (JsonGenerator json = this.jsonFactory.createGenerator(writer)) {
            json.writeStartObject();
            json.writeStringField("sid", event);
            json.writeStringField("level", level.toString());
            json.writeStringField("sid_ns", fqnNS);
            json.writeObjectFieldStart("_");
            for (Map.Entry<String, Object> stringObjectEntry : payload.entrySet()) {
                json.writeObjectField(stringObjectEntry.getKey(), stringObjectEntry.getValue());
            }
            json.writeEndObject();
            json.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}

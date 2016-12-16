package org.ngmon.logger.serialize;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ngmon.logger.enums.EventLevel;

public class EventWrapper {

    @JsonProperty(required = true)
    private String schemaId;
    @JsonProperty(required = true)
    private long timestamp;
    @JsonProperty(required = true)
    private EventLevel eventLevel;
    @JsonProperty(required = true)
    private String payload;

    public EventWrapper(String schemaId, long timestamp, EventLevel eventLevel, String payload) {
        this.schemaId = schemaId;
        this.timestamp = timestamp;
        this.eventLevel = eventLevel;
        this.payload = payload;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public EventLevel getEventLevel() {
        return eventLevel;
    }

    public String getPayload() {
        return payload;
    }
}

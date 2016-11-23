package org.ngmon.logger.serialize;

import org.ngmon.logger.enums.EventLevel;

public class EventWrapper {

    private String schemaId;
    private long timestamp;
    private EventLevel eventLevel;
    private byte[] payload;

    public EventWrapper(String schemaId, long timestamp, EventLevel eventLevel, byte[] payload) {
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

    public byte[] getPayload() {
        return payload;
    }
}

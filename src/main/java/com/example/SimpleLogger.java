package com.example;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.ngmon.logger.*;
import org.ngmon.logger.common.Tuple2;
import org.ngmon.logger.enums.EventLevel;
import org.ngmon.logger.injection.LogEvent;
import org.ngmon.logger.serialize.EventWrapper;
import org.ngmon.logger.serialize.Serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleLogger implements Logger {

    private Map<String, Tuple2<Schema, Serializer<GenericRecord>>> schemaMap = new HashMap<>();
    private final Schema wrapperSchema = new ReflectData().getSchema(EventWrapper.class);
    private final Serializer<EventWrapper> mainSerializer = new Serializer<>(wrapperSchema);

    public void log(EventLevel level, LogEvent logEvent, String signature) {
        Tuple2<Schema, Serializer<GenericRecord>> tuple2 = getCachedType(signature, logEvent);
        try {
            long timestamp = System.currentTimeMillis();
            byte[] payload = tuple2.f1.serialize(logEvent.getData(tuple2.f0));
            byte[] eventWrapperBytes = this.mainSerializer.serialize(new EventWrapper(signature, timestamp, level, payload));
            System.out.println(new String(eventWrapperBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Tuple2<Schema, Serializer<GenericRecord>> getCachedType(String signature, LogEvent logEvent) {
        Tuple2<Schema, Serializer<GenericRecord>> tuple2;
        if (!this.schemaMap.containsKey(signature)) {
            Schema schema = logEvent.getSchema();
            Serializer<GenericRecord> serializer = new Serializer<>(schema);
            tuple2 = new Tuple2<>(schema, serializer);
            this.schemaMap.put(signature, tuple2);
        } else {
            tuple2 = this.schemaMap.get(signature);
        }
        return tuple2;
    }
}

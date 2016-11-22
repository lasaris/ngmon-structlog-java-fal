package com.example;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificData;
import org.ngmon.logger.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleLogger implements Logger {

    private Map<String, Schema> schemaMap = new HashMap<>();
    private final Schema wrapperSchema = new ReflectData().getSchema(EventWrapper.class);
    private final Serializer<EventWrapper> mainSerializer = new Serializer<>(wrapperSchema);

    public void log(EventLevel level, LogEvent logEvent, String signature) {
        Schema schema = getSchema(signature, logEvent);
        Serializer<GenericRecord> payloadSerializer = new Serializer<>(schema);
        try {
            long timestamp = System.currentTimeMillis();
            byte[] payload = payloadSerializer.serialize(logEvent.getData(schema));
            byte[] eventWrapperBytes = mainSerializer.serialize(new EventWrapper(signature, timestamp, level, payload));
            System.out.println(new String(eventWrapperBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Schema getSchema(String signature, LogEvent logEvent) {
        Schema schema;
        if (!this.schemaMap.containsKey(signature)) {
            schema = logEvent.getSchema();
            this.schemaMap.put(signature, schema);
            createShemaFile(signature, schema);
        } else {
            schema = this.schemaMap.get(signature);
        }
        return schema;
    }

    private void createShemaFile(String signature, Schema schema) {
        try {
            String dir = "schemas/";
            Files.createDirectories(Paths.get(dir));
            FileOutputStream out = new FileOutputStream(dir + signature + ".avsc");
            out.write(schema.toString(true).getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

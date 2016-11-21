package com.example;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.ngmon.logger.EventLevel;
import org.ngmon.logger.LogEvent;
import org.ngmon.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SimpleLogger implements Logger {

    public void log(EventLevel level, LogEvent logEvent) {
        Schema schema = logEvent.getSchema();
        GenericRecord data = logEvent.getData(schema);

        try {
            System.out.println(new String(serialize(schema, data)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    byte[] serialize(Schema schema, GenericRecord record) throws IOException {
        ReflectDatumWriter<GenericRecord> datumWriter = new ReflectDatumWriter<>(schema);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().jsonEncoder(schema, out, false);
        datumWriter.write(record, encoder);
        encoder.flush();
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }
}

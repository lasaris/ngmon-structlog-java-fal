package org.ngmon.logger;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Serializer<T> {

    private final ReflectDatumWriter<T> datumWriter;
    private final Schema schema;
    private final EncoderFactory encoderFactory = EncoderFactory.get();

    public Serializer(Schema schema) {
        this.schema = schema;
        this.datumWriter = new ReflectDatumWriter<>(schema);
    }

    public byte[] serialize(T record) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Encoder encoder = this.encoderFactory.jsonEncoder(schema, out, false);
        this.datumWriter.write(record, encoder);
        encoder.flush();
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }
}

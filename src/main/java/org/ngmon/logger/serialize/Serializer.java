package org.ngmon.logger.serialize;

import org.apache.avro.Schema;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Serializer<T> {

    private final ReflectDatumWriter<T> datumWriter;
    private final Schema schema;
    private final EncoderFactory encoderFactory = EncoderFactory.get();

    public Serializer(Schema schema) {
        this.schema = schema;
        this.datumWriter = new ReflectDatumWriter<>(schema);
        createSchemaFile();
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

    private void createSchemaFile() {
        try {
            String dir = "schemas/" + this.schema.getNamespace().replace(".", "/") + "/";
            Files.createDirectories(Paths.get(dir));
            FileOutputStream out = new FileOutputStream(dir + this.schema.getName() + ".avsc");
            out.write(this.schema.toString(true).getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

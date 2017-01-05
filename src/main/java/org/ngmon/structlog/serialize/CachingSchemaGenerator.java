package org.ngmon.structlog.serialize;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;
import org.ngmon.structlog.injection.LogEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CachingSchemaGenerator {

    private final ObjectMapper mapper = new ObjectMapper();
    private Map<String, JsonSchema> schemaMap = new HashMap<>();

    public CachingSchemaGenerator() {
        JavaType javaType = this.mapper.constructType(EventWrapper.class);
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        try {
            this.mapper.acceptJsonFormatVisitor(javaType, visitor);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        JsonSchema wrapperSchema = visitor.finalSchema();
        wrapperSchema.set$schema("http://json-schema.org/draft-03/schema#");
        createSchemaFile("wrapper", "EventWrapper", wrapperSchema);
    }

    public void cacheType(String signature, LogEvent logEvent) {
        if (!this.schemaMap.containsKey(signature)) {
            JsonSchema schema = logEvent.getSchema(signature);
            this.schemaMap.put(signature, schema);
            createSchemaFile("events", signature, schema);
        }
    }

    private void createSchemaFile(String namespace, String signature, JsonSchema schema) {
        try {
            String dir = "schemas/" + namespace.replace(".", "/") + "/";
            Files.createDirectories(Paths.get(dir));
            FileOutputStream out = new FileOutputStream(dir + signature + ".json");
            out.write(this.mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(schema));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

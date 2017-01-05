package org.ngmon.logger.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;
import org.ngmon.logger.enums.EventLevel;
import org.ngmon.logger.injection.LogEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CachingSchemaGenerator {

    private final ObjectMapper mapper = new ObjectMapper();
    private Map<String, JsonSchema> schemaMap = new HashMap<>();

    public CachingSchemaGenerator() throws JsonMappingException {
        JavaType javaType = this.mapper.constructType(EventWrapper.class);
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        this.mapper.acceptJsonFormatVisitor(javaType, visitor);
        JsonSchema wrapperSchema = visitor.finalSchema();
        wrapperSchema.set$schema("http://json-schema.org/draft-03/schema#");
        createSchemaFile("wrapper", "EventWrapper", wrapperSchema);
    }

    public String getRecord(EventLevel level, LogEvent logEvent, String signature) throws JsonProcessingException {
        JsonSchema _s = getCachedType(signature, logEvent);
        long timestamp = System.currentTimeMillis();
        String payload = this.mapper.writeValueAsString(logEvent.getValueMap());
        return this.mapper.writeValueAsString(new EventWrapper(signature, timestamp, level, payload));
    }

    private JsonSchema getCachedType(String signature, LogEvent logEvent) {
        JsonSchema schema;
        if (!this.schemaMap.containsKey(signature)) {
            schema = logEvent.getSchema();
            this.schemaMap.put(signature, schema);
            createSchemaFile("events", signature, schema);
        } else {
            schema = this.schemaMap.get(signature);
        }
        return schema;
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

package org.ngmon.logger;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LogEvent {

    private Map<String, Integer> existenceMap = new HashMap<>();
    private Map<Tuple2<String, Type>, Object> valueMap = new HashMap<>();
    private ReflectData reflectData = new ReflectData();

    void setMessage(String message) {
        this.put("message", String.class, message);
    }

    void put(String paramName, Type paramType, Object paramValue) {
        this.valueMap.put(new Tuple2<>(getName(paramName), paramType), paramValue);
    }

    String getSignature() {
        Tuple2<String, Type> key = new Tuple2<>("message", String.class);
        String message = (String) this.valueMap.get(key);
        return "Event" + hash(valueMap.entrySet()) + "_" + hash(message);
    }

    private String hash(Object o) {
        int hashCode = o.hashCode();
        if (hashCode < 0) {
            hashCode *= -1;
            return "N" + hashCode;
        } else {
            return "P" + hashCode;
        }
    }

    private String getName(String paramName) {
        Integer repetition = this.existenceMap.putIfAbsent(paramName, 2);

        if (repetition != null) {
            this.existenceMap.put(paramName, repetition + 1);
            return paramName + repetition;
        } else {
            return paramName;
        }
    }

    public Schema getSchema() {
        SchemaBuilder.FieldAssembler<Schema> fieldAssembler = SchemaBuilder.record(getSignature()).namespace("logger").fields();
        for (Tuple2<String, Type> tuple2 : this.valueMap.keySet()) {
            fieldAssembler = fieldAssembler.name(tuple2.f0).type(this.reflectData.getSchema(tuple2.f1)).noDefault();
        }
        return fieldAssembler.endRecord();
    }

    public GenericRecord getData(Schema schema) {
        GenericRecord data = new GenericData.Record(schema);
        for (Map.Entry<Tuple2<String, Type>, Object> tuple2ObjectEntry : this.valueMap.entrySet()) {
            Tuple2<String, Type> key = tuple2ObjectEntry.getKey();
            String name = key.f0;
            Object value = tuple2ObjectEntry.getValue();
            data.put(name, value);
        }
        return data;
    }

    @Override
    public String toString() {
        return getSignature() + "{" + "valueMap=" + valueMap + '}';
    }
}

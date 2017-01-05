package org.ngmon.structlog;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;
import org.ngmon.structlog.annotation.Var;
import org.ngmon.structlog.common.Tuple2;
import org.ngmon.structlog.enums.EventLevel;
import org.ngmon.structlog.injection.LogContext;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StructLog<T extends LogContext> {

    private final Logger logger;
    private final Class<T> contextClass;
    private final Map<String, Tuple2<Type, JsonSchema>> varCache = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();

    public StructLog(Class<T> contextClass, Logger logger) throws JsonMappingException {
        checkClass(contextClass);
        this.contextClass = contextClass;
        this.logger = logger;
    }

    private void checkClass(Class<T> contextClass) throws JsonMappingException {
        Method[] declaredMethods = contextClass.getDeclaredMethods();
        Set<String> sett = new HashSet<>();
        for (Method method : declaredMethods) {
            if (method.getAnnotation(Var.class) != null) {
                String methodName = method.getName();
                if (!sett.add(methodName)) {
                    throw new IllegalArgumentException(contextClass.getName() + " contains overloaded methods/variables");
                } else {
                    if (methodName.equals("message")) {
                        throw new IllegalArgumentException("Context parameter cannot be named 'message'");
                    }
                    Type type = method.getGenericParameterTypes()[0];
                    JavaType javaType = this.mapper.constructType(type);
                    this.mapper.acceptJsonFormatVisitor(javaType, this.visitor);
                    JsonSchema jsonSchema = this.visitor.finalSchema();
                    jsonSchema.setRequired(true);
                    this.varCache.put(methodName, new Tuple2<>(type, jsonSchema));
                }
            }
        }
    }

    public T debug(String message) {
        return msg(EventLevel.DEBUG, message);
    }

    public T info(String message) {
        return msg(EventLevel.INFO, message);
    }

    public T error(String message) {
        return msg(EventLevel.ERROR, message);
    }

    private T msg(EventLevel level, String message) {
        try {
            T instance = this.contextClass.newInstance();
            instance.setLevel(level);
            instance.setMessage(message);
            instance.setLogger(this.logger);
            instance.setVarCache(this.varCache);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}

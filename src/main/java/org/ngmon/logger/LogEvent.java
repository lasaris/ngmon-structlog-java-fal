package org.ngmon.logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LogEvent {

    private Map<String, Integer> existenceMap = new HashMap<>();
    private Map<Tuple2<String, Type>, Object> valueMap = new HashMap<>();
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void put(String paramName, Type paramType, Object paramValue) {
        this.valueMap.put(new Tuple2<>(getName(paramName), paramType), paramValue);
    }

    String getSignature() {
        return "Event" + String.valueOf(valueMap.entrySet().hashCode()) + "_" + message.hashCode();
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

    @Override
    public String toString() {
        return getSignature() + "{" +
                "valueMap=" + valueMap +
                ", message='" + message + '\'' +
                '}';
    }
}

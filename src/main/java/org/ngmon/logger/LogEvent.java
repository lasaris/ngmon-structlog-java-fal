package org.ngmon.logger;

import java.util.HashMap;
import java.util.Map;

public class LogEvent {

    private Map<String, Integer> existenceMap = new HashMap<>();
    private Map<Tuple2<String, Class>, Object> valueMap = new HashMap<>();
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void put(String paramName, Class paramClass, Object paramValue) {
        this.valueMap.put(new Tuple2<>(getName(paramName), paramClass), paramValue);
    }

    String getSignature() {
        final String s = "Event" + String.valueOf(valueMap.entrySet().hashCode()) + "_" + message.hashCode();
        return s;
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

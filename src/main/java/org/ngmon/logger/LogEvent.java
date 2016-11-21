package org.ngmon.logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LogEvent {

    private Map<String, Integer> existenceMap = new HashMap<>();
    private Map<Tuple2<String, Type>, Object> valueMap = new HashMap<>();

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

    @Override
    public String toString() {
        return getSignature() + "{" + "valueMap=" + valueMap + '}';
    }
}

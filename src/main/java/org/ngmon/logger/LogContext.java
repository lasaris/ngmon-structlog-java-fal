package org.ngmon.logger;

import java.util.HashMap;
import java.util.Map;

public abstract class LogContext {

    private Map<String, Integer> existenceMap = new HashMap<>();
    private Map<String, Object> valueMap = new HashMap<>();
    private String message;
    private String level;
    private Logger logger;

    public void log() {
        this.valueMap.put("event", message);
        this.valueMap.put("level", level);
        this.logger.log(this.valueMap);
    }

    void setLevel(String level) {
        this.level = level;
    }

    void setMessage(String message) {
        this.message = message;
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }

    void inject(String methodName, String[] parameterNames, Object[] args) {
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Object argValue = args[i];
            this.valueMap.put(parameterName, argValue);
        }
    }
}

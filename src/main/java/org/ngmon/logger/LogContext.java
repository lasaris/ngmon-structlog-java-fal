package org.ngmon.logger;

import java.lang.reflect.Type;

public abstract class LogContext {

    private LogEvent logEvent = new LogEvent();
    private Logger logger;
    private EventLevel level;


    public void log() {
        this.logger.log(this.level, this.logEvent);
    }

    void setLevel(EventLevel level) {
        this.level = level;
    }

    void setMessage(String message) {
        this.logEvent.setMessage(message);
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }

    void inject(String paramName, Type paramType, Object paramValue) {
        if (paramName.equals("message")) {
            throw new IllegalArgumentException("Context parameter cannot be named 'message'");
        }

        this.logEvent.put(paramName, paramType, paramValue);
    }
}

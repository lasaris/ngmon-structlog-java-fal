package org.ngmon.logger.injection;

import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;
import org.ngmon.logger.common.Tuple2;
import org.ngmon.logger.enums.EventLevel;
import org.ngmon.logger.Logger;

import java.lang.reflect.Type;
import java.util.Map;

public abstract class LogContext {

    private LogEvent logEvent = new LogEvent();
    private Logger logger;
    private EventLevel level;
    private Map<String, Tuple2<Type, JsonSchema>> varCache;

    public void log() {
        this.logger.log(this.level, this.logEvent, this.logEvent.getSignature());
    }

    public void setLevel(EventLevel level) {
        this.level = level;
    }

    public void setMessage(String message) {
        this.logEvent.setMessage(message);
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setVarCache(Map<String, Tuple2<Type, JsonSchema>> varCache) {
        this.varCache = varCache;
    }

    protected void inject(String paramName, Object paramValue) {
        Tuple2<Type, JsonSchema> tuple2 = varCache.get(paramName);
        this.logEvent.put(paramName, tuple2.f0, tuple2.f1, paramValue);
    }
}

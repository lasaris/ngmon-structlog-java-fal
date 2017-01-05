package org.ngmon.structlog;

import org.ngmon.structlog.enums.EventLevel;
import org.ngmon.structlog.injection.LogEvent;

public interface Logger {

    public void log(EventLevel level, LogEvent logEvent, String signature);
}

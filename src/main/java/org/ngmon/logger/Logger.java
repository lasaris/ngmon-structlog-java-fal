package org.ngmon.logger;

import org.ngmon.logger.enums.EventLevel;
import org.ngmon.logger.injection.LogEvent;

public interface Logger {

    public void log(EventLevel level, LogEvent logEvent, String signature);
}

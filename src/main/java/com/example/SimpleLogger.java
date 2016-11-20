package com.example;

import org.ngmon.logger.EventLevel;
import org.ngmon.logger.LogEvent;
import org.ngmon.logger.Logger;

public class SimpleLogger implements Logger {

	public void log(EventLevel level, LogEvent dataMap) {
		System.err.println(level + " :: " + dataMap);
	}
}

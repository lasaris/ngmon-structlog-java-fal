package com.example;

import org.ngmon.logger.Logger;

import java.util.Map;

public class SimpleLogger implements Logger {

	public void log(Map<String, Object> dataMap) {
		System.err.println(dataMap);
	}
}

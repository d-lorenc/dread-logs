package com.naked.logs.log4j.inmemory;

import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jInMemoryLogger {
	
    private final Log4jInMemoryAppender appender;
	private final Logger logger;
    
    public Log4jInMemoryLogger(String loggerName) {
    	this(loggerName, Level.ALL);
    }

    public Log4jInMemoryLogger(String loggerName, Level level) {
    	this(loggerName, level, new Log4jSimpleLayout());
	}
    
	Log4jInMemoryLogger(String loggerName, Level level, Layout layout) {
		this(Logger.getLogger(loggerName), new Log4jInMemoryAppender(layout), level);
    }
	
	Log4jInMemoryLogger(Logger logger, Log4jInMemoryAppender appender, Level level) {
		this.logger = logger;
		this.appender = appender;
		
		logger.setLevel(level);
        logger.addAppender(appender);
	}

    public void enableStdOutLogging() {
        appender.enableStdOutLogging();
    }

    public void removeAppender() {
        logger.removeAppender(appender);
    }

	public List<LoggingEvent> getEvents() {
		return appender.getCopyOfEvents();
	}
	
	@Override
	public String toString() {
		return appender.toString();
	}
	
}
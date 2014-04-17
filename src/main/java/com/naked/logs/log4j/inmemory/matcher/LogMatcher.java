package com.naked.logs.log4j.inmemory.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.hamcrest.TypeSafeMatcher;

import com.naked.logs.log4j.inmemory.Log4jInMemoryLogger;

public abstract class LogMatcher extends TypeSafeMatcher<Log4jInMemoryLogger> {

	private static final String EMPTY_VALUE = "[...]";
	
	private Level expectedLevel = Level.ALL;
	private String expectedLoggerName;
	private String expectedExceptionMessage;
	private Class<? extends Throwable> expectedExceptionClass;
	private Throwable expectedException;
	
	private final Map<String, String> expectedMdcs = new HashMap<String, String>();

	@Override
	protected boolean matchesSafely(Log4jInMemoryLogger inMemoryLog4jLogger) {
		for (LoggingEvent event : inMemoryLog4jLogger.getEvents()) {
			if (eventHasCorrectLevel(event) 
					&& matchesMessage(event)
					&& matchesLoggerName(event)
					&& containsExceptionMessage(event)
					&& containsExceptionClass(event)
					&& containsException(event)
					&& containsMdc(event)) {
				return true;
			}
		}
		return false;
	}

	protected abstract boolean matchesMessage(LoggingEvent event);

    protected String defaultIfNull(Object value) {
    	if (value == null) {
    		return EMPTY_VALUE; 
    	}
		return value.toString();
	}
	
	
	private boolean containsMdc(LoggingEvent event) {
		if (expectedMdcs.isEmpty()) {
			return true;
		}
		
		for (Entry<String, String> mdc : expectedMdcs.entrySet()) {
			Object mdcValue = event.getMDC(mdc.getKey());
			if ((mdc.getValue() !=  mdcValue) || (mdcValue != null && !mdcValue.equals(mdc.getValue()))) {
				 return false;
			}
		}
		return true;
	}

	private boolean containsExceptionMessage(LoggingEvent event) {
		if (expectedExceptionMessage == null) {
			return true;
		}
		
        ThrowableInformation throwableInfo = event.getThrowableInformation();
        if (throwableInfo != null && throwableInfo.getThrowable().getMessage().contains(expectedExceptionMessage)) {
        	return true;
        }
        
		return false;
	}
	
	private boolean containsExceptionClass(LoggingEvent event) {
		if (expectedExceptionClass == null) {
			return true;
		}
		
        ThrowableInformation throwableInfo = event.getThrowableInformation();
        if (throwableInfo != null && throwableInfo.getThrowable().getClass().equals(expectedExceptionClass)) {
        	return true;
        }
        
		return false;
	}

	private boolean containsException(LoggingEvent event) {
		if (expectedException == null) {
			return true;
		}
		
        ThrowableInformation throwableInfo = event.getThrowableInformation();
        if (throwableInfo != null && throwableInfo.getThrowable().equals(expectedException)) {
        	return true;
        }
        
		return false;
	}
	
	private boolean matchesLoggerName(LoggingEvent event) {
		if (expectedLoggerName == null) {
			return true;
		}
		return event.getLoggerName().equals(expectedLoggerName);
	}

	private boolean eventHasCorrectLevel(LoggingEvent event) {
        return (expectedLevel.equals(Level.ALL) || event.getLevel().equals(expectedLevel));
    }

	public LogMatcher onLevel(Level expectedLevel) {
		this.expectedLevel = expectedLevel;
		return this;
	}

	public LogMatcher withLoggerName(String expectedLoggerName) {
		this.expectedLoggerName = expectedLoggerName;
		return this;
	}

	public LogMatcher withException(String expectedExceptionMessage) {
		this.expectedExceptionMessage = expectedExceptionMessage;
		return this;
	}

	public LogMatcher withException(Class<? extends Throwable> expectedExceptionClass) {
		this.expectedExceptionClass = expectedExceptionClass;
		return this;
	}
	
	public LogMatcher withException(Throwable expectedException) {
		this.expectedException = expectedException;
		return this;
	}

	public LogMatcher withMdc(String expectedMdcKey, String expectedMdcValue) {
		expectedMdcs.put(expectedMdcKey, expectedMdcValue);
		return this;
	}
	
	protected Level getExpectedLevel() {
		return expectedLevel;
	}
	
	protected String getExpectedLoggerName() {
		return expectedLoggerName;
	}
	
}
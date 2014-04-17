package com.naked.logs.log4j.inmemory.matcher;

import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.Description;

public class Log4jHasLogMatcher extends Log4jLogMatcher {

	private final String expectedMessage;
	
	public Log4jHasLogMatcher(String expectedMessage) {
		this.expectedMessage = expectedMessage;
	}
	
	@Override
	protected boolean matchesMessage(LoggingEvent event) {
		return containsMessage(event);
	}
	
	private boolean containsMessage(LoggingEvent event) {
		return event.getMessage().toString().contains(expectedMessage);
	}

	public void describeTo(Description description) {
		description.appendValue(String.format("<%s %s %s>", defaultIfNull(getExpectedLevel()), defaultIfNull(getExpectedLoggerName()), expectedMessage));
	}

}
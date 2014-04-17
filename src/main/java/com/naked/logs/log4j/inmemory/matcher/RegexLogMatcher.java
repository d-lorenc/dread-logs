package com.naked.logs.log4j.inmemory.matcher;

import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.Description;

public class RegexLogMatcher extends LogMatcher {

	private final String messageRegex;

	public RegexLogMatcher(String messageRegex) {
		this.messageRegex = messageRegex;
	}
	
	@Override
	protected boolean matchesMessage(LoggingEvent event) {
		return matchesMessageRegex(event);
	}
	
	private boolean matchesMessageRegex(LoggingEvent event) {
		return event.getMessage().toString().matches(messageRegex);
	}

	public void describeTo(Description description) {
		description.appendValue(String.format("<%s %s [matches: %s]>", defaultIfNull(getExpectedLevel()), defaultIfNull(getExpectedLoggerName()), messageRegex));
	}

}

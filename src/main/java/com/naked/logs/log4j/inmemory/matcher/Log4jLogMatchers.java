package com.naked.logs.log4j.inmemory.matcher;


public class Log4jLogMatchers {
	
	private Log4jLogMatchers() {
	}

	public static Log4jHasLogMatcher hasLog(String expectedMessage) {
		return new Log4jHasLogMatcher(expectedMessage);
	}

	public static Log4jNoLogMatcher noLog(String unwantedMessage) {
		return new Log4jNoLogMatcher(unwantedMessage);
	}

	public static Log4jRegexLogMatcher hasLogMatching(String messageRegex) {
		return new Log4jRegexLogMatcher(messageRegex);
	}
	
}

package com.naked.logs.log4j.inmemory.matcher;


public class LogMatchers {
	
	private LogMatchers() {
	}

	public static HasLogMatcher hasLog(String expectedMessage) {
		return new HasLogMatcher(expectedMessage);
	}

	public static NoLogMatcher noLog(String unwantedMessage) {
		return new NoLogMatcher(unwantedMessage);
	}

	public static RegexLogMatcher hasLogMatching(String messageRegex) {
		return new RegexLogMatcher(messageRegex);
	}
	
}

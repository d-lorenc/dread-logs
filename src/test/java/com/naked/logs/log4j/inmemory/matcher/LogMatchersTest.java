package com.naked.logs.log4j.inmemory.matcher;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class LogMatchersTest {

	@Test
	public void shouldCreateHasLogMatcher() throws Exception {
		HasLogMatcher hasLog = LogMatchers.hasLog("expected message");
		
		assertNotNull(hasLog);
	}
	
	@Test
	public void shouldCreateNoLogMatcher() throws Exception {
		NoLogMatcher noLog = LogMatchers.noLog("unwanted message");
		
		assertNotNull(noLog);
	}
	
	@Test
	public void shouldCreateRegexLogMatcher() throws Exception {
		RegexLogMatcher regexLog = LogMatchers.hasLogMatching("regex");
		
		assertNotNull(regexLog);
	}
	
}

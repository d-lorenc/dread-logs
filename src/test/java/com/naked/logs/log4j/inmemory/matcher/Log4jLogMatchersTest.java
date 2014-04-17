package com.naked.logs.log4j.inmemory.matcher;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class Log4jLogMatchersTest {

    @Test
    public void shouldCreateHasLogMatcher() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jLogMatchers.hasLog("expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateNoLogMatcher() throws Exception {
        Log4jNoLogMatcher noLog = Log4jLogMatchers.noLog("unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateRegexLogMatcher() throws Exception {
        Log4jRegexLogMatcher regexLog = Log4jLogMatchers.hasLogMatching("regex");

        assertNotNull(regexLog);
    }

}

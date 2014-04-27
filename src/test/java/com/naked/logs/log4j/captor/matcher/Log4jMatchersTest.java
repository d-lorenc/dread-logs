package com.naked.logs.log4j.captor.matcher;

import static org.apache.log4j.Level.DEBUG;
import static org.apache.log4j.Level.INFO;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class Log4jMatchersTest {

    @Test
    public void shouldCreateHasLogMatcher() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog("expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithLevel() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog(INFO, "expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithStringMatcher() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog(containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithLevelANdStringMatcher() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog(INFO, containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateNoLogMatcher() throws Exception {
        Log4jNoLogMatcher noLog = Log4jMatchers.noLog("unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherWithLevel() throws Exception {
        Log4jNoLogMatcher noLog = Log4jMatchers.noLog(DEBUG, "unwanted message");

        assertNotNull(noLog);
    }

}

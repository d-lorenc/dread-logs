package com.naked.logs.captor.logback;

import static ch.qos.logback.classic.Level.DEBUG;
import static ch.qos.logback.classic.Level.INFO;
import static ch.qos.logback.classic.Level.TRACE;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LogbackMatchersTest {

    @Test
    public void shouldCreateHasLogMatcher() throws Exception {
        LogbackHasLogMatcher hasLog = LogbackMatchers.hasLog("expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithLevel() throws Exception {
        LogbackHasLogMatcher hasLog = LogbackMatchers.hasLog(INFO, "expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithStringMatcher() throws Exception {
        LogbackHasLogMatcher hasLog = LogbackMatchers.hasLog(containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithLevelANdStringMatcher() throws Exception {
        LogbackHasLogMatcher hasLog = LogbackMatchers.hasLog(INFO, containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateNoLogMatcher() throws Exception {
        LogbackNoLogMatcher noLog = LogbackMatchers.noLog("unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherWithLevel() throws Exception {
        LogbackNoLogMatcher noLog = LogbackMatchers.noLog(DEBUG, "unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForStringMatcher() throws Exception {
        LogbackNoLogMatcher noLog = LogbackMatchers.noLog(containsString("message"));

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForLevelAndStringMatcher() throws Exception {
        LogbackNoLogMatcher noLog = LogbackMatchers.noLog(TRACE, containsString("message"));

        assertNotNull(noLog);
    }


}
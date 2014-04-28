package com.naked.logs.jul.captor.matcher;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class JulMatchersTest {

    @Test
    public void shouldCreateHasLogMatcherForMessage() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog("expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherForLevelAndMessage() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog(INFO, "expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherForStringMatcher() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog(containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherForLevelAndStringMatcher() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog(INFO, containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForMessage() throws Exception {
        JulNoLogMatcher noLog = JulMatchers.noLog("unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForLevelAndMessage() throws Exception {
        JulNoLogMatcher noLog = JulMatchers.noLog(FINE, "unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForStringMatcher() throws Exception {
        JulNoLogMatcher noLog = JulMatchers.noLog(containsString("message"));

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForLevelAndStringMatcher() throws Exception {
        JulNoLogMatcher noLog = JulMatchers.noLog(FINE, containsString("message"));

        assertNotNull(noLog);
    }

}


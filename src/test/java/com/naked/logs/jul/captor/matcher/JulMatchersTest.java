package com.naked.logs.jul.captor.matcher;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class JulMatchersTest {

    @Test
    public void shouldCreateHasLogMatcher() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog("expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithLevel() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog(INFO, "expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithStringMatcher() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog(containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherWithLevelANdStringMatcher() throws Exception {
        JulHasLogMatcher hasLog = JulMatchers.hasLog(INFO, containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateNoLogMatcher() throws Exception {
        JulNoLogMatcher noLog = JulMatchers.noLog("unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherWithLevel() throws Exception {
        JulNoLogMatcher noLog = JulMatchers.noLog(FINE, "unwanted message");

        assertNotNull(noLog);
    }

}

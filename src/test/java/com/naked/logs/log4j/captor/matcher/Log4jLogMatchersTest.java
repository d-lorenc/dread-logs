package com.naked.logs.log4j.captor.matcher;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.naked.logs.log4j.captor.matcher.Log4jHasLogMatcher;
import com.naked.logs.log4j.captor.matcher.Log4jLogMatchers;
import com.naked.logs.log4j.captor.matcher.Log4jNoLogMatcher;
import com.naked.logs.log4j.captor.matcher.Log4jRegexLogMatcher;

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

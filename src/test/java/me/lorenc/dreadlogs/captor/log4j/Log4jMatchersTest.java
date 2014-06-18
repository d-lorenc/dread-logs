package me.lorenc.dreadlogs.captor.log4j;

import static org.apache.log4j.Level.DEBUG;
import static org.apache.log4j.Level.INFO;
import static org.apache.log4j.Level.TRACE;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import me.lorenc.dreadlogs.captor.log4j.Log4jHasLogMatcher;
import me.lorenc.dreadlogs.captor.log4j.Log4jMatchers;
import me.lorenc.dreadlogs.captor.log4j.Log4jNoLogMatcher;

import org.junit.Test;

public class Log4jMatchersTest {

    @Test
    public void shouldCreateHasLogMatcherForMessage() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog("expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherForLevelAndMessage() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog(INFO, "expected message");

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherForStringMatcher() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog(containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateHasLogMatcherForLevelAndStringMatcher() throws Exception {
        Log4jHasLogMatcher hasLog = Log4jMatchers.hasLog(INFO, containsString("message"));

        assertNotNull(hasLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForMessage() throws Exception {
        Log4jNoLogMatcher noLog = Log4jMatchers.noLog("unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForLevelAndMessage() throws Exception {
        Log4jNoLogMatcher noLog = Log4jMatchers.noLog(DEBUG, "unwanted message");

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForStringMatcher() throws Exception {
        Log4jNoLogMatcher noLog = Log4jMatchers.noLog(containsString("message"));

        assertNotNull(noLog);
    }

    @Test
    public void shouldCreateNoLogMatcherForLevelAndStringMatcher() throws Exception {
        Log4jNoLogMatcher noLog = Log4jMatchers.noLog(TRACE, containsString("message"));

        assertNotNull(noLog);
    }

}

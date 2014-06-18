package me.lorenc.dreadlogs.captor.log4j.functional;

import static me.lorenc.dreadlogs.captor.log4j.Log4jMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.log4j.Log4jMatchers.noLog;
import static org.apache.log4j.Level.*;
import static org.junit.Assert.assertThat;
import me.lorenc.dreadlogs.captor.log4j.Log4jCaptor;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Log4jCaptorLogLevelFilteringFunctionalTest {

    private static final String LOGGER_NAME = "me.lorenc.dreadlogs.some.package";

    private Logger logger;
    private Log4jCaptor captor;

    @Before
    public void before() throws Exception {
        logger = Logger.getLogger(LOGGER_NAME);
        captor = new Log4jCaptor(LOGGER_NAME);
    }

    @After
    public void after() throws Exception {
        captor.detachAppender();
    }

    @Test
    public void shouldCaptureFatalLog() throws Exception {

        logger.fatal("fatal message");

        assertThat(captor, hasLog(FATAL, "fatal message"));
    }

    @Test
    public void shouldCaptureErrorLog() throws Exception {

        logger.error("error message");

        assertThat(captor, hasLog(ERROR, "error message"));
    }

    @Test
    public void shouldCaptureWarnLog() throws Exception {

        logger.warn("warn message");

        assertThat(captor, hasLog(WARN, "warn message"));
    }

    @Test
    public void shouldCaptureInfoLog() throws Exception {

        logger.info("info message");

        assertThat(captor, hasLog(INFO, "info message"));
    }

    @Test
    public void shouldCaptureDebugLog() throws Exception {

        logger.debug("debug message");

        assertThat(captor, hasLog(DEBUG, "debug message"));
    }

    @Test
    public void shouldCaptureTraceLog() throws Exception {

        logger.trace("trace message");

        assertThat(captor, hasLog(TRACE, "trace message"));
    }

    @Test
    public void shouldNotFindLogsOnOtherLevels() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog(INFO, "a message"));

        assertThat(captor, noLog(FATAL, "a message"));
        assertThat(captor, noLog(ERROR, "a message"));
        assertThat(captor, noLog(WARN, "a message"));
        assertThat(captor, noLog(DEBUG, "a message"));
        assertThat(captor, noLog(TRACE, "a message"));
    }

    @Test
    public void shouldNotCaptureFatalLogIfLevelDisabled() throws Exception {
        Log4jCaptor captor = new Log4jCaptor(LOGGER_NAME, OFF);

        logger.fatal("fatal message");

        assertThat(captor, noLog("fatal message"));
    }

    @Test
    public void shouldNotCaptureErrorLogIfLevelDisabled() throws Exception {
        Log4jCaptor captor = new Log4jCaptor(LOGGER_NAME, FATAL);

        logger.error("error message");

        assertThat(captor, noLog("error message"));
    }

    @Test
    public void shouldNotCaptureInfoLogIfLevelDisabled() throws Exception {
        Log4jCaptor captor = new Log4jCaptor(LOGGER_NAME, WARN);

        logger.info("info message");

        assertThat(captor, noLog("info message"));
    }

    @Test
    public void shouldNotCaptureDebugLogIfLevelDisabled() throws Exception {
        Log4jCaptor captor = new Log4jCaptor(LOGGER_NAME, INFO);

        logger.debug("debug message");

        assertThat(captor, noLog("debug message"));
    }

    @Test
    public void shouldNotCaptureTraceLogIfLevelDisabled() throws Exception {
        Log4jCaptor captor = new Log4jCaptor(LOGGER_NAME, DEBUG);

        logger.trace("trace message");

        assertThat(captor, noLog("trace message"));
    }

}

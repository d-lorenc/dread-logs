package com.naked.logs.log4j.functional;

import static com.naked.logs.log4j.captor.matcher.Log4jMatchers.hasLog;
import static com.naked.logs.log4j.captor.matcher.Log4jMatchers.noLog;
import static com.naked.logs.matcher.PatternMatcher.matches;
import static org.apache.log4j.Level.*;
import static org.junit.Assert.assertThat;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.naked.logs.log4j.captor.Log4jCaptor;

public class Log4jCaptorFunctionalTest {

    private static final String LOGGER_NAME = "com.naked.logs.some.package";

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
    public void shouldCaptureFatalLogWithException() throws Exception {

        logger.fatal("fatal message", new Throwable("an exception"));

        assertThat(captor, hasLog(FATAL, "fatal message").withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureErrorLog() throws Exception {

        logger.error("error message");

        assertThat(captor, hasLog(ERROR, "error message"));
    }

    @Test
    public void shouldCaptureErrorLogWithException() throws Exception {

        logger.error("error message", new Throwable("an exception"));

        assertThat(captor, hasLog(ERROR, "error message").withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureWarnLog() throws Exception {

        logger.warn("warn message");

        assertThat(captor, hasLog(WARN, "warn message"));
    }

    @Test
    public void shouldCaptureWarnLogWithException() throws Exception {

        logger.warn("warn message", new Throwable("an exception"));

        assertThat(captor, hasLog(WARN, "warn message").withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureInfoLog() throws Exception {

        logger.info("info message");

        assertThat(captor, hasLog(INFO, "info message"));
    }

    @Test
    public void shouldCaptureInfoLogWithException() throws Exception {

        logger.info("info message", new Throwable("an exception"));

        assertThat(captor, hasLog(INFO, "info message").withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureDebugLog() throws Exception {

        logger.debug("debug message");

        assertThat(captor, hasLog(DEBUG, "debug message"));
    }

    @Test
    public void shouldCaptureDebugLogWithException() throws Exception {

        logger.debug("debug message", new Throwable("an exception"));

        assertThat(captor, hasLog(DEBUG, "debug message").withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureTraceLog() throws Exception {

        logger.trace("trace message");

        assertThat(captor, hasLog(TRACE, "trace message"));
    }

    @Test
    public void shouldCaptureTraceLogWithException() throws Exception {

        logger.trace("trace message", new Throwable("an exception"));

        assertThat(captor, hasLog(TRACE, "trace message").withExceptionMessage("an exception"));
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

    @Test
    public void shouldCaptureLogWithCorrectMessage() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldCaptureLogWithCorrectLevel() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog(INFO, "a message"));
    }

    @Test
    public void shouldCaptureLogWithCorrectLoggerName() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog("a message").withLoggerName(LOGGER_NAME));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionMessage() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionClass(IllegalArgumentException.class).withExceptionMessage("illegal argument"));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClass() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionClass(IllegalArgumentException.class));
    }

    @Test
    public void shouldCaptureLogWithCorrectMdcValue() throws Exception {

        MDC.put("key", "value");
        logger.info("a message");
        MDC.remove("key");

        assertThat(captor, hasLog("a message").withMdc("key", "value"));
    }

    @Test
    public void shouldCaptureLogWithCorrectMdcValues() throws Exception {

        MDC.put("key1", "one");
        MDC.put("key2", "two");
        logger.info("a message");
        MDC.remove("key1");
        MDC.remove("key2");

        assertThat(captor, hasLog("a message").withMdc("key1", "one").withMdc("key2", "two"));
    }

    @Test
    public void shouldNotFindLogsOnDifferentLevels() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog(INFO, "a message"));

        assertThat(captor, noLog(FATAL, "a message"));
        assertThat(captor, noLog(ERROR, "a message"));
        assertThat(captor, noLog(WARN, "a message"));
        assertThat(captor, noLog(DEBUG, "a message"));
        assertThat(captor, noLog(TRACE, "a message"));
    }

    @Test
    public void shouldCaptureLogWithMatchingMessage() throws Exception {

        logger.info("a regex message");

        assertThat(captor, hasLog(matches("a regex message")));
        assertThat(captor, hasLog(matches("^a regex message$")));
        assertThat(captor, hasLog(matches("^a [a-z]* message$")));
    }

}

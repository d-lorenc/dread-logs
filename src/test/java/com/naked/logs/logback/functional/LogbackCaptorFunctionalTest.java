package com.naked.logs.logback.functional;

import static ch.qos.logback.classic.Level.*;
import static com.naked.logs.logback.captor.matcher.LogbackLogMatchers.hasLog;
import static com.naked.logs.logback.captor.matcher.LogbackLogMatchers.hasLogMatching;
import static com.naked.logs.logback.captor.matcher.LogbackLogMatchers.noLog;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import com.naked.logs.logback.captor.LogbackCaptor;

public class LogbackCaptorFunctionalTest {

    private static final String LOGGER_NAME = "com.naked.logs.some.package";
    private Logger logger;
    private LogbackCaptor captor;

    @Before
    public void before() throws Exception {
        logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        captor = new LogbackCaptor(LOGGER_NAME);
    }

    @After
    public void after() throws Exception {
        captor.detachAppender();
    }

    @Test
    public void shouldCaptureErrorLog() throws Exception {

        logger.error("error message");

        assertThat(captor, hasLog("error message").onLevel(ERROR));
    }

    @Test
    public void shouldCaptureErrorLogWithException() throws Exception {

        logger.error("error message", new Throwable("an exception"));

        assertThat(captor, hasLog("error message").onLevel(ERROR).withException("an exception"));
    }

    @Test
    public void shouldCaptureWarnLog() throws Exception {

        logger.warn("warn message");

        assertThat(captor, hasLog("warn message").onLevel(WARN));
    }

    @Test
    public void shouldCaptureWarnLogWithException() throws Exception {

        logger.warn("warn message", new Throwable("an exception"));

        assertThat(captor, hasLog("warn message").onLevel(WARN).withException("an exception"));
    }

    @Test
    public void shouldCaptureInfoLog() throws Exception {

        logger.info("info message");

        assertThat(captor, hasLog("info message").onLevel(INFO));
    }

    @Test
    public void shouldCaptureInfoLogWithException() throws Exception {

        logger.info("info message", new Throwable("an exception"));

        assertThat(captor, hasLog("info message").onLevel(INFO).withException("an exception"));
    }

    @Test
    public void shouldCaptureDebugLog() throws Exception {

        logger.debug("debug message");

        assertThat(captor, hasLog("debug message").onLevel(DEBUG));
    }

    @Test
    public void shouldCaptureDebugLogWithException() throws Exception {

        logger.debug("debug message", new Throwable("an exception"));

        assertThat(captor, hasLog("debug message").onLevel(DEBUG).withException("an exception"));
    }

    @Test
    public void shouldCaptureTraceLog() throws Exception {

        logger.trace("trace message");

        assertThat(captor, hasLog("trace message").onLevel(TRACE));
    }

    @Test
    public void shouldCaptureTraceLogWithException() throws Exception {

        logger.trace("trace message", new Throwable("an exception"));

        assertThat(captor, hasLog("trace message").onLevel(TRACE).withException("an exception"));
    }

    @Test
    public void shouldNotCaptureErrorLogIfLevelDisabled() throws Exception {
        LogbackCaptor captor = new LogbackCaptor(LOGGER_NAME, Level.OFF);

        logger.error("error message");

        assertThat(captor, noLog("error message"));
    }

    @Test
    public void shouldNotCaptureInfoLogIfLevelDisabled() throws Exception {
        LogbackCaptor captor = new LogbackCaptor(LOGGER_NAME, Level.WARN);

        logger.info("info message");

        assertThat(captor, noLog("info message"));
    }

    @Test
    public void shouldNotCaptureDebugLogIfLevelDisabled() throws Exception {
        LogbackCaptor captor = new LogbackCaptor(LOGGER_NAME, Level.INFO);

        logger.debug("debug message");

        assertThat(captor, noLog("debug message"));
    }

    @Test
    public void shouldNotCaptureTraceLogIfLevelDisabled() throws Exception {
        LogbackCaptor captor = new LogbackCaptor(LOGGER_NAME, Level.DEBUG);

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

        assertThat(captor, hasLog("a message").onLevel(INFO));
    }

    @Test
    public void shouldCaptureLogWithCorrectLoggerName() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog("a message").withLoggerName(LOGGER_NAME));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionMessage() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withException(IllegalArgumentException.class).withException("illegal argument"));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClass() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withException(IllegalArgumentException.class));
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

        assertThat(captor, hasLog("a message").onLevel(INFO));

        assertThat(captor, noLog("a message").onLevel(ERROR));
        assertThat(captor, noLog("a message").onLevel(WARN));
        assertThat(captor, noLog("a message").onLevel(DEBUG));
        assertThat(captor, noLog("a message").onLevel(TRACE));
    }

    @Test
    public void shouldCaptureLogWithMatchingMessage() throws Exception {

        logger.info("a regex message");

        assertThat(captor, hasLogMatching("a regex message"));
        assertThat(captor, hasLogMatching("^a regex message$"));
        assertThat(captor, hasLogMatching("^a [a-z]* message$"));
    }

}

package com.naked.logs.captor.logback.functional;

import static ch.qos.logback.classic.Level.*;
import static com.naked.logs.captor.PatternMatcher.matches;
import static com.naked.logs.captor.logback.LogbackMatchers.hasLog;
import static com.naked.logs.captor.logback.LogbackMatchers.noLog;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import com.naked.logs.captor.logback.LogbackCaptor;

public class LogbackCaptorLogLevelFilteringFunctionalTest {

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
    public void shouldCaptureWarnLog() throws Exception {

        logger.warn("warn message");

        assertThat(captor, hasLog("warn message").onLevel(WARN));
    }

    @Test
    public void shouldCaptureInfoLog() throws Exception {

        logger.info("info message");

        assertThat(captor, hasLog("info message").onLevel(INFO));
    }

    @Test
    public void shouldCaptureDebugLog() throws Exception {

        logger.debug("debug message");

        assertThat(captor, hasLog("debug message").onLevel(DEBUG));
    }

    @Test
    public void shouldCaptureTraceLog() throws Exception {

        logger.trace("trace message");

        assertThat(captor, hasLog("trace message").onLevel(TRACE));
    }

    @Test
    public void shouldCaptureLogWithMatchingMessage() throws Exception {

        logger.info("a regex message");

        assertThat(captor, hasLog(matches("a regex message")));
        assertThat(captor, hasLog(matches("^a regex message$")));
        assertThat(captor, hasLog(matches("^a [a-z]* message$")));
    }

    @Test
    public void shouldNotFindLogsOnOtherLevels() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog("a message").onLevel(INFO));

        assertThat(captor, noLog("a message").onLevel(ERROR));
        assertThat(captor, noLog("a message").onLevel(WARN));
        assertThat(captor, noLog("a message").onLevel(DEBUG));
        assertThat(captor, noLog("a message").onLevel(TRACE));
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

}

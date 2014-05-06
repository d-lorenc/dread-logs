package com.naked.logs.captor.logback.functional;

import static ch.qos.logback.classic.Level.INFO;
import static com.naked.logs.captor.logback.LogbackMatchers.hasLog;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.naked.logs.captor.logback.LogbackCaptor;

public class LogbackCaptorHasLogFunctionalTest {

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

        assertThat(captor, hasLog("a message").withExceptionMessage("illegal argument"));
    }

    @Test
    public void shouldCaptureLogWithExceptionMessageMatchingMatcher() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionMessage(containsString("illegal")));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClass() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionClass(IllegalArgumentException.class));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClassAndMessage() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withException(IllegalArgumentException.class, "illegal argument"));
    }

    @Test
    public void shouldCaptureLogWithExceptionClassAndMessageMatchingMatcher() throws Exception {

        logger.error("a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withException(IllegalArgumentException.class, containsString("illegal")));
    }

    @Test
    public void shouldCaptureLogWithMessageMatchingMatcher() throws Exception {

        logger.info("a matcher message");

        assertThat(captor, hasLog(containsString("matcher")));
    }

}

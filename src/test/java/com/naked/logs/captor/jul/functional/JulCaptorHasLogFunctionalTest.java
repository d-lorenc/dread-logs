package com.naked.logs.captor.jul.functional;

import static com.naked.logs.captor.PatternMatcher.matches;
import static com.naked.logs.captor.jul.JulMatchers.hasLog;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.naked.logs.captor.jul.JulCaptor;

public class JulCaptorHasLogFunctionalTest {

    private static final String LOGGER_NAME = "com.naked.logs.some.package";

    private Logger logger;
    private JulCaptor captor;

    @Before
    public void before() throws Exception {
        logger = Logger.getLogger(LOGGER_NAME);
        captor = new JulCaptor(LOGGER_NAME);
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

        logger.log(SEVERE, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionMessage("illegal argument"));
    }

    @Test
    public void shouldCaptureLogWithExceptionMessageMatchingMatcher() throws Exception {

        logger.log(SEVERE, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionMessage(containsString("illegal")));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClass() throws Exception {

        logger.log(SEVERE, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionClass(IllegalArgumentException.class));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClassAndMessage() throws Exception {

        logger.log(SEVERE, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withException(IllegalArgumentException.class, "illegal argument"));
    }

    @Test
    public void shouldCaptureLogWithExceptionClassAndMessageMatchingMatcher() throws Exception {

        logger.log(SEVERE, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withException(IllegalArgumentException.class, containsString("illegal")));
    }

    @Test
    public void shouldCaptureLogWithMessageMatchingRegex() throws Exception {

        logger.info("a regex message");

        assertThat(captor, hasLog(matches("a regex message")));
        assertThat(captor, hasLog(matches("^a regex message$")));
        assertThat(captor, hasLog(matches("^a [a-z]* message$")));
    }

    @Test
    public void shouldCaptureLogWithMessageMatchingMatcher() throws Exception {

        logger.info("a matcher message");

        assertThat(captor, hasLog(containsString("matcher")));
    }

}

package me.lorenc.dreadlogs.captor.log4j.functional;

import static me.lorenc.dreadlogs.captor.PatternMatcher.matches;
import static me.lorenc.dreadlogs.captor.log4j.Log4jMatchers.hasLog;
import static org.apache.log4j.Level.INFO;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import me.lorenc.dreadlogs.captor.log4j.Log4jCaptor;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Log4jCaptorHasLogFunctionalTest {

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

package com.naked.logs.captor.jul.functional;

import static com.naked.logs.captor.PatternMatcher.matches;
import static com.naked.logs.captor.jul.JulMatchers.hasLog;
import static com.naked.logs.captor.jul.JulMatchers.noLog;
import static java.util.logging.Level.*;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.naked.logs.captor.jul.JulCaptor;

public class JulCaptorFunctionalTest {

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
    public void shouldCaptureSevereLog() throws Exception {

        logger.severe("severe message");

        assertThat(captor, hasLog("severe message").onLevel(SEVERE));
    }

    @Test
    public void shouldCaptureSevereLogWithException() throws Exception {

        logger.log(SEVERE, "severe message", new Throwable("an exception"));

        assertThat(captor, hasLog("severe message").onLevel(SEVERE).withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureWarningLog() throws Exception {

        logger.warning("warning message");

        assertThat(captor, hasLog("warning message").onLevel(WARNING));
    }

    @Test
    public void shouldCaptureWarningLogWithException() throws Exception {

        logger.log(WARNING, "warning message", new Throwable("an exception"));

        assertThat(captor, hasLog("warning message").onLevel(WARNING).withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureInfoLog() throws Exception {

        logger.info("info message");

        assertThat(captor, hasLog("info message").onLevel(INFO));
    }

    @Test
    public void shouldCaptureInfoLogWithException() throws Exception {

        logger.log(INFO, "info message", new Throwable("an exception"));

        assertThat(captor, hasLog("info message").onLevel(INFO).withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureFineLog() throws Exception {

        logger.fine("fine message");

        assertThat(captor, hasLog("fine message").onLevel(FINE));
    }

    @Test
    public void shouldCaptureFineLogWithException() throws Exception {

        logger.log(FINE, "fine message", new Throwable("an exception"));

        assertThat(captor, hasLog("fine message").onLevel(FINE).withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureFinerLog() throws Exception {

        logger.finer("finer message");

        assertThat(captor, hasLog("finer message").onLevel(FINER));
    }

    @Test
    public void shouldCaptureFinerLogWithException() throws Exception {

        logger.log(FINER, "finer message", new Throwable("an exception"));

        assertThat(captor, hasLog("finer message").onLevel(FINER).withExceptionMessage("an exception"));
    }

    @Test
    public void shouldCaptureFinestLog() throws Exception {

        logger.finer("finest message");

        assertThat(captor, hasLog("finest message").onLevel(FINER));
    }

    @Test
    public void shouldCaptureFinestLogWithException() throws Exception {

        logger.log(FINEST, "finest message", new Throwable("an exception"));

        assertThat(captor, hasLog("finest message").onLevel(FINEST).withExceptionMessage("an exception"));
    }

    @Test
    public void shouldNotCaptureSevereLogIfLevelDisabled() throws Exception {
        JulCaptor captor = new JulCaptor(LOGGER_NAME, OFF);

        logger.severe("severe message");

        assertThat(captor, noLog("severe message"));
    }

    @Test
    public void shouldNotCaptureInfoLogIfLevelDisabled() throws Exception {
        JulCaptor captor = new JulCaptor(LOGGER_NAME, WARNING);

        logger.info("info message");

        assertThat(captor, noLog("info message"));
    }

    @Test
    public void shouldNotCaptureFineLogIfLevelDisabled() throws Exception {
        JulCaptor captor = new JulCaptor(LOGGER_NAME, INFO);

        logger.fine("fine message");

        assertThat(captor, noLog("fine message"));
    }

    @Test
    public void shouldNotCaptureFinerLogIfLevelDisabled() throws Exception {
        JulCaptor captor = new JulCaptor(LOGGER_NAME, FINE);

        logger.finer("finer message");

        assertThat(captor, noLog("finer message"));
    }

    @Test
    public void shouldNotCaptureFinestLogIfLevelDisabled() throws Exception {
        JulCaptor captor = new JulCaptor(LOGGER_NAME, FINER);

        logger.finest("finest message");

        assertThat(captor, noLog("finest message"));
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

        assertThat(captor, hasLog("a message").withException(IllegalArgumentException.class, "illegal argument"));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClass() throws Exception {

        logger.log(SEVERE, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, hasLog("a message").withExceptionClass(IllegalArgumentException.class));
    }

    @Test
    public void shouldNotFindLogsOnDifferentLevels() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog("a message").onLevel(INFO));

        assertThat(captor, noLog("a message").onLevel(SEVERE));
        assertThat(captor, noLog("a message").onLevel(WARNING));
        assertThat(captor, noLog("a message").onLevel(FINE));
        assertThat(captor, noLog("a message").onLevel(FINER));
        assertThat(captor, noLog("a message").onLevel(FINEST));
    }

    @Test
    public void shouldCaptureLogWithMatchingMessage() throws Exception {

        logger.info("a regex message");

        assertThat(captor, hasLog(matches("a regex message")));
        assertThat(captor, hasLog(matches("^a regex message$")));
        assertThat(captor, hasLog(matches("^a [a-z]* message$")));
    }

}

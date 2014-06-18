package me.lorenc.dreadlogs.captor.jul.functional;

import static java.util.logging.Level.*;
import static me.lorenc.dreadlogs.captor.jul.JulMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.jul.JulMatchers.noLog;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import me.lorenc.dreadlogs.captor.jul.JulCaptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JulCaptorLogLevelFilteringFunctionalTest {

    private static final String LOGGER_NAME = "me.lorenc.dreadlogs.some.package";

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
    public void shouldCaptureWarningLog() throws Exception {

        logger.warning("warning message");

        assertThat(captor, hasLog("warning message").onLevel(WARNING));
    }

    @Test
    public void shouldCaptureInfoLog() throws Exception {

        logger.info("info message");

        assertThat(captor, hasLog("info message").onLevel(INFO));
    }

    @Test
    public void shouldCaptureFineLog() throws Exception {

        logger.fine("fine message");

        assertThat(captor, hasLog("fine message").onLevel(FINE));
    }

    @Test
    public void shouldCaptureFinerLog() throws Exception {

        logger.finer("finer message");

        assertThat(captor, hasLog("finer message").onLevel(FINER));
    }

    @Test
    public void shouldCaptureFinestLog() throws Exception {

        logger.finer("finest message");

        assertThat(captor, hasLog("finest message").onLevel(FINER));
    }

    @Test
    public void shouldNotFindLogsOnOtherLevels() throws Exception {

        logger.info("a message");

        assertThat(captor, hasLog("a message").onLevel(INFO));

        assertThat(captor, noLog("a message").onLevel(SEVERE));
        assertThat(captor, noLog("a message").onLevel(WARNING));
        assertThat(captor, noLog("a message").onLevel(FINE));
        assertThat(captor, noLog("a message").onLevel(FINER));
        assertThat(captor, noLog("a message").onLevel(FINEST));
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

}

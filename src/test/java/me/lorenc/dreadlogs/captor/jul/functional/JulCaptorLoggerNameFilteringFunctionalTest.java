package me.lorenc.dreadlogs.captor.jul.functional;

import static me.lorenc.dreadlogs.captor.jul.JulMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.jul.JulMatchers.noLog;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import me.lorenc.dreadlogs.captor.jul.JulCaptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JulCaptorLoggerNameFilteringFunctionalTest {

    private static final String LOGGER_NAME = "me.lorenc.dreadlogs.some.package";

    private Logger logger;
    private JulCaptor captor;

    @Before
    public void before() throws Exception {
        logger = Logger.getLogger(LOGGER_NAME);
    }

    @After
    public void after() throws Exception {
        captor.detachAppender();
    }

    @Test
    public void shouldCaptureLogForExactLoggerName() throws Exception {
        captor = new JulCaptor(LOGGER_NAME);

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldCaptureLogWhenCaptorSetToParrentPackage() throws Exception {
        captor = new JulCaptor("me.lorenc.dreadlogs");

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogForDifferentLoggerName() throws Exception {
        captor = new JulCaptor("me.lorenc.dreadlogs.another.package");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogWhenCaptorSetToChildPackage() throws Exception {
        captor = new JulCaptor("me.lorenc.dreadlogs.some.package.child");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

}

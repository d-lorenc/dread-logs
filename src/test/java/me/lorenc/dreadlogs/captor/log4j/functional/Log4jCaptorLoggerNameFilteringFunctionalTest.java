package me.lorenc.dreadlogs.captor.log4j.functional;

import static me.lorenc.dreadlogs.captor.log4j.Log4jMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.log4j.Log4jMatchers.noLog;
import static org.junit.Assert.assertThat;
import me.lorenc.dreadlogs.captor.log4j.Log4jCaptor;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Log4jCaptorLoggerNameFilteringFunctionalTest {

    private static final String LOGGER_NAME = "me.lorenc.dreadlogs.some.package";

    private Logger logger;
    private Log4jCaptor captor;

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
        captor = new Log4jCaptor(LOGGER_NAME);

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldCaptureLogWhenCaptorSetToParrentPackage() throws Exception {
        captor = new Log4jCaptor("me.lorenc.dreadlogs");

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogForDifferentLoggerName() throws Exception {
        captor = new Log4jCaptor("me.lorenc.dreadlogs.another.package");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogWhenCaptorSetToChildPackage() throws Exception {
        captor = new Log4jCaptor("me.lorenc.dreadlogs.some.package.child");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

}

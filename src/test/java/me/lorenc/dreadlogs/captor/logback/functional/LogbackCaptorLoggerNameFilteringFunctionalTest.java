package me.lorenc.dreadlogs.captor.logback.functional;

import static me.lorenc.dreadlogs.captor.logback.LogbackMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.logback.LogbackMatchers.noLog;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import me.lorenc.dreadlogs.captor.logback.LogbackCaptor;

public class LogbackCaptorLoggerNameFilteringFunctionalTest {

    private static final String LOGGER_NAME = "me.lorenc.dreadlogs.some.package";

    private Logger logger;

    @Rule
    public LogbackCaptor captor = new LogbackCaptor(LOGGER_NAME);

    @Before
    public void before() throws Exception {
        logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
    }

    @Test
    public void shouldCaptureLogForExactLoggerName() throws Exception {
        captor = new LogbackCaptor(LOGGER_NAME);

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldCaptureLogWhenCaptorSetToParrentPackage() throws Exception {
        captor = new LogbackCaptor("me.lorenc.dreadlogs");

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogForDifferentLoggerName() throws Exception {
        captor = new LogbackCaptor("me.lorenc.dreadlogs.another.package");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogWhenCaptorSetToChildPackage() throws Exception {
        captor = new LogbackCaptor("me.lorenc.dreadlogs.some.package.child");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

}

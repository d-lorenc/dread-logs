package me.lorenc.dreadlogs.captor.logback.functional;

import static me.lorenc.dreadlogs.captor.logback.LogbackMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.logback.LogbackMatchers.noLog;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.Logger;
import me.lorenc.dreadlogs.captor.logback.LogbackCaptor;

public class LogbackCaptorMdcFunctionalTest {

    private static final String LOGGER_NAME = "me.lorenc.dreadlogs.some.package";

    private Logger logger;

    @Rule
    public LogbackCaptor captor = new LogbackCaptor(LOGGER_NAME);

    @Before
    public void before() throws Exception {
        logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
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
    public void shouldNotFindLogWhenNoMdcPresent() throws Exception {

        logger.info("a message");

        assertThat(captor, noLog("a message").withMdc("key", "value"));
    }

    @Test
    public void shouldNotFindLogWhenNoMdcMatch() throws Exception {

        MDC.put("key", "another value");
        logger.info("a message");
        MDC.remove("key");

        assertThat(captor, noLog("a message").withMdc("key", "value"));
    }

}

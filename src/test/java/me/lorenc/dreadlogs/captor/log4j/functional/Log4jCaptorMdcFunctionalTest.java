package me.lorenc.dreadlogs.captor.log4j.functional;

import static me.lorenc.dreadlogs.captor.log4j.Log4jMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.log4j.Log4jMatchers.noLog;
import static org.junit.Assert.assertThat;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import me.lorenc.dreadlogs.captor.log4j.Log4jCaptor;

public class Log4jCaptorMdcFunctionalTest {

    private static final String LOGGER_NAME = "me.lorenc.dreadlogs.some.package";

    private Logger logger;

    @Rule
    public Log4jCaptor captor = new Log4jCaptor(LOGGER_NAME);

    @Before
    public void before() throws Exception {
        logger = Logger.getLogger(LOGGER_NAME);
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

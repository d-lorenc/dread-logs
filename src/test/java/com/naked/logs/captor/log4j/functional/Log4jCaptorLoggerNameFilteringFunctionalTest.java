package com.naked.logs.captor.log4j.functional;

import static com.naked.logs.captor.log4j.Log4jMatchers.hasLog;
import static com.naked.logs.captor.log4j.Log4jMatchers.noLog;
import static org.junit.Assert.assertThat;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.naked.logs.captor.log4j.Log4jCaptor;

public class Log4jCaptorLoggerNameFilteringFunctionalTest {

    private static final String LOGGER_NAME = "com.naked.logs.some.package";

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
        captor = new Log4jCaptor("com.naked.logs");

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogForDifferentLoggerName() throws Exception {
        captor = new Log4jCaptor("com.naked.logs.another.package");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogWhenCaptorSetToChildPackage() throws Exception {
        captor = new Log4jCaptor("com.naked.logs.some.package.child");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

}

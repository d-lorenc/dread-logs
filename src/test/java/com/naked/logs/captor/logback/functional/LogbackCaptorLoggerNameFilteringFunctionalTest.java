package com.naked.logs.captor.logback.functional;

import static com.naked.logs.captor.logback.LogbackMatchers.hasLog;
import static com.naked.logs.captor.logback.LogbackMatchers.noLog;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.naked.logs.captor.logback.LogbackCaptor;

public class LogbackCaptorLoggerNameFilteringFunctionalTest {

    private static final String LOGGER_NAME = "com.naked.logs.some.package";

    private Logger logger;
    private LogbackCaptor captor;

    @Before
    public void before() throws Exception {
        logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
    }

    @After
    public void after() throws Exception {
        captor.detachAppender();
    }

    @Test
    public void shouldCaptureLogForExactLoggerName() throws Exception {
        captor = new LogbackCaptor(LOGGER_NAME);

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldCaptureLogWhenCaptorSetToParrentPackage() throws Exception {
        captor = new LogbackCaptor("com.naked.logs");

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogForDifferentLoggerName() throws Exception {
        captor = new LogbackCaptor("com.naked.logs.another.package");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogWhenCaptorSetToChildPackage() throws Exception {
        captor = new LogbackCaptor("com.naked.logs.some.package.child");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

}

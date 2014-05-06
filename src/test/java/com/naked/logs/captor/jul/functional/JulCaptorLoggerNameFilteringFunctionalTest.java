package com.naked.logs.captor.jul.functional;

import static com.naked.logs.captor.jul.JulMatchers.hasLog;
import static com.naked.logs.captor.jul.JulMatchers.noLog;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.naked.logs.captor.jul.JulCaptor;

public class JulCaptorLoggerNameFilteringFunctionalTest {

    private static final String LOGGER_NAME = "com.naked.logs.some.package";

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
        captor = new JulCaptor("com.naked.logs");

        logger.info("a message");

        assertThat(captor, hasLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogForDifferentLoggerName() throws Exception {
        captor = new JulCaptor("com.naked.logs.another.package");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

    @Test
    public void shouldNotCaptureLogWhenCaptorSetToChildPackage() throws Exception {
        captor = new JulCaptor("com.naked.logs.some.package.child");

        logger.info("a message");

        assertThat(captor, noLog("a message"));
    }

}

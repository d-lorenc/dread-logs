package me.lorenc.dreadlogs.captor.jul.functional;

import static me.lorenc.dreadlogs.captor.jul.JulMatchers.hasLog;
import static me.lorenc.dreadlogs.captor.jul.JulMatchers.noLog;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import me.lorenc.dreadlogs.captor.jul.JulCaptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JulCaptorMdcFunctionalTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
    public void shouldThrowUnsupportedOperationExceptionOnHasLogMdcExpectations() throws Exception {

        logger.info("a message");

        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("MDC expectations must not be set for the logger implementation which does not support MDC.");

        assertThat(captor, hasLog("a message").withMdc("any", "any"));
    }

    @Test
    public void shouldThrowUnsupportedOperationExceptionOnNoLogMdcExpectations() throws Exception {

        logger.info("a message");

        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("MDC expectations must not be set for the logger implementation which does not support MDC.");

        assertThat(captor, noLog("a message").withMdc("any", "any"));
    }

}

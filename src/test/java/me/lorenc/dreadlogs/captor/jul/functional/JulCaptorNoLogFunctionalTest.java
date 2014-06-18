package me.lorenc.dreadlogs.captor.jul.functional;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static me.lorenc.dreadlogs.captor.PatternMatcher.matches;
import static me.lorenc.dreadlogs.captor.jul.JulMatchers.noLog;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import me.lorenc.dreadlogs.captor.jul.JulCaptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JulCaptorNoLogFunctionalTest {

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
    public void shouldNotFindLogWhenNoMessageMatch() throws Exception {

        logger.info("a message");

        assertThat(captor, noLog("a specific message"));
    }

    @Test
    public void shouldNotFindLogWhenMessageMatcherDoesNotMatch() throws Exception {

        logger.info("a message");

        assertThat(captor, noLog(containsString("a specific message")));
    }

    @Test
    public void shouldNotFindLogWhenMessageDoesNotMatchRegex() throws Exception {

        logger.info("a message");

        assertThat(captor, noLog(matches("^regex message$")));
    }

    @Test
    public void shouldNotFindLogWhenNoLevelMatch() throws Exception {

        logger.info("a message");

        assertThat(captor, noLog("a message").onLevel(SEVERE));
    }

    @Test
    public void shouldNotFindLogWhenNoLoggerNameMatch() throws Exception {

        logger.info("a message");

        assertThat(captor, noLog("a message").withLoggerName("another.logger.name"));
    }

    @Test
    public void shouldNotFindLogWhenNoExceptionPresent() throws Exception {

        logger.info("a message");

        assertThat(captor, noLog("a message").withExceptionClass(Exception.class));
        assertThat(captor, noLog("a message").withException(Exception.class, any(String.class)));
    }

    @Test
    public void shouldNotFindLogWhenNoExactExceptionClassMatch() throws Exception {

        logger.log(INFO, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, noLog("a message").withExceptionClass(Exception.class));
        assertThat(captor, noLog("a message").withException(Exception.class, "illegal argument"));
    }

    @Test
    public void shouldNotFindLogWhenNoExceptionMessageMatch() throws Exception {

        logger.log(INFO, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, noLog("a message").withExceptionMessage("illegal input"));
        assertThat(captor, noLog("a message").withException(IllegalArgumentException.class, "illegal input"));
    }

    @Test
    public void shouldNotFindLogWhenExceptionMessageMatcherDoesNotMatch() throws Exception {

        logger.log(INFO, "a message", new IllegalArgumentException("illegal argument"));

        assertThat(captor, noLog("a message").withExceptionMessage(startsWith("Not valid")));
        assertThat(captor, noLog("a message").withException(IllegalArgumentException.class, startsWith("Not valid")));
    }

}

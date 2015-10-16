package me.lorenc.dreadlogs.captor.log4j;

import static org.apache.log4j.Level.INFO;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Log4jCaptorTest {

    @Mock
    private Logger logger;
    @Mock
    private Log4jCapturingAppender appender;
    @Mock
    private Layout layout;

    private Log4jCaptor log4jCaptor;

    @Before
    public void before() throws Exception {
        log4jCaptor = new Log4jCaptor(logger, appender, INFO);
    }

    @Test
    public void shouldCreateWithLoggerNameOnly() throws Exception {
        new Log4jCaptor("name.only");
    }

    @Test
    public void shouldCreateWithLoggerNameAndLevel() throws Exception {
        new Log4jCaptor("name.and.level", INFO);
    }

    @Test
    public void shouldCreateWithFullConstructor() throws Exception {
        new Log4jCaptor("name.and.level", INFO, layout);
    }

    @Test
    public void shouldSetLoggerLevelOnConstruction() throws Exception {
        verify(logger).setLevel(INFO);
    }

    @Test
    public void shouldAttachAppenderToLoggerOnConstruction() throws Exception {
        verify(logger).addAppender(appender);
    }

    @Test
    public void shouldDetachAppender() throws Exception {
        log4jCaptor.detachAppender();

        verify(logger).removeAppender(appender);
    }
    @Test
    public void shouldDetachAppenderOnAfter() throws Exception {
        log4jCaptor.after();

        verify(logger).removeAppender(appender);
    }

    @Test
    public void shouldReturnLoggingEvents() throws Exception {
        List<LoggingEvent> expectedEvents = new LinkedList<LoggingEvent>();
        when(appender.getCapturedLogs()).thenReturn(expectedEvents);

        List<LoggingEvent> actualEvents = log4jCaptor.getCapturedLogs();

        assertThat(actualEvents, sameInstance(expectedEvents));
    }

    @Test
    public void shouldStringifyAppender() throws Exception {
        when(appender.toString()).thenReturn("log lines");

        String loggerString = log4jCaptor.toString();

        assertThat(loggerString, equalTo("log lines"));
    }

}

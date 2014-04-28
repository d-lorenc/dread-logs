package com.naked.logs.captor.log4j;

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

import com.naked.logs.captor.log4j.Log4jCaptor;
import com.naked.logs.captor.log4j.Log4jCapturingAppender;

@RunWith(MockitoJUnitRunner.class)
public class Log4jCaptorTest {

    @Mock
    private Logger logger;
    @Mock
    private Log4jCapturingAppender appender;
    @Mock
    private Layout layout;

    private Log4jCaptor capturingLogger;

    @Before
    public void before() throws Exception {
        capturingLogger = new Log4jCaptor(logger, appender, INFO);
    }

    @Test
    public void shouldCreateLoggerWithLoggerNameOnly() throws Exception {
        new Log4jCaptor("name.only");
    }

    @Test
    public void shouldCreateLoggerWithLoggerNameAndLevel() throws Exception {
        new Log4jCaptor("name.and.level", INFO);
    }

    @Test
    public void shouldCreateLoggerWithFullConstructor() throws Exception {
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
        capturingLogger.detachAppender();

        verify(logger).removeAppender(appender);
    }

    @Test
    public void shouldReturnLoggingEvents() throws Exception {
        List<LoggingEvent> expectedEvents = new LinkedList<LoggingEvent>();
        when(appender.getCapturedLogs()).thenReturn(expectedEvents);

        List<LoggingEvent> actualEvents = capturingLogger.getCapturedLogs();

        assertThat(actualEvents, sameInstance(expectedEvents));
    }

    @Test
    public void shouldStringifyAppender() throws Exception {
        when(appender.toString()).thenReturn("log lines");

        String loggerString = capturingLogger.toString();

        assertThat(loggerString, equalTo("log lines"));
    }

}

package com.naked.logs.log4j.inmemory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.log4j.inmemory.Log4jInMemoryAppender;
import com.naked.logs.log4j.inmemory.Log4jInMemoryLogger;

@RunWith(MockitoJUnitRunner.class)
public class Log4jInMemoryLoggerTest {

    @Mock
    private Logger logger;
    @Mock
    private Log4jInMemoryAppender appender;
    @Mock
    private Layout layout;

    private Log4jInMemoryLogger inMemoryLogger;

    @Before
    public void before() throws Exception {
        inMemoryLogger = new Log4jInMemoryLogger(logger, appender, Level.INFO);
    }

    @Test
    public void shouldCreateLoggerWithLoggerNameOnly() throws Exception {
        new Log4jInMemoryLogger("name.only");
    }

    @Test
    public void shouldCreateLoggerWithLoggerNameAndLevel() throws Exception {
        new Log4jInMemoryLogger("name.and.level", Level.INFO);
    }

    @Test
    public void shouldCreateLoggerWithFullConstructor() throws Exception {
        new Log4jInMemoryLogger("name.and.level", Level.INFO, layout);
    }

    @Test
    public void shouldSetLevelOnLoggerOnConstruction() throws Exception {
        verify(logger).setLevel(Level.INFO);
    }

    @Test
    public void shouldAddAppenderToLoggerOnConstruction() throws Exception {
        verify(logger).addAppender(appender);
    }

    @Test
    public void shouldEnableStdOutOnAppender() throws Exception {
        inMemoryLogger.enableStdOutLogging();

        verify(appender).enableStdOutLogging();
    }

    @Test
    public void shouldRemoveAppender() throws Exception {
        inMemoryLogger.removeAppender();

        verify(logger).removeAppender(appender);
    }

    @Test
    public void shouldReturnLoggingEvents() throws Exception {
        List<LoggingEvent> expectedEvents = new LinkedList<LoggingEvent>();
        when(appender.getCopyOfEvents()).thenReturn(expectedEvents);

        List<LoggingEvent> actualEvents = inMemoryLogger.getEvents();

        assertThat(actualEvents, sameInstance(expectedEvents));
    }

    @Test
    public void shouldStringifyAppender() throws Exception {
        when(appender.toString()).thenReturn("log lines");

        String loggerString = inMemoryLogger.toString();

        assertThat(loggerString, equalTo("log lines"));
    }

}

package com.naked.logs.logback.captor.matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;

@RunWith(MockitoJUnitRunner.class)
public class LogbackEntryTest {


    @Mock
    private ILoggingEvent loggingEvent;

    private LogbackEntry logbackEntry;

    @Before
    public void before() throws Exception {
        logbackEntry = new LogbackEntry(loggingEvent);
    }

    @Test
    public void shouldGetMessageOfLoggingEvent() throws Exception {
        String expectedMessage = "message";
        when(loggingEvent.getMessage()).thenReturn(expectedMessage);

        String message = logbackEntry.getMessage();

        assertThat(message, equalTo(expectedMessage));
    }

    @Test
    public void shouldGetLoggerNameOfLoggingEvent() throws Exception {
        String expectedLoggerName = "com.naked.logs.some.package";
        when(loggingEvent.getLoggerName()).thenReturn(expectedLoggerName);

        String loggerName = logbackEntry.getLoggerName();

        assertThat(loggerName, equalTo(expectedLoggerName));
    }

    @Test
    public void shouldGetLevelOfLoggingEvent() throws Exception {
        Level expectedLevel = Level.INFO;
        when(loggingEvent.getLevel()).thenReturn(expectedLevel);

        Level level = logbackEntry.getLevel();

        assertThat(level, equalTo(expectedLevel));
    }

    @Test
    public void shouldGetExceptionMessageOfLoggingEvent() throws Exception {
        String expectedExceptionMessage = "exception message";
        when(loggingEvent.getThrowableProxy()).thenReturn(new ThrowableProxy(new Exception(expectedExceptionMessage)));

        String exceptionMessage = logbackEntry.getExceptionMessage();

        assertThat(exceptionMessage, equalTo(expectedExceptionMessage));
    }

    @Test
    public void shouldGetExceptionClassOfLoggingEvent() throws Exception {
        IllegalArgumentException expectedException = new IllegalArgumentException();
        when(loggingEvent.getThrowableProxy()).thenReturn(new ThrowableProxy(expectedException));

        String exceptionClassName = logbackEntry.getExceptionClassName();

        assertEquals(expectedException.getClass().getName(), exceptionClassName);
    }

    @Test
    public void shouldReturnNullExceptionMessageWhenNoThrowable() throws Exception {
        when(loggingEvent.getThrowableProxy()).thenReturn(null);

        String exceptionMessage = logbackEntry.getExceptionMessage();

        assertThat(exceptionMessage, nullValue());
    }

    @Test
    public void shouldNullReturnExceptionClassWhenNoThrowableOfLoggingEvent() throws Exception {
        when(loggingEvent.getThrowableProxy()).thenReturn(null);

        String exceptionClassName = logbackEntry.getExceptionClassName();

        assertThat(exceptionClassName, nullValue());
    }

    @Test
    public void shouldGetMdcOfLoggingEvent() throws Exception {
        String mdcKey = "mdc key";
        String expectedMdcValue = "mdc value";
        Map<String, String> mdcMap = new HashMap<String, String>();
        mdcMap.put(mdcKey, expectedMdcValue);
        when(loggingEvent.getMDCPropertyMap()).thenReturn(mdcMap);

        String mdcValue = logbackEntry.getMdcValue(mdcKey );

        assertThat(mdcValue, equalTo(expectedMdcValue));
    }

}

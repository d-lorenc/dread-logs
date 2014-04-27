package com.naked.logs.log4j.captor.matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Log4jEntryTest {

    @Mock
    private LoggingEvent loggingEvent;

    private Log4jEntry log4jEntry;

    @Before
    public void before() throws Exception {
        log4jEntry = new Log4jEntry(loggingEvent);
    }

    @Test
    public void shouldGetMessageOfLoggingEvent() throws Exception {
        String expectedMessage = "message";
        when(loggingEvent.getMessage()).thenReturn(expectedMessage);

        String message = log4jEntry.getMessage();

        assertThat(message, equalTo(expectedMessage));
    }

    @Test
    public void shouldGetLoggerNameOfLoggingEvent() throws Exception {
        String expectedLoggerName = "com.naked.logs.some.package";
        when(loggingEvent.getLoggerName()).thenReturn(expectedLoggerName);

        String loggerName = log4jEntry.getLoggerName();

        assertThat(loggerName, equalTo(expectedLoggerName));
    }

    @Test
    public void shouldGetLevelOfLoggingEvent() throws Exception {
        Level expectedLevel = Level.INFO;
        when(loggingEvent.getLevel()).thenReturn(expectedLevel);

        Level level = log4jEntry.getLevel();

        assertThat(level, equalTo(expectedLevel));
    }

    @Test
    public void shouldGetExceptionMessageOfLoggingEvent() throws Exception {
        String expectedExceptionMessage = "exception message";
        when(loggingEvent.getThrowableInformation()).thenReturn(new ThrowableInformation(new Exception(expectedExceptionMessage)));

        String exceptionMessage = log4jEntry.getExceptionMessage();

        assertThat(exceptionMessage, equalTo(expectedExceptionMessage));
    }

    @Test
    public void shouldGetExceptionClassOfLoggingEvent() throws Exception {
        IllegalArgumentException expectedException = new IllegalArgumentException();
        when(loggingEvent.getThrowableInformation()).thenReturn(new ThrowableInformation(expectedException));

        String exceptionClassName = log4jEntry.getExceptionClassName();

        assertEquals(expectedException.getClass().getName(), exceptionClassName);
    }

    @Test
    public void shouldReturnNullExceptionMessageWhenNoThrowable() throws Exception {
        when(loggingEvent.getThrowableInformation()).thenReturn(null);

        String exceptionMessage = log4jEntry.getExceptionMessage();

        assertThat(exceptionMessage, nullValue());
    }

    @Test
    public void shouldNullReturnExceptionClassWhenNoThrowableOfLoggingEvent() throws Exception {
        when(loggingEvent.getThrowableInformation()).thenReturn(null);

        String exceptionClassName = log4jEntry.getExceptionClassName();

        assertThat(exceptionClassName, nullValue());
    }

    @Test
    public void shouldGetMdcOfLoggingEvent() throws Exception {
        String mdcKey = "mdc key";
        String expectedMdcValue = "mdc value";
        when(loggingEvent.getMDC(mdcKey)).thenReturn(expectedMdcValue);

        String mdcValue = log4jEntry.getMdcValue(mdcKey );

        assertThat(mdcValue, equalTo(expectedMdcValue));
    }

}

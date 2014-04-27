package com.naked.logs.jul.captor.matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JulEntryTest {

    @Mock
    private LogRecord logRecord;

    private JulEntry julEntry;

    @Before
    public void before() throws Exception {
        julEntry = new JulEntry(logRecord);
    }

    @Test
    public void shouldGetMessageOfLoggingEvent() throws Exception {
        String expectedMessage = "message";
        when(logRecord.getMessage()).thenReturn(expectedMessage);

        String message = julEntry.getMessage();

        assertThat(message, equalTo(expectedMessage));
    }

    @Test
    public void shouldGetLoggerNameOfLoggingEvent() throws Exception {
        String expectedLoggerName = "com.naked.logs.some.package";
        when(logRecord.getLoggerName()).thenReturn(expectedLoggerName);

        String loggerName = julEntry.getLoggerName();

        assertThat(loggerName, equalTo(expectedLoggerName));
    }

    @Test
    public void shouldGetLevelOfLoggingEvent() throws Exception {
        Level expectedLevel = Level.INFO;
        when(logRecord.getLevel()).thenReturn(expectedLevel);

        Level level = julEntry.getLevel();

        assertThat(level, equalTo(expectedLevel));
    }

    @Test
    public void shouldGetExceptionMessageOfLoggingEvent() throws Exception {
        String expectedExceptionMessage = "exception message";
        when(logRecord.getThrown()).thenReturn(new Exception(expectedExceptionMessage));

        String exceptionMessage = julEntry.getExceptionMessage();

        assertThat(exceptionMessage, equalTo(expectedExceptionMessage));
    }

    @Test
    public void shouldGetExceptionClassOfLoggingEvent() throws Exception {
        IllegalArgumentException expectedException = new IllegalArgumentException();
        when(logRecord.getThrown()).thenReturn(expectedException);

        String exceptionClassName = julEntry.getExceptionClassName();

        assertEquals(expectedException.getClass().getName(), exceptionClassName);
    }

    @Test
    public void shouldReturnNullExceptionMessageWhenNoThrowable() throws Exception {
        when(logRecord.getThrown()).thenReturn(null);

        String exceptionMessage = julEntry.getExceptionMessage();

        assertThat(exceptionMessage, nullValue());
    }

    @Test
    public void shouldNullReturnExceptionClassWhenNoThrowableOfLoggingEvent() throws Exception {
        when(logRecord.getThrown()).thenReturn(null);

        String exceptionClassName = julEntry.getExceptionClassName();

        assertThat(exceptionClassName, nullValue());
    }

    @Test
    public void shouldGetNullAsJulDoesNotSupportMdc() throws Exception {
        String mdcKey = "any key";

        String mdcValue = julEntry.getMdcValue(mdcKey );

        assertThat(mdcValue, nullValue());
    }

}

package com.naked.logs.captor.jul;

import static java.util.logging.Level.INFO;
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

import com.naked.logs.captor.jul.JulEntry;

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
    public void shouldGetMessage() throws Exception {
        when(logRecord.getMessage()).thenReturn("message");

        String message = julEntry.getMessage();

        assertThat(message, equalTo("message"));
    }

    @Test
    public void shouldGetLoggerName() throws Exception {
        when(logRecord.getLoggerName()).thenReturn("com.naked.logs.some.package");

        String loggerName = julEntry.getLoggerName();

        assertThat(loggerName, equalTo("com.naked.logs.some.package"));
    }

    @Test
    public void shouldGetLevel() throws Exception {
        when(logRecord.getLevel()).thenReturn(INFO);

        Level level = julEntry.getLevel();

        assertThat(level, equalTo(INFO));
    }

    @Test
    public void shouldGetExceptionMessageOfLoggingEvent() throws Exception {
        when(logRecord.getThrown()).thenReturn(new Exception("exception message"));

        String exceptionMessage = julEntry.getExceptionMessage();

        assertThat(exceptionMessage, equalTo("exception message"));
    }

    @Test
    public void shouldGetExceptionClassOfLoggingEvent() throws Exception {
        when(logRecord.getThrown()).thenReturn(new IllegalArgumentException());

        String exceptionClassName = julEntry.getExceptionClassName();

        assertEquals(IllegalArgumentException.class.getName(), exceptionClassName);
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
        String mdcValue = julEntry.getMdcValue("any key" );

        assertThat(mdcValue, nullValue());
    }

}

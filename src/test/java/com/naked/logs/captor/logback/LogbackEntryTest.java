package com.naked.logs.captor.logback;

import static ch.qos.logback.classic.Level.INFO;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
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
    public void shouldGetMessage() throws Exception {
        when(loggingEvent.getMessage()).thenReturn("message");

        String message = logbackEntry.getMessage();

        assertThat(message, equalTo("message"));
    }

    @Test
    public void shouldGetLoggerName() throws Exception {
        when(loggingEvent.getLoggerName()).thenReturn("com.naked.logs.some.package");

        String loggerName = logbackEntry.getLoggerName();

        assertThat(loggerName, equalTo("com.naked.logs.some.package"));
    }

    @Test
    public void shouldGetLevel() throws Exception {
        when(loggingEvent.getLevel()).thenReturn(INFO);

        Level level = logbackEntry.getLevel();

        assertThat(level, equalTo(INFO));
    }

    @Test
    public void shouldGetExceptionMessage() throws Exception {
        when(loggingEvent.getThrowableProxy()).thenReturn(new ThrowableProxy(new Exception("exception message")));

        String exceptionMessage = logbackEntry.getExceptionMessage();

        assertThat(exceptionMessage, equalTo("exception message"));
    }

    @Test
    public void shouldGetExceptionClass() throws Exception {
        when(loggingEvent.getThrowableProxy()).thenReturn(new ThrowableProxy(new IllegalArgumentException()));

        String exceptionClassName = logbackEntry.getExceptionClassName();

        assertThat(exceptionClassName, equalTo(IllegalArgumentException.class.getName()));
    }

    @Test
    public void shouldReturnNullExceptionMessageWhenNoThrowableProxy() throws Exception {
        when(loggingEvent.getThrowableProxy()).thenReturn(null);

        String exceptionMessage = logbackEntry.getExceptionMessage();

        assertThat(exceptionMessage, nullValue());
    }

    @Test
    public void shouldNullReturnExceptionClassWhenNoThrowableProxy() throws Exception {
        when(loggingEvent.getThrowableProxy()).thenReturn(null);

        String exceptionClassName = logbackEntry.getExceptionClassName();

        assertThat(exceptionClassName, nullValue());
    }

    @Test
    public void shouldGetMdc() throws Exception {
        Map<String, String> mdcMap = new HashMap<String, String>();
        mdcMap.put("mdc key", "mdc value");
        when(loggingEvent.getMDCPropertyMap()).thenReturn(mdcMap);

        String mdcValue = logbackEntry.getMdcValue("mdc key");

        assertThat(mdcValue, equalTo("mdc value"));
    }

}

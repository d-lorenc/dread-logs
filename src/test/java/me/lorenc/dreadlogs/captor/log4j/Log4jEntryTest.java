package me.lorenc.dreadlogs.captor.log4j;

import static org.apache.log4j.Level.INFO;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
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
    public void shouldGetMessage() throws Exception {
        when(loggingEvent.getMessage()).thenReturn("message");

        String message = log4jEntry.getMessage();

        assertThat(message, equalTo("message"));
    }

    @Test
    public void shouldGetLoggerName() throws Exception {
        when(loggingEvent.getLoggerName()).thenReturn("me.lorenc.dreadlogs.some.package");

        String loggerName = log4jEntry.getLoggerName();

        assertThat(loggerName, equalTo("me.lorenc.dreadlogs.some.package"));
    }

    @Test
    public void shouldGetLevel() throws Exception {
        when(loggingEvent.getLevel()).thenReturn(INFO);

        Level level = log4jEntry.getLevel();

        assertThat(level, equalTo(INFO));
    }

    @Test
    public void shouldGetExceptionMessage() throws Exception {
        when(loggingEvent.getThrowableInformation()).thenReturn(new ThrowableInformation(new Exception("exception message")));

        String exceptionMessage = log4jEntry.getExceptionMessage();

        assertThat(exceptionMessage, equalTo("exception message"));
    }

    @Test
    public void shouldGetExceptionClass() throws Exception {
        when(loggingEvent.getThrowableInformation()).thenReturn(new ThrowableInformation(new IllegalArgumentException()));

        String exceptionClassName = log4jEntry.getExceptionClassName();

        assertThat(exceptionClassName, equalTo(IllegalArgumentException.class.getName()));
    }

    @Test
    public void shouldReturnNullExceptionMessageWhenNoThrowable() throws Exception {
        when(loggingEvent.getThrowableInformation()).thenReturn(null);

        String exceptionMessage = log4jEntry.getExceptionMessage();

        assertThat(exceptionMessage, nullValue());
    }

    @Test
    public void shouldNullReturnExceptionClassWhenNoThrowable() throws Exception {
        when(loggingEvent.getThrowableInformation()).thenReturn(null);

        String exceptionClassName = log4jEntry.getExceptionClassName();

        assertThat(exceptionClassName, nullValue());
    }

    @Test
    public void shouldGetMdc() throws Exception {
        when(loggingEvent.getMDC("mdc key")).thenReturn("mdc value");

        String mdcValue = log4jEntry.getMdcValue("mdc key" );

        assertThat(mdcValue, equalTo("mdc value"));
    }

    @Test
    public void shouldReturnNullMdcIfNotFound() throws Exception {
        when(loggingEvent.getMDC("mdc key")).thenReturn(null);

        String mdcValue = log4jEntry.getMdcValue("mdc key" );

        assertThat(mdcValue, nullValue());
    }

    @Test
    public void shouldReturnTrueAsLog4jSupportsMdc() throws Exception {

        boolean mdcSupported = log4jEntry.isMdcSupported();

        assertThat(mdcSupported, equalTo(true));
    }

}

package com.naked.logs.captor;

import static org.apache.log4j.Level.TRACE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.captor.log4j.Log4jCaptor;

@RunWith(MockitoJUnitRunner.class)
public class LogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private Matcher<String> stringMatcher;
    @Mock
    private Description description;
    @Mock
    private LoggingEvent log;
    @Mock
    private LogEntry<Level> logEntry;
    @Captor
    private ArgumentCaptor<LogEntry<Level>> entryCaptor;

    private LogMatcher<Log4jCaptor, LoggingEvent, Level> logMatcher;

    @Before
    public void before() throws Exception {
        logMatcher = new LogMatcher<Log4jCaptor, LoggingEvent, Level>(logExpectations) {

            @Override
            protected LogEntry<Level> createLogEntry(LoggingEvent log) {
                return logEntry;
            }

            @Override
            protected boolean matchesSafely(Log4jCaptor captor) {
                return false;
            }
        };
    }

    @Test
    public void shouldSetExpectedMessage() throws Exception {

        logMatcher.withMessage("expected message");

        verify(logExpectations).setExpectedMessage("expected message");
    }

    @Test
    public void shouldSetExpectedMessageMatcher() throws Exception {

        logMatcher.withMessage(stringMatcher);

        verify(logExpectations).setExpectedMessage(stringMatcher);
    }

    @Test
    public void shouldSetExpectedLevel() throws Exception {
        logMatcher.onLevel(TRACE);

        verify(logExpectations).setExpectedLevel(TRACE);
    }

    @Test
    public void shouldSetExpectedLoggerName() throws Exception {

        logMatcher.withLoggerName("com.naked.logs.some.package");

        verify(logExpectations).setExpectedLoggerName("com.naked.logs.some.package");
    }

    @Test
    public void shouldSetExpectedExceptionMessage() throws Exception {

        logMatcher.withExceptionMessage("exception message");

        verify(logExpectations).setExpectedExceptionMessage("exception message");
    }

    @Test
    public void shouldSetExpectedExceptionMessageMatcher() throws Exception {

        logMatcher.withExceptionMessage(stringMatcher);

        verify(logExpectations).setExpectedExceptionMessage(stringMatcher);
    }

    @Test
    public void shouldSetExpectedExceptionClass() throws Exception {

        logMatcher.withExceptionClass(Throwable.class);

        verify(logExpectations).setExpectedExceptionClass(Throwable.class);
    }

    @Test
    public void shouldSetExpectedExceptionMessageAndClass() throws Exception {

        logMatcher.withException(Throwable.class, "exception message");

        verify(logExpectations).setExpectedExceptionMessage("exception message");
        verify(logExpectations).setExpectedExceptionClass(Throwable.class);
    }

    @Test
    public void shouldSetExpectedExceptionMessageMatcherAndClass() throws Exception {

        logMatcher.withException(Throwable.class, stringMatcher);

        verify(logExpectations).setExpectedExceptionMessage(stringMatcher);
        verify(logExpectations).setExpectedExceptionClass(Throwable.class);
    }

    @Test
    public void shouldAddExpectedMdc() throws Exception {

        logMatcher.withMdc("key", "value");

        verify(logExpectations).addExpectedMdc("key", "value");
    }

    @Test
    public void shouldAppendDescription() throws Exception {
        when(logExpectations.toString()).thenReturn("log expectations");

        logMatcher.describeTo(description);

        verify(description).appendValue("log expectations");
    }

    @Test
    public void shouldCallFulfillExpectationsForLogEntry() throws Exception {
        when(logExpectations.fulfillsExpectations(logEntry)).thenReturn(true);

        boolean fulfills = logMatcher.fulfillsExpectations(log);

        assertThat(fulfills, equalTo(true));

        verify(logExpectations).fulfillsExpectations(entryCaptor.capture());
        assertThat(entryCaptor.getValue(), sameInstance(logEntry));
    }

}

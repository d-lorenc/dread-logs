package com.naked.logs.matcher;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

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

import com.naked.logs.log4j.captor.Log4jCaptor;
import com.naked.logs.log4j.captor.matcher.Log4jEntry;

@RunWith(MockitoJUnitRunner.class)
public class LogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private Matcher<String> messageMatcher;
    @Mock
    private Log4jCaptor captor;
    @Mock
    private LoggingEvent logOne, logTwo;
    @Mock
    private Log4jEntry logEntryOne, logEntryTwo;
    @Mock
    private Description description;
    @Captor
    private ArgumentCaptor<Log4jEntry> entryCaptor;

    private List<LoggingEvent> logs;

    private LogMatcher<Log4jCaptor, LoggingEvent, Level> logMatcher;

    @Before
    public void before() throws Exception {
        logMatcher = new LogMatcher<Log4jCaptor, LoggingEvent, Level>(logExpectations) {
            @Override
            protected LogEntry<Level> createLogEntry(LoggingEvent log) {
                if (log == logOne) {
                    return logEntryOne;
                }
                return logEntryTwo;
            }
        };

        logs = new LinkedList<LoggingEvent>();
        logs.add(logOne);
        logs.add(logTwo);

        when(captor.getCapturedLogs()).thenReturn(logs);
    }

    @Test
    public void shouldSetExpectedMessage() throws Exception {
        String expectedMessage = "expected message";
        logMatcher.withMessage(expectedMessage);

        verify(logExpectations).setExpectedMessage(expectedMessage);
    }

    @Test
    public void shouldSetMessageMatcher() throws Exception {
        logMatcher.withMessage(messageMatcher);

        verify(logExpectations).setExpectedMessage(messageMatcher);
    }

    @Test
    public void shouldSetExpectedLevel() throws Exception {
        Level expectedLevel = Level.TRACE;
        logMatcher.onLevel(expectedLevel);

        verify(logExpectations).setExpectedLevel(expectedLevel);
    }

    @Test
    public void shouldSetExpectedLoggerName() throws Exception {
        String expectedLoggerName = "com.naked.logs.some.package";
        logMatcher.withLoggerName(expectedLoggerName);

        verify(logExpectations).setExpectedLoggerName(expectedLoggerName);
    }

    @Test
    public void shouldSetExpectedExceptionMessage() throws Exception {
        String expectedExceptionMessage = "exception message";
        logMatcher.withExceptionMessage(expectedExceptionMessage);

        verify(logExpectations).setExpectedExceptionMessage(expectedExceptionMessage);
    }

    @Test
    public void shouldSetExpectedExceptionClass() throws Exception {
        Class<? extends Throwable> expectedExceptionClass = Throwable.class;
        logMatcher.withExceptionClass(expectedExceptionClass);

        verify(logExpectations).setExpectedExceptionClass(expectedExceptionClass);
    }

    @Test
    public void shouldSetExpectedExceptionMessageAndClass() throws Exception {
        String expectedExceptionMessage = "exception message";
        Class<? extends Throwable> expectedExceptionClass = Throwable.class;
        logMatcher.withException(expectedExceptionClass, expectedExceptionMessage);

        verify(logExpectations).setExpectedExceptionMessage(expectedExceptionMessage);
        verify(logExpectations).setExpectedExceptionClass(expectedExceptionClass);
    }

    @Test
    public void shouldAddExpectedMdc() throws Exception {
        String expectedMdcKey = "key";
        String expectedMdcValue = "value";
        logMatcher.withMdc(expectedMdcKey, expectedMdcValue);

        verify(logExpectations).addExpectedMdc(expectedMdcKey, expectedMdcValue);
    }

    @Test
    public void shouldReturnFalseWhenNoLogEntries() throws Exception {
        when(captor.getCapturedLogs()).thenReturn(new LinkedList<LoggingEvent>());;

        boolean matches = logMatcher.matchesSafely(captor);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoneOfLogEntriesMatchesExpectations() throws Exception {
        when(logExpectations.fulfilsExpectations(any(Log4jEntry.class))).thenReturn(false);

        boolean matches = logMatcher.matchesSafely(captor);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnTrueWhenLogEntryMatchesExpectations() throws Exception {
        when(logExpectations.fulfilsExpectations(any(Log4jEntry.class))).thenReturn(false, true);

        boolean matches = logMatcher.matchesSafely(captor);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnTrueOnFirstMatchingLogEntry() throws Exception {
        when(logExpectations.fulfilsExpectations(any(Log4jEntry.class))).thenReturn(true, false);

        boolean matches = logMatcher.matchesSafely(captor);

        assertTrue(matches);
        verify(logExpectations).fulfilsExpectations(entryCaptor.capture());
        assertThat(entryCaptor.getValue(), sameInstance(logEntryOne));
    }

    @Test
    public void shouldConvertLoggingEventsToLogEntries() throws Exception {

        logMatcher.matchesSafely(captor);

        verify(logExpectations, times(2)).fulfilsExpectations(entryCaptor.capture());
        assertThat(entryCaptor.getAllValues(), hasItems(logEntryOne, logEntryTwo));
    }

    @Test
    public void shouldAppendDescription() throws Exception {
        String logExpectationsString = "log expectations";
        when(logExpectations.toString()).thenReturn(logExpectationsString);

        logMatcher.describeTo(description);

        verify(description).appendValue(logExpectationsString);
    }

}

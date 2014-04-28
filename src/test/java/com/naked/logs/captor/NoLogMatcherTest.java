package com.naked.logs.captor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.captor.log4j.Log4jCaptor;
import com.naked.logs.captor.log4j.Log4jEntry;

@RunWith(MockitoJUnitRunner.class)
public class NoLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private Log4jCaptor captor;
    @Mock
    private LoggingEvent logOne, logTwo;
    @Mock
    private Log4jEntry logEntryOne, logEntryTwo;

    private NoLogMatcher<Log4jCaptor, LoggingEvent, Level> noLogMatcher;

    @Before
    public void before() throws Exception {
        noLogMatcher = new NoLogMatcher<Log4jCaptor, LoggingEvent, Level>(logExpectations) {
            @Override
            protected LogEntry<Level> createLogEntry(LoggingEvent log) {
                if (log == logOne) {
                    return logEntryOne;
                }
                return logEntryTwo;
            }
        };

        List<LoggingEvent> logs = new LinkedList<LoggingEvent>();
        logs.add(logOne);
        logs.add(logTwo);
        when(captor.getCapturedLogs()).thenReturn(logs);
    }

    @Test
    public void shouldReturnTrueWhenNoLogEntries() throws Exception {
        when(captor.getCapturedLogs()).thenReturn(new LinkedList<LoggingEvent>());

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertTrue(notFound);
    }

    @Test
    public void shouldReturnTrueWhenNoneOfLogEntriesMatchesExpectations() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(false);

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertTrue(notFound);
    }

    @Test
    public void shouldReturnFalseWhenOneLogEntryMatchesExpectations() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(false, true);

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertFalse(notFound);
    }

    @Test
    public void shouldReturnTrueOnFirstMatchingLogEntry() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(true, false);

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertFalse(notFound);
    }

}

package me.lorenc.dreadlogs.captor;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;
import me.lorenc.dreadlogs.captor.NoLogMatcher;
import me.lorenc.dreadlogs.captor.log4j.Log4jCaptor;
import me.lorenc.dreadlogs.captor.log4j.Log4jEntry;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NoLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private Log4jCaptor captor;
    @Mock
    private LoggingEvent logOne, logTwo;

    private NoLogMatcher<Log4jCaptor, LoggingEvent, Level> noLogMatcher;

    @Before
    public void before() throws Exception {
        noLogMatcher = new NoLogMatcher<Log4jCaptor, LoggingEvent, Level>(logExpectations) {
            @Override
            protected LogEntry<Level> createLogEntry(LoggingEvent log) {
                return null;
            }
        };

        when(captor.getCapturedLogs()).thenReturn(asList(logOne, logTwo));
    }

    @Test
    public void shouldReturnTrueWhenNoLogEntries() throws Exception {
        when(captor.getCapturedLogs()).thenReturn(new LinkedList<LoggingEvent>());

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertTrue(notFound);
    }

    @Test
    public void shouldReturnTrueWhenNoMatches() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(false);

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertTrue(notFound);
    }

    @Test
    public void shouldReturnFalseWhenOneLogEntryMatchesExpectations() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(false, true);

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertFalse(notFound);
        verify(logExpectations, times(2)).fulfillsExpectations(any(Log4jEntry.class));
    }

    @Test
    public void shouldReturnFalseOnFirstMatchingLogEntry() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(true, false);

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertFalse(notFound);
        verify(logExpectations, times(1)).fulfillsExpectations(any(Log4jEntry.class));
    }

}

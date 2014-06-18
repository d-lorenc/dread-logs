package me.lorenc.dreadlogs.captor;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import me.lorenc.dreadlogs.captor.HasLogMatcher;
import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;
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
public class HasLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private Log4jCaptor captor;
    @Mock
    private LoggingEvent logOne, logTwo;

    private HasLogMatcher<Log4jCaptor, LoggingEvent, Level> hasLogMatcher;

    @Before
    public void before() throws Exception {
        hasLogMatcher = new HasLogMatcher<Log4jCaptor, LoggingEvent, Level>(logExpectations) {
            @Override
            protected LogEntry<Level> createLogEntry(LoggingEvent log) {
                return null;
            }
        };

        when(captor.getCapturedLogs()).thenReturn(asList(logOne, logTwo));
    }

    @Test
    public void shouldReturnFalseWhenNoLogEntries() throws Exception {
        when(captor.getCapturedLogs()).thenReturn(new LinkedList<LoggingEvent>());

        boolean found = hasLogMatcher.matchesSafely(captor);

        assertFalse(found);
    }

    @Test
    public void shouldReturnFalseWhenNoMatches() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(false);

        boolean found = hasLogMatcher.matchesSafely(captor);

        assertFalse(found);
    }

    @Test
    public void shouldReturnTrueWhenOneLogEntryMatchesExpectations() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(false, true);

        boolean found = hasLogMatcher.matchesSafely(captor);

        assertTrue(found);
        verify(logExpectations, times(2)).fulfillsExpectations(any(Log4jEntry.class));
    }

    @Test
    public void shouldReturnTrueOnFirstMatchingLogEntry() throws Exception {
        when(logExpectations.fulfillsExpectations(any(Log4jEntry.class))).thenReturn(true, false);

        boolean found = hasLogMatcher.matchesSafely(captor);

        assertTrue(found);
        verify(logExpectations, times(1)).fulfillsExpectations(any(Log4jEntry.class));
    }

}

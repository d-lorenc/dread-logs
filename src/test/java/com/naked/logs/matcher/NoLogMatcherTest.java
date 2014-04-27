package com.naked.logs.matcher;

import static org.apache.log4j.Level.DEBUG;
import static org.apache.log4j.Level.INFO;
import static org.apache.log4j.Level.TRACE;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.StringDescription;
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
public class NoLogMatcherTest {

    @Mock
    private Log4jCaptor captor;
    @Mock
    private LoggingEvent logOne, logTwo;
    @Mock
    private Log4jEntry logEntryOne, logEntryTwo;
    @Captor
    private ArgumentCaptor<Log4jEntry> entryCaptor;

    private StringDescription description;
    private List<LoggingEvent> logs;

    private NoLogMatcher<Log4jCaptor, LoggingEvent, Level> noLogMatcher;

    @Before
    public void before() throws Exception {
        noLogMatcher = new NoLogMatcher<Log4jCaptor, LoggingEvent, Level>("expected message") {
            @Override
            protected LogEntry<Level> createLogEntry(LoggingEvent log) {
                if (log == logOne) {
                    return logEntryOne;
                }
                return logEntryTwo;
            }
        };

        description = new StringDescription();

        logs = new LinkedList<LoggingEvent>();
        logs.add(logOne);
        logs.add(logTwo);
        when(captor.getCapturedLogs()).thenReturn(logs);

        when(logEntryOne.getMessage()).thenReturn("message one");
        when(logEntryOne.getLevel()).thenReturn(DEBUG);
        when(logEntryTwo.getMessage()).thenReturn("message two");
        when(logEntryTwo.getLevel()).thenReturn(TRACE);

    }

    @Test
    public void shouldReturnTrueWhenMessageNotFound() throws Exception {

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertTrue(notFound);
    }

    @Test
    public void shouldReturnTrueWhenMessageNotFoundOnLevel() throws Exception {
        noLogMatcher.onLevel(INFO);

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertTrue(notFound);
    }

    @Test
    public void shouldReturnFalseWhenFoundMatchingMessage() throws Exception {
        when(logEntryOne.getMessage()).thenReturn("expected message");

        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertFalse(notFound);
    }

    @Test
    public void shouldReturnFalseWhenFoundMatchingMessageOnLevel() throws Exception {
        when(logEntryOne.getMessage()).thenReturn("expected message");
        when(logEntryOne.getLevel()).thenReturn(INFO);

        noLogMatcher.onLevel(INFO);
        boolean notFound = noLogMatcher.matchesSafely(captor);

        assertFalse(notFound);
    }

    @Test
    public void shouldCreateDescriptionWhenNoLevelAndLoggerName() throws Exception {
        noLogMatcher.describeTo(description);

        assertThat(description.toString(), equalTo("No [ANY LEVEL] logs containing message [expected message]"));
    }

    @Test
    public void shouldCreateDescriptionWithLevel() throws Exception {
        noLogMatcher.onLevel(Level.INFO);

        noLogMatcher.describeTo(description);

        assertThat(description.toString(), equalTo("No [INFO] logs containing message [expected message]"));
    }

}

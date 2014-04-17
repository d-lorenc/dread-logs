package com.naked.logs.log4j.inmemory.matcher;

import static org.apache.log4j.Level.DEBUG;
import static org.apache.log4j.Level.INFO;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.log4j.inmemory.Log4jInMemoryLogger;

@RunWith(MockitoJUnitRunner.class)
public class Log4jNoLogMatcherTest {

    @Mock
    private Log4jInMemoryLogger inMemoryLog4jLogger;
    @Mock
    private LoggingEvent event;

    private List<LoggingEvent> events;
    private Log4jNoLogMatcher noLogMatcher;
    private StringDescription description;

    @Before
    public void before() throws Exception {
        events = new LinkedList<LoggingEvent>();
        events.add(event);
        when(inMemoryLog4jLogger.getEvents()).thenReturn(events);

        description = new StringDescription();

        noLogMatcher = new Log4jNoLogMatcher("expected message");
    }

    @Test
    public void shouldReturnTrueWhenMessageNotFound() throws Exception {
        when(event.getMessage()).thenReturn("another message");

        boolean matches = noLogMatcher.matchesSafely(inMemoryLog4jLogger);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnTrueWhenMessageNotFoundOnLevel() throws Exception {
        when(event.getMessage()).thenReturn("expected message");
        when(event.getLevel()).thenReturn(DEBUG);

        noLogMatcher.onLevel(INFO);
        boolean matches = noLogMatcher.matchesSafely(inMemoryLog4jLogger);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenFoundMatchingMessage() throws Exception {
        when(event.getMessage()).thenReturn("expected message");

        boolean matches = noLogMatcher.matchesSafely(inMemoryLog4jLogger);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenFoundMatchingMessageOnLevel() throws Exception {
        when(event.getMessage()).thenReturn("expected message");
        when(event.getLevel()).thenReturn(DEBUG);

        noLogMatcher.onLevel(DEBUG);
        boolean matches = noLogMatcher.matchesSafely(inMemoryLog4jLogger);

        assertFalse(matches);
    }

    @Test
    public void shouldCreateDescriptionWhenNoLevelAndLoggerName() throws Exception {
        noLogMatcher.describeTo(description);

        assertThat(description.toString(), equalTo("NO [ALL] logs containing message [expected message]"));
    }

    @Test
    public void shouldCreateDescriptionWithLevel() throws Exception {
        noLogMatcher.onLevel(Level.INFO);

        noLogMatcher.describeTo(description);

        assertThat(description.toString(), equalTo("NO [INFO] logs containing message [expected message]"));
    }

}

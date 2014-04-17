package com.naked.logs.log4j.inmemory.matcher;

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
public class Log4jHasLogMatcherTest {

    @Mock
    private Log4jInMemoryLogger inMemoryLog4jLogger;
    @Mock
    private LoggingEvent event;

    private List<LoggingEvent> events;
    private Log4jHasLogMatcher hasLogMatcher;
    private StringDescription description;

    @Before
    public void before() throws Exception {
        events = new LinkedList<LoggingEvent>();
        events.add(event);
        when(inMemoryLog4jLogger.getEvents()).thenReturn(events);

        description = new StringDescription();

        hasLogMatcher = new Log4jHasLogMatcher("expected message");
    }

    @Test
    public void shouldReturnTrueWhenMessageMatches() throws Exception {
        when(event.getMessage()).thenReturn("expected message");

        boolean matches = hasLogMatcher.matchesSafely(inMemoryLog4jLogger);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMessageMatch() throws Exception {
        when(event.getMessage()).thenReturn("another message");

        boolean matches = hasLogMatcher.matchesSafely(inMemoryLog4jLogger);

        assertFalse(matches);
    }

    @Test
    public void shouldCreateDescriptionWhenNoLevelAndLoggerName() throws Exception {
        hasLogMatcher.describeTo(description);

        assertThat(description.toString(), equalTo("\"<ALL [...] expected message>\""));
    }

    @Test
    public void shouldCreateDescriptionWithLevel() throws Exception {
        hasLogMatcher.onLevel(Level.INFO);

        hasLogMatcher.describeTo(description);

        assertThat(description.toString(), equalTo("\"<INFO [...] expected message>\""));
    }

    @Test
    public void shouldCreateDescriptionWithLoggerName() throws Exception {
        hasLogMatcher.withLoggerName("com.logger.name");

        hasLogMatcher.describeTo(description);

        assertThat(description.toString(), equalTo("\"<ALL com.logger.name expected message>\""));
    }

}

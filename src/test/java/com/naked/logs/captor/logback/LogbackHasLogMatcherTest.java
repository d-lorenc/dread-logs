package com.naked.logs.captor.logback;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;
import com.naked.logs.captor.logback.LogbackHasLogMatcher;

@RunWith(MockitoJUnitRunner.class)
public class LogbackHasLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private ILoggingEvent log;

    private LogbackHasLogMatcher logbackHasLogMatcher;

    @Before
    public void before() throws Exception {
        logbackHasLogMatcher = new LogbackHasLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateLog4jEntryFromLoggingEvent() throws Exception {
        when(log.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = logbackHasLogMatcher.createLogEntry(log);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}
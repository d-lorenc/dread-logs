package com.naked.logs.logback.captor.matcher;

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

import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.LogExpectations;

@RunWith(MockitoJUnitRunner.class)
public class LogbackNoLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private ILoggingEvent log;

    private LogbackNoLogMatcher logbackNoLogMatcher;

    @Before
    public void before() throws Exception {
        logbackNoLogMatcher = new LogbackNoLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateLogbackEntryFromLoggingEvent() throws Exception {
        when(log.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = logbackNoLogMatcher.createLogEntry(log);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}

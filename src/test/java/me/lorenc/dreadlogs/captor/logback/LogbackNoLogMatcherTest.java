package me.lorenc.dreadlogs.captor.logback;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;
import me.lorenc.dreadlogs.captor.logback.LogbackNoLogMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

@RunWith(MockitoJUnitRunner.class)
public class LogbackNoLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private ILoggingEvent loggingEvent;

    private LogbackNoLogMatcher logbackNoLogMatcher;

    @Before
    public void before() throws Exception {
        logbackNoLogMatcher = new LogbackNoLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateLogbackEntryFromLoggingEvent() throws Exception {
        when(loggingEvent.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = logbackNoLogMatcher.createLogEntry(loggingEvent);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}

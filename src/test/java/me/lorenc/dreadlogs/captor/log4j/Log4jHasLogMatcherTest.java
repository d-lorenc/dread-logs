package me.lorenc.dreadlogs.captor.log4j;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;

@RunWith(MockitoJUnitRunner.class)
public class Log4jHasLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private LoggingEvent loggingEvent;

    private Log4jHasLogMatcher log4jHasLogMatcher;

    @Before
    public void before() throws Exception {
        log4jHasLogMatcher = new Log4jHasLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateLog4jEntryFromLoggingEvent() throws Exception {
        when(loggingEvent.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = log4jHasLogMatcher.createLogEntry(loggingEvent);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}

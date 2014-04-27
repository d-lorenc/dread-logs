package com.naked.logs.log4j.captor.matcher;

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

import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.LogExpectations;

@RunWith(MockitoJUnitRunner.class)
public class Log4jHasLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private LoggingEvent log;

    private Log4jHasLogMatcher log4jHasLogMatcher;

    @Before
    public void before() throws Exception {
        log4jHasLogMatcher = new Log4jHasLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateLog4jEntryFromLoggingEvent() throws Exception {
        when(log.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = log4jHasLogMatcher.createLogEntry(log);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}
package com.naked.logs.captor.log4j;

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

import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;
import com.naked.logs.captor.log4j.Log4jNoLogMatcher;

@RunWith(MockitoJUnitRunner.class)
public class Log4jNoLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private LoggingEvent loggingEvent;

    private Log4jNoLogMatcher log4jNoLogMatcher;

    @Before
    public void before() throws Exception {
        log4jNoLogMatcher = new Log4jNoLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateLog4jEntryFromLoggingEvent() throws Exception {
        when(loggingEvent.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = log4jNoLogMatcher.createLogEntry(loggingEvent);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}

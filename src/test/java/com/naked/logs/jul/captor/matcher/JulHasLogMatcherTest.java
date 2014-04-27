package com.naked.logs.jul.captor.matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.LogExpectations;


@RunWith(MockitoJUnitRunner.class)
public class JulHasLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private LogRecord log;

    private JulHasLogMatcher julHasLogMatcher;

    @Before
    public void before() throws Exception {
        julHasLogMatcher = new JulHasLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateLog4jEntryFromLoggingEvent() throws Exception {
        when(log.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = julHasLogMatcher.createLogEntry(log);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}

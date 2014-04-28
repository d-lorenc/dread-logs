package com.naked.logs.captor.jul;

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

import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;
import com.naked.logs.captor.jul.JulHasLogMatcher;


@RunWith(MockitoJUnitRunner.class)
public class JulHasLogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private LogRecord logRecord;

    private JulHasLogMatcher julHasLogMatcher;

    @Before
    public void before() throws Exception {
        julHasLogMatcher = new JulHasLogMatcher(logExpectations);
    }

    @Test
    public void shouldCreateJulEntryFromLogRecord() throws Exception {
        when(logRecord.getMessage()).thenReturn("message");

        LogEntry<Level> logEntry = julHasLogMatcher.createLogEntry(logRecord);

        assertThat(logEntry.getMessage(), equalTo("message"));
    }

}

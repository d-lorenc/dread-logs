package com.naked.logs.captor;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogCaptureTest {

    @Mock
    private LoggingEvent event, anotherEvent;

    private LogCapture<LoggingEvent> logCapture;

    @Before
    public void before() throws Exception {
        logCapture = new LogCapture<LoggingEvent>();
    }

    @Test
    public void shouldCaptureLog() throws Exception {
        logCapture.capture(event);

        List<LoggingEvent> capturedLogs = logCapture.getCapturedLogs();

        assertThat(capturedLogs, hasSize(1));
        assertThat(capturedLogs, hasItem(event));
    }

    @Test
    public void shouldClearCapturedLogsOnReset() throws Exception {
        logCapture.capture(event);

        logCapture.reset();

        assertThat(logCapture.getCapturedLogs(), empty());
    }

    @Test
    public void shouldReturnSnapshotOfCapturedLogs() throws Exception {
        logCapture.capture(event);

        List<LoggingEvent> capturedLogs = logCapture.getCapturedLogs();

        logCapture.capture(anotherEvent);

        assertThat(capturedLogs, hasSize(1));
        assertThat(capturedLogs, hasItem(event));
    }

    @Test
    public void shouldNotBeAbleToAddToCapturedLogList() throws Exception {
        logCapture.capture(event);

        logCapture.getCapturedLogs().add(anotherEvent);

        assertThat(logCapture.getCapturedLogs(), hasItem(event));
        assertThat(logCapture.getCapturedLogs(), not(hasItem(anotherEvent)));
    }

}

package com.naked.logs.captor.logback;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.captor.LogCapture;

@RunWith(MockitoJUnitRunner.class)
public class LogbackCapturingAppenderTest {

    @Mock
    private LogCapture<ILoggingEvent> logCapture;
    @Mock
    private ILoggingEvent loggingEvent;

    private LogbackCapturingAppender capturingAppender;

    @Before
    public void before() throws Exception {
        capturingAppender = new LogbackCapturingAppender(logCapture);
    }

    @Test
    public void shouldCaptureLoggingEvent() throws Exception {

        capturingAppender.append(loggingEvent);

        verify(logCapture, only()).capture(loggingEvent);
    }

    @Test
    public void shouldResetLogCapture() throws Exception {

        capturingAppender.reset();

        verify(logCapture, only()).reset();
    }

    @Test
    public void shouldReturnCapturedLogs() throws Exception {
        List<ILoggingEvent> expectedList = new LinkedList<ILoggingEvent>();
        when(logCapture.getCapturedLogs()).thenReturn(expectedList);

        List<ILoggingEvent> capturedLogs = capturingAppender.getCapturedLogs();

        assertThat(capturedLogs, sameInstance(expectedList));

        verify(logCapture, only()).getCapturedLogs();
    }

}

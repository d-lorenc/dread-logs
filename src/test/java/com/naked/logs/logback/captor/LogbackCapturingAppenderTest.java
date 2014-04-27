package com.naked.logs.logback.captor;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.captor.LogCapture;

@RunWith(MockitoJUnitRunner.class)
public class LogbackCapturingAppenderTest {

    @Mock
    private LogCapture<ILoggingEvent> logCapture;
    @Mock
    private ILoggingEvent event;

    @InjectMocks
    private LogbackCapturingAppender capturingAppender;

    @Test
    public void shouldCaptureLoggingEvent() throws Exception {

        capturingAppender.append(event);

        verify(logCapture).capture(event);
        verifyNoMoreInteractions(logCapture);
    }

    @Test
    public void shouldResetLogCapture() throws Exception {

        capturingAppender.reset();

        verify(logCapture).reset();
        verifyNoMoreInteractions(logCapture);
    }

    @Test
    public void shouldReturnCapturedLogs() throws Exception {
        List<ILoggingEvent> expectedList = new LinkedList<ILoggingEvent>();
        when(logCapture.getCapturedLogs()).thenReturn(expectedList);

        List<ILoggingEvent> capturedLogs = capturingAppender.getCapturedLogs();

        assertThat(capturedLogs, sameInstance(expectedList));

        verify(logCapture).getCapturedLogs();
        verifyNoMoreInteractions(logCapture);
    }

}

package com.naked.logs.log4j.captor;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.captor.LogCapture;
import com.naked.logs.log4j.captor.Log4jCapturingAppender;

@RunWith(MockitoJUnitRunner.class)
public class Log4jCapturingAppenderTest {

    @Mock
    private LogCapture<LoggingEvent> logCapture;
    @Mock
    private LoggingEvent event;
    @Mock
    private Layout layout;

    @InjectMocks
    private Log4jCapturingAppender capturingAppender;

    @Before
    public void before() throws Exception {
        when(layout.format(event)).thenReturn("expected message");
    }

    @Test
    public void shouldHaveDefaultConstructor() throws Exception {
        Log4jCapturingAppender capturingAppender = new Log4jCapturingAppender(layout);

        assertThat(capturingAppender.getCapturedLogs(), empty());
    }

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
        List<LoggingEvent> expectedList = new LinkedList<LoggingEvent>();
        when(logCapture.getCapturedLogs()).thenReturn(expectedList);

        List<LoggingEvent> capturedLogs = capturingAppender.getCapturedLogs();

        assertThat(capturedLogs, sameInstance(expectedList));

        verify(logCapture).getCapturedLogs();
        verifyNoMoreInteractions(logCapture);
    }

    @Test
    public void shouldStringifyAppender() throws Exception {
        when(logCapture.getCapturedLogs()).thenReturn(asList(event));

        capturingAppender.append(event);
        String stringLog = capturingAppender.toString();

        assertThat(stringLog, equalTo("expected message"));
    }

    @Test
    public void shouldImplementRestOfHandlerMethods() throws Exception {
        capturingAppender.close();
        boolean requiresLayout = capturingAppender.requiresLayout();

        assertFalse(requiresLayout);
        verifyZeroInteractions(logCapture);
    }

}

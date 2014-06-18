package me.lorenc.dreadlogs.captor.log4j;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import me.lorenc.dreadlogs.captor.LogCapture;
import me.lorenc.dreadlogs.captor.log4j.Log4jCapturingAppender;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Log4jCapturingAppenderTest {

    @Mock
    private LogCapture<LoggingEvent> logCapture;
    @Mock
    private LoggingEvent loggingEvent;
    @Mock
    private Layout layout;

    private Log4jCapturingAppender capturingAppender;

    @Before
    public void before() throws Exception {
        capturingAppender = new Log4jCapturingAppender(layout, logCapture);
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
        List<LoggingEvent> expectedList = new LinkedList<LoggingEvent>();
        when(logCapture.getCapturedLogs()).thenReturn(expectedList);

        List<LoggingEvent> capturedLogs = capturingAppender.getCapturedLogs();

        assertThat(capturedLogs, sameInstance(expectedList));

        verify(logCapture, only()).getCapturedLogs();
    }

    @Test
    public void shouldStringifyAppender() throws Exception {
        when(layout.format(loggingEvent)).thenReturn("expected message");
        when(logCapture.getCapturedLogs()).thenReturn(asList(loggingEvent));

        capturingAppender.append(loggingEvent);
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

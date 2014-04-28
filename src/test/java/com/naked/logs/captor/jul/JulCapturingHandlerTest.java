package com.naked.logs.captor.jul;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.captor.LogCapture;
import com.naked.logs.captor.jul.JulCapturingHandler;

@RunWith(MockitoJUnitRunner.class)
public class JulCapturingHandlerTest {

    @Mock
    private LogCapture<LogRecord> logCapture;
    @Mock
    private LogRecord logRecord;

    @InjectMocks
    private JulCapturingHandler capturingHandler;

    @Test
    public void shouldCaptureLogRecord() throws Exception {

        capturingHandler.publish(logRecord);

        verify(logCapture).capture(logRecord);
        verifyNoMoreInteractions(logCapture);
    }

    @Test
    public void shouldResetLogCapture() throws Exception {

        capturingHandler.reset();

        verify(logCapture).reset();
        verifyNoMoreInteractions(logCapture);
    }

    @Test
    public void shouldReturnCapturedLogs() throws Exception {
        List<LogRecord> expectedList = new LinkedList<LogRecord>();
        when(logCapture.getCapturedLogs()).thenReturn(expectedList);

        List<LogRecord> capturedLogs = capturingHandler.getCapturedLogs();

        assertSame(expectedList, capturedLogs);

        verify(logCapture).getCapturedLogs();
        verifyNoMoreInteractions(logCapture);
    }

    @Test
    public void shouldImplementRestOfHandlerMethods() throws Exception {
        capturingHandler.flush();
        capturingHandler.close();

        verifyZeroInteractions(logCapture);
    }

}

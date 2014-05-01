package com.naked.logs.captor.jul;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.captor.LogCapture;

@RunWith(MockitoJUnitRunner.class)
public class JulCapturingHandlerTest {

    @Mock
    private LogCapture<LogRecord> logCapture;
    @Mock
    private LogRecord logRecord;

    private JulCapturingHandler capturingHandler;


    @Before
    public void before() throws Exception {
        capturingHandler = new JulCapturingHandler(logCapture);
    }

    @Test
    public void shouldCaptureLogRecord() throws Exception {

        capturingHandler.publish(logRecord);

        verify(logCapture, only()).capture(logRecord);
    }

    @Test
    public void shouldResetLogCapture() throws Exception {

        capturingHandler.reset();

        verify(logCapture, only()).reset();
    }

    @Test
    public void shouldReturnCapturedLogs() throws Exception {
        List<LogRecord> expectedList = new LinkedList<LogRecord>();
        when(logCapture.getCapturedLogs()).thenReturn(expectedList);

        List<LogRecord> capturedLogs = capturingHandler.getCapturedLogs();

        assertThat(capturedLogs, sameInstance(expectedList));

        verify(logCapture, only()).getCapturedLogs();
    }

    @Test
    public void shouldImplementRestOfHandlerMethods() throws Exception {
        capturingHandler.flush();
        capturingHandler.close();

        verifyZeroInteractions(logCapture);
    }

}

package com.naked.logs.jul.captor;

import static java.util.logging.Level.INFO;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.jul.captor.JulCapturingHandler;
import com.naked.logs.jul.captor.JulCaptor;

@RunWith(MockitoJUnitRunner.class)
public class JulCaptorTest {

    @Mock
    private Logger logger;
    @Mock
    private JulCapturingHandler handler;

    private JulCaptor capturingLogger;

    @Before
    public void before() throws Exception {
        capturingLogger = new JulCaptor(logger, handler, INFO);
    }

    @Test
    public void shouldCreateLoggerWithLoggerNameOnly() throws Exception {
        new JulCaptor("name.only");
    }

    @Test
    public void shouldCreateLoggerWithLoggerNameAndLevel() throws Exception {
        new JulCaptor("name.and.level", INFO);
    }

    @Test
    public void shouldSetLoggerLevelOnConstruction() throws Exception {
        verify(logger).setLevel(INFO);
    }

    @Test
    public void shouldAttachAppenderToLoggerOnConstruction() throws Exception {
        verify(logger).addHandler(handler);
    }

    @Test
    public void shouldDetachAppender() throws Exception {
        capturingLogger.detachAppender();

        verify(logger).removeHandler(handler);
    }

    @Test
    public void shouldReturnLoggingEvents() throws Exception {
        List<LogRecord> expectedEvents = new LinkedList<LogRecord>();
        when(handler.getCapturedLogs()).thenReturn(expectedEvents);

        List<LogRecord> actualEvents = capturingLogger.getCapturedLogs();

        assertThat(actualEvents, sameInstance(expectedEvents));
    }

}

package me.lorenc.dreadlogs.captor.jul;

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

@RunWith(MockitoJUnitRunner.class)
public class JulCaptorTest {

    @Mock
    private Logger logger;
    @Mock
    private JulCapturingHandler handler;

    private JulCaptor julCaptor;

    @Before
    public void before() throws Exception {
        julCaptor = new JulCaptor(logger, handler, INFO);
    }

    @Test
    public void shouldCreateWithLoggerNameOnly() throws Exception {
        new JulCaptor("name.only");
    }

    @Test
    public void shouldCreateWithLoggerNameAndLevel() throws Exception {
        new JulCaptor("name.and.level", INFO);
    }

    @Test
    public void shouldSetLoggerLevelOnConstruction() throws Exception {
        verify(logger).setLevel(INFO);
    }

    @Test
    public void shouldAttachHandlerToLoggerOnConstruction() throws Exception {
        verify(logger).addHandler(handler);
    }

    @Test
    public void shouldDetachAppender() throws Exception {
        julCaptor.detachAppender();

        verify(logger).removeHandler(handler);
    }

    @Test
    public void shouldDetachAppenderInAfter() throws Exception {
        julCaptor.after();

        verify(logger).removeHandler(handler);
    }

    @Test
    public void shouldReturnLogRecords() throws Exception {
        List<LogRecord> expectedRecords = new LinkedList<LogRecord>();
        when(handler.getCapturedLogs()).thenReturn(expectedRecords);

        List<LogRecord> actualRecords = julCaptor.getCapturedLogs();

        assertThat(actualRecords, sameInstance(expectedRecords));
    }

}

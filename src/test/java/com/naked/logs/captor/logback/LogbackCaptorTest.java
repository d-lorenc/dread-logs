package com.naked.logs.captor.logback;

import static ch.qos.logback.classic.Level.INFO;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Logger.class) //due to the Logger's 'final' nature the power (mock) needs to be used
public class LogbackCaptorTest {

    @Mock
    private Logger logger;
    @Mock
    private LogbackCapturingAppender appender;

    private LogbackCaptor logbackCaptor;

    @Before
    public void before() throws Exception {
        logbackCaptor = new LogbackCaptor(logger, appender, INFO);
    }

    @Test
    public void shouldCreateWithLoggerNameOnly() throws Exception {
        new LogbackCaptor("name.only");
    }

    @Test
    public void shouldCreateWithLoggerNameAndLevel() throws Exception {
        new LogbackCaptor("name.and.level", INFO);
    }

    @Test
    public void shouldSetLoggerLevelOnConstruction() throws Exception {
        verify(logger).setLevel(INFO);
    }

    @Test
    public void shouldAttachAppenderToLoggerAndStartItOnConstruction() throws Exception {
        InOrder inOrder = inOrder(logger, appender);
        inOrder.verify(logger).addAppender(appender);
        inOrder.verify(appender).start();;
    }

    @Test
    public void shouldDetachAppender() throws Exception {
        logbackCaptor.detachAppender();

        verify(logger).detachAppender(appender);
    }

    @Test
    public void shouldReturnLoggingEvents() throws Exception {
        List<ILoggingEvent> expectedEvents = new LinkedList<ILoggingEvent>();
        when(appender.getCapturedLogs()).thenReturn(expectedEvents);

        List<ILoggingEvent> actualEvents = logbackCaptor.getCapturedLogs();

        assertThat(actualEvents, sameInstance(expectedEvents));
    }

}

package com.naked.logs.matcher;

import static org.apache.log4j.Level.INFO;
import static org.apache.log4j.Level.WARN;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class LogExpectationsTest {

    private LogExpectations<Level> logExpectations;

    @Mock
    private LogEntry<Level> logEntry;

    @Before
    public void before() throws Exception {
        logExpectations = new LogExpectations<Level>();

        when(logEntry.getMessage()).thenReturn("a message");
        when(logEntry.getLevel()).thenReturn(INFO);
        when(logEntry.getLoggerName()).thenReturn("com.naked.logs.a.package");
        when(logEntry.getExceptionMessage()).thenReturn("exception message");
        when(logEntry.getExceptionClassName()).thenReturn(NullPointerException.class.getName());
        when(logEntry.getMdcValue("a key")).thenReturn("a value");

        logExpectations.setExpectedMessage("a message");
        logExpectations.setExpectedLevel(INFO);
        logExpectations.setExpectedLoggerName("com.naked.logs.a.package");
        logExpectations.setExpectedExceptionMessage("exception message");
        logExpectations.setExpectedExceptionClass(NullPointerException.class);
        logExpectations.addExpectedMdc("a key", "a value");
    }

    @Test
    public void shouldReturnTrueWhenNoExpectationsSpecified() throws Exception {
        LogExpectations<Level> emptyLogExpectations = new LogExpectations<Level>();

        boolean matches = emptyLogExpectations.fulfilsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnTrueWhenAllExpectationsMatch() throws Exception {

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMessageMatch() throws Exception {
        logExpectations.setExpectedMessage("another message");

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenMessageMatcherDoesNotMatch() throws Exception {
        logExpectations.setExpectedMessage(containsString("something else"));

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnTrueWhenMessageMatcherMatches() throws Exception {
        logExpectations.setExpectedMessage(containsString("message"));

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoLevelMatch() throws Exception {
        logExpectations.setExpectedLevel(WARN);

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoLoggerNameMatch() throws Exception {
        logExpectations.setExpectedLoggerName("com.another.package");

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoExceptionNameMatch() throws Exception {
        logExpectations.setExpectedExceptionMessage("another exception message");

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnTrueWhenExceptionMessageMatcherMatches() throws Exception {
        logExpectations.setExpectedExceptionMessage(containsString("exception"));

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoExceptionClassMatch() throws Exception {
        logExpectations.setExpectedExceptionClass(IllegalArgumentException.class);

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMdcFound() throws Exception {
        logExpectations.addExpectedMdc("another key", "another value");

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMdcValueMatch() throws Exception {
        logExpectations.addExpectedMdc("a key", "another value");

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenExpectedMdcNull() throws Exception {
        logExpectations.addExpectedMdc("a key", null);

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnTrueWhenNullMdcValuesMatch() throws Exception {
        when(logEntry.getMdcValue("a key")).thenReturn(null);
        logExpectations.addExpectedMdc("a key", null);

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnTrueWhenMutlipleMdcsMatch() throws Exception {
        when(logEntry.getMdcValue("another key")).thenReturn("another value");
        logExpectations.addExpectedMdc("a key", "a value");
        logExpectations.addExpectedMdc("another key", "another value");

        boolean matches = logExpectations.fulfilsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldStringify() throws Exception {

        assertThat(logExpectations.toString(), equalTo("Level:[INFO] LoggerName:[com.naked.logs.a.package] MDC:[{a key=a value}] "
                + "ExceptionClass:[java.lang.NullPointerException] ExceptionMessage:[\"exception message\"] Message:[\"a message\"]"));
    }

}

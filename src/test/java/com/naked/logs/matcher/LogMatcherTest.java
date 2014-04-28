package com.naked.logs.matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.log4j.captor.Log4jCaptor;

@RunWith(MockitoJUnitRunner.class)
public class LogMatcherTest {

    @Mock
    private LogExpectations<Level> logExpectations;
    @Mock
    private Matcher<String> stringMatcher;
    @Mock
    private Description description;
    @Mock
    private LoggingEvent log;
    @Mock
    private LogEntry<Level> logEntry;
    @Captor
    private ArgumentCaptor<LogEntry<Level>> entryCaptor;

    private LogMatcher<Log4jCaptor, LoggingEvent, Level> logMatcher;

    @Before
    public void before() throws Exception {
        logMatcher = new LogMatcher<Log4jCaptor, LoggingEvent, Level>(logExpectations) {


            @Override
            protected LogEntry<Level> createLogEntry(LoggingEvent log) {
                return logEntry;
            }

            @Override
            protected boolean matchesSafely(Log4jCaptor captor) {
                return false;
            }
        };
    }

    @Test
    public void shouldSetExpectedMessage() throws Exception {
        String expectedMessage = "expected message";
        logMatcher.withMessage(expectedMessage);

        verify(logExpectations).setExpectedMessage(expectedMessage);
    }

    @Test
    public void shouldSetExpectedMessageMatcher() throws Exception {
        logMatcher.withMessage(stringMatcher);

        verify(logExpectations).setExpectedMessage(stringMatcher);
    }

    @Test
    public void shouldSetExpectedLevel() throws Exception {
        Level expectedLevel = Level.TRACE;
        logMatcher.onLevel(expectedLevel);

        verify(logExpectations).setExpectedLevel(expectedLevel);
    }

    @Test
    public void shouldSetExpectedLoggerName() throws Exception {
        String expectedLoggerName = "com.naked.logs.some.package";
        logMatcher.withLoggerName(expectedLoggerName);

        verify(logExpectations).setExpectedLoggerName(expectedLoggerName);
    }

    @Test
    public void shouldSetExpectedExceptionMessage() throws Exception {
        String expectedExceptionMessage = "exception message";
        logMatcher.withExceptionMessage(expectedExceptionMessage);

        verify(logExpectations).setExpectedExceptionMessage(expectedExceptionMessage);
    }

    @Test
    public void shouldSetExpectedExceptionMessageMatcher() throws Exception {
        logMatcher.withExceptionMessage(stringMatcher);

        verify(logExpectations).setExpectedExceptionMessage(stringMatcher);
    }

    @Test
    public void shouldSetExpectedExceptionClass() throws Exception {
        Class<? extends Throwable> expectedExceptionClass = Throwable.class;
        logMatcher.withExceptionClass(expectedExceptionClass);

        verify(logExpectations).setExpectedExceptionClass(expectedExceptionClass);
    }

    @Test
    public void shouldSetExpectedExceptionMessageAndClass() throws Exception {
        String expectedExceptionMessage = "exception message";
        Class<? extends Throwable> expectedExceptionClass = Throwable.class;
        logMatcher.withException(expectedExceptionClass, expectedExceptionMessage);

        verify(logExpectations).setExpectedExceptionMessage(expectedExceptionMessage);
        verify(logExpectations).setExpectedExceptionClass(expectedExceptionClass);
    }

    @Test
    public void shouldSetExpectedExceptionMessageMatcherAndClass() throws Exception {
        Class<? extends Throwable> expectedExceptionClass = Throwable.class;
        logMatcher.withException(expectedExceptionClass, stringMatcher);

        verify(logExpectations).setExpectedExceptionMessage(stringMatcher);
        verify(logExpectations).setExpectedExceptionClass(expectedExceptionClass);
    }

    @Test
    public void shouldAddExpectedMdc() throws Exception {
        String expectedMdcKey = "key";
        String expectedMdcValue = "value";
        logMatcher.withMdc(expectedMdcKey, expectedMdcValue);

        verify(logExpectations).addExpectedMdc(expectedMdcKey, expectedMdcValue);
    }

    @Test
    public void shouldAppendDescription() throws Exception {
        String logExpectationsString = "log expectations";
        when(logExpectations.toString()).thenReturn(logExpectationsString);

        logMatcher.describeTo(description);

        verify(description).appendValue(logExpectationsString);
    }

    @Test
    public void shouldCallFulfillExpectationsForLogEntry() throws Exception {
        when(logExpectations.fulfillsExpectations(logEntry)).thenReturn(true);

        boolean fulfills = logMatcher.fulfillsExpectations(log);

        assertThat(fulfills, equalTo(true));

        verify(logExpectations).fulfillsExpectations(entryCaptor.capture());
        assertThat(entryCaptor.getValue(), sameInstance(logEntry));
    }

}

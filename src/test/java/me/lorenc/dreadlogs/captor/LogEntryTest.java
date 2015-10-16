package me.lorenc.dreadlogs.captor;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogEntryTest {

    private class TestLogEntry extends LogEntry<Level> {

        private boolean mdcSupported;
        private String message;
        private String loggerName;
        private Level level;
        private String exceptionMessage;
        private String exceptionClassName;
        private final Map<String, String> mdcMap = new HashMap<String, String>();

        @Override
        public boolean isMdcSupported() {
            return mdcSupported;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getMdcValue(String mdcKey) {
            return mdcMap.get(mdcKey);
        }

        @Override
        public String getLoggerName() {
            return loggerName;
        }

        @Override
        public Level getLevel() {
            return level;
        }

        @Override
        public String getExceptionMessage() {
            return exceptionMessage;
        }

        @Override
        public String getExceptionClassName() {
            return exceptionClassName;
        }

        @Override
        public Map<String, ?> getMdcMap() {
            return mdcMap;
        }
    }

    @Mock
    private LogExpectations<Level> logExpectations;

    private TestLogEntry logEntry;

    @Before
    public void before() throws Exception {
        logEntry = new TestLogEntry();

        logEntry.level = Level.DEBUG;
        logEntry.loggerName = "some.package";
        logEntry.exceptionClassName = "SomeExceptionClass";
        logEntry.exceptionMessage = "Exception message";
        logEntry.message = "Log message";
        logEntry.mdcSupported = true;
        logEntry.mdcMap.put("key", "value");
    }

    @Test
    public void shouldStringify() throws Exception {
        String string = logEntry.toString();

        assertThat(string, equalTo(
                "Level:[DEBUG] "
                + "LoggerName:[some.package] "
                + "MDC:[{key=value}] "
                + "ExceptionClass:[SomeExceptionClass] "
                + "ExceptionMessage:[Exception message] "
                + "Message:[Log message]"));
    }

    @Test
    public void shouldStringifyForNullValues() throws Exception {
        logEntry.level = null;
        logEntry.loggerName = null;
        logEntry.exceptionClassName = null;
        logEntry.exceptionMessage = null;
        logEntry.message = null;
        logEntry.mdcSupported = true;
        logEntry.mdcMap.clear();

        String string = logEntry.toString();

        assertThat(string, equalTo(
                "Level:[] "
                + "LoggerName:[] "
                + "MDC:[] "
                + "ExceptionClass:[] "
                + "ExceptionMessage:[] "
                + "Message:[]"));
    }

    @Test
    public void shouldStringifyWithoutMdcWhenNotSupported() throws Exception {
        logEntry.mdcSupported = false;

        String string = logEntry.toString();

        assertThat(string, equalTo(
                "Level:[DEBUG] "
                + "LoggerName:[some.package] "
                + "ExceptionClass:[SomeExceptionClass] "
                + "ExceptionMessage:[Exception message] "
                + "Message:[Log message]"));
    }

    @Test
    public void shouldStringifyOnlyExpectedValues() throws Exception {
        when(logExpectations.isMessageSet()).thenReturn(true);

        String string = logEntry.toStringOnlyExpected(logExpectations);

        assertThat(string, equalTo("Message:[Log message]"));
    }

    @Test
    public void shouldStringifyAllIfAllExpected() throws Exception {
        when(logExpectations.isLevelSet()).thenReturn(true);
        when(logExpectations.isLogerNameSet()).thenReturn(true);
        when(logExpectations.isExceptionClassSet()).thenReturn(true);
        when(logExpectations.isExceptionMessageSet()).thenReturn(true);
        when(logExpectations.isMdcSet()).thenReturn(true);
        when(logExpectations.isMessageSet()).thenReturn(true);

        logEntry.mdcSupported = true;

        String string = logEntry.toStringOnlyExpected(logExpectations);

        assertThat(string, equalTo(
                "Level:[DEBUG] "
                + "LoggerName:[some.package] "
                + "MDC:[{key=value}] "
                + "ExceptionClass:[SomeExceptionClass] "
                + "ExceptionMessage:[Exception message] "
                + "Message:[Log message]"));
    }

    @Test
    public void shouldStringifyAllExpectedExceptMdcIfNotSupported() throws Exception {
        when(logExpectations.isLevelSet()).thenReturn(true);
        when(logExpectations.isLogerNameSet()).thenReturn(true);
        when(logExpectations.isExceptionClassSet()).thenReturn(true);
        when(logExpectations.isExceptionMessageSet()).thenReturn(true);
        when(logExpectations.isMdcSet()).thenReturn(true);
        when(logExpectations.isMessageSet()).thenReturn(true);

        logEntry.mdcSupported = false;

        String string = logEntry.toStringOnlyExpected(logExpectations);

        assertThat(string, equalTo(
                "Level:[DEBUG] "
                        + "LoggerName:[some.package] "
                        + "ExceptionClass:[SomeExceptionClass] "
                        + "ExceptionMessage:[Exception message] "
                        + "Message:[Log message]"));
    }

}

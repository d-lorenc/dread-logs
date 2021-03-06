package me.lorenc.dreadlogs.captor;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matcher;

public class LogExpectations<LEVEL> {

    private LEVEL expectedLevel;
    private Matcher<String> expectedMessageMatcher;
    private String expectedLoggerName;
    private Matcher<String> expectedExceptionMessageMatcher;
    private Class<? extends Throwable> expectedExceptionClass;
    private final Map<String, String> expectedMdcs = new HashMap<String, String>();

    public boolean fulfillsExpectations(LogEntry<LEVEL> logEntry) {
        return eventHasCorrectLevel(logEntry.getLevel())
                && matchesMessage(logEntry.getMessage())
                && matchesLoggerName(logEntry.getLoggerName())
                && matchesExceptionMessage(logEntry.getExceptionMessage())
                && matchesExceptionClass(logEntry.getExceptionClassName())
                && matchesMdcs(logEntry);
    }

    private boolean eventHasCorrectLevel(LEVEL level) {
        return expectedLevel == null || expectedLevel.equals(level);
    }

    private boolean matchesMessage(String message) {
        return expectedMessageMatcher == null || expectedMessageMatcher.matches(message);
    }

    private boolean matchesLoggerName(String loggerName) {
        return expectedLoggerName == null || expectedLoggerName.equals(loggerName);
    }

    private boolean matchesExceptionMessage(String exceptionMessage) {
        return expectedExceptionMessageMatcher == null || expectedExceptionMessageMatcher.matches(exceptionMessage);
    }

    private boolean matchesExceptionClass(String exceptionClassName) {
        return expectedExceptionClass == null || expectedExceptionClass.getName().equals(exceptionClassName);
    }

    private boolean matchesMdcs(LogEntry<LEVEL> logEntry) {
        if (expectedMdcs.isEmpty()) {
            return true;
        }

        if (!logEntry.isMdcSupported()) {
            throw new UnsupportedOperationException("MDC expectations must not be set for the logger implementation which does not support MDC.");
        }

        for (Entry<String, String> expectedMdc : expectedMdcs.entrySet()) {
            Object mdcValue = logEntry.getMdcValue(expectedMdc.getKey());
            if ((expectedMdc.getValue() != mdcValue) || (mdcValue != null && !mdcValue.equals(expectedMdc.getValue()))) {
                return false;
            }
        }
        return true;
    }

    public void setExpectedLevel(LEVEL expectedLevel) {
        this.expectedLevel = expectedLevel;
    }

    public void setExpectedMessage(String expectedMessage) {
        setExpectedMessage(equalTo(expectedMessage));
    }

    public void setExpectedMessage(Matcher<String> expectedMessageMatcher) {
        this.expectedMessageMatcher = expectedMessageMatcher;
    }

    public void setExpectedLoggerName(String expectedLoggerName) {
        this.expectedLoggerName = expectedLoggerName;
    }

    public void setExpectedExceptionMessage(String expectedExceptionMessage) {
        this.expectedExceptionMessageMatcher = equalTo(expectedExceptionMessage);
    }

    public void setExpectedExceptionMessage(Matcher<String> expectedExceptionMessage) {
        this.expectedExceptionMessageMatcher = expectedExceptionMessage;
    }

    public void setExpectedExceptionClass(Class<? extends Throwable> expectedExceptionClass) {
        this.expectedExceptionClass = expectedExceptionClass;
    }

    public void addExpectedMdc(String expectedMdcKey, String expectedMdcValue) {
        this.expectedMdcs.put(expectedMdcKey, expectedMdcValue);
    }

    @Override
    public String toString() {
        return new OnlyExpectedToStringBuilder()
            .appendIf(isLevelSet(), "Level", expectedLevel)
            .appendIf(isLogerNameSet(), "LoggerName", expectedLoggerName)
            .appendIf(isMdcSet(), "MDC", expectedMdcs)
            .appendIf(isExceptionClassSet(), "ExceptionClass", expectedExceptionClass)
            .appendIf(isExceptionMessageSet(), "ExceptionMessage", expectedExceptionMessageMatcher)
            .appendIf(isMessageSet(), "Message", expectedMessageMatcher)
            .toString();
    }

    public boolean isLevelSet() {
        return expectedLevel != null;
    }

    public boolean isMessageSet() {
        return expectedMessageMatcher != null;
    }

    public boolean isLogerNameSet() {
        return expectedLoggerName != null;
    }

    public boolean isExceptionMessageSet() {
        return expectedExceptionMessageMatcher != null;
    }

    public boolean isExceptionClassSet() {
        return expectedExceptionClass != null;
    }

    public boolean isMdcSet() {
        return !expectedMdcs.isEmpty();
    }

}

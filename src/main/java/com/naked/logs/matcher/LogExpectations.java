package com.naked.logs.matcher;

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

    public boolean fulfilsExpectations(LogEntry<LEVEL> logEntry) {
        return eventHasCorrectLevel(logEntry.getLevel())
                && matchesMessage(logEntry.getMessage())
                && matchesLoggerName(logEntry.getLoggerName())
                && containsExceptionMessage(logEntry.getExceptionMessage())
                && containsExceptionClass(logEntry.getExceptionClassName())
                && containsMdc(logEntry);
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

    private boolean containsExceptionMessage(String exceptionMessage) {
        return expectedExceptionMessageMatcher == null || expectedExceptionMessageMatcher.matches(exceptionMessage);
    }

    private boolean containsExceptionClass(String exceptionClassName) {
        return expectedExceptionClass == null || expectedExceptionClass.getName().equals(exceptionClassName);
    }

    private boolean containsMdc(LogEntry<LEVEL> logEntry) {
        if (expectedMdcs.isEmpty()) {
            return true;
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

    public void setExpectedMessage(Matcher<String> messageMatcher) {
        this.expectedMessageMatcher = messageMatcher;
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
        return String.format("Level:[%s] LoggerName:[%s] MDC:[%s] ExceptionClass:[%s] ExceptionMessage:[%s] Message:[%s]",
                expectedLevel, expectedLoggerName, expectedMdcs, expectedExceptionClass.getName(), expectedExceptionMessageMatcher,
                expectedMessageMatcher != null ? expectedMessageMatcher : "");
    }

}

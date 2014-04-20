package com.naked.logs.logback.captor.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.TypeSafeMatcher;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;

import com.naked.logs.logback.captor.LogbackCaptor;

public abstract class LogbackLogMatcher extends TypeSafeMatcher<LogbackCaptor> {

    private static final String EMPTY_VALUE = "[...]";

    private Level expectedLevel = Level.ALL;
    private String expectedLoggerName;
    private String expectedExceptionMessage;
    private Class<? extends Throwable> expectedExceptionClass;

    private final Map<String, String> expectedMdcs = new HashMap<String, String>();

    protected abstract boolean matchesMessage(ILoggingEvent event);

    protected String defaultIfNull(Object value) {
        if (value == null) {
            return EMPTY_VALUE;
        }
        return value.toString();
    }

    public LogbackLogMatcher onLevel(Level expectedLevel) {
        this.expectedLevel = expectedLevel;
        return this;
    }

    public LogbackLogMatcher withLoggerName(String expectedLoggerName) {
        this.expectedLoggerName = expectedLoggerName;
        return this;
    }

    public LogbackLogMatcher withException(String expectedExceptionMessage) {
        this.expectedExceptionMessage = expectedExceptionMessage;
        return this;
    }

    public LogbackLogMatcher withException(Class<? extends Throwable> expectedExceptionClass) {
        this.expectedExceptionClass = expectedExceptionClass;
        return this;
    }

    public LogbackLogMatcher withMdc(String expectedMdcKey, String expectedMdcValue) {
        expectedMdcs.put(expectedMdcKey, expectedMdcValue);
        return this;
    }

    protected Level getExpectedLevel() {
        return expectedLevel;
    }

    protected String getExpectedLoggerName() {
        return expectedLoggerName;
    }

    @Override
    protected boolean matchesSafely(LogbackCaptor captor) {
        for (ILoggingEvent event : captor.getCapturedLogs()) {
            if (eventHasCorrectLevel(event)
                    && matchesMessage(event)
                    && matchesLoggerName(event)
                    && containsExceptionMessage(event)
                    && containsExceptionClass(event)
                    && containsMdc(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsMdc(ILoggingEvent event) {
        if (expectedMdcs.isEmpty()) {
            return true;
        }

        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
        for (Entry<String, String> mdc : expectedMdcs.entrySet()) {
            Object mdcValue = mdcPropertyMap.get(mdc.getKey());
            if ((mdc.getValue() != mdcValue) || (mdcValue != null && !mdcValue.equals(mdc.getValue()))) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesLoggerName(ILoggingEvent event) {
        if (expectedLoggerName == null) {
            return true;
        }
        return event.getLoggerName().equals(expectedLoggerName);
    }

    private boolean eventHasCorrectLevel(ILoggingEvent event) {
        return (expectedLevel.equals(Level.ALL) || event.getLevel().equals(expectedLevel));
    }

    private boolean containsExceptionMessage(ILoggingEvent event) {
        return messageExceptionCheck.check(event);
    }

    private boolean containsExceptionClass(ILoggingEvent event) {
        return classExceptionCheck.check(event);
    }

    private abstract class ExceptionCheck {

        private boolean check(ILoggingEvent event) {
            if (!expectationExists()) {
                return true;
            }

            IThrowableProxy throwableProxy = event.getThrowableProxy();
            if (throwableProxy != null && check(throwableProxy)) {
                return true;
            }

            return false;
        }

        protected abstract boolean expectationExists();

        protected abstract boolean check(IThrowableProxy throwableProxy);
    }

    private final ExceptionCheck classExceptionCheck = new ExceptionCheck() {

        @Override
        protected boolean expectationExists() {
            return expectedExceptionClass != null;
        }

        @Override
        protected boolean check(IThrowableProxy throwableProxy) {
            if (expectedExceptionClass == null) {
                return true;
            }

            return throwableProxy.getClassName().equals(expectedExceptionClass.getName());
        }
    };

    private final ExceptionCheck messageExceptionCheck = new ExceptionCheck() {

        @Override
        protected boolean expectationExists() {
            return expectedExceptionMessage != null;
        }

        @Override
        protected boolean check(IThrowableProxy throwableProxy) {
            if (expectedExceptionMessage == null) {
                return true;
            }

            return throwableProxy.getMessage().contains(expectedExceptionMessage);
        }
    };

}
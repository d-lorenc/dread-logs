package com.naked.logs.jul.captor.matcher;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.hamcrest.TypeSafeMatcher;

import com.naked.logs.jul.captor.JulCaptor;

public abstract class JulLogMatcher extends TypeSafeMatcher<JulCaptor> {

    private static final String EMPTY_VALUE = "[...]";

    private Level expectedLevel = Level.ALL;
    private String expectedLoggerName;
    private String expectedExceptionMessage;
    private Class<? extends Throwable> expectedExceptionClass;

    protected abstract boolean matchesMessage(LogRecord event);

    protected String defaultIfNull(Object value) {
        if (value == null) {
            return EMPTY_VALUE;
        }
        return value.toString();
    }

    public JulLogMatcher onLevel(Level expectedLevel) {
        this.expectedLevel = expectedLevel;
        return this;
    }

    public JulLogMatcher withLoggerName(String expectedLoggerName) {
        this.expectedLoggerName = expectedLoggerName;
        return this;
    }

    public JulLogMatcher withException(String expectedExceptionMessage) {
        this.expectedExceptionMessage = expectedExceptionMessage;
        return this;
    }

    public JulLogMatcher withException(Class<? extends Throwable> expectedExceptionClass) {
        this.expectedExceptionClass = expectedExceptionClass;
        return this;
    }

    protected Level getExpectedLevel() {
        return expectedLevel;
    }

    protected String getExpectedLoggerName() {
        return expectedLoggerName;
    }

    @Override
    protected boolean matchesSafely(JulCaptor captor) {
        for (LogRecord event : captor.getCapturedLogs()) {
            if (eventHasCorrectLevel(event)
                    && matchesMessage(event)
                    && matchesLoggerName(event)
                    && containsExceptionMessage(event)
                    && containsExceptionClass(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesLoggerName(LogRecord event) {
        if (expectedLoggerName == null) {
            return true;
        }
        return event.getLoggerName().equals(expectedLoggerName);
    }

    private boolean eventHasCorrectLevel(LogRecord event) {
        return (expectedLevel.equals(Level.ALL) || event.getLevel().equals(expectedLevel));
    }

    private boolean containsExceptionMessage(LogRecord event) {
        return messageExceptionCheck.check(event);
    }

    private boolean containsExceptionClass(LogRecord event) {
        return classExceptionCheck.check(event);
    }

    private abstract class ExceptionCheck {

        private boolean check(LogRecord event) {
            if (!expectationExists()) {
                return true;
            }

            if (check(event.getThrown())) {
                return true;
            }

            return false;
        }

        protected abstract boolean expectationExists();

        protected abstract boolean check(Throwable throwable);
    }

    private final ExceptionCheck classExceptionCheck = new ExceptionCheck() {

        @Override
        protected boolean expectationExists() {
            return expectedExceptionClass != null;
        }

        @Override
        protected boolean check(Throwable throwable) {
            if (expectedExceptionClass == null) {
                return true;
            }

            return throwable.getClass().equals(expectedExceptionClass);
        }
    };

    private final ExceptionCheck messageExceptionCheck = new ExceptionCheck() {

        @Override
        protected boolean expectationExists() {
            return expectedExceptionMessage != null;
        }

        @Override
        protected boolean check(Throwable throwable) {
            if (expectedExceptionMessage == null) {
                return true;
            }

            return throwable.getMessage().contains(expectedExceptionMessage);
        }
    };

}
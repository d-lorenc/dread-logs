package com.naked.logs.log4j.inmemory.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.hamcrest.TypeSafeMatcher;

import com.naked.logs.log4j.inmemory.Log4jInMemoryLogger;

public abstract class Log4jLogMatcher extends TypeSafeMatcher<Log4jInMemoryLogger> {

    private static final String EMPTY_VALUE = "[...]";

    private Level expectedLevel = Level.ALL;
    private String expectedLoggerName;
    private String expectedExceptionMessage;
    private Class<? extends Throwable> expectedExceptionClass;
    private Throwable expectedException;

    private final Map<String, String> expectedMdcs = new HashMap<String, String>();

    protected abstract boolean matchesMessage(LoggingEvent event);

    protected String defaultIfNull(Object value) {
        if (value == null) {
            return EMPTY_VALUE;
        }
        return value.toString();
    }

    public Log4jLogMatcher onLevel(Level expectedLevel) {
        this.expectedLevel = expectedLevel;
        return this;
    }

    public Log4jLogMatcher withLoggerName(String expectedLoggerName) {
        this.expectedLoggerName = expectedLoggerName;
        return this;
    }

    public Log4jLogMatcher withException(String expectedExceptionMessage) {
        this.expectedExceptionMessage = expectedExceptionMessage;
        return this;
    }

    public Log4jLogMatcher withException(Class<? extends Throwable> expectedExceptionClass) {
        this.expectedExceptionClass = expectedExceptionClass;
        return this;
    }

    public Log4jLogMatcher withException(Throwable expectedException) {
        this.expectedException = expectedException;
        return this;
    }

    public Log4jLogMatcher withMdc(String expectedMdcKey, String expectedMdcValue) {
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
    protected boolean matchesSafely(Log4jInMemoryLogger inMemoryLog4jLogger) {
        for (LoggingEvent event : inMemoryLog4jLogger.getEvents()) {
            if (eventHasCorrectLevel(event) 
                    && matchesMessage(event) 
                    && matchesLoggerName(event) 
                    && containsExceptionMessage(event)
                    && containsExceptionClass(event) 
                    && containsException(event) 
                    && containsMdc(event)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean containsMdc(LoggingEvent event) {
        if (expectedMdcs.isEmpty()) {
            return true;
        }

        for (Entry<String, String> mdc : expectedMdcs.entrySet()) {
            Object mdcValue = event.getMDC(mdc.getKey());
            if ((mdc.getValue() != mdcValue) || (mdcValue != null && !mdcValue.equals(mdc.getValue()))) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesLoggerName(LoggingEvent event) {
        if (expectedLoggerName == null) {
            return true;
        }
        return event.getLoggerName().equals(expectedLoggerName);
    }

    private boolean eventHasCorrectLevel(LoggingEvent event) {
        return (expectedLevel.equals(Level.ALL) || event.getLevel().equals(expectedLevel));
    }

    private boolean containsExceptionMessage(LoggingEvent event) {
        return messageExceptionCheck.check(event);
    }

    private boolean containsExceptionClass(LoggingEvent event) {
        return classExceptionCheck.check(event);
    }

    private boolean containsException(LoggingEvent event) {
        return equalsExceptionCheck.check(event);
    }
    
    private abstract class ExceptionCheck {
        
        private boolean check(LoggingEvent event) {
            if (!expectationExists()) {
                return true;
            }
            
            ThrowableInformation throwableInfo = event.getThrowableInformation();
            if (throwableInfo != null && check(throwableInfo.getThrowable())) {
                return true;
            }

            return false;
        }
        
        protected abstract boolean expectationExists();

        protected abstract boolean check(Throwable throwable);
    }
    
    private final ExceptionCheck equalsExceptionCheck = new ExceptionCheck() {
        
        @Override
        protected boolean expectationExists() {
            return expectedException != null;
        }        
        
        @Override
        protected boolean check(Throwable throwable) {
            return throwable.equals(expectedException);
        }
    };
    
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
package com.naked.logs.jul.captor.matcher;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.naked.logs.jul.captor.JulCaptor;

public class JulNoLogMatcher extends TypeSafeMatcher<JulCaptor> {

    private final String unwantedMessage;
    private Level expectedLevel = Level.ALL;

    public JulNoLogMatcher(String unwantedMessage) {
        this.unwantedMessage = unwantedMessage;
    }

    public void describeTo(Description description) {
        description.appendText(String.format("NO [%s] logs containing message [%s]", expectedLevel, unwantedMessage));
    }

    @Override
    protected boolean matchesSafely(JulCaptor captor) {
        for (LogRecord event : captor.getCapturedLogs()) {
            if (eventHasCorrectLevel(event)
                    && event.getMessage().toString().contains(unwantedMessage)) {
                return false;
            }
        }
        return true;
    }

    public JulNoLogMatcher onLevel(Level expectedLevel) {
        this.expectedLevel = expectedLevel;
        return this;
    }

    private boolean eventHasCorrectLevel(LogRecord event) {
        return (expectedLevel.equals(Level.ALL) || event.getLevel().equals(expectedLevel));
    }

}

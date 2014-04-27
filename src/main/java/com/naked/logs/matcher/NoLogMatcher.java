package com.naked.logs.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.naked.logs.captor.Captor;

public abstract class NoLogMatcher<CAPTOR extends Captor<LOG>, LOG, LEVEL> extends TypeSafeMatcher<CAPTOR> {

    private final String unwantedMessage;
    private LEVEL level;

    public NoLogMatcher(String unwantedMessage) {
        this.unwantedMessage = unwantedMessage;
    }

    protected abstract LogEntry<LEVEL> createLogEntry(LOG log);

    public void describeTo(Description description) {
        description.appendText(String.format("No [%s] logs containing message [%s]", levelToString(), unwantedMessage));
    }

    private String levelToString() {
        return level == null ? "ANY LEVEL" : level.toString();
    }

    @Override
    protected boolean matchesSafely(CAPTOR captor) {
        for (LOG log : captor.getCapturedLogs()) {
            LogEntry<LEVEL> logEntry = createLogEntry(log);
            if (eventHasCorrectLevel(logEntry) && containsUnwantedMessage(logEntry)) {
                return false;
            }
        }
        return true;
    }

    public NoLogMatcher<CAPTOR, LOG, LEVEL> onLevel(LEVEL level) {
        this.level = level;
        return this;
    }

    private boolean eventHasCorrectLevel(LogEntry<LEVEL> logEntry) {
        return level == null || logEntry.getLevel().equals(level);
    }

    private boolean containsUnwantedMessage(LogEntry<LEVEL> logEntry) {
        return logEntry.getMessage().contains(unwantedMessage);
    }

}


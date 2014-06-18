package me.lorenc.dreadlogs.captor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public abstract class LogMatcher<CAPTOR extends Captor<LOG>, LOG, LEVEL> extends TypeSafeMatcher<CAPTOR> {

    private final LogExpectations<LEVEL> logExpectations;

    public LogMatcher(LogExpectations<LEVEL> logExpectations) {
        this.logExpectations = logExpectations;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withMessage(String expectedMessage) {
        logExpectations.setExpectedMessage(expectedMessage);
        return this;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withMessage(Matcher<String> messageMatcher) {
        logExpectations.setExpectedMessage(messageMatcher);
        return this;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> onLevel(LEVEL expectedLevel) {
        logExpectations.setExpectedLevel(expectedLevel);
        return this;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withLoggerName(String expectedLoggerName) {
        logExpectations.setExpectedLoggerName(expectedLoggerName);
        return this;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withExceptionMessage(String expectedExceptionMessage) {
        logExpectations.setExpectedExceptionMessage(expectedExceptionMessage);
        return this;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withExceptionMessage(Matcher<String> expectedExceptionMessageMatcher) {
        logExpectations.setExpectedExceptionMessage(expectedExceptionMessageMatcher);
        return this;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withExceptionClass(Class<? extends Throwable> expectedExceptionClass) {
        logExpectations.setExpectedExceptionClass(expectedExceptionClass);
        return this;
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withException(Class<? extends Throwable> expectedExceptionClass, String expectedExceptionMessage) {
        return withExceptionClass(expectedExceptionClass).withExceptionMessage(expectedExceptionMessage);
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withException(Class<? extends Throwable> expectedExceptionClass, Matcher<String> expectedExceptionMessageMatcher) {
        return withExceptionClass(expectedExceptionClass).withExceptionMessage(expectedExceptionMessageMatcher);
    }

    public LogMatcher<CAPTOR, LOG, LEVEL> withMdc(String expectedMdcKey, String expectedMdcValue) {
        logExpectations.addExpectedMdc(expectedMdcKey, expectedMdcValue);
        return this;
    }

    protected boolean fulfillsExpectations(LOG log) {
        return logExpectations.fulfillsExpectations(createLogEntry(log));
    }

    public void describeTo(Description description) {
        description.appendText("\n" + logExpectations.toString());
    }

    @Override
    protected void describeMismatchSafely(CAPTOR captor, Description mismatchDescription) {
        List<LOG> capturedLogs = captor.getCapturedLogs();

        if (capturedLogs.isEmpty()) {
            mismatchDescription.appendText("NO captured logs");
            return;
        }

        mismatchDescription.appendText("captured logs\n" + stringifyCapturedLogs(capturedLogs));
    }

    private String stringifyCapturedLogs(List<LOG> capturedLogs) {
        List<String> capturedLogEntryInfos = new LinkedList<String>();
        for (LOG log : capturedLogs) {
            capturedLogEntryInfos.add(createLogEntry(log).toStringOnlyExpected(logExpectations));
        }
        return join(capturedLogEntryInfos);
    }

    private String join(List<String> values) {
        StringBuilder builder = new StringBuilder();

        for (Iterator<String> iterator = values.iterator();;) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append("\n");
            } else {
                break;
            }
        }

        return builder.toString();
    }

    protected abstract LogEntry<LEVEL> createLogEntry(LOG log);

}
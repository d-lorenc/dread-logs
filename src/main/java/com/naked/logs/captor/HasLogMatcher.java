package com.naked.logs.captor;


public abstract class HasLogMatcher<CAPTOR extends Captor<LOG>, LOG, LEVEL> extends LogMatcher<CAPTOR, LOG, LEVEL> {

    public HasLogMatcher(LogExpectations<LEVEL> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected boolean matchesSafely(CAPTOR captor) {
        for (LOG log : captor.getCapturedLogs()) {
            if (fulfillsExpectations(log)) {
                return true;
            }
        }
        return false;
    }

}
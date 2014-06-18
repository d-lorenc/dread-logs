package me.lorenc.dreadlogs.captor;


public abstract class NoLogMatcher<CAPTOR extends Captor<LOG>, LOG, LEVEL> extends LogMatcher<CAPTOR, LOG, LEVEL> {

    public NoLogMatcher(LogExpectations<LEVEL> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected boolean matchesSafely(CAPTOR captor) {
        for (LOG log : captor.getCapturedLogs()) {
            if (fulfillsExpectations(log)) {
                return false;
            }
        }
        return true;
    }

}


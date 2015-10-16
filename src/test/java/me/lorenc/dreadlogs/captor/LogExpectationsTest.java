package me.lorenc.dreadlogs.captor;

import static org.apache.log4j.Level.INFO;
import static org.apache.log4j.Level.WARN;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogExpectationsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private LogEntry<Level> logEntry;

    private LogExpectations<Level> logExpectations;

    @Before
    public void before() throws Exception {
        logExpectations = new LogExpectations<Level>();

        when(logEntry.getMessage()).thenReturn("a message");
        when(logEntry.getLevel()).thenReturn(INFO);
        when(logEntry.getLoggerName()).thenReturn("me.lorenc.dreadlogs.a.package");
        when(logEntry.getExceptionMessage()).thenReturn("exception message");
        when(logEntry.getExceptionClassName()).thenReturn(NullPointerException.class.getName());
        when(logEntry.getMdcValue("a key")).thenReturn("a value");
        when(logEntry.isMdcSupported()).thenReturn(true);

        logExpectations.setExpectedMessage("a message");
        logExpectations.setExpectedLevel(INFO);
        logExpectations.setExpectedLoggerName("me.lorenc.dreadlogs.a.package");
        logExpectations.setExpectedExceptionMessage("exception message");
        logExpectations.setExpectedExceptionClass(NullPointerException.class);
        logExpectations.addExpectedMdc("a key", "a value");
    }

    @Test
    public void shouldReturnTrueWhenNoExpectationsSpecified() throws Exception {
        LogExpectations<Level> emptyLogExpectations = new LogExpectations<Level>();

        boolean matches = emptyLogExpectations.fulfillsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnTrueWhenAllExpectationsMatch() throws Exception {

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMessageMatch() throws Exception {
        logExpectations.setExpectedMessage("another message");

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMessageMatcherMatch() throws Exception {
        logExpectations.setExpectedMessage(containsString("something else"));

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnTrueWhenMessageMatcherMatches() throws Exception {
        logExpectations.setExpectedMessage(containsString("message"));

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoLevelMatch() throws Exception {
        logExpectations.setExpectedLevel(WARN);

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoLoggerNameMatch() throws Exception {
        logExpectations.setExpectedLoggerName("com.another.package");

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoExceptionMessageMatch() throws Exception {
        logExpectations.setExpectedExceptionMessage("another exception message");

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoExceptionMessageMetcherMatch() throws Exception {
        logExpectations.setExpectedExceptionMessage(containsString("another message"));

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnTrueWhenExceptionMessageMatcherMatches() throws Exception {
        logExpectations.setExpectedExceptionMessage(containsString("exception"));

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoExceptionClassMatch() throws Exception {
        logExpectations.setExpectedExceptionClass(IllegalArgumentException.class);

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMdcFound() throws Exception {
        logExpectations.addExpectedMdc("another key", "another value");

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenNoMdcValueMatch() throws Exception {
        logExpectations.addExpectedMdc("a key", "another value");

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnFalseWhenExpectedMdcNull() throws Exception {
        logExpectations.addExpectedMdc("a key", null);

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertFalse(matches);
    }

    @Test
    public void shouldReturnTrueWhenNullMdcValuesMatch() throws Exception {
        when(logEntry.getMdcValue("a key")).thenReturn(null);
        logExpectations.addExpectedMdc("a key", null);

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldReturnTrueWhenMutlipleMdcsMatch() throws Exception {
        when(logEntry.getMdcValue("another key")).thenReturn("another value");
        logExpectations.addExpectedMdc("a key", "a value");
        logExpectations.addExpectedMdc("another key", "another value");

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldThrowUnsupportedOperationExceptionWhenMdcSetButNotSupported() throws Exception {
        when(logEntry.isMdcSupported()).thenReturn(false);

        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("MDC expectations must not be set for the logger implementation which does not support MDC.");

        logExpectations.fulfillsExpectations(logEntry);
    }

    @Test
    public void shouldReturnTrueWhenMdcNotSupportedAndNotSet() throws Exception {
        when(logEntry.isMdcSupported()).thenReturn(false);
        LogExpectations<Level> logExpectations = new LogExpectations<Level>();

        boolean matches = logExpectations.fulfillsExpectations(logEntry);

        assertTrue(matches);
    }

    @Test
    public void shouldStringify() throws Exception {
        assertThat(logExpectations.toString(), equalTo("Level:[INFO] LoggerName:[me.lorenc.dreadlogs.a.package] MDC:[{a key=a value}] "
                + "ExceptionClass:[java.lang.NullPointerException] ExceptionMessage:[\"exception message\"] Message:[\"a message\"]"));
    }

    @Test
    public void shouldStringifyToEmptyStringWhenAllNullValues() throws Exception {
        logExpectations = new LogExpectations<Level>();

        String string = logExpectations.toString();

        assertThat(string, equalTo(""));
    }

    @Test
    public void shouldStringifyOnlySetValues() throws Exception {
        logExpectations = new LogExpectations<Level>();
        logExpectations.setExpectedLevel(INFO);
        logExpectations.setExpectedLoggerName("me.lorenc.dreadlogs.a.package");
        logExpectations.setExpectedMessage("a message");

        String string = logExpectations.toString();

        assertThat(string, equalTo("Level:[INFO] LoggerName:[me.lorenc.dreadlogs.a.package] Message:[\"a message\"]"));
    }

    @Test
    public void shouldStringifyWithMatchers() throws Exception {
        logExpectations = new LogExpectations<Level>();
        logExpectations.setExpectedMessage(containsString("a message"));

        String string = logExpectations.toString();

        assertThat(string, equalTo("Message:[a string containing \"a message\"]"));
    }

    @Test
    public void shouldReturnFalseIfNotExpectingSpecificLevel() throws Exception {
        logExpectations = new LogExpectations<Level>();

        boolean levelSet = logExpectations.isLevelSet();

        assertFalse(levelSet);
    }

    @Test
    public void shouldReturnTrueIfExpectingSpecificLevel() throws Exception {

        boolean levelSet = logExpectations.isLevelSet();

        assertTrue(levelSet);
    }

    @Test
    public void shouldReturnFalseIfNotExpectingSpecificMessage() throws Exception {
        logExpectations = new LogExpectations<Level>();

        boolean messageSet = logExpectations.isMessageSet();

        assertFalse(messageSet);
    }

    @Test
    public void shouldReturnTrueIfExpectingSpecificMessage() throws Exception {

        boolean messageSet = logExpectations.isMessageSet();

        assertTrue(messageSet);
    }

    @Test
    public void shouldReturnFalseIfNotExpectingSpecificLogerName() throws Exception {
        logExpectations = new LogExpectations<Level>();

        boolean logerNameSet = logExpectations.isLogerNameSet();

        assertFalse(logerNameSet);
    }

    @Test
    public void shouldReturnTrueIfExpectingSpecificLoggerName() throws Exception {

        boolean logerNameSet = logExpectations.isLogerNameSet();

        assertTrue(logerNameSet);
    }

    @Test
    public void shouldReturnFalseIfNotExpectingSpecificExceptionMessage() throws Exception {
        logExpectations = new LogExpectations<Level>();

        boolean exceptionMessageSet = logExpectations.isExceptionMessageSet();

        assertFalse(exceptionMessageSet);
    }

    @Test
    public void shouldReturnTrueIfExpectingSpecificExceptionMessage() throws Exception {

        boolean exceptionMessageSet = logExpectations.isExceptionMessageSet();

        assertTrue(exceptionMessageSet);
    }

    @Test
    public void shouldReturnFalseIfNotExpectingSpecificExceptionClass() throws Exception {
        logExpectations = new LogExpectations<Level>();

        boolean exceptionClassSet = logExpectations.isExceptionClassSet();

        assertFalse(exceptionClassSet);
    }

    @Test
    public void shouldReturnTrueIfExpectingSpecificExceptionClass() throws Exception {

        boolean exceptionClassSet = logExpectations.isExceptionClassSet();

        assertTrue(exceptionClassSet);
    }

    @Test
    public void shouldReturnFalseIfNotExpectingSpecificMdc() throws Exception {
        logExpectations = new LogExpectations<Level>();

        boolean mdcSet = logExpectations.isMdcSet();

        assertFalse(mdcSet);
    }

    @Test
    public void shouldReturnTrueIfExpectingSpecificMdc() throws Exception {

        boolean mdcSet = logExpectations.isMdcSet();

        assertTrue(mdcSet);
    }

}

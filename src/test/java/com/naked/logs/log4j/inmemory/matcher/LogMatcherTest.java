package com.naked.logs.log4j.inmemory.matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.log4j.Log4jInMemoryLogger;


@RunWith(MockitoJUnitRunner.class)
public class LogMatcherTest {

	@Mock
	private Log4jInMemoryLogger inMemoryLog4jLogger;
	@Mock
	private LoggingEvent event;

	private NullPointerException exception = new NullPointerException("exception message");
	private List<LoggingEvent> events;
	
	private LogMatcher logMatcher;

	@Before
	public void before() throws Exception {
		when(event.getLevel()).thenReturn(Level.DEBUG);
		when(event.getLoggerName()).thenReturn("com.some.package");
		when(event.getThrowableInformation()).thenReturn(new ThrowableInformation(exception));
		when(event.getMDC("key")).thenReturn("value");
		when(event.getMDC("another key")).thenReturn("value");
		
		events = new LinkedList<LoggingEvent>();
		events.add(event);
		when(inMemoryLog4jLogger.getEvents()).thenReturn(events);
		
		logMatcher = new LogMatcher(){
			
			public void describeTo(Description arg0) {
			}
			
			@Override
			protected boolean matchesMessage(LoggingEvent event) {
				return true;
			}
		};
	}
	
	@Test
	public void shouldReturnFalseWhenNoEvents() throws Exception {
		when(inMemoryLog4jLogger.getEvents()).thenReturn(new LinkedList<LoggingEvent>());
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}

	@Test
	public void shouldReturnTrueWhenExpectationsNotSpecified() throws Exception {
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoLevelMatch() throws Exception {
		logMatcher.onLevel(Level.WARN);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnTrueWhenLevelMatches() throws Exception {
		logMatcher.onLevel(Level.DEBUG);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoLoggerNameMatch() throws Exception {
		logMatcher.withLoggerName("com.another.package");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnTrueWhenLoggerNameMatches() throws Exception {
		logMatcher.withLoggerName("com.some.package");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoExceptionNameMatch() throws Exception {
		logMatcher.withException("another exception message");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnTrueWhenExceptionNameMatches() throws Exception {
		logMatcher.withException("exception message");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoThrowabaleInformationForExceptionNameMatch() throws Exception {
		when(event.getThrowableInformation()).thenReturn(null);
		logMatcher.withException("exception message");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoExceptionClassMatch() throws Exception {
		logMatcher.withException(IllegalArgumentException.class);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}

	@Test
	public void shouldReturnTrueWhenExceptionClassMatches() throws Exception {
		logMatcher.withException(NullPointerException.class);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoThrowableInfromationOnExceptionClassMatch() throws Exception {
		when(event.getThrowableInformation()).thenReturn(null);
		logMatcher.withException(IllegalArgumentException.class);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoExceptionMatch() throws Exception {
		logMatcher.withException(new IllegalArgumentException("exception message"));
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnTrueWhenExceptionMatches() throws Exception {
		logMatcher.withException(exception);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoThrowableInformationOnExceptionMatch() throws Exception {
		when(event.getThrowableInformation()).thenReturn(null);
		logMatcher.withException(new IllegalArgumentException("exception message"));
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoMdcMatch() throws Exception {
		logMatcher.withMdc("another key", "another value");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenMdcNull() throws Exception {
		when(event.getMDC("key null")).thenReturn(null);
		logMatcher.withMdc("key null", "value");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenExpectedMdcNull() throws Exception {
		when(event.getMDC("key null")).thenReturn("value");
		logMatcher.withMdc("key null", null);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnTrueWhenExpectedMdcNull() throws Exception {
		when(event.getMDC("key null")).thenReturn(null);
		logMatcher.withMdc("key null", null);
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnTrueWhenMdcMatches() throws Exception {
		logMatcher.withMdc("key", "value");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoAllMdcsMatch() throws Exception {
		logMatcher.withMdc("key", "value");
		logMatcher.withMdc("another key", "another value");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldReturnTrueWhenAllMdcsMatch() throws Exception {
		logMatcher.withMdc("key", "value");
		logMatcher.withMdc("another key", "value");
		
		boolean matches = logMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnDefaultPlaceholderIfNull() throws Exception {
		String value = logMatcher.defaultIfNull(null);
		
		assertThat(value, equalTo("[...]"));
	}
	
	@Test
	public void shouldReturnStringValueIfNotNull() throws Exception {
		String value = logMatcher.defaultIfNull("not null");
		
		assertThat(value, equalTo("not null"));
	}
	
}

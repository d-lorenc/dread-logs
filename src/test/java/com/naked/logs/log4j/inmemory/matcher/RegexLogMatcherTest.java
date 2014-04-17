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
import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.log4j.Log4jInMemoryLogger;


@RunWith(MockitoJUnitRunner.class)
public class RegexLogMatcherTest {

	@Mock
	private Log4jInMemoryLogger inMemoryLog4jLogger;
	@Mock
	private LoggingEvent event;

	private List<LoggingEvent> events;
	private RegexLogMatcher regexLogMatcher;
	private StringDescription description;

	@Before
	public void before() throws Exception {
		events = new LinkedList<LoggingEvent>();
		events.add(event);
		when(inMemoryLog4jLogger.getEvents()).thenReturn(events);

		description = new StringDescription();
		
		regexLogMatcher = new RegexLogMatcher("^expected message$");
	}
	
	@Test
	public void shouldReturnTrueWhenMessageRegexMatches() throws Exception {
		when(event.getMessage()).thenReturn("expected message");
		
		boolean matches = regexLogMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertTrue(matches);
	}
	
	@Test
	public void shouldReturnFalseWhenNoMessageRegexMatch() throws Exception {
		when(event.getMessage()).thenReturn("another message");
		
		boolean matches = regexLogMatcher.matchesSafely(inMemoryLog4jLogger);
		
		assertFalse(matches);
	}
	
	@Test
	public void shouldCreateDescriptionWhenNoLevelAndLoggerName() throws Exception {
		regexLogMatcher.describeTo(description);
		
		assertThat(description.toString(), equalTo("\"<ALL [...] [matches: ^expected message$]>\""));
	}

	@Test
	public void shouldCreateDescriptionWithLevel() throws Exception {
		regexLogMatcher.onLevel(Level.INFO);
		
		regexLogMatcher.describeTo(description);
		
		assertThat(description.toString(), equalTo("\"<INFO [...] [matches: ^expected message$]>\""));
	}
	
	@Test
	public void shouldCreateDescriptionWithLoggerName() throws Exception {
		regexLogMatcher.withLoggerName("com.logger.name");
		
		regexLogMatcher.describeTo(description);
		
		assertThat(description.toString(), equalTo("\"<ALL com.logger.name [matches: ^expected message$]>\""));
	}
	
}

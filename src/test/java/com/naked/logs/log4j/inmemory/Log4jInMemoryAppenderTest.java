package com.naked.logs.log4j.inmemory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.naked.logs.log4j.inmemory.Log4jInMemoryAppender;


@RunWith(MockitoJUnitRunner.class)
public class Log4jInMemoryAppenderTest {

	@Mock
	private LoggingEvent event;
	@Mock
	private Layout layout;
	
	@InjectMocks
	private Log4jInMemoryAppender inMemoryAppender;
	
	
	@Before
	public void before() throws Exception {
		when(layout.format(event)).thenReturn("expecte message");
	}
	
	@Test
	public void shouldStringifyAppender() throws Exception {
		
		inMemoryAppender.append(event);
		String stringLog = inMemoryAppender.toString();
		
		assertThat(stringLog, equalTo("expecte message"));
	}

	@Test
	public void shouldReturnEvents() throws Exception {
		inMemoryAppender.append(event);
		
		List<LoggingEvent> events = inMemoryAppender.getCopyOfEvents();
		assertThat(events.get(0), sameInstance(event));
	}
	
	@Test
	public void shouldReturnSnapshotOfEvents() throws Exception {
		inMemoryAppender.append(event);
		List<LoggingEvent> events = inMemoryAppender.getCopyOfEvents();

		assertThat(events.size(), equalTo(1));
		assertThat(events.get(0), sameInstance(event));
		
		inMemoryAppender.append(event);
		
		assertThat(events.size(), equalTo(1));
		assertThat(events.get(0), sameInstance(event));
	}
	
	@Test
	public void shouldResetEvents() throws Exception {
		inMemoryAppender.append(event);
		assertThat(inMemoryAppender.getCopyOfEvents().size(), equalTo(1));

		inMemoryAppender.reset();;
		assertThat(inMemoryAppender.getCopyOfEvents().size(), equalTo(0));
	}
	
	@Test
	public void shouldNotPrintToStdOutByDefault() throws Exception {
		
		StdOutCaptor stdOutCaptor = new StdOutCaptor();
		stdOutCaptor.start();
		
		try {
			inMemoryAppender.enableStdOutLogging();
			
			inMemoryAppender.append(event);
		
			assertThat(stdOutCaptor.getAsSring(), equalTo("expecte message\n"));
		} finally {		
			stdOutCaptor.stop();
		}
	}
	
	@Test
	public void shouldPrintToStdOut() throws Exception {
		
		StdOutCaptor stdOutCaptor = new StdOutCaptor();
		stdOutCaptor.start();
		
		try {
			inMemoryAppender.append(event);
		
			assertThat(stdOutCaptor.getAsSring(), isEmptyString());
		} finally {		
			stdOutCaptor.stop();
		}
	}
	
	private static class StdOutCaptor {
		
		private PrintStream originalSystemOut;
		private ByteArrayOutputStream out;

		public void start() {
			originalSystemOut = System.out;
			out = new ByteArrayOutputStream();
			System.setOut(new PrintStream(out));
		}
		
		public void stop() {
			System.setOut(originalSystemOut);			
		}

		public String getAsSring() {
			return out.toString();
		}
	}
	
}

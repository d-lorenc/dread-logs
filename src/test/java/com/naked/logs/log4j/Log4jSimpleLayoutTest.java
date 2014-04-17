package com.naked.logs.log4j;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Log4jSimpleLayoutTest {

	@Mock
	private LoggingEvent event;
	
	@Test
	public void shouldFormatLoggingEvent() throws Exception {
		when(event.getLevel()).thenReturn(Level.INFO);
		when(event.getMessage()).thenReturn("a message");
		when(event.getLoggerName()).thenReturn("com.some.package");
		
		Log4jSimpleLayout layout = new Log4jSimpleLayout();
		
		String format = layout.format(event);
		
		assertThat(format, equalTo("INFO com.some.package a message"));
	}
	
}

package com.naked.logs.log4j.functional;

import static com.naked.logs.log4j.inmemory.matcher.LogMatchers.hasLog;
import static com.naked.logs.log4j.inmemory.matcher.LogMatchers.hasLogMatching;
import static com.naked.logs.log4j.inmemory.matcher.LogMatchers.noLog;
import static org.apache.log4j.Level.DEBUG;
import static org.apache.log4j.Level.ERROR;
import static org.apache.log4j.Level.FATAL;
import static org.apache.log4j.Level.INFO;
import static org.apache.log4j.Level.TRACE;
import static org.apache.log4j.Level.WARN;
import static org.junit.Assert.assertThat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.naked.logs.log4j.Log4jInMemoryLogger;

public class Log4jInMemoryLoggerFunctionalTest {
	
    private static final String LOGGER_NAME = "com.logfi.some.package";
	private Logger logger;
	private Log4jInMemoryLogger inMemoryLogger;
    
	@Before
	public void before() throws Exception {
		logger = Logger.getLogger(LOGGER_NAME);
		inMemoryLogger = new Log4jInMemoryLogger(LOGGER_NAME);
	}
	
	@After
	public void after() throws Exception {
		inMemoryLogger.removeAppender();
	}
	
	@Test
    public void shouldCaptureFatalLog() throws Exception {
        
        logger.fatal("fatal message");

        assertThat(inMemoryLogger, hasLog("fatal message").onLevel(FATAL));
    }

	@Test
	public void shouldCaptureFatalLogWithException() throws Exception {
		
		logger.fatal("fatal message", new Throwable("an exception"));
		
		assertThat(inMemoryLogger, hasLog("fatal message").onLevel(FATAL).withException("an exception"));
	}

	@Test
    public void shouldCaptureErrorLog() throws Exception {
        
        logger.error("error message");

        assertThat(inMemoryLogger, hasLog("error message").onLevel(ERROR));
    }

	@Test
	public void shouldCaptureErrorLogWithException() throws Exception {
		
		logger.error("error message", new Throwable("an exception"));
		
		assertThat(inMemoryLogger, hasLog("error message").onLevel(ERROR).withException("an exception"));
	}

	@Test
	public void shouldCaptureWarnLog() throws Exception {
		
		logger.warn("warn message");
		
		assertThat(inMemoryLogger, hasLog("warn message").onLevel(WARN));
	}
	
	@Test
	public void shouldCaptureWarnLogWithException() throws Exception {
		
		logger.warn("warn message", new Throwable("an exception"));
		
		assertThat(inMemoryLogger, hasLog("warn message").onLevel(WARN).withException("an exception"));
	}
	
	@Test
	public void shouldCaptureInfoLog() throws Exception {
		
		logger.info("info message");
		
		assertThat(inMemoryLogger, hasLog("info message").onLevel(INFO));
	}
	
	@Test
	public void shouldCaptureInfoLogWithException() throws Exception {
		
		logger.info("info message", new Throwable("an exception"));
		
		assertThat(inMemoryLogger, hasLog("info message").onLevel(INFO).withException("an exception"));
	}	
	
	@Test
	public void shouldCaptureDebugLog() throws Exception {
		
		logger.debug("debug message");
		
		assertThat(inMemoryLogger, hasLog("debug message").onLevel(DEBUG));
	}
	
	@Test
	public void shouldCaptureDebugLogWithException() throws Exception {
		
		logger.debug("debug message", new Throwable("an exception"));
		
		assertThat(inMemoryLogger, hasLog("debug message").onLevel(DEBUG).withException("an exception"));
	}
	
	@Test
	public void shouldCaptureTraceLog() throws Exception {
		
		logger.trace("trace message");
		
		assertThat(inMemoryLogger, hasLog("trace message").onLevel(TRACE));
	}
	
	@Test
	public void shouldCaptureTraceLogWithException() throws Exception {
		
		logger.trace("trace message", new Throwable("an exception"));
		
		assertThat(inMemoryLogger, hasLog("trace message").onLevel(TRACE).withException("an exception"));
	}

    @Test
    public void shouldNotCaptureFatalLogIfLevelDisabled() throws Exception {
        Log4jInMemoryLogger inMemoryLogger = new Log4jInMemoryLogger(LOGGER_NAME, Level.OFF);
        
        logger.fatal("fatal message");
        
        assertThat(inMemoryLogger, noLog("fatal message"));
    }
	
    @Test
    public void shouldNotCaptureErrorLogIfLevelDisabled() throws Exception {
        Log4jInMemoryLogger inMemoryLogger = new Log4jInMemoryLogger(LOGGER_NAME, Level.FATAL);
        
        logger.error("error message");

        assertThat(inMemoryLogger, noLog("error message"));
    }

    @Test
    public void shouldNotCaptureInfoLogIfLevelDisabled() throws Exception {
        Log4jInMemoryLogger inMemoryLogger = new Log4jInMemoryLogger(LOGGER_NAME, Level.WARN);

        logger.info("info message");

        assertThat(inMemoryLogger, noLog("info message"));
    }

    @Test
    public void shouldNotCaptureDebugLogIfLevelDisabled() throws Exception {
        Log4jInMemoryLogger inMemoryLogger = new Log4jInMemoryLogger(LOGGER_NAME, Level.INFO);

        logger.debug("debug message");

        assertThat(inMemoryLogger, noLog("debug message"));
    }

    @Test
    public void shouldNotCaptureTraceLogIfLevelDisabled() throws Exception {
        Log4jInMemoryLogger inMemoryLogger = new Log4jInMemoryLogger(LOGGER_NAME, Level.DEBUG);

        logger.trace("trace message");

        assertThat(inMemoryLogger, noLog("trace message"));
    }
    
    @Test
    public void shouldCaptureLogWithCorrectMessage() throws Exception {
        
        logger.info("a message");
        
        assertThat(inMemoryLogger, hasLog("a message"));
    }
    
    @Test
    public void shouldCaptureLogWithCorrectLevel() throws Exception {
        
        logger.info("a message");
        
        assertThat(inMemoryLogger, hasLog("a message").onLevel(INFO));
    }

    @Test
    public void shouldCaptureLogWithCorrectLoggerName() throws Exception {
    	
    	logger.info("a message");
    	
    	assertThat(inMemoryLogger, hasLog("a message").withLoggerName(LOGGER_NAME));
    }

    @Test
    public void shouldCaptureLogWithCorrectExceptionClass() throws Exception {
    	
    	logger.error("a message", new IllegalArgumentException("illegal argument"));
    	
    	assertThat(inMemoryLogger, hasLog("a message").withException(IllegalArgumentException.class).withException("illegal argument"));
    }
    
    @Test
    public void shouldCaptureLogWithCorrectException() throws Exception {
    	IllegalArgumentException expectedException = new IllegalArgumentException("illegal argument");
    	
		logger.error("a message", expectedException);
    	
    	assertThat(inMemoryLogger, hasLog("a message").withException(expectedException));
    }
    
    @Test
    public void shouldCaptureLogWithCorrectMdcValue() throws Exception {
    	
    	MDC.put("key", "value");
    	logger.info("a message");
    	MDC.remove("key");
    	
    	assertThat(inMemoryLogger, hasLog("a message").withMdc("key", "value"));
    }
    
    @Test
    public void shouldNotFindLogsOnDifferentLevels() throws Exception {
    	
    	logger.info("a message");
    	
    	assertThat(inMemoryLogger, hasLog("a message").onLevel(INFO));

    	assertThat(inMemoryLogger, noLog("a message").onLevel(FATAL));
    	assertThat(inMemoryLogger, noLog("a message").onLevel(ERROR));
    	assertThat(inMemoryLogger, noLog("a message").onLevel(WARN));
    	assertThat(inMemoryLogger, noLog("a message").onLevel(DEBUG));
    	assertThat(inMemoryLogger, noLog("a message").onLevel(TRACE));
    }
    
    @Test
    public void shouldCaptureLogWithMatchingMessage() throws Exception {
    	
    	logger.info("a regex message");
    	
    	assertThat(inMemoryLogger, hasLogMatching("a regex message"));
    	assertThat(inMemoryLogger, hasLogMatching("^a regex message$"));
    	assertThat(inMemoryLogger, hasLogMatching("^a [a-z]* message$"));
    }
    
}

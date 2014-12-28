dread-logs
==========

[![Build Status](https://travis-ci.org/d-lorenc/dread-logs.svg?branch=master)](https://travis-ci.org/d-lorenc/dread-logs) [![Coverage Status](https://coveralls.io/repos/d-lorenc/dread-logs/badge.png?branch=master)](https://coveralls.io/r/d-lorenc/dread-logs?branch=master)
[ ![Codeship Status for d-lorenc/dread-logs](https://codeship.com/projects/8d9c5bc0-706d-0132-8c23-465f6b223ee2/status?branch=master)](https://codeship.com/projects/54515)

A Java library for testing logs. It supports most commonly used logging libraries, namely [log4j](http://logging.apache.org/log4j), [Logback](http://logback.qos.ch/) and Java logging framework (JUL = java.util.logging). It is aimed for unit testing (TDD) but can also be used in integration and functional tests as long as they run in the same JVM as your system under test.

example
------
```java
@Test
public void shouldCaptureLogWithMessage() throws Exception {
    //create a log captor which attaches an extra appender to collect logs in memory
    Log4jCaptor captor = new Log4jCaptor("me.lorenc.dreadlogs");

    //execute code which logs something
    Logger logger = Logger.getLogger("me.lorenc.dreadlogs.some.package");
    logger.info("a message");

    //verify that the captor has your log message
    assertThat(captor, hasLog("a message").onLevel(INFO));

    //clean up - by detaching appender you make sure it stops collecting any further logs, otherwise you may end up with OOME
    captor.detachAppender();
}
```
or like this:
```java
private Logger logger;
private Log4jCaptor captor;

@Before
public void before() throws Exception {
    logger = Logger.getLogger("me.lorenc.dreadlogs.some.package");
    captor = new Log4jCaptor("me.lorenc.dreadlogs");
}

@After
public void after() throws Exception {
    captor.detachAppender();
}

@Test
public void shouldCaptureLogWithMessageSimples() throws Exception {

    logger.info("a message");

    assertThat(captor, hasLog("a message"));
}
```
<dd>* hasLog is a static method in me.lorenc.dreadlogs.captor.log4j.Log4jMatchers</dd>

For more examples, please check out functional tests:
* [log4j](https://github.com/d-lorenc/dread-logs/tree/master/src/test/java/me/lorenc/dreadlogs/captor/log4j/functional)
* [logback](https://github.com/d-lorenc/dread-logs/tree/master/src/test/java/me/lorenc/dreadlogs/captor/logback/functional)
* [jul](https://github.com/d-lorenc/dread-logs/tree/master/src/test/java/me/lorenc/dreadlogs/captor/jul/functional)

install
-------
##### maven
```xml
<dependency>
    <groupId>me.lorenc.dreadlogs</groupId>
	<artifactId>dreadlogs</artifactId>
	<version>0.1</version>
</dependency>
```
##### gradle
```
'me.lorenc.dreadlogs:dreadlogs:0.1'
```
##### sbt
```
libraryDependencies += "me.lorenc.dreadlogs" % "dreadlogs" % "0.1"
```
##### dependencies
Hamcrest, and of course a logging library of your choice (log4j or logback-classic), which you should already have in your project.  

how it works
------------
It uses log appenders which attach to the logging library and capture and store log events in memory. A few available matchers allow for easy assertions. Apart from checking for a log message, the matchers let you specify expected level, logger name (package), exception details or even MDC values.

why "capturing appenders"
------------------------
There is definitely more than one way to test the logs. In a unit test a logger can be mocked. That way any interactions with the logger API can be verified. The other extreme would be writing logs to a file (or a stream) and test the actual file/stream content, including line formatting etc. That is not ideal for unit testing (involves file system), but may be preferable for functional tests.
The "capturing appender" is a solution in the middle, which is fast and isolated enough for unit testing. At the same time it can be applied in other types of tests, as long as they run in the same JVM as the systems which writes the logs. A good example would be a lightweight independent system component which can run on its own (e.g. inside embedded container). 
Such a component can be started from a test, which means that everything can run within the same JVM.

motivation
----------
I have been working on many agile projects, where we would follow TDD and/or BDD principles, and quite often write some tests to check logs too. Unfortunately I haven't come across any library which would help in that. I would usually create some sort of in-memory-appender with a bunch of classes to make log assertions neat and simple. And I would repeat the same process for many projects (different logging framework, employer). So here is my attempt to create a library, which covers the most common Java log frameworks, and simplifies verification of logs in tests. As this makes testing logs quite trivial, you may even stop asking yourself whether you should be testing logs or not.

java version
------------
1.5 or later

import ch.qos.logback.classic.Level

statusListener(OnConsoleStatusListener)

// To get the level of logging from env
def _level = System.getenv("APP_LOG_LEVEL")
_level = (_level != null ? _level.toUpperCase() : "INFO")

// To get the log pattern from env
def _pattern = System.getenv("APP_LOG_PATTERN")
_pattern = (_pattern != null ? _pattern :
	"%d{ISO8601} %-5level [%t] %c{1.}: %msg%n%throwable")
	
// To get the loggers level from env
def _loggers = System.getenv("APP_LOG_LOGGERS")
if( null != _loggers ){
  _loggers.split(',').each {
    def _logger = it.split(':')
  	logger(_logger[0], Level.toLevel(_logger[1]))
  }
}

def appenders = ["CONSOLE"]

appender("CONSOLE", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = _pattern
  }
}

// logger("kafka", ERROR)
// logger("org.apache", ERROR)
// logger("org.springframework", ERROR)
// logger("org.hibernate", ERROR)
// logger("javax.management", ERROR)
// logger("state.change", ERROR)

root(Level.toLevel(_level), appenders)

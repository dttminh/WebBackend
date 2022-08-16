import com.roojai.util.ConstantUtil
import grails.util.Environment
import net.logstash.logback.appender.LogstashTcpSocketAppender
import net.logstash.logback.encoder.LogstashEncoder

// See http://logback.qos.ch/manual/groovy.html for details on configuration

/*
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

root(ERROR, ['STDOUT'])

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}*/

def current_env = Environment.getCurrent();

if( Environment.isDevelopmentMode() ) {
	def USER_HOME = ConstantUtil.getAppLogPath()

	//println "USER_HOME=${USER_HOME}"

	appender('STDOUT', ConsoleAppender) {
		encoder(PatternLayoutEncoder) {
			pattern = "%level %logger - %msg%n"
		}
	}
	root(ERROR, ['STDOUT'])

	appender("ROLLING", RollingFileAppender) {
		encoder(PatternLayoutEncoder) {
		  Pattern = "%d{HH:mm:ss.SSS} %level %mdc %logger - %m%n"
		}
		rollingPolicy(TimeBasedRollingPolicy) {
		  FileNamePattern = "${USER_HOME}"+"stacktrace-%d{yyyy-MM-dd}.txt"
		}
	}

	root(ERROR,["ROLLING"])

	//test
	/*appender("logstash", LogstashTcpSocketAppender) {
		remoteHost = "104.215.188.4"
		port = 50045
		encoder(LogstashEncoder){
			customFields = """{ "token": "AUeHSrrdsdoiknf51_INF45@${current_env}", "environment":"${current_env}", "APP_NAME":"Quotaion website" }"""
		}
		keepAliveDuration = "5 minutes"
	}
	root(ERROR, ["logstash"])*/

}else{

	appender("logstash", LogstashTcpSocketAppender) {
		destination = "104.215.188.4:50045"
		encoder(LogstashEncoder){
			customFields = """{ "token": "AUeHSrrdsdoiknf51_INF45@${current_env}", "environment":"${current_env}", "APP_NAME":"Quotaion website" }"""
		}
		keepAliveDuration = "5 minutes"
	}
	root(ERROR, ["logstash"])
}

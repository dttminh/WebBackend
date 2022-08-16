package com.roojai


import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class LoggingInterceptorSpec extends Specification implements InterceptorUnitTest<LoggingInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test logging interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"logging")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}

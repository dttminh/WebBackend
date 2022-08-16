package com.roojai

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class HttpInterceptorSpec extends Specification implements InterceptorUnitTest<HttpInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test http interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"http")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}

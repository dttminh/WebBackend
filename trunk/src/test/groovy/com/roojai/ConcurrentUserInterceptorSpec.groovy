package com.roojai


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ConcurrentUserInterceptor)
class ConcurrentUserInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test concurrentUser interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"concurrentUser")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}

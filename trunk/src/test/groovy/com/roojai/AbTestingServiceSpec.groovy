package com.roojai

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class AbTestingServiceSpec extends Specification implements ServiceUnitTest<AbTestingService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}

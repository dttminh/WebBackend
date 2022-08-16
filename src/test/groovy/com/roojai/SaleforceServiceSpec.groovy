package com.roojai

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SaleforceService)
class SaleforceServiceSpec extends Specification {
	def token;
	def json;
    def setup() {
		//token = getService().getAccessToken()
		//println token
		json = getService().createQuotation()
    }

    def cleanup() {
    }
	
	
    /*void testGetToken() {
        expect:"fix me"
            token!=null && !token.equals("")
    }*/
	void testCreateQuotation() {
		expect:"fix me"
			json!=null
	}
}

package com.roojai

import grails.rest.Resource;

//@Resource(uri='/api/countries', readOnly=true,formats=['json', 'xml'] )
class Country {
	String id
	String name
	static mapping = {
		id column: 'code', generator: 'assigned'
		version false
	}
    static constraints = {
		
    }
}
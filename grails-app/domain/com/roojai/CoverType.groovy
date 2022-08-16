package com.roojai

import grails.rest.Resource;

//@Resource(uri='/api/cover-types', readOnly=true ,formats=['json', 'xml'] )
class CoverType {
	String id
	String nameEn
	String nameTh
	static mapping = {
		id column: 'code', generator: 'assigned'
		version false
	}
    static constraints = {
		
    }
}

package com.roojai

class ABTestingCounter {
	int id
	String testingVersion
	int count
	int summary
	String productType
	
	static mapping = {
		version false
	}
	
    static constraints = {
		count nullable: true,defaultValue: 0
		summary nullable: true,defaultValue: 0
    }
}

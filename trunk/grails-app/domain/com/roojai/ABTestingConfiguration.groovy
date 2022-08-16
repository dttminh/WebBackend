package com.roojai

class ABTestingConfiguration {
	int id
	String testingVersion
	int testingPercent
	String returnValue
	String productType
	
	static mapping = {
		version false
	}
	
    static constraints = {
		testingPercent defaultValue: 0
    }
}

package com.roojai

class Systemconfiguration {
	int id
	String category
	String name
	String value
	String description
	
	static mapping = {
		value sqlType: 'text'
		version false
	}
	
    static constraints = {
		description nullable: true
		value nullable:true
    }
}

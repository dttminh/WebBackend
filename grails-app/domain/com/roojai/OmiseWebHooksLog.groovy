package com.roojai

class OmiseWebHooksLog {
	long id
	String opportunityNumber
	String webhooksData
	Date created
	String refNo
	
	static mapping = {
		webhooksData type: 'text'
		version false
	}
	
    static constraints = {
		opportunityNumber blank: true, nullable: true
		webhooksData blank: true, nullable: true
		created blank: false, nullable: false
		refNo blank: true, nullable: true
    }
}

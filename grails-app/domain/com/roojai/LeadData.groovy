package com.roojai

class LeadData {
	long id
	String leadName
	String method
	String firstName
	String lastName
	String email
	String phone
	String referralCode
	String jsonInput
	String jsonOutput
	String errorMessage
	Date created
	
	static mapping = {
		jsonInput type: 'text'
		jsonOutput type: 'text'
		errorMessage type: 'text'
		version false
	}
    static constraints = {
		leadName blank: true, nullable: true
		method blank: true, nullable: true
		firstName blank: true, nullable: true
		lastName blank: true, nullable: true
		email blank: true, nullable: true
		phone blank: true, nullable: true
		referralCode blank: true, nullable: true
		jsonInput blank: true, nullable: true
		jsonOutput blank: true, nullable: true
		errorMessage blank: true, nullable: true
		created blank: true, nullable: true
    }
}

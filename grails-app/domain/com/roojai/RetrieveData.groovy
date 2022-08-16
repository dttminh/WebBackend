package com.roojai

class RetrieveData {
	long id
	String opportunityNumber
	String opportunityNumberEncrypt
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
		opportunityNumber blank: true, nullable: true
		opportunityNumberEncrypt blank: true, nullable: true
		jsonInput blank: true, nullable: true
		jsonOutput blank: true, nullable: true
		errorMessage blank: true, nullable: true
		created blank: true, nullable: true
    }
}

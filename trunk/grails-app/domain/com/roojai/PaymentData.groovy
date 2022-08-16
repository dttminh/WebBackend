package com.roojai

class PaymentData {
	long id
	String opportunityNumber
	String payment_type//"Bill payment","Internet banking","Credit Card"
	String barcodeNumber
	String errorCode
	String errorMessage
	Date created
	String json_input
	String json_output
	static mapping = {
		json_input sqlType: 'text'
		json_output sqlType: 'text'
		version false
	}
	
    static constraints = {
		opportunityNumber nullable: false
		payment_type nullable: true
		barcodeNumber nullable: true
		errorCode nullable: true
		errorMessage nullable: true
		created nullable: true
		json_input nullable: true
		json_output nullable: true
    }
}

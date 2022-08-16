package com.roojai

class SendMailData {
	long id
	String messageType
	String opportunityNumber
	Date created
	String jsonInput
	String jsonOutput
	String method
	String customerName
	String phoneNumber
	String email
	String amount
	String status
	String paysbuyInvoiceNo
	String errorMessage
	static mapping = {
		jsonInput type: 'text'
		jsonOutput type: 'text'
		errorMessage type: 'text'
		version false
	}
	static constraints = {
		opportunityNumber blank: true, nullable: true
		messageType blank: false, nullable: false
		created blank: false, nullable: false
		jsonInput blank: true, nullable: true
		jsonOutput blank: true, nullable: true
		method blank: true, nullable: true
		customerName blank: true, nullable: true
		phoneNumber blank: true, nullable: true
		email blank: true, nullable: true
		amount blank: true, nullable: true
		status blank: false, nullable: false
		paysbuyInvoiceNo blank: true, nullable: true
		errorMessage blank: true, nullable: true
	}
}

package com.roojai

class OmiseData {
	long id
	String opportunityNumber
	String invoiceNo
	String payerName
	String payerMobile
	String payerEmail
	String amount
	String tokenId
	String cardId
	String customerId
	String jsonRequestCustomer
	String jsonResponseCustomer
	String chargeId
	String jsonRequestCharge
	String jsonResponseCharge
	String hdIssuingBank
	String hdPaymentFrequency
	String paymentType
	String errorMessage
	String kpiTokenId
	String kpiCardId
	String kpiCustomerId
	String kpiJsonRequestCustomer
	String kpiJsonResponseCustomer
	String kpiChargeId
	String kpiJsonRequestCharge
	String kpiJsonResponseCharge
	String sourceId
	String sourceType
	boolean isCreateSourceSuccess
	Date created
    static mapping = {
		jsonRequestCustomer type: 'text'
		jsonResponseCustomer type: 'text'
		jsonRequestCharge type: 'text'
		jsonResponseCharge type: 'text'
		kpiJsonRequestCustomer type: 'text'
		kpiJsonResponseCustomer type: 'text'
		kpiJsonRequestCharge type: 'text'
		kpiJsonResponseCharge type: 'text'
		version false
    }
	
	static constraints = {
		opportunityNumber blank: true, nullable: true
		invoiceNo  blank: true, nullable: true
		payerName  blank: true, nullable: true
		payerMobile  blank: true, nullable: true
		payerEmail  blank: true, nullable: true
		amount  blank: true, nullable: true
		tokenId  blank: true, nullable: true
		cardId  blank: true, nullable: true
		customerId blank: true, nullable: true
		jsonRequestCustomer blank: true, nullable: true
		jsonResponseCustomer blank: true, nullable: true
		chargeId blank: true, nullable: true
		jsonRequestCharge blank: true, nullable: true
		jsonResponseCharge blank: true, nullable: true
		hdIssuingBank blank: true, nullable: true
		hdPaymentFrequency blank: true, nullable: true
		paymentType blank: true, nullable: true
		errorMessage blank: true, nullable: true
		kpiTokenId  blank: true, nullable: true
		kpiCardId  blank: true, nullable: true
		kpiCustomerId  blank: true, nullable: true
		kpiJsonRequestCustomer  blank: true, nullable: true
		kpiJsonResponseCustomer  blank: true, nullable: true
		kpiChargeId  blank: true, nullable: true
		kpiJsonRequestCharge  blank: true, nullable: true
		kpiJsonResponseCharge  blank: true, nullable: true
		sourceId blank: true, nullable: true
		sourceType blank: true, nullable: true
		created blank: true, nullable: true
	}
}

package com.roojai

class PaysbuyData {
	int id
	String pay_type
	String parameter_type
	String invoice_no
	String payer_name
	String payer_mobile
	String payer_email
	String amt
	String bank_code
	String ref_id
	String result
	String barcode_id
	String credit_no
	String credit_exp_month
	String credit_exp_year
	String credit_cardholdername
	String apcode
	String fee
	String method
	String description
	String create_date
	String payment_date
	String create_token
	String token_id
	String die_date
	Date event_date
	String hdPolicyStartDate
	String hdIssuingBank
	String hdPaymentFrequency
	String merchant_background_url
	
	static mapping = {
		description type: 'text'
		merchant_background_url type: 'text'
		version false
	}
	
    static constraints = {
		pay_type blank: false, nullable: false
		parameter_type blank: false, nullable: false
		invoice_no nullable: true
		payer_name nullable: true
		payer_mobile nullable: true
		payer_email nullable: true
		amt nullable: true
		bank_code nullable: true
		ref_id nullable: true
		result nullable: true
		barcode_id nullable: true
		credit_no nullable: true
		credit_exp_month nullable: true
		credit_exp_year nullable: true
		credit_cardholdername nullable: true
		apcode nullable: true
		fee nullable: true
		method nullable: true
		description nullable: true
		create_date nullable: true
		payment_date nullable: true
		create_token nullable: true
		token_id nullable: true
		die_date nullable: true
		event_date nullable: true
		hdPolicyStartDate nullable: true
		hdIssuingBank nullable: true
		hdPaymentFrequency nullable: true
		merchant_background_url nullable: true
    }
}

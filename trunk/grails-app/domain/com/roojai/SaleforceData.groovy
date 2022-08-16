package com.roojai

class SaleforceData {
	int id
	String masterset_id
	String opportunity_number
	String send_email_flag
	String isupdate
	String call_me_back
	String json_input
	String output
	Date created
	String remote_addr
	String productType

	static mapping = {
		json_input sqlType: 'text'
		output sqlType: 'text'
		version false
	}

    static constraints = {
		masterset_id blank: true, nullable: true
		opportunity_number blank: true, nullable: true
		send_email_flag blank: true, nullable: true
		isupdate blank: true, nullable: true
		call_me_back blank: true, nullable: true
		json_input blank: true, nullable: true
		output blank: true, nullable: true
		created blank: true, nullable: true
		remote_addr blank: true, nullable: true
		productType blank: true, nullable: true
    }
}

package com.roojai

class CustomerReview {
	Long id
	String facebookId
	Integer overallRate
	Integer websiteRate
	Integer crmRate
	Integer productRate
	String comment
	Date created
    static constraints = {
		websiteRate nullable: true
		crmRate nullable: true
		productRate nullable: true
    }
}

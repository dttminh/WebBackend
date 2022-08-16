package com.roojai

class RateUsData {
	int id
	String opportunityNumber
	String externalPictureURL
	String socialNetwork
	String socialMediaFName
	String socialMediaLName
	String profileURL
	String comment
	String overallRating
	String websiteRating
	String productRating
	String callCenterRating
	String postDate
	String socialMediaFeedbackResult
	Date created
	String errorMessage
	
	static mapping = {
		version false
	}
	
    static constraints = {
		opportunityNumber blank: false, nullable: false
		externalPictureURL blank: false, nullable: false
		socialNetwork blank: false, nullable: false
		socialMediaFName blank: false, nullable: false
		socialMediaLName blank: false, nullable: false
		profileURL blank: false, nullable: false
		comment blank: false, nullable: false
		overallRating  blank: false, nullable: false
		websiteRating nullable: true
		productRating nullable: true
		callCenterRating nullable: true
		postDate blank: false, nullable: false
		socialMediaFeedbackResult nullable: true
		created blank: false, nullable: false
		errorMessage blank:true , nullable : true
    }
}

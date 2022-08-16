package com.roojai

class RadarData {
	Integer ownerIs
	String carBrand
	String carModel
	Integer carYear
	String carDesc
	String gender
	Integer age
	String drivingExp
	Integer lastAccident
	String carFromHomeToWork
	String carInTheCourseOfWork
	String carUsageType
	String declareNCB
	String howlongbeeninsured
	String masterSetId
	String xmlData
	String xmlInput
	String opportunityNumber
	Date createDate
	String remote_addr
	
	static mapping = {
		xmlData type: 'text'
		xmlInput type: 'text'
	 }

	static constraints = {
		ownerIs blank: true, nullable: true
		carBrand blank: true, nullable: true
		carModel blank: true, nullable: true
		carYear blank: true, nullable: true
		carDesc blank: true, nullable: true
		gender blank: true, nullable: true
		age blank: true, nullable: true
		drivingExp blank: true, nullable: true
		lastAccident blank: true, nullable: true
		carFromHomeToWork blank: true, nullable: true
		carInTheCourseOfWork blank: true, nullable: true
		carUsageType blank: true, nullable: true
		declareNCB blank: true, nullable: true
		howlongbeeninsured blank: true, nullable: true
		masterSetId blank: true, nullable: true
		xmlData blank: true, nullable: true
		xmlInput blank: true, nullable: true
		opportunityNumber blank: true, nullable: true
		createDate blank: true, nullable: true
		remote_addr blank: true , nullable : true
	}
}

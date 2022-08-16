package com.roojai

class CallMeBackData {
	int id
	String customerName
	String phoneNumber
	String type
	String status
	String result
	Date created
	static mapping = {
		version false
		result type:'text'
	}
		
    static constraints = {
		
    }
}

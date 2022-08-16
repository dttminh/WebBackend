package com.roojai

class OTP {
	long id
	String phoneNumber
	String refID
	String OTP
	boolean expired
	boolean status
	Date created
	static mapping = {
		version false
	}
	
    static constraints = {

    }
}

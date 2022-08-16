package com.roojai

class UserSession {
	String username
	String ipAddress
	Date lastLoginDate
	static mapping = {
		version false
	}
}
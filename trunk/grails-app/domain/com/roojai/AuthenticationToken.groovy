package com.roojai

class AuthenticationToken {
	String tokenValue
	String username
	Date dateCreated
	Date lastUpdated
	Integer accessCount = 0
	Date refreshed = new Date()
	
	def afterLoad() {
	  accessCount++
	  if (refreshed < new Date() -1) {
		  refreshed = new Date()
	  }
	}
	
	static mapping = {
		version false
	}
	
    static constraints = {
    }
	
}

package com.roojai

class RemoveStaleTokensJob {	
	static triggers = {
		//http://grails-plugins.github.io/grails-quartz/guide/scheduling.html
		simple name: 'every4hours',startDelay: 60000,repeatInterval: 4*60*60*1000
	}

	void execute() {
		AuthenticationToken.executeUpdate('delete AuthenticationToken a where a.refreshed < ?', [new Date()-1])
	}
}

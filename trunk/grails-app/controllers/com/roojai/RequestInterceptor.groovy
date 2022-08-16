package com.roojai


class RequestInterceptor {
	
	RequestInterceptor(){
		 //matchAll().excludes(controller:"login")
	}
	
    boolean before() { 
		/*AccessLog acl = new AccessLog(ip:request.getHeader("True-Client-IP"), method:request.getMethod(), requestURI:request.getRequestURI(), protocol:request.getProtocol(), responseStatus:response.getStatus(), created:new Date())
		acl.save(flush:true)
		true*/ 
		
		return true
	}

    boolean after() { 
		//response.addHeader("Strict-Transport-Security", "max-age=31536000");
		true 
	}

    void afterView() {
        // no-op
    }
}

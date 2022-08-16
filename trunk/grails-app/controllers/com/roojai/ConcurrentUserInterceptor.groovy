package com.roojai

import com.roojai.util.StringUtil;

class ConcurrentUserInterceptor {
	def springSecurityService
	def LoginService
	ConcurrentUserInterceptor(){
		match(controller: "backOffice|systemconfiguration|paysbuyData|paymentData|radarData|saleforceData|sendMailData|rateUsData|OmiseData|OmiseWebHooksLog|RetrieveData|LeadData|role|user|userRole|ABTestingConfiguration|ABTestingCounter")//, action: "index|create"
		//.excludes(controller: 'backOffice')
	}

    boolean before() {
		def username = springSecurityService.principal.username
		UserSession userSession = UserSession.findByUsername(username);
		def ipAddress = ""//request.getRemoteAddr()
		def ip = request.getHeader("X-FORWARDED-FOR");// proxy 61.90.131.82:56948, 61.90.131.82:56948
		if(ip==null){
			ipAddress = StringUtil.getIpAdress(request);
		}else{
			def tmpIp = ip.split(",")[0]
			ipAddress = tmpIp ? tmpIp.split(":")[0]:""
		}
		//println("IP Address: "+ipAddress);
		def fl = request.getSession().getAttribute("firstLogin") ? request.getSession().getAttribute("firstLogin"):false
		if(fl){
			def grecaptchaResp = request.getSession().getAttribute("grecaptchaResp") ? request.getSession().getAttribute("grecaptchaResp"):""
			def verify = LoginService.VerifyRecaptcha(grecaptchaResp)
			request.getSession().removeAttribute("grecaptchaResp")
			if(verify){
				if(userSession){
					userSession.ipAddress = ipAddress
					userSession.lastLoginDate = new Date()
					userSession.save()
				}else{
					UserSession userSession1 = new UserSession(username: username, ipAddress: ipAddress, lastLoginDate: new Date())
					userSession1.save()
				}
			}else{
				request.logout()
				flash.message = "Please try again"
				redirect(controller: "login", action: "auth")
			}
		}else{
			if(userSession){
				if(!userSession.ipAddress.equals(ipAddress)){
					request.logout()
					flash.message = "Access denide!!!"
					redirect(controller: "login", action: "auth")
				}
			}else{
				request.logout()
				flash.message = "Access denide!!!"
				redirect(controller: "login", action: "auth")
			}
		}
		request.getSession().removeAttribute("firstLogin");
		true
	}

    boolean after() { true }

    void afterView() {
        // no-op
    }
}

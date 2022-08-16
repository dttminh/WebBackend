package com.roojai

import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject;
import com.roojai.util.OtpUtil
import groovy.json.JsonSlurper

@Secured(['ROLE_API'])
class OTPController {
	static responseFormats = ['json', 'xml']
	def Sms2Service
	def SystemconfigurationService
	def otpService

	def generateOTP(){
		def phoneNumber = request.JSON.hdMobileNumber
		def locale = request.JSON.locale
		JSONObject root = new JSONObject();
		try{
			int refIdDigit = Integer.parseInt(SystemconfigurationService.getValueByKey("otp.refIdDigit"))
			int otpDigit = Integer.parseInt(SystemconfigurationService.getValueByKey("otp.otpDigit"))
			String refID = OtpUtil.generateRefNum(refIdDigit)
			String otp = OtpUtil.generateOTP(otpDigit)
			def jsonSlurper = new JsonSlurper()
			def object = jsonSlurper.parseText(SystemconfigurationService.getValueByKey("otp.message"));
			String message = locale=="th" ? object.th : object.en
			message = message.replaceAll("otpCode", otp);
			message = message.replaceAll("refCode", refID);
			boolean status = Sms2Service.send(phoneNumber,message)
			OTP otpData = new OTP(phoneNumber:phoneNumber,refID:refID,OTP:otp,expired:false,status:status,created:new Date())
			otpData.save(flush:true)
			root.put("success",true)
			root.put("refID",refID)
		}catch(Exception e){
			root.put("success",false)
			root.put("refID","")
			log.error(e.getMessage(), e)
		}
		respond root
	}

	def verifyOTP(){
		def refID = request.JSON.refID
		def otp = request.JSON.OTP
		int time = Integer.parseInt(SystemconfigurationService.getValueByKey("otp.expireTime"));
		JSONObject root = new JSONObject();
		try{
			def data = OTP.findAll("from OTP where refID = ? and OTP = ? and expired = 0 and GETDATE () < DATEADD(minute, ?, created) ",[refID,otp,time])
			if(data.size() == 1){
				OTP otpData = data[0]
				otpData.expired = true
				otpData.save(flush:true)
				root.put("success",true)
			}else{
				root.put("success",false)
			}
		}catch(Exception e){
			root.put("success",false)
		}
		respond root
	}

	def generateEmailOTP(){
		try {
			def email = params.destination
			respond otpService.generateEmailOTP(email)
		} catch (Exception ex) {

		}

	}
}

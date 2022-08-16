package com.roojai

import grails.gorm.transactions.Transactional
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.grails.web.json.JSONObject

@Transactional
class PromotionService {
	def SystemconfigurationService
	def SaleforceService
	def createReferrerLead(referral,firstName,lastName,tel,email,lang){
		LeadData ld = new LeadData(leadName:"Referrer",method:"createReferrerLead",firstName:firstName,lastName:lastName,email:email,phone:tel,referralCode:referral)
		def result
		try{
			JSONObject root = new JSONObject()
			JSONObject item = new JSONObject()
			JSONObject lead = new JSONObject()
			lead.put("FirstName",firstName)
			lead.put("LastName",lastName)
			lead.put("Email",email)
			lead.put("Phone",tel)
			lead.put("Referral_Code__c",referral)
			String langInput = (lang!=null && !lang.trim().equals("")) ? lang.toUpperCase() : "TH"
			lead.put("Language__c", langInput)
			item.put("lead",lead)
			item.put("flagCreateLead",true)
			root.put("item",item)
			
			ld.jsonInput = root.toString()
			def jsonOutput = salesforceExecute(root.toString(),"/services/apexrest/leadapi/")
			ld.jsonOutput = jsonOutput
			if( jsonOutput.startsWith("[") ){
				JSONObject jsonReturn = new JSONObject()
				jsonReturn.put("success",false)
				jsonReturn.put("validCode","")
				jsonReturn.put("validMessage","")
				jsonReturn.put("errorCode","103")
				jsonReturn.put("errorMessage","Salesforce Exception")
				result = jsonReturn.toString()
			}else{
				result = jsonOutput
			}
		}catch(Exception e){
			JSONObject jsonReturn = new JSONObject()
			jsonReturn.put("success",false)
			jsonReturn.put("validCode","")
			jsonReturn.put("validMessage","")
			jsonReturn.put("errorCode","103")
			jsonReturn.put("errorMessage",e.getMessage())
			result = jsonReturn.toString()
			log.error(e.getMessage(), e)
			ld.errorMessage = e.getMessage()
		}finally{
			ld.created = new Date()
			ld.save(flush:true)
		}
		return result
	}
	
	def validateReferralNumber(referral){
		LeadData ld = new LeadData(leadName:"Referrer",method:"validateReferralNumber",referralCode:referral)
		def result = ""
		try{
			JSONObject root = new JSONObject()
			JSONObject item = new JSONObject()
			JSONObject lead = new JSONObject()
			lead.put("Referral_Code__c",referral)
			item.put("lead",lead)
			item.put("flagCreateLead",false)
			root.put("item",item)
			ld.jsonInput = root.toString()
			def jsonOutput = salesforceExecute(root.toString(),"/services/apexrest/leadapi/")
			ld.jsonOutput = jsonOutput
			if( jsonOutput.startsWith("[") ){
				JSONObject jsonReturn = new JSONObject()
				jsonReturn.put("success",false)
				jsonReturn.put("validCode","")
				jsonReturn.put("validMessage","")
				jsonReturn.put("errorCode","103")
				jsonReturn.put("errorMessage","Salesforce Exception")
				result = jsonReturn.toString()
			}else{
				result = jsonOutput
			}
		}catch(Exception e){
			JSONObject jsonReturn = new JSONObject()
			jsonReturn.put("success",false)
			jsonReturn.put("validCode","")
			jsonReturn.put("validMessage","")
			jsonReturn.put("errorCode","103")
			jsonReturn.put("errorMessage",e.getMessage())
			result = jsonReturn.toString()
			ld.errorMessage = e.getMessage()
		}finally{
			ld.created = new Date()
			ld.save(flush:true)
		}
		return result
	}
	
	def getStat(referrer){
		LeadData ld = new LeadData(leadName:"ReferrerStat",method:"getStat",referralCode:referrer)
		JSONObject jsonReturn = new JSONObject()
		try{
			JSONObject requestJson = new JSONObject()
			requestJson.put("referenceNumber", referrer)
			ld.jsonInput = requestJson.toString()
			def result = salesforceExecute(requestJson.toString(),"/services/apexrest/luckydrawchanceforwinning/")
			ld.jsonOutput = result
			if( result.startsWith("[") ){
				jsonReturn.put("success",false)
			}else{
				jsonReturn = new JSONObject(result)
				jsonReturn.put("success",true)
			}
		}catch(Exception e){
			jsonReturn.put("success",false)
			ld.errorMessage = e.getMessage()
		}finally{
			ld.created = new Date()
			ld.save(flush:true)
		}
		return jsonReturn
	}
	
	def salesforceExecute(jsonInput,api){
		StringBuffer result = new StringBuffer()
		try{
			JSONObject jsonToken = SaleforceService.getAccessToken()
			def token = jsonToken.access_token
			def url = jsonToken.instance_url+api
			def tokenType = jsonToken.token_type
			
			HttpClient client = HttpClientBuilder.create().build()
			HttpPost post = new HttpPost(url)
			StringEntity params = new StringEntity(jsonInput,"utf-8")
			post.setEntity(params)
			post.addHeader("Authorization",tokenType+" "+token)
			post.addHeader("content-type", "application/json")
			post.addHeader("charset", "UTF-8")
			HttpResponse response = client.execute(post)
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"))
			String line = ""
			while ((line = rd.readLine()) != null) {
				  result.append(line);
			}
		}catch(Exception e){
			throw e;
        }
        return result.toString();
	}
}

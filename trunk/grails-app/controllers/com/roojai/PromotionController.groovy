package com.roojai

import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class PromotionController{
	def SystemconfigurationService
	def LoginService
	def PromotionService
	def createReferrerLead(){
		render PromotionService.createReferrerLead(params.referrer,params.first_name,params.last_name,params.tel,params.email,params.lang)
		//render PromotionService.createReferrerLead("88888888","Weha","Tubsungnune","0899999999","test@e.com")
		/*
		JSONObject root = new JSONObject();
		try{
			def url = SystemconfigurationService.getValueByKey("saleforce.WebToLead_URL")//"https://webto.salesforce.com/servlet/servlet.WebToLead"//?encoding=UTF-8
			def oid = SystemconfigurationService.getValueByKey("saleforce.WebToLead_OID")
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			//Referral_Code__c  
			//<input  id="00NO0000002mz8L" maxlength="20" name="00NO0000002mz8L" size="20" type="text" /> 
			urlParameters.add(new BasicNameValuePair("00NO0000002mz8L", params.referrer));
			urlParameters.add(new BasicNameValuePair("oid", oid));
			urlParameters.add(new BasicNameValuePair("first_name", params.first_name));
			urlParameters.add(new BasicNameValuePair("last_name", params.last_name));
			urlParameters.add(new BasicNameValuePair("phone", params.tel));
			urlParameters.add(new BasicNameValuePair("lang", "th"));
			urlParameters.add(new BasicNameValuePair("email", params.email));
			urlParameters.add(new BasicNameValuePair("retURL", ""));
			urlParameters.add(new BasicNameValuePair("description", "From Referrer"));
			//urlParameters.add(new BasicNameValuePair("debug", "1"));
			//urlParameters.add(new BasicNameValuePair("debugEmail", "wehat@roojai.com"));
			post.setEntity(new UrlEncodedFormEntity(urlParameters,"UTF-8"));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			root.put("success",true)
		}catch(Exception e){
			e.printStackTrace();
			root.put("success",false)
		}
		render root.toString()
		*/
	}

	def validateReferralNumber(){
		render PromotionService.validateReferralNumber(params.referrer)
	}
	
	def checkStat(){
		def referrer = params.referrer
		//def recapcha = params.recapcha
		JSONObject jsonResp = new JSONObject()
		//def verify = LoginService.VerifyRecaptcha(recapcha)
		//if(verify == true){
			def validate = PromotionService.validateReferralNumber(referrer)
			JSONObject jsonValidate = new JSONObject(validate)
			if(jsonValidate.success == true && jsonValidate.validCode.equals("1")){
				jsonResp = PromotionService.getStat(referrer)
				jsonResp.put("validCode","1")
			}else{
				jsonResp = jsonValidate
			}
			//jsonResp.put("capchasuccess",true)
		/*}else{
			jsonResp.put("capchasuccess",false)
			jsonResp.put("errorCode","103")
			jsonResp.put("errorMessage","Invalid capcha")
		}*/
		render jsonResp
	}
}

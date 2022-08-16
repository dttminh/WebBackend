package com.roojai

import grails.plugin.springsecurity.annotation.Secured
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class SaleforcePaymentCallBackController {
	def SystemconfigurationService
	def SaleforceService
	
	@Secured(['permitAll'])
	def paymentRespHandler(){
		JSONObject jsonToken = SaleforceService.getAccessToken()
		def url = jsonToken.instance_url+"/services/apexrest/paymentRespHandler"
		def token = jsonToken.access_token
		HttpClient client = HttpClientBuilder.create().build()
		HttpPost post = new HttpPost(url)
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>()
		params.each { name, value ->
			if(!name.equalsIgnoreCase("controller") && !name.equalsIgnoreCase("format") && !name.equalsIgnoreCase("action")){
				//println name+":"+value
				urlParameters.add(new BasicNameValuePair(name, value))
			}
		}
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		post.addHeader("Authorization","Bearer "+token)
		post.addHeader("charset", "UTF-8")
		HttpResponse response = client.execute(post)
		render request.isGet() ? "Complete":""
	}
}

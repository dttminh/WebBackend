package com.roojai

import grails.gorm.transactions.Transactional
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.grails.web.json.JSONObject

@Transactional
class LoginService {
	def SystemconfigurationService
    def serviceMethod() {

    }
	
	def VerifyRecaptcha(gRecaptchaResponse){
		def url = SystemconfigurationService.getValueByKey("recaptcha.siteverify")
		def secret = SystemconfigurationService.getValueByKey("recaptcha.secretkey")
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
		try{
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("secret", secret));
			urlParameters.add(new BasicNameValuePair("response", gRecaptchaResponse));
			post.setEntity(new UrlEncodedFormEntity(urlParameters,"UTF-8"));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			JSONObject root = new JSONObject(result.toString())
			return root.success
		}catch(Exception e){
			log.error(e.getMessage(), e)
			return false;
		}
	}
}

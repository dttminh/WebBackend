package com.roojai

import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.grails.web.json.JSONObject

class Sms2Service {
	private String subAccountId = "Ignite_8iIF8_hq";
	private String url="https://api.wavecell.com/sms/v1/"+subAccountId+"/single";
	private String apiKey ="4wPtb3heZqYmXEhy4sJplJC4uXdDlDFDle6k4DrM";
	private String sender="Roojai";
	
	boolean send(String phoneNumber,String message)throws Exception{
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		post.setEntity(new UrlEncodedFormEntity(urlParameters,"UTF-8"));
		post.addHeader("Authorization","Bearer "+apiKey);
		post.addHeader("content-type", "application/json");
		post.addHeader("charset","UTF-8");
		
		JSONObject jsonObj =new JSONObject();
		jsonObj.put("source", sender);
		jsonObj.put("destination", "66"+phoneNumber.substring(1));
		jsonObj.put("text", message);
		jsonObj.put("encoding", "AUTO");
		
		StringEntity params = new StringEntity(jsonObj.toString(),"utf-8");
        post.setEntity(params);
		
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			  result.append(line);
		}
		JSONObject jsonResult = new JSONObject(result.toString());
		JSONObject jsonStatus = (JSONObject)jsonResult.get("status");
		if( jsonStatus!=null ){
			if( jsonStatus.get("code").equals("QUEUED") )
				return true;
		}
		return false;
	}
	/*public static void main(String[] args) throws Exception{
		SmsUtil smsSender = new SmsUtil();
		smsSender.send("0897857124", "รู้ใจขอบคุณสำหรับการชำระเงินของคุณ ภายใน 24 ชั่วโมง คุณจะได้รับอีเมล์ต้อนรับ พบปัญหาหรือไม่ได้รับอีเมล์! โทร 021172222");
	}*/
}

package com.roojai

import grails.gorm.transactions.Transactional
import org.apache.commons.codec.digest.DigestUtils

@Transactional
class SmsService {
	def SystemconfigurationService
    def send(String phoneNumber,String message) {
		def userId=SystemconfigurationService.getValueByKey("sms.userId")//"admin@primarythailand.com";
		def password=SystemconfigurationService.getValueByKey("sms.password")//"admin@123";
		def accountId = SystemconfigurationService.getValueByKey("sms.accountId")//"80005871";
		def senderId=SystemconfigurationService.getValueByKey("sms.senderId")//"Roojai";
		def result = false;
		try {
			String plain = userId+password+accountId+senderId+phoneNumber+message;
			String hashKey= DigestUtils.md5Hex(plain);
			String url = "https://sms-magic.in/smapi/get?userid="+userId+"&accountid="+accountId+"&to="+phoneNumber+"&senderid="+senderId+"&hashkey="+hashKey+"&msg="+URLEncoder.encode(message,"utf-8");
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader buffIn = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = buffIn.readLine()) != null) {
				response.append(inputLine);
			}
			buffIn.close();

			//print result
			if( response!=null && response.toString().toLowerCase().indexOf("success")>=0 ){
				result = true;
			}
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e)
		} catch (ProtocolException e) {
			log.error(e.getMessage(), e)
		} catch (IOException e) {
			log.error(e.getMessage(), e)
		}
		return result;
    }
}

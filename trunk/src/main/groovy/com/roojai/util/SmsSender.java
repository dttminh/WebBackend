package com.roojai.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class SmsSender {
	static Logger logger = LoggerFactory.getLogger("com.roojai.util.SmsSender");
	private String userId="admin@primarythailand.com";
	private String password="admin@123";
	private String accountId = "80005871";
	private String senderId="Roojai";
	
	public void send(String phoneNumber,String message){
		try {
			String plain = userId+password+accountId+senderId+phoneNumber+message;
			String hashKey= DigestUtils.md5Hex(plain);
			String url = "https://sms-magic.in/smapi/get?userid="+userId+"&accountid="+accountId+"&to="+phoneNumber+"&senderid="+senderId+"&hashkey="+hashKey+"&msg="+URLEncoder.encode(message,"utf-8");
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (ProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
	public static void main(String[] args) {
		SmsSender smsSender = new SmsSender();
		smsSender.send("0897857124", "ทดสอบ");
	}
}

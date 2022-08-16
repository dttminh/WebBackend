package com.roojai.basic

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair
import org.grails.web.json.JSONArray;
import org.grails.web.json.JSONObject
import org.grails.web.json.parser.JSONParser;

import com.google.gson.JsonObject
import com.google.gson.JsonParser;


class TestGroovy2 {
	static void main(args){
		//testPost();
		testJson();
	}
	static void testPost(){
		String url = "https://cs6.salesforce.com/services/oauth2/token";
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		//post.setHeader("User-Agent", );

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("client_id", "3MVG9e2mBbZnmM6n9yvy.kGopm0_WaUju7B_Y4bDBGYIw2tGp4wvCtSci.VneVnHxq0E64m2aCJF_GX6wO6_M"));
		urlParameters.add(new BasicNameValuePair("client_secret", "2593480653449605265"));
		urlParameters.add(new BasicNameValuePair("username", "chot.a@songsermsubthai.com.dev"));
		urlParameters.add(new BasicNameValuePair("password", "sf123456z5ocijSxQH9rdWjPRMdfoQwqs"));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		//System.out.println("Response Code : "
			//	+ response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		JsonObject json = (JsonObject)new JsonParser().parse(result.toString());
		println result
		println json.get("access_token");
	}
	static void testJson(){
		JSONObject root = new JSONObject();
			JSONObject item = new JSONObject();
			// policyHolder
			JSONObject phaccount = new JSONObject();
			phaccount.put("thaiIdNumber","")
			phaccount.put("phoneNumber","")
			phaccount.put("passPortNumber","")
			phaccount.put("occupation","")
			phaccount.put("maritialStatus","")
			phaccount.put("lastName","")
			phaccount.put("isMainDriver","")
			phaccount.put("gender","Male")
			phaccount.put("firstName","")
			phaccount.put("email","")
			phaccount.put("drivingExperience","")
			phaccount.put("driverAge","")
			phaccount.put("driverAccidents","")
			item.put("phaccount", phaccount)
			
			// drivers
			JSONArray drivers = new JSONArray();
			// driver 1
			JSONObject driver1 = new JSONObject();
			driver1.put("thaiIdNumber","")
			driver1.put("phoneNumber","")
			driver1.put("passPortNumber","")
			driver1.put("occupation","")
			driver1.put("maritialStatus","Married")
			driver1.put("lastName","")
			driver1.put("isMainDriver",true)
			driver1.put("gender","Male")
			driver1.put("firstName","")
			driver1.put("email","")
			driver1.put("drivingExperience","4")
			driver1.put("driverAge",34)
			driver1.put("driverAccidents","None")
			drivers.put(driver1)
			
			// driver 2
			JSONObject driver2 = new JSONObject();
			driver2.put("thaiIdNumber","")
			driver2.put("phoneNumber","")
			driver2.put("passPortNumber","")
			driver2.put("occupation","")
			driver2.put("maritialStatus","Single")
			driver2.put("lastName","")
			driver2.put("isMainDriver",false)
			driver2.put("gender","Male")
			driver2.put("firstName","")
			driver2.put("email","")
			driver2.put("drivingExperience","4")
			driver2.put("driverAge",30)
			driver2.put("driverAccidents","1")
			drivers.put(driver2)
			item.put("drivers", drivers);
		
			// quoteLineList
			JSONArray quoteLineList = new JSONArray();
			//quote1
			JSONObject quote1 = new JSONObject();
			quote1.put("Basic_premium__c",22786.5533885914)
			quote1.put("PA_passenger_SI__c",200000.00)
			quote1.put("PA_Driver_SI__c",200000.00)
			quote1.put("Bail_Bond_SI__c",300000.00)
			quote1.put("Medical_Expenses_SI__c",100000.00)
			quote1.put("Cover_Code__c","VOL")
			quote1.put("Adjusted_technical_premium__c",0.00)
			quote1.put("Reason_for_premium_adjustment__c","")
			quoteLineList.put(quote1);
			item.put("quoteLineList", quoteLineList);
		
			// quote
			JSONObject quote = new JSONObject();
			quote.put("Year_of_Manufacture__c","2015")
			quote.put("Make__c","Toyota")
			quote.put("Is_Main_Driver_PH__c",true)
			quote.put("Model_Description__c","")
			quote.put("Seat_Capacity1__c",5)
			quote.put("Engine_size1__c",1497)
			quote.put("Final_Premium_Class1__c",23)
			quote.put("Final_Premium_Group1__c",21)
			quote.put("Final_UW_Class1__c","0")
			quote.put("Final_UW_Group1__c","21")
			quote.put("Tariff_Group1__c",5)
			quote.put("Vehicle_Sum_Insured1__c",520000)
			quote.put("Vehicle_Usage__c","Driving to and back from work")
			quote.put("Voluntary_Type__c","110")
			quote.put("Vehicle_Key1__c","TOYO15DN")
			quote.put("Declared_NCB__c","20%")
			quote.put("Excess__c","3000")
			quote.put("Compulsory_Plan__c","No")
			quote.put("Driver_Plan__c","Named Driver")
			quote.put("Workshop_Type__c","Any Workshop")
			quote.put("Plan_Type__c","Type 1")
			quote.put("Masterset_Id__c","26c1a8ae-2331-40c4-a6e4-60ccfb730eba")
			quote.put("Created_From__c","Web");// Fixed
			quote.put("Selected_Option__c","T1/E3000/Any/Named Driver/Comp")
			quote.put("send_email_flag__c",true)
			quote.put("Name","TBA");
			item.put("quote", quote)
			
		root.put("item",item);
		println root.toString();
	}
}

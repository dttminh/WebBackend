package com.roojai

import grails.gorm.transactions.Transactional
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.grails.web.json.JSONObject

@Transactional
class CMSemailNotificationService {
    def SaleforceService

    JSONObject saveSubmission( JSONObject submitData ){

        try{
            JSONObject token = SaleforceService.getAccessToken()

            if( token == null || !token.access_token ){
                submitData.put("success", false);
                submitData.put("message", "Saleforce Token empty!")
                return submitData
            }

            JSONObject saleforce_data = new JSONObject();

            saleforce_data.put("LastName", submitData.lastname )
            saleforce_data.put("Email", submitData.email )
            saleforce_data.put("status", "Open" )
            saleforce_data.put("LeadSource", "Web" )
            saleforce_data.put("Renewal_Month__c", submitData.month )

            return sendToSaleforce( saleforce_data, token )

        }catch( Exception e ){
            submitData.put("success", false);
            submitData.put("message", e.getMessage())
            return submitData
        }
    }

    JSONObject sendToSaleforce( JSONObject data, JSONObject token ){
        String access_token = "";
        String tokenType = "";
        String url = "";

        access_token = token.access_token;
        tokenType = token.token_type;
        url = token.instance_url + "/services/data/v37.0/sobjects/Lead"

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(data.toString(), "UTF-8");
        post.setEntity(entity);
        post.addHeader("content-type", "application/json");
        post.addHeader("Authorization", tokenType+" " + access_token );
        post.addHeader("charset", "UTF-8")
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";

        while( (line = rd.readLine()) != null ){
            result.append(line);
        }

        String string_result = result.toString()
        JSONObject return_rosponse = new JSONObject();

        if( string_result.startsWith("[") && string_result.indexOf("errorCode") != -1 ){
            return_rosponse.put("success",false)
            return_rosponse.put("message",string_result)
        }else{
            return_rosponse.put("success",true)
            return_rosponse.put("message",string_result)
        }

        return return_rosponse;
    }
}

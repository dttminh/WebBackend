package com.roojai

import com.roojai.util.FileCacheUtil
import grails.converters.JSON
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.grails.web.json.JSONObject
import org.springframework.dao.DataAccessResourceFailureException

class HttpService extends ServiceBase {

    final Integer TokenExpireTime = 90*60;// 1.5 hour
    final String RATEUS_CACHE = "RateUsToken";
    final String MICROSERVICE_CACHE = "MicroServiceToken";

    public static final String SYS_CONFIG_CATEGORY_RATEUS_API = "rateUs"
    public static final String SYS_CONFIG_URL = "url"
    public static final String SYS_CONFIG_USERNAME = "username"
    public static final String SYS_CONFIG_PASSWORD = "password"

    public static final String HEADER_CONTENT_TYPE = "content-type"
    public static final String HEADER_APPLICATION_JSON = "application/json"
    public static final String HEADER_APPLICATION_TEXT = "application/TEXT"
    public static final String HEADER_UTF_8 = "utf-8"

    public static final String SYS_CONFIG_CATEGORY_MICROSERVICE_API = "microservice"
    public static final String SYS_CONFIG_MICROSERVICE_URL = "url"
    public static final String SYS_CONFIG_MICROSERVICE_USERNAME = "username"
    public static final String SYS_CONFIG_MICROSERVICE_PASSWORD = "password"

    def getRateUsAccessToken() {
        try {
            String token = FileCacheUtil.getCache(RATEUS_CACHE, TokenExpireTime);
            if (token == null) {
                HttpClient client = HttpClientBuilder.create().build()

                //"http://roojaidev:8080/backEnd/api/login"
                def tokenEndPoint = getSystemConfig(
                        SYS_CONFIG_CATEGORY_RATEUS_API + '.' + SYS_CONFIG_URL) + "login"
                HttpPost post = new HttpPost(tokenEndPoint)

                JSONObject json = new JSONObject()
                json.put(SYS_CONFIG_USERNAME, getSystemConfig(
                        SYS_CONFIG_CATEGORY_RATEUS_API + '.' + SYS_CONFIG_USERNAME))
                json.put(SYS_CONFIG_PASSWORD, getSystemConfig(
                        SYS_CONFIG_CATEGORY_RATEUS_API + '.' + SYS_CONFIG_PASSWORD))

                post.addHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                post.addHeader("charset", "UTF-8")

                StringEntity params = new StringEntity(json.toString(), HEADER_UTF_8)
                post.setEntity(params)

                HttpResponse response = client.execute(post)

                // println 'rateUs tk: ' + response

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer()
                String line = ""
                while ((line = rd.readLine()) != null) {
                    result.append(line)
                }
                JSONObject jsonResponse = new JSONObject(result.toString())
                FileCacheUtil.putCache(RATEUS_CACHE, result.toString())
                return jsonResponse
            } else {
                JSONObject jsonResponse = new JSONObject(token)
                return jsonResponse
            }
        } catch(Exception e){
            log.error(e.getMessage(), e)
            throw e
        }
    }

    def requestRateUsHttpGet(String URI) {

        StringBuffer result = new StringBuffer()
        try{
            HttpClient client = HttpClientBuilder.create().build()
            HttpGet getRequest = new HttpGet(URI)
            // getRequest.addHeader("Authorization",tokenType+" "+token)
            getRequest.addHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
            getRequest.addHeader("charset", "UTF-8")
            // StringEntity entity = new StringEntity(obj.toString(), "utf-8")
            HttpResponse response = client.execute(getRequest)
            // println 'Http Get response: ' + response
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"))
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch(Exception e){
            log.error(e.getMessage(), e)
            throw e
        }
        return result.toString();
    }

    def requestRateUsHttpPost(String URI, root) {
        // println 'post URL: ' + URI
        // println 'root: ' + root
        StringBuffer result = new StringBuffer()
        try{
            JSONObject jsonToken = getRateUsAccessToken()
            def token = jsonToken.access_token
            def tokenType = jsonToken.token_type
            // def url = jsonToken.instance_url + path

            HttpClient client = HttpClientBuilder.create().build()
            HttpPost postRequest = new HttpPost(URI)
            def a = root as JSON
            StringEntity params = new StringEntity(a.toString(),"utf-8")
            postRequest.setEntity(params)

            postRequest.addHeader("Authorization",tokenType+" "+token)
            postRequest.addHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
            postRequest.addHeader("charset", "UTF-8")

            HttpResponse response = client.execute(postRequest)
            // println 'response: ' + response
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"))
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch(Exception e){
            log.error(e.getMessage(), e)
            throw e;
        }
        return result.toString();
    }

    def getMicroServiceAccessToken() {
        String token = FileCacheUtil.getCache(MICROSERVICE_CACHE, TokenExpireTime);
        if( token==null ){
            HttpClient client = HttpClientBuilder.create().build()

            def tokenEndPoint = getSystemConfig(
                    SYS_CONFIG_CATEGORY_MICROSERVICE_API + '.' + SYS_CONFIG_URL) + "login"
            HttpPost post = new HttpPost(tokenEndPoint.replace("10.10.15.17:8080", "localhost:9000"))

            JSONObject json = new JSONObject()
            json.put(SYS_CONFIG_MICROSERVICE_USERNAME, getSystemConfig(
                    SYS_CONFIG_CATEGORY_MICROSERVICE_API+'.'+SYS_CONFIG_MICROSERVICE_USERNAME))
            json.put(SYS_CONFIG_MICROSERVICE_PASSWORD, getSystemConfig(
                    SYS_CONFIG_CATEGORY_MICROSERVICE_API+'.'+SYS_CONFIG_MICROSERVICE_PASSWORD))

            post.addHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
            post.addHeader("charset", "UTF-8")
            StringEntity params = new StringEntity(json.toString(), HEADER_UTF_8)
            post.setEntity(params)

            HttpResponse response = client.execute(post)
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer()
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line)
            }

            JSONObject jsonResponse = new JSONObject();
            if(result != null && !result.toString().isEmpty()){
                jsonResponse = new JSONObject(result.toString())
                FileCacheUtil.putCache(MICROSERVICE_CACHE, result.toString())
            }

            return jsonResponse
        } else {
            JSONObject jsonResponse = new JSONObject(token)
            return jsonResponse
        }
    }

    def requestMicroServiceHttpPost(String URI, JSONObject request) {
        StringBuffer result = new StringBuffer()
        try{
            JSONObject jsonToken = getMicroServiceAccessToken()
            def token = jsonToken.access_token
            def tokenType = jsonToken.token_type

            HttpClient client = HttpClientBuilder.create().build()
            HttpPost postRequest = new HttpPost(URI)
            StringEntity params = new StringEntity(request.toString(),"utf-8")
            postRequest.setEntity(params)

            postRequest.addHeader("Authorization",tokenType+" "+token)
            postRequest.addHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
            postRequest.addHeader("charset", "UTF-8")

            HttpResponse response = client.execute(postRequest)
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"))
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch(Exception e){
            log.error(e.getMessage(), e)
            throw e;
        }
        return result.toString();
    }

    def requestMicroServiceHttpGet(String URI) {
        StringBuffer result = new StringBuffer()
        try{
            JSONObject jsonToken = getMicroServiceAccessToken()
            def token = jsonToken.access_token
            def tokenType = jsonToken.token_type

            HttpClient client = HttpClientBuilder.create().build()
            HttpGet getRequest = new HttpGet(URI)
            getRequest.addHeader("Authorization",tokenType+" "+token)
            getRequest.addHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
            getRequest.addHeader("charset", "UTF-8")

            HttpResponse response = client.execute(getRequest)
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"))
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch(Exception e){
            log.error(e.getMessage(), e)
            throw e;
        }
        return result.toString();
    }

    def executeRequestPostJSON(String postUrl, String requestJson, String tokenType, String token, int requestTimeout) throws Exception {
        String result;
        def timeout = requestTimeout * 1000
        URL url = new URL(postUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        if(tokenType != null && token != null) {
            conn.setRequestProperty("Authorization", tokenType + " " + token);
        }
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        OutputStream os = conn.getOutputStream();
        os.write(requestJson.getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream()), "UTF-8"));

        String output;
        while ((output = br.readLine()) != null) {
            result = output;
        }

        conn.disconnect();

        return result

    }

    boolean testConnection(){
        boolean returnValue = true

        try{
            User.executeQuery("select 1 from User where 1=0")
        }catch(DataAccessResourceFailureException e){
            returnValue = false
        }

        return returnValue
    }
}

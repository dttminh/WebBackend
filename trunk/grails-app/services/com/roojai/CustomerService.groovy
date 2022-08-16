package com.roojai

import org.grails.web.json.JSONException
import org.grails.web.json.JSONObject

class CustomerService {
    def systemconfigurationService
    def httpService

    JSONObject getRefCode(String id) {
        JSONObject returnResult = new JSONObject()
        String getRefCodeURL = systemconfigurationService.getValueByKey("microservice.url") + "referal/getRefCode"
        JSONObject requestBody = new JSONObject()
        requestBody.put("input", id)
        try {
            returnResult = new JSONObject(httpService.requestMicroServiceHttpPost(getRefCodeURL, requestBody))
        } catch (JSONException e) {
            returnResult.put("status": false)
            returnResult.put("message": "Invalid JSON String")
        }
        return returnResult
    }

    JSONObject validate(String phoneNumber) {
        JSONObject returnResult = new JSONObject()
        boolean blacklist = false

        int countBlacklist = Blacklist.countByPhoneNumber(phoneNumber)

        if (countBlacklist > 0) {
            blacklist = true
        }

        returnResult.put("blacklist", blacklist)

        return returnResult
    }
}

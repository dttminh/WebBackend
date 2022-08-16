package com.roojai

import org.grails.web.json.JSONObject

class SfmcsService {
    SystemconfigurationService systemconfigurationService
    HttpService httpService
    String getToken(){
        try {
            Map<String, String> systemConfig = systemconfigurationService.getValueByCategory("sfmcs");
            if (systemConfig != null) {
                JSONObject requestJSON = new JSONObject();
                requestJSON.put("username", systemConfig.get("username"));
                requestJSON.put("password", systemConfig.get("password"));
                String url = systemConfig.get("url") + "login";
                int timeout = systemConfig.get("timeout") ? Integer.parseInt(systemConfig.get("timeout")) : 30;
                return httpService.executeRequestPostJSON(url,requestJSON.toString(),null,null,timeout);
            } else {
                return null;
            }
        }catch(Exception e){
            log.error(e.getMessage(), e)
        }
    }
}

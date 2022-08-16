package com.roojai

import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class OCRController {
    static responseFormats = ['json', 'xml']
    NjsmcsService njsmcsService;
    def getInfo(){
        String token = njsmcsService.getToken();
        respond new JSONObject(token);
    }
}

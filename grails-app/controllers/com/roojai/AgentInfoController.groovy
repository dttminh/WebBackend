package com.roojai

import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class AgentInfoController {
    static responseFormats = ['json', 'xml']
    SfmcsService sfmcsService;
    def login(){
        String token = sfmcsService.getToken();
        respond new JSONObject(token);
    }
}

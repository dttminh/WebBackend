package com.roojai

import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class EventLogController {
    static responseFormats = ['json']

    def eventLogService

    def index() {
        JSONObject jsonResponse = new JSONObject()
        int id = eventLogService.save(request.JSON)
        jsonResponse.put("success", id > 0)

        respond(jsonResponse, status: id > 0 ? 200 : 500)
    }
}

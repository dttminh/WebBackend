package com.roojai

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_API'])
class LogController {

    static responseFormats = ['json']

    def index() {
        request.JSON.put("userAgent", request.getHeader("user-agent"))
        request.JSON.put("referer", request.getHeader("referer"))
        log.error(request.JSON.toString())
        respond([:], status: 200)
    }
}

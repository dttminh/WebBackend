package com.roojai

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_API'])
class CustomerController {
    static responseFormats = ['json']

    def customerService

    def getRefCode(){
        respond customerService.getRefCode(params.id)
    }

    def validate(){
        respond customerService.validate(params.phoneNumber)
    }
}

package com.roojai

import grails.converters.JSON

class ServiceBase {

    static systemconfigurationService

    public static final HTTP_CODE_400_MESSAGE = "default.http_status_code.400.message"
    public static final HTTP_CODE_404_MESSAGE = "default.http_status_code.404.message"
    public static final HTTP_CODE_500_MESSAGE = "default.http_status_code.500.message"
    public static final HTTP_CODE_502_MESSAGE = "default.http_status_code.502.message"

    protected success(model) {
        render text: model
    }

    protected successAsJson(model) {
        render text: (model as JSON)
    }

    protected badRequest(String errMsg){
        render text: errMsg
    }

    static def getSystemConfig(key) {
        return systemconfigurationService.getValueByKey( key )
    }
}

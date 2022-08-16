package com.roojai

class EventLog {
    int id
    String opportunityNumber
    String accessToken
    String value
    String productType
    String prefLang
    String fieldName
    String testingVersion
    String onlineScreen
    String clientId
    Date created
    String utmSource

    static constraints = {
        testingVersion nullable: true, blank: true
        clientId nullable: true, blank: true
        utmSource nullable: true, blank: true
    }

    static mapping = {
        version false
    }
}

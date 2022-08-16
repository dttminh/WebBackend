package com.roojai

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class EventLogService {

    int save(JSONObject request) {
        EventLog eventLog = new EventLog()
        eventLog.opportunityNumber = request.opportunityNumber
        eventLog.accessToken = request.accessToken
        eventLog.value = request.value
        eventLog.productType = request.productType
        eventLog.prefLang = request.prefLang
        eventLog.fieldName = request.fieldName
        eventLog.testingVersion = request.testingVersion
        eventLog.onlineScreen = request.onlineScreen
        eventLog.clientId = request.clientId
        eventLog.created = new Date()
        eventLog.utmSource = request.utmSource
        eventLog.save(flush: true)

        return eventLog.id
    }
}

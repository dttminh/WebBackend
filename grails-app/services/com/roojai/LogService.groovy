package com.roojai


import grails.gorm.transactions.Transactional
import grails.util.Environment

@Transactional
class LogService {

    def SystemconfigurationService

    def sendMail(String errorMessage, String method) {
        if (Environment.current == Environment.PRODUCTION || Environment.current == Environment.TEST) {
            try {
                SendMailData sm = new SendMailData(
                        messageType: "Log",
                        opportunityNumber: null,
                        created: new Date(),
                        customerName: null,
                        phoneNumber: null,
                        email: null,
                        amount: null,
                        status: "N",
                        errorMessage: Environment.current.toString() + " : " + errorMessage,
                        method: method
                )
                sm.save(flush: true)
            }catch(Exception e){
                log.error(e.getMessage(), e)
            }
        }
    }
}

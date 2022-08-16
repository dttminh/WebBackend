package com.roojai


import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject
import org.hibernate.HibernateException

@Transactional
class CallMeBackNonceService {

    JSONObject saveRequest(params, request, opportunityNumber){
        JSONObject json = new JSONObject()
        try{

            CallMeBackNonce callMeBackNonce = new CallMeBackNonce()
            callMeBackNonce.nonce = params.nonce
            callMeBackNonce.planId = params.planID
            callMeBackNonce.brand = params.brand
            callMeBackNonce.year = Integer.parseInt(params.year)
            callMeBackNonce.model = params.model
            callMeBackNonce.cc = params.cc
            callMeBackNonce.lang = params.lang
            callMeBackNonce.planType = params.planType
            callMeBackNonce.excessAmount = Integer.parseInt(params.excessAmount)
            callMeBackNonce.isPanelWorkshop = params.isPanelWorkshop.toBoolean()
            callMeBackNonce.isCompulsoryAvailable = params.isCompulsoryAvailable.toBoolean()
            callMeBackNonce.vehiclesumInsuredAmount = Integer.parseInt(params.vehiclesumInsuredAmount)
            callMeBackNonce.pricePerYear = Double.parseDouble(params.pricePerYear)
            callMeBackNonce.customerEmail = params.customerEmail
            callMeBackNonce.customerPhoneNumber = params.customerPhoneNumber
            callMeBackNonce.customerLineId = params.customerLineID
            callMeBackNonce.name = params.name
            callMeBackNonce.prefix = params.prefix
            callMeBackNonce.isMale = params.isMale.toBoolean()
            callMeBackNonce.isSingle = params.isSingle.toBoolean()
            callMeBackNonce.dob = params.dob
            callMeBackNonce.yrOfDriving = Integer.parseInt(params.yrOfDriving)
            callMeBackNonce.noOfClaim = Integer.parseInt(params.noOfClaim)
            callMeBackNonce.isGoToWorkOnly = params.isGoToWorkOnly.toBoolean()
            callMeBackNonce.isDashcam = params.isDashcam.toBoolean()
            callMeBackNonce.ncbRate = params.ncbRate
            callMeBackNonce.created = new Date()
            callMeBackNonce.request = params
            callMeBackNonce.ipAddress = request.getHeader("True-Client-IP")
            callMeBackNonce.opportunityNumber = opportunityNumber;
            callMeBackNonce.subModel = params.subModel;

            callMeBackNonce.save(flush:true)


            json.put("success", true)
            json.put("errorMessage","")

        }catch(HibernateException | IndexOutOfBoundsException | ClassCastException | NullPointerException e){
            json.put("success", false)
            json.put("errorMessage",e.getMessage())
        }

        return json;
    }
}

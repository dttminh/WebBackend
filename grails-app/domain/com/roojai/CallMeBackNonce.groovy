package com.roojai

class CallMeBackNonce {
    int id
    Date created
    String nonce
    String planId
    String brand
    int year
    String model
    String cc
    String lang
    String planType
    int excessAmount
    boolean isPanelWorkshop
    boolean isCompulsoryAvailable
    int vehiclesumInsuredAmount
    double pricePerYear
    String customerEmail
    String customerPhoneNumber
    String customerLineId
    String name
    String prefix
    boolean isMale
    boolean isSingle
    String dob
    int yrOfDriving
    int noOfClaim
    boolean isGoToWorkOnly
    boolean isDashcam
    String ncbRate
    String request
    String ipAddress
    String opportunityNumber
    String subModel

    static mapping = {
        planId index: 'call_me_back_nonce_planid_idx'
        created index: 'call_me_back_nonce_created_idx'
        nonce index: 'call_me_back_nonce_nonce_idx'
        version false
    }

    static constraints = {
        nonce blank:true, nullable:true
        planId blank:true, nullable:true
        brand blank:true, nullable:true
        year blank:true, nullable:true
        model blank:true, nullable:true
        cc blank:true, nullable:true
        lang blank:true, nullable:true
        planType blank:true, nullable:true
        excessAmount blank:true, nullable:true
        isPanelWorkshop blank:true, nullable:true
        isCompulsoryAvailable blank:true, nullable:true
        vehiclesumInsuredAmount blank:true, nullable:true
        pricePerYear blank:true, nullable:true
        customerEmail blank:true, nullable:true
        customerPhoneNumber blank:true, nullable:true
        customerLineId blank:true, nullable:true
        name blank:true, nullable:true
        prefix blank:true, nullable:true
        isMale blank:true, nullable:true
        isSingle blank:true, nullable:true
        dob blank:true, nullable:true
        yrOfDriving blank:true, nullable:true
        noOfClaim blank:true, nullable:true
        isGoToWorkOnly blank:true, nullable:true
        isDashcam blank:true, nullable:true
        ncbRate blank:true, nullable:true
        request blank:true, nullable:true
        ipAddress blank:true, nullable:true
        opportunityNumber blank:true, nullable:true
        subModel blank:true, nullable:true
    }
}

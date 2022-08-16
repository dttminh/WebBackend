package com.roojai

class BrokerDetails {

    String intermediaryAccountID
    String intermediaryReptEmail
    String intermediaryReptID
    String id

    static mapping = {
        version false
        table name: "broker_details"
        intermediaryAccountID column: "intermediary_account_id"
        intermediaryReptEmail column: "intermediary_rept_email"
        intermediaryReptID column: "intermediary_rept_id"
        id name: "intermediaryReptEmail"
    }

    static constraints = {
    }
}

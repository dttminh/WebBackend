package com.roojai.saleforce

class BrokerInfo {
    String intermediaryAccountID = ""
    String intermediaryReptID = ""

    @Override
    public String toString() {
        return "BrokerInfo [intermediaryAccountID=" + intermediaryAccountID +
                ", intermediaryReptID=" + intermediaryReptID +
                "]"
    }
}

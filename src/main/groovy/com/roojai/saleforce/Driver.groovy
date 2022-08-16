package com.roojai.saleforce

class Driver {
    String residentStatus = "";
    String thaiIdNumber = "";
    String phoneNumber = "";
    String passPortNumber = "";
    String countryOfIssuance = "";
    String occupation = "";
    String occupationGroup = "";
    String maritialStatus = "";//Married,Single,
    String lastName = "";
    String isMainDriver = false;// * boolean
    String gender = "";//Male , FeMale
    String firstName = "";
    String email = "";
    String drivingExperience = "";
    String driverAge = 0;// *Integer
    String driverAccidents = "";// None
    String postalCode = "";
    String houseNumber = "";
    String village = "";
    String soi = "";
    String subDistrict = "";
    String subDistrictCode = "";
    String companyNumber = "";
    String businessRegNumber = "";
    String dob = "";
    String district = "";
    String districtCode = "";
    String province = "";
    String provinceCode = "";
    String PersonHasOptedOutOfEmail = 'No'

    @Override
    public String toString() {
        return "Driver [residentStatus=" + residentStatus +
                ", thaiIdNumber=" + thaiIdNumber +
                ", phoneNumber=" + phoneNumber +
                ", passPortNumber=" + passPortNumber +
                ", countryOfIssuance=" + countryOfIssuance +
                ", occupation=" + occupation +
                ", occupationGroup=" + occupationGroup +
                ", maritialStatus=" + maritialStatus +
                ", lastName=" + lastName +
                ", isMainDriver=" + isMainDriver +
                ", gender=" + gender +
                ", firstName=" + firstName +
                ", email=" + email +
                ", drivingExperience=" + drivingExperience +
                ", driverAge=" + driverAge +
                ", driverAccidents=" + driverAccidents +
                ", postalCode=" + postalCode +
                ", houseNumber=" + houseNumber +
                ", village=" + village +
                ", soi=" + soi +
                ", subDistrict=" + subDistrict +
                ", subDistrictCode=" + subDistrictCode +
                ", companyNumber=" + companyNumber +
                ", businessRegNumber=" + businessRegNumber +
                ", dob=" + dob +
                ", district=" + district +
                ", districtCode=" + districtCode +
                ", province=" + province +
                ", provinceCode=" + provinceCode +
                ", PersonHasOptedOutOfEmail" + PersonHasOptedOutOfEmail +
                "]"
    }

}

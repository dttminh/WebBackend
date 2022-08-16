package com.roojai

import com.roojai.saleforce.Driver
import com.roojai.saleforce.Payment
import com.roojai.saleforce.ProductType
import com.roojai.saleforce.QuotationForm
import com.roojai.saleforce.QuoteLine
import com.roojai.util.StatusUtil
import com.roojai.util.StringUtil
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class QuotationController extends RestfulController {

    static responseFormats = ['json']

    def saleforceService
    def quotationService
    def paymentService

    QuotationController() {
        super(com.roojai.radar.output.Root)
    }

    def index() {
        String protections = [ProductType.Health.text(), ProductType.PersonalAccident.text(), ProductType.Covid19.text(), ProductType.CovidInboundTravel.text()]

        if (request.JSON?.productType != null && protections.contains(request.JSON.productType)) {
            health()
        } else {
            respond([:], status: 404)
        }
    }

    def saveQuotation() {
        QuotationForm quotationForm = createQuotationForm(params)

        JSONObject jsonResponse = saleforceService.saveQuotation(quotationForm, StringUtil.getIpAdress(request))

        respond(jsonResponse, status: jsonResponse ? 200 : 500)
    }

    @Deprecated
    def validateQuote() {
        render saleforceService.validateQuote(params)
    }

    //TODO Delete at 1.2.8
    def callMeBack() {
        render quotationService.checkCallBackSaleforce(params.current, params.hdnName, params.hdMobileNumber)
    }

    def retrieveQuotation() {
        JSONObject jsonResponse = new JSONObject()

        int statusCode = 200

        String opportunityNumberEncrypt = params.quotationId ?: params.opportunity_number

        if (opportunityNumberEncrypt != null && opportunityNumberEncrypt != "") {
            String opportunityNumber = StringUtil.decrypt(params.quotationId ?: params.opportunity_number)

            jsonResponse = quotationService.retrieveQuotation(opportunityNumber, opportunityNumberEncrypt)

            if (jsonResponse.success == false) {
                statusCode = 500
            }
        } else {
            statusCode = 400
        }

        respond(jsonResponse, status: statusCode)
    }

    def createPartnerQuote() {
        def hdLastName = params.last_name
        def hdMobileNumber = params.phone
        def hdEmail = params.email
        def leadSource = params.lead_source
        def carBrand = params.brand
        def carModel = params.model // Yaris,
        def coverType = StatusUtil.getCoverTypeConvert(params.coverType) // class
        def excess = params.excess
        def cc = params.cc
        def carYear = params.year
        def workshop = params.workshop == "0" ? "PanelWorkshop" : "AnyWorkshop" // planname
        def includeComp = (params.includeComp && params.includeComp != "") ? "Comp" : "NoComp"
        //def description = params.description
        //def insurePrice = params.insure-price-hidden
        //def premium = params.premium
        def sumInsure = params.sumInsure // sum insure
        def premium = params.premium

        // fixed
        def hdAge = 34
        def hdGender = "Male"
        def hdMaritialStatus = "Married"
        def hdDrivingExp = "6+"
        //def derivedNCB = 30
        def declaredNCB = "N"//"I don't know"
        def isSFCallMeBack = true
        def isInTimeCallMeBack = quotationService.isInTimeCallMeBack()
        if (isInTimeCallMeBack) {
            //call me back
            quotationService.callMeBack(hdLastName, hdMobileNumber, false)
            isSFCallMeBack = false  // tell sf don't log on call me back
        }
        def includeCompulsory = includeComp//"NoComp"
        def hdLastAccident = 0
        //def carDesc = "ไม่แน่ใจ"
        def driverNamed = "AnyOver30"
        // search on redbook
        //JSONObject redBook = redBookService.searchRedBookByPartnerModel(Partner.Gobear,carBrand,carModel,carYear,carDesc,cc)

        QuotationForm form = new QuotationForm()
        // quote is mandatory
        def quote = new com.roojai.saleforce.Quote()
        form.quote = quote
        quote.Year_of_Manufacture = carYear
        quote.Make = carBrand
        quote.Is_Main_Driver_PH = true
        quote.Declared_NCB = declaredNCB//derivedNCB
        quote.Excess = excess
        quote.Compulsory_Plan = includeCompulsory
        quote.Driver_Plan = driverNamed
        quote.Workshop_Type = workshop
        quote.Plan_Type = coverType
        //quote.Masterset_Id = masterSetId
        quote.Created_From = "Web" // Fixed
        //quote.Opportunity_Number = quotationId
        quote.send_email_flag = false
        quote.isUpdate = false
        quote.callMeBack = isSFCallMeBack //callMeback
        quote.carModel = carModel
        //quote.Model_Description = carDesc
        quote.Vehicle_Usage = "Social, pleasure and travelling"
        //quote.hdhowlongbeeninsured = hdhowlongbeeninsured
        //quote.hdChassisNumber = hdChassisNumber
        //quote.hdCarPlateProvince = hdCarPlateProvince ? Global.hdCarPlateProvince[1] : ""
        //quote.hdCarPlateNumber = hdCarPlateNumber
        //quote.hdCarOwnership = hdCarOwnership || "0"
        //quote.Start_date=hdPolicyStartDate
        //quote.End_date=hdPolicyEndDate
        //quote.whoDriveTheCar = hdOwnerIs
        //quote.previousInsurer=hdPreviousInsurer ? Global.hdPreviousInsurer.nameEn : ""
        //quote.previousCoverType=hdPreviousCurrentCoverType ? Global.hdPreviousCurrentCoverType.nameEn : ""
        //quote.abTestingVersion=ABTesting
        //quote.carAccessoryCarCamera=carAccessoryCarCamera
        quote.channel = leadSource
        //quote.isActivatePolicy=isActivatePolicy || false
        quote.Adjusted_vehicle_Sum_Insured = sumInsure
        quote.Engine_size = cc
        quote.Rating_Factor_Defaulted = true
        quote.Google_Client_ID = params.Google_Client_ID

        // phaccount
        def phaccount = new Driver()
        form.phaccount = phaccount
        //phaccount.residentStatus = hdResidentialStatus
        //phaccount.thaiIdNumber = hdCarOwnership == '0' && Global.hdResidentialStatus == "0" ? Global.hdIdentificationNumber : ""
        phaccount.phoneNumber = hdMobileNumber
        //phaccount.passPortNumber = hdCarOwnership == '0' && Global.hdResidentialStatus == "1" ? Global.hdPassportNumber : ""
        //phaccount.countryOfIssuance = hdCarOwnership == '0' && Global.hdResidentialStatus == "1" ? Global.hdCountry.name : ""
        //phaccount.occupation = hdCarOwnership != '1' && Global.hdOccupation ? Global.hdOccupation.nameEn : ""
        phaccount.maritialStatus = hdMaritialStatus
        phaccount.lastName = hdLastName
        phaccount.isMainDriver = true
        phaccount.gender = hdGender
        //phaccount.firstName = hdFirstName
        phaccount.email = hdEmail
        phaccount.drivingExperience = hdDrivingExp
        phaccount.driverAge = hdAge
        phaccount.driverAccidents = hdLastAccident
        //phaccount.postalCode = hdPostalCode
        //phaccount.houseNumber = hdHousingNumber
        //phaccount.village = hdVillageorBulding
        //phaccount.soi = hdRoadorSOI
        //phaccount.subDistrict = hdSubDistrictEN
        //phaccount.subDistrictCode = hdSubDistrictCode
        //phaccount.district = hdDistrict[1] || ""
        //phaccount.districtCode= hdDistrict[0] || ""
        //phaccount.province = hdProvince[2] || ""
        //phaccount.provinceCode = hdProvince[0] || ""
        //phaccount.companyNumber = hdCarOwnership != '1' ? "" : Global.hdCompanyname
        //phaccount.businessRegNumber = hdCarOwnership != '1' ? "" : Global.hdBusinessRegistrationNumber
        phaccount.dob = ''

        // driver[0]
        Driver driver = new Driver()
        form.drivers.add(driver)
        //driver.residentStatus=hdResidentialStatusMD : Global.hdResidentialStatus
        //driver.thaiIdNumber", hasMD ? (Global.hdResidentialStatusMD == "0" ? Global.hdIdentificationNumberMD : "") : (Global.hdResidentialStatus == "0" ? Global.hdIdentificationNumber : "")
        driver.phoneNumber = hdMobileNumber
        //driver.passPortNumber", hasMD ? (Global.hdResidentialStatusMD == "1" ? Global.hdPassportNumberMD : "") : (Global.hdResidentialStatus == "1" ? Global.hdPassportNumber : "")
        //driver.countryOfIssuance", hasMD ? (Global.hdResidentialStatusMD == "1" ? Global.hdCountryMD.name : "") : (Global.hdResidentialStatus == "1" ? Global.hdCountry.name : "")
        //driver.occupation", occupation.nameEn ? occupation.nameEn : ""
        //driver.occupationGroup", occupation.occupationGroup ? occupation.occupationGroup : ""
        driver.maritialStatus = hdMaritialStatus
        driver.lastName = hdLastName
        driver.isMainDriver = true
        driver.gender = hdGender
        driver.firstName = ''
        driver.email = hdEmail
        driver.drivingExperience = hdDrivingExp
        driver.driverAge = hdAge
        driver.driverAccidents = hdLastAccident
        driver.dob = ''

        // quoteline ** mandatory **
        QuoteLine quoteLine = new QuoteLine()
        quoteLine.Basic_premium__c = premium
        quoteLine.PA_passenger_SI__c = "100000"
        quoteLine.PA_Driver_SI__c = "100000"
        quoteLine.Bail_Bond_SI__c = "300000"
        quoteLine.Medical_Expenses_SI__c = "100000"
        quoteLine.Cover_Code__c = "VOL" // fixed
        quoteLine.Adjusted_technical_premium__c = "0.00" // fixed
        quoteLine.Reason_for_premium_adjustment__c = "" // fixed
        form.quoteLine = quoteLine

        // call saveQuotation
        def result = saleforceService.saveQuotation(form, StringUtil.getIpAdress(request))
        //println result
        render result
    }

    def findObjectByKey(String key, objList) {
        for (int i = 0; i < objList.size(); i++) {
            if (objList[i].accessoryName.toLowerCase().indexOf(key.toLowerCase()) >= 0) { //equals
                //println( key )
                return objList[i]
            }
        }
        return null
    }

    def health() {
        request.JSON?.item?.quote?.customerIpAddress = StringUtil.getIpAdress(request)

        JSONObject jsonRespond = quotationService.saveHealthQuote(request.JSON)

        respond(jsonRespond, status: jsonRespond == null ? 500 : 200)
    }

    private QuotationForm createQuotationForm(Map params){
        QuotationForm quotationForm = new QuotationForm()

        if (params.phaccount) {
            quotationForm.phaccount = new Driver(params.phaccount)
        }

        bindData(quotationForm, params, [include: ['drivers']])

        if (params.quoteLine) {
            quotationForm.quoteLine = new QuoteLine(params.quoteLine)
        }

        if (params.quote) {
            quotationForm.quote = new com.roojai.saleforce.Quote(params.quote)
        }

        bindData(quotationForm, params, [include: ['accessories']])

        bindData(quotationForm, params, [include: ['brokerInfo']])

        if (params.payment) {
            quotationForm.payment = new Payment(params.payment)
        }

        return quotationForm
    }

}

package com.roojai


import com.roojai.saleforce.Partner
import com.roojai.saleforce.ProductType
import com.roojai.util.DateTimeUtil
import com.roojai.util.StatusUtil
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONException
import org.grails.web.json.JSONObject

import java.nio.charset.UnsupportedCharsetException
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class QuotationService {

    SaleforceService saleforceService
    RedBookService redBookService
    RetrieveService retrieveService
    SystemconfigurationService systemconfigurationService
    PaymentService paymentService
    OccupationService occupationService

    String callMeBack(userName, phoneNumber, retry) {
        JSONObject root = new JSONObject()
        def status
        def returnMessage
        try {
            def url = systemconfigurationService.getValueByKey("onlinecontact.url")
//"https://onlinecontact.roojai.com/JJCallMeBack/callstored.aspx"

            HttpClient client = HttpClientBuilder.create().build()
            HttpPost post = new HttpPost(url)
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>()
            urlParameters.add(new BasicNameValuePair("CID", ""))
            urlParameters.add(new BasicNameValuePair("CName", userName))
            urlParameters.add(new BasicNameValuePair("Addr", ""))
            urlParameters.add(new BasicNameValuePair("1ContactOpt", "Voice"))
            urlParameters.add(new BasicNameValuePair("1ContactDetail", phoneNumber))
            urlParameters.add(new BasicNameValuePair("2ContactOpt", "Email"))
            urlParameters.add(new BasicNameValuePair("2ContactDetail", ""))
            urlParameters.add(new BasicNameValuePair("3ContactOpt", ""))
            urlParameters.add(new BasicNameValuePair("3ContactDetail", ""))
            urlParameters.add(new BasicNameValuePair("EmailOutMsg", ""))
            urlParameters.add(new BasicNameValuePair("SMOutMsg", ""))
            urlParameters.add(new BasicNameValuePair("VoiceOutMsg", ""))
            urlParameters.add(new BasicNameValuePair("ProgID", systemconfigurationService.getValueByKey("onlinecontact.ProgID")))
            urlParameters.add(new BasicNameValuePair("Lang", "TH"))
            post.addHeader("charset", "UTF-8")
            post.setEntity(new UrlEncodedFormEntity(urlParameters, "utf-8"))
            HttpResponse response = client.execute(post)
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"))
            StringBuffer result = new StringBuffer()
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line)
            }
            //<?xml version="1.0" encoding="utf-8"?><response><message>ÃƒÂ Ã‚Â¸Ã‚Â£ÃƒÂ Ã‚Â¸Ã‚Â²ÃƒÂ Ã‚Â¸Ã‚Â¢ÃƒÂ Ã‚Â¸?ÃƒÂ Ã‚Â¸Ã‚Â²ÃƒÂ Ã‚Â¸Ã‚Â£ÃƒÂ Ã‚Â¸Ã‚ÂªÃƒÂ Ã‚Â¸Ã‚Â³ÃƒÂ Ã‚Â¹Ã¢â€šÂ¬ÃƒÂ Ã‚Â¸Ã‚Â£ÃƒÂ Ã‚Â¹Ã¢â‚¬Â¡ÃƒÂ Ã‚Â¸Ã‹â€ [ÃƒÂ Ã‚Â¹Ã†â€™ÃƒÂ Ã‚Â¸Ã¢â€žÂ¢ÃƒÂ Ã‚Â¹Ã¢â€šÂ¬ÃƒÂ Ã‚Â¸Ã‚Â§ÃƒÂ Ã‚Â¸Ã‚Â¥ÃƒÂ Ã‚Â¸Ã‚Â²ÃƒÂ Ã‚Â¸Ã¢â‚¬â€�ÃƒÂ Ã‚Â¸Ã‚Â³ÃƒÂ Ã‚Â¸?ÃƒÂ Ã‚Â¸Ã‚Â²ÃƒÂ Ã‚Â¸Ã‚Â£]</message></response>
            def XML = new XmlParser().parseText(result.toString())
            returnMessage = XML.message.text()
            status = "true"
            def msg = retry ? systemconfigurationService.getValueByKey("message.thankMessageWorkingTimeRetry") : systemconfigurationService.getValueByKey("message.thankMessageWorkingTime")
            root.put("message", msg)
            root.put("success", true)
        } catch (Exception e) {
            log.error(e.getMessage(), e)
            returnMessage = "Exception : " + e.getMessage()
            status = false
            root.put("message", "Exception")
            root.put("success", false)
        } finally {
            CallMeBackData callData = new CallMeBackData(customerName: userName, phoneNumber: phoneNumber, type: "JJCallMeBack", status: status, result: returnMessage, created: new Date())
            callData.save(flush: true)
        }
        return root.toString()
    }

    //TODO Delete at 1.2.8
    String salesforceCallBack(userName, phoneNumber) {
        def returnMessage
        def status
        def returnResult
        try {
            JSONObject jsonToken = saleforceService.getAccessToken()
            def token = jsonToken.access_token
            def url = jsonToken.instance_url + "/services/data/v37.0/sobjects/Lead"
            JSONObject parameter = new JSONObject()
            parameter.put("LastName", userName)
            parameter.put("Phone", phoneNumber)
            parameter.put("status", "Open")
            parameter.put("LeadSource", "Web")
            parameter.put("Is_After_Office_Hours__c", true)

            HttpClient client = HttpClientBuilder.create().build()
            HttpPost post = new HttpPost(url)
            StringEntity params = new StringEntity(parameter.toString(), "utf-8")
            post.setEntity(params)
            post.addHeader("content-type", "application/json")
            post.addHeader("Authorization", "Bearer " + token)
            post.addHeader("charset", "UTF-8")
            HttpResponse response = client.execute(post)

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"))
            StringBuffer result = new StringBuffer()
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line)
            }
            returnMessage = result.toString()
            String time = new Date().format("HH:mm", TimeZone.getTimeZone('Asia/Bangkok'))
            String workingStartTime = systemconfigurationService.getValueByKey("onlinecontact.WorkingStartTime")
            String workingEndTime = systemconfigurationService.getValueByKey("onlinecontact.WorkingEndTime")
            JSONObject root = new JSONObject(result.toString())
            root.put("message", systemconfigurationService.getValueByKey(time >= workingStartTime && time <= workingEndTime ? "message.thankMessageWorkingTime" : "message.thankMessageNotWorkingTime"))
            status = root.success
            returnResult = root.toString()
        } catch (Exception e) {
            log.error(e.getMessage(), e)
            returnMessage = "Exception : " + e.getMessage()
            status = "false"
            JSONObject root = new JSONObject()
            root.put("message", "Exception")
            root.put("success", false)
            returnResult = root.toString()
        } finally {
            CallMeBackData callData = new CallMeBackData(customerName: userName, phoneNumber: phoneNumber, type: "Lead", status: status, result: returnMessage, created: new Date())
            callData.save(flush: true)
        }
        return returnResult
    }

    //TODO Delete at 1.2.8
    def checkCallBackSaleforce(currentPage, hdnName, hdMobileNumber) {
        def result = ""
        if (currentPage.startsWith("/quotationInput")) {
            result = salesforceCallBack(hdnName, hdMobileNumber)
        } else {
            String time = new Date().format("HH:mm", TimeZone.getTimeZone('Asia/Bangkok'))
            String workingStartTime = systemconfigurationService.getValueByKey("onlinecontact.WorkingStartTime")
            String workingEndTime = systemconfigurationService.getValueByKey("onlinecontact.WorkingEndTime")
            String message = systemconfigurationService.getValueByKey(time >= workingStartTime && time <= workingEndTime ? "message.thankMessageWorkingTime" : "message.thankMessageNotWorkingTime")
            JSONObject root = new JSONObject()
            root.put("success", true)
            root.put("message", message)
            result = root.toString()
            CallMeBackData callData = new CallMeBackData(customerName: hdnName, phoneNumber: hdMobileNumber, type: "SaveQuotation", status: "true", result: message, created: new Date())
            callData.save(flush: true)
        }
        return result
    }

    JSONObject retrieveQuotation(String opportunityNumber, String opportunityNumberEncrypt) {
        String jsonInput, jsonOutput, errorMessage
        JSONObject root = new JSONObject()
        root.put("success", true)
        JSONObject occupation

        try {
            JSONObject jsonToken = saleforceService.getAccessToken()
            String token = jsonToken.access_token
            String url = jsonToken.instance_url + "/services/apexrest/retrievequoteapi"
            String tokenType = jsonToken.token_type

            JSONObject parameter = new JSONObject()
            JSONObject quote = new JSONObject()
            JSONObject Opportunity_Number = new JSONObject()
            Opportunity_Number.put("Opportunity_Number", opportunityNumber)
            quote.put("quote", Opportunity_Number)
            parameter.put("item", quote)

            jsonInput = parameter.toString()

            HttpClient client = HttpClientBuilder.create().build()
            HttpPost post = new HttpPost(url)
            StringEntity params = new StringEntity(parameter.toString(), "utf-8")
            post.setEntity(params)
            post.addHeader("Authorization", tokenType + " " + token)
            post.addHeader("content-type", "application/json")
            post.addHeader("charset", "UTF-8")
            HttpResponse response = client.execute(post)
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"))
            StringBuffer result = new StringBuffer()
            String line
            while ((line = rd.readLine()) != null) {
                result.append(line)
            }
            jsonOutput = result.toString()

            JSONObject obj = new JSONObject(result.toString())

            if (obj?.data != null) {
                String protections = [ProductType.Health.text(), ProductType.PersonalAccident.text(), ProductType.Covid19.text(), ProductType.CovidInboundTravel.text()]
                String motors = [ProductType.MotorCar.text(), ProductType.MotorBike.text(), ProductType.ExtendedWarranty.text()]

                LocalDateTime date1 = LocalDateTime.of(2022,3,31,23,0)
                LocalDateTime date2 = LocalDateTime.now()

                if(obj.data.quote?.productType != null && obj.data.quote.productType.equals(ProductType.Covid19.text()) && date2.isAfter(date1)){
                    root.put("success", false)
                }else if (obj.data.quote?.productType != null && (protections.contains(obj.data.quote.productType))) {
                    obj.data = retrieveHealthProduct(obj.data)

                    root.put("phaccount", obj.data.phaccount)
                    root.put("quoteLineList", obj.data.quoteLineList)
                    root.put("brokerInfo", obj.data.brokerInfo)
                    root.put("quote", obj.data.quote)
                    root.put("questionResponses", obj.data.questionResponses)
                    root.put("beneficiary", obj.data.beneficiary)
                    root.put("coverList", obj.data.coverList)
                } else if (obj.data.quote?.Product_Type != null && motors.contains(obj.data.quote.Product_Type)) {
                    JSONObject dataObject = new JSONObject()

                    JSONObject redBook
                    if (obj.data.quote.leadSource != null && obj.data.quote.leadSource.equalsIgnoreCase(Partner.Gobear.text)) {
                        redBook = redBookService.searchVehicleKeyGobear(obj.data.quote.Vehicle_Key1)
                        root.put("finalValue", redBook ? redBook.gobearFinalValue : "")
                    } else {
                        redBook = redBookService.searchVehicleKey(obj.data.quote.Vehicle_Key1)
                        root.put("finalValue", redBook ? redBook.finalValue : "")
                    }

                    if (redBook != null) {
                        root.put("carBrand", redBook.makedesc)
                        root.put("carModel", redBook.modelFamilyDesc)
                        root.put("carYear", redBook.yearGroup)
                        root.put("carDesc", redBook.finalModelDesc)

                        root.put("vehicleKey", obj.data.quote.Vehicle_Key1)
                        root.put("hdMaritalStatus", obj.data.phaccount.maritialStatus)
                        root.put("hdGender", obj.data.phaccount.gender)
                        root.put("driverAccidents", StatusUtil.reverseDriverAccidents(obj.data.phaccount.driverAccidents))
                        root.put("drivingExperience", StatusUtil.reverseDrivingExperience(obj.data.phaccount.drivingExperience))
                        root.put("hdAge", obj.data.phaccount.driverAge == null ? "0" : obj.data.phaccount.driverAge)
                        root.put("hdDeclareNCB", StatusUtil.reverseDeclaredNCB(obj.data.quote.Declared_NCB))
                        root.put("masterSetId", obj.data.quote.Masterset_Id)
                        root.put("includeCompulsory", StatusUtil.reverseIncludeCompulsory(obj.data.quote.Compulsory_Plan))
                        root.put("quotationId", obj.data.quote.QuoteNumber)
                        root.put("coverType", StatusUtil.getCoverTypeConvert(obj.data.quote.Plan_Type))
                        root.put("excessPrice", retrieveService.converseNullTostring(obj.data.quote.Excess))
                        root.put("workshop", obj.data.quote.Workshop_Type != null ? obj.data.quote.Workshop_Type.replaceAll("\\s", "") : "")
                        root.put("hdOwnerIs", obj.data.phaccount.isMainDriver ? "0" : "2")
                        root.put("driverNamed", StatusUtil.driverNamedConvert(obj.data.quote.Driver_Plan))
                        root.put("hdIdentificationNumber", retrieveService.converseNullTostring(obj.data.phaccount.thaiIdNumber))
                        root.put("hdResidentialStatus", StatusUtil.reverseResidentStatus(obj.data.phaccount.residentStatus))
                        root.put("hdPassportNumber", retrieveService.converseNullTostring(obj.data.phaccount.passPortNumber))
                        root.put("hdCountry", retrieveService.getCountry(obj.data.phaccount.countryOfIssuance))
                        occupation = occupationService.getOccupation(obj.data.phaccount.occupation, obj.data.quote.Product_Type, obj.data.quote.policyInsurer)
                        root.put("hdOccupation", occupation.size() > 0 ? occupation : "")
                        root.put("hdDateofBirth", DateTimeUtil.converseISODateToThaiDate(obj.data.phaccount.dob))
                        root.put("hdFirstName", retrieveService.converseNullTostring(obj.data.phaccount.firstName))
                        root.put("hdLastName", retrieveService.converseNullTostring(obj.data.phaccount.lastName))
                        root.put("hdEmail", retrieveService.converseNullTostring(obj.data.phaccount.email))
                        root.put("hdMobileNumber", retrieveService.converseNullTostring(obj.data.phaccount.phoneNumber))
                        root.put("hdHousingNumber", retrieveService.converseNullTostring(obj.data.phaccount.houseNumber))
                        root.put("hdPostalCode", retrieveService.converseNullTostring(obj.data.phaccount.postalCode))
                        root.put("hdRoadorSOI", retrieveService.converseNullTostring(obj.data.phaccount.soi))
                        root.put("hdVillageorBulding", retrieveService.converseNullTostring(obj.data.phaccount.village))

                        def provinceDetail = retrieveService.getProvinceDetail(obj.data.phaccount.provinceCode, obj.data.phaccount.districtCode, obj.data.phaccount.subDistrictCode, obj.data.phaccount.postalCode)

                        root.put("hdProvince", (provinceDetail != null && provinceDetail.province.length() > 2) ? provinceDetail.province : "")
                        root.put("hdDistrict", (provinceDetail != null && provinceDetail.district.length() > 2) ? provinceDetail.district : "")

                        if (provinceDetail != null && provinceDetail.subDistrict.length() > 2) {
                            root.put("hdSubDistrictCode", retrieveService.converseNullTostring(provinceDetail.subDistrict[0]))
                            root.put("hdSubDistrict", retrieveService.converseNullTostring(provinceDetail.subDistrict[1]))
                            root.put("hdSubDistrictEN", retrieveService.converseNullTostring(provinceDetail.subDistrict[2]))
                        } else {
                            root.put("hdSubDistrict", retrieveService.converseNullTostring(obj.data.phaccount.subDistrict))
                            root.put("hdSubDistrictCode", retrieveService.converseNullTostring(obj.data.phaccount.subDistrictCode))
                            root.put("hdSubDistrictEN", retrieveService.converseNullTostring(obj.data.phaccount.subDistrict))
                        }

                        root.put("hdCarPlateNumber", retrieveService.converseNullTostring(obj.data.quote.carPlateNumber))
                        root.put("hdIsRedCarPlate", obj.data.quote.carPlateNumber == null ? "1" : "0")
                        root.put("hdCarPlateProvince", retrieveService.getProvince(obj.data.quote.carPlateProvince))
                        root.put("hdEngineNumber", retrieveService.converseNullTostring(obj.data.quote.engineNumber))
                        root.put("hdChassisNumber", retrieveService.converseNullTostring(obj.data.quote.chassisNumber))
                        root.put("hdPolicyStartDate", DateTimeUtil.converseISODateToThaiDate(obj.data.quote.Start_date))
                        root.put("hdPolicyEndDate", DateTimeUtil.converseISODateToThaiDate(obj.data.quote.End_date))
                        root.put("hdEmail", retrieveService.converseNullTostring(obj.data.phaccount.email))
                        root.put("hdCarOwnership", StatusUtil.reverseCarOwnerShip(obj.data.quote.carOwnerShip))
                        root.put("hdhowlongbeeninsured", StatusUtil.reverseHowLongBeenInsured(obj.data.quote.howLongInsured))
                        root.put("Vehicle_Usage", obj.data.quote.Vehicle_Usage)
                        root.put("taxiVehForHire", obj.data.quote.taxiVehForHire == null ? "" : obj.data.quote.taxiVehForHire)
                        root.put("rentalVehicle", obj.data.quote.rentalVehicle == null ? "" : obj.data.quote.rentalVehicle)
                        root.put("goodsTransportVehicle", obj.data.quote.goodsTransportVehicle == null ? "" : obj.data.quote.goodsTransportVehicle)
                        root.put("whoseGoods", obj.data.quote.whoseGoods == null ? "" : obj.data.quote.whoseGoods)
                        root.put("goodsTransportRoute", obj.data.quote.goodsTransportRoute == null ? "" : obj.data.quote.goodsTransportRoute)
                        root.put("hdBusinessRegistrationNumber", retrieveService.converseNullTostring(obj.data.phaccount.businessRegNumber))
                        root.put("hdCompanyname", retrieveService.converseNullTostring(obj.data.phaccount.companyNumber))
                        root.put("hdPreviousInsurer", retrieveService.getInsurer(obj.data.quote.previousInsurer))
                        root.put("hdPreviousCurrentCoverType", retrieveService.getCoverType(obj.data.quote.previousCoverType))
                        root.put("isActive", obj.data.quote.isActive ? obj.data.quote.isActive : false)
                        root.put("paymentStatus", StatusUtil.getPaymentStatus(obj.data.quote.paymentStatus))
                        root.put("paymentMethod", StatusUtil.getPaymentMethod(obj.data.quote.paymentMethod))
                        root.put("leadSource", obj.data.quote.leadSource)
                        root.put("isLeadSourceCodeValid", obj.data.quote.isLeadSourceCodeValid)
                        root.put("referralNumber", obj.data.quote.referralNumber)
                        root.put("tentativeStartDate", obj.data.quote.tentativeStartDate ? obj.data.quote.tentativeStartDate : "")
                        String currentPolicyExpiryDate = ""
                        if (obj.data.quote.CurrentPolicyExpiryDate != null) {
                            currentPolicyExpiryDate = DateTimeUtil.converseISODateToThaiDate(obj.data.quote.CurrentPolicyExpiryDate.substring(0, 10))
                        }
                        root.put("currentPolicyExpiryDate", currentPolicyExpiryDate)
                        root.put("prefLang", obj.data.quote.prefLang ? obj.data.quote.prefLang.toLowerCase() : "th")
                        root.put("ABTesting", retrieveService.converseNullTostring(obj.data.quote.abTestingVersion))
                        root.put("carAccessoryCarCamera", obj.data.quote.carAccessoryCarCamera)
                        root.put("Renewal_Adjustment_Value", obj.data.quote.Renewal_Adjustment_Value ?: "")
                        root.put("isRenewalPolicy", obj.data.quote.isRenewalPolicy)
                        root.put("carAccessoriesSI", obj.data.quote.carAccessoriesSI)
                        root.put("accessories", obj.data.accessories)
                        root.put("familyCode", obj.data.quote.FamilyCode ?: "")
                        root.put("kerbWeight", obj.data.quote.KerbWeight ?: "")
                        root.put("power", obj.data.quote.Power ?: "")
                        root.put("newPrice", obj.data.quote.NewPrice ?: "")
                        root.put("finalUWClass", obj.data.quote.Final_UW_Class ?: "")
                        root.put("finalUWGroup", obj.data.quote.Final_UW_Group ?: "")
                        root.put("finalPremiumClass", obj.data.quote.Final_Premium_Class ?: "")
                        root.put("finalPremiumGroup", obj.data.quote.Final_Premium_Group ?: "")
                        root.put("tariffGroup", obj.data.quote.Tariff_Group ?: "")
                        root.put("vehicleSumInsured", obj.data.quote.Vehicle_Sum_Insured1 ?: "")
                        root.put("adjustedVehicleSumInsured", obj.data.quote.Adjusted_vehicle_Sum_Insured ?: "")
                        root.put("postalCode", obj.data.quote.postalCode)
                        root.put("odoMeterReading", obj.data.quote.odoMeterReading)
                        root.put("PersonHasOptedOutOfEmail", obj.data.phaccount.PersonHasOptedOutOfEmail ? 'Yes' : 'No')
                        root.put("flagBlockContact", obj.data.phaccount.flagBlockContact)
                        root.put("Beneficiary", obj.data.quote.Beneficiary ?: "")
                        root.put("Additional_Benefit_PA_ME_SI", obj.data.quoteLineList[0] != null && obj.data.quoteLineList[0].PA_passenger_SI__c != null ? String.valueOf(obj.data.quoteLineList[0].PA_passenger_SI__c.intValue()) : '100000')
                        root.put("Voluntary_TPBI", obj.data.quoteLineList[0] != null && obj.data.quoteLineList[0].TPBI_per_person_SI__c != null ? String.valueOf(obj.data.quoteLineList[0].TPBI_per_person_SI__c.intValue()) : '')
                        root.put("CarFinancing", obj.data.quote.CarFinancing ?: "")
                        def ageMD = 0
                        def ageND = 0
                        def hdnIsDriverYounger = "no"
                        def hdnDriver_Youngest_Gender = ""
                        def hdnDriver_Youngest_Age = 0
                        def hdnDriver_Youngest_Marital_Status = ""

                        JSONArray drivers = new JSONArray()
                        JSONArray jsNDDriver = new JSONArray()
                        for (JSONObject driverData : new JSONArray(obj.data.drivers)) {
                            JSONObject driver = new JSONObject()
                            driver.put("thaiIdNumber", driverData.thaiIdNumber)
                            driver.put("residentStatus", StatusUtil.reverseResidentStatus(driverData.residentStatus))
                            driver.put("phoneNumber", driverData.phoneNumber)
                            driver.put("passPortNumber", driverData.passPortNumber)
                            driver.put("occupation", driverData.occupation)
                            driver.put("occupationGroup", driverData.occupationGroup)
                            driver.put("maritialStatus", driverData.maritialStatus)
                            driver.put("lastName", driverData.lastName)
                            driver.put("isMainDriver", driverData.isMainDriver)
                            driver.put("gender", driverData.gender)
                            driver.put("firstName", driverData.firstName)
                            driver.put("email", driverData.email)
                            driver.put("drivingExperience", StatusUtil.reverseDrivingExperience(driverData.drivingExperience))
                            driver.put("driverAge", driverData.driverAge)
                            driver.put("driverAccidents", StatusUtil.reverseDriverAccidents(driverData.driverAccidents))
                            driver.put("dob", driverData.dob)
                            driver.put("countryOfIssuance", retrieveService.getCountry(driverData.countryOfIssuance))
                            drivers.add(driver)

                            if (driverData.isMainDriver) {
                                root.put("hdIdentificationNumberMD", retrieveService.converseNullTostring(driverData.thaiIdNumber))
                                root.put("hdResidentialStatusMD", StatusUtil.reverseResidentStatus(driverData.residentStatus))
                                root.put("hdPassportNumberMD", retrieveService.converseNullTostring(driverData.passPortNumber))
                                root.put("hdFirstNameMD", retrieveService.converseNullTostring(driverData.firstName))
                                root.put("hdLastNameMD", retrieveService.converseNullTostring(driverData.lastName))
                                root.put("hdDateofBirthMD", DateTimeUtil.converseISODateToThaiDate(driverData.dob))
                                root.put("hdEmailAddressMD", retrieveService.converseNullTostring(driverData.email))
                                root.put("hdMobileNumberMD", retrieveService.converseNullTostring(driverData.phoneNumber))
                                root.put("hdDriverAgeMD", retrieveService.converseNullTo0(driverData.driverAge))
                                ageMD = driverData.driverAge
                                root.put("hdAgeMD", driverData.driverAge)
                                root.put("hdCountryMD", retrieveService.getCountry(driverData.countryOfIssuance))
                                occupation = occupationService.getOccupation(driverData.occupation, obj.data.quote.Product_Type, obj.data.quote.policyInsurer)
                                root.put("hdOccupationMD", occupation.size() > 0 ? occupation : "")
                                root.put("hdMaritalStatusMD", driverData.maritialStatus)
                                root.put("hdGenderMD", driverData.gender)
                                root.put("drivingExperienceMD", StatusUtil.reverseDrivingExperience(driverData.drivingExperience))
                                root.put("driverAccidentsMD", StatusUtil.reverseDriverAccidents(driverData.driverAccidents))
                            } else {
                                JSONObject jsTmp = new JSONObject()
                                jsTmp.put("hdResidentialStatusND", StatusUtil.reverseResidentStatus(driverData.residentStatus))
                                jsTmp.put("hdIdentificationNumberND", retrieveService.converseNullTostring(driverData.thaiIdNumber))
                                jsTmp.put("hdPassportNumberND", retrieveService.converseNullTostring(driverData.passPortNumber))
                                jsTmp.put("hdCountryND", retrieveService.getCountry(driverData.countryOfIssuance))
                                jsTmp.put("hdDateofBirthND", DateTimeUtil.converseISODateToThaiDate(driverData.dob))
                                jsTmp.put("hdDriverAgeND", retrieveService.converseNullTo0(driverData.driverAge))
                                jsTmp.put("hdFirstNameND", retrieveService.converseNullTostring(driverData.firstName))
                                jsTmp.put("hdLastNameND", retrieveService.converseNullTostring(driverData.lastName))
                                jsTmp.put("hdMaritalStatusND", driverData.maritialStatus)
                                jsTmp.put("hdGenderND", driverData.gender)
                                jsTmp.put("hdDrivingExperienceND", StatusUtil.reverseDrivingExperience(driverData.drivingExperience))
                                jsTmp.put("hdAccidentsInLast12MonthsND", StatusUtil.reverseDriverAccidents(driverData.driverAccidents))
                                occupation = occupationService.getOccupation(driverData.occupation, obj.data.quote.Product_Type, obj.data.quote.policyInsurer)
                                jsTmp.put("hdOccupationND", occupation.size() > 0 ? occupation : "")
                                jsNDDriver.put(jsTmp)

                                ageND = driverData.driverAge
                                if (ageND < ageMD || ageND < hdnDriver_Youngest_Age) {
                                    hdnIsDriverYounger = "yes"
                                    hdnDriver_Youngest_Gender = driverData.gender == null ? "" : driverData.gender
                                    hdnDriver_Youngest_Age = ageND
                                    hdnDriver_Youngest_Marital_Status = driverData.maritialStatus == null ? "" : driverData.maritialStatus
                                }
                            }
                            root.put("NDDriver", jsNDDriver)
                        }
                        dataObject.put("drivers", drivers)

                        root.put("hdnIsDriverYounger", hdnIsDriverYounger)
                        root.put("hdnDriver_Youngest_Gender", hdnDriver_Youngest_Gender)
                        root.put("hdnDriver_Youngest_Age", hdnDriver_Youngest_Age)
                        root.put("hdnDriver_Youngest_Marital_Status", hdnDriver_Youngest_Marital_Status)
                        root.put("rsaProduct", obj.data.quote.RSA_Product ?: "No")
                        root.put("ePolicyDocument", obj.data.quote.ePolicyDocument != null ? obj.data.quote.ePolicyDocument : "")
                        root.put("opportunityId", obj.data.quote.opportunityId)
                        root.put("carReplacementProductName", obj.data.quote.carReplacementProductName)
                        root.put("carReplacementProductOptionNo", obj.data.quote.carReplacementProductOptionNo)

                        JSONObject phaccount = new JSONObject()
                        phaccount.put("lastName", obj.data.phaccount.lastName)
                        phaccount.put("subDistrictCode", obj.data.phaccount.subDistrictCode)
                        phaccount.put("occupation", obj.data.phaccount.occupation)
                        phaccount.put("occupationGroup", obj.data.phaccount.occupationGroup)
                        phaccount.put("districtCode", obj.data.phaccount.districtCode)
                        phaccount.put("gender", obj.data.phaccount.gender)
                        phaccount.put("maritialStatus", obj.data.phaccount.maritialStatus)
                        phaccount.put("drivingExperience", StatusUtil.reverseDrivingExperience(obj.data.phaccount.drivingExperience))
                        phaccount.put("postalCode", obj.data.phaccount.postalCode)
                        phaccount.put("houseNumber", obj.data.phaccount.houseNumber)
                        phaccount.put("countryOfIssuance", retrieveService.getCountry(obj.data.phaccount.countryOfIssuance))
                        phaccount.put("soi", obj.data.phaccount.soi)
                        phaccount.put("companyNumber", obj.data.phaccount.companyNumber)
                        phaccount.put("province", obj.data.phaccount.province)
                        phaccount.put("village", obj.data.phaccount.village)
                        phaccount.put("email", obj.data.phaccount.email)
                        phaccount.put("PersonHasOptedOutOfEmail", obj.data.phaccount.PersonHasOptedOutOfEmail)
                        phaccount.put("driverAccidents", StatusUtil.reverseDriverAccidents(obj.data.phaccount.driverAccidents))
                        phaccount.put("isMainDriver", obj.data.phaccount.isMainDriver)
                        phaccount.put("residentStatus", StatusUtil.reverseResidentStatus(obj.data.phaccount.residentStatus))
                        phaccount.put("businessRegNumber", obj.data.phaccount.businessRegNumber)
                        phaccount.put("provinceCode", obj.data.phaccount.provinceCode)
                        phaccount.put("thaiIdNumber", obj.data.phaccount.thaiIdNumber)
                        phaccount.put("firstName", obj.data.phaccount.firstName)
                        phaccount.put("phoneNumber", obj.data.phaccount.phoneNumber)
                        phaccount.put("passPortNumber", obj.data.phaccount.passPortNumber)
                        phaccount.put("dob", obj.data.phaccount.dob)
                        phaccount.put("district", obj.data.phaccount.district)
                        phaccount.put("driverAge", obj.data.phaccount.driverAge)
                        phaccount.put("subDistrict", obj.data.phaccount.subDistrict)
                        dataObject.put("phaccount", phaccount)

                        JSONObject quoteObject = new JSONObject()
                        quoteObject.put("isNewInsurer", obj.data.quote.isNewInsurer)
                        quoteObject.put("premiumToPay", obj.data.quote.premiumToPay)
                        quoteObject.put("paymentFrequency", obj.data.quote.paymentFrequency ?: "")
                        quoteObject.put("totalPremium", obj.data.quote.totalPremium ?: "")
                        quoteObject.put("transactionNumber", obj.data.quote.transactionNumber)
                        if (obj?.data?.quote?.Product_Type?.equalsIgnoreCase("MotorBike")) {
                            quoteObject.put("policyInsurer", obj.data.quote.policyInsurer ?:"")
                        }
                        quoteObject.put("approvedCode", obj.data.quote.approvedCode)
                        quoteObject.put("carPlateNumber", obj.data.quote.carPlateNumber)
                        quoteObject.put("carPlateProvince", obj.data.quote.carPlateProvince)
                        quoteObject.put("productType", obj?.data?.quote?.Product_Type)
                        quoteObject.put("extendedWarrantyProductOptionNo", obj?.data?.quote?.extendedWarrantyProductOptionNo)
                        quoteObject.put("postalCode", obj.data.quote.postalCode)
                        quoteObject.put("odoMeterReading", obj.data.quote.odoMeterReading)
                        quoteObject.put("rsaProduct", obj.data.quote.RSA_Product ?: "No")
                        quoteObject.put("opportunityNumber", obj.data.quote.QuoteNumber)
                        quoteObject.put("carBrand", redBook?.makedesc)
                        quoteObject.put("carModel", redBook?.modelFamilyDesc)
                        quoteObject.put("carYear", redBook?.yearGroup)
                        quoteObject.put("carDesc", redBook?.finalModelDesc)
                        quoteObject.put("versionId", obj.data.quote.versionId)

                        dataObject.put("quote", quoteObject)

                        JSONArray quoteLineList = new JSONArray()
                        JSONObject quoteLineListObject = new JSONObject()

                        if (obj?.data?.quoteLineList[0]?.PA_Driver_SI__c != null) {
                            quoteLineListObject.put("PA_Driver_SI__c", String.valueOf((int) obj.data.quoteLineList[0].PA_Driver_SI__c))
                        }
                        if (obj?.data?.quoteLineList[0]?.Medical_Expenses_SI__c != null) {
                            quoteLineListObject.put("Medical_Expenses_SI__c", String.valueOf((int) obj.data.quoteLineList[0].Medical_Expenses_SI__c))
                        }

                        if(obj?.data?.quoteLineList[0]?.Adjusted_technical_premium__c != null) {
                            quoteLineListObject.put("Adjusted_technical_premium__c", obj.data.quoteLineList[0].Adjusted_technical_premium__c)
                        }

                        if(obj?.data?.quoteLineList[0]?.Premium_adjustment_pct__c != null) {
                            quoteLineListObject.put("Premium_adjustment_pct__c", obj.data.quoteLineList[0].Premium_adjustment_pct__c)
                        }
                        if(obj?.data?.quoteLineList[0]?.Reason_for_premium_adjustment__c){
                            quoteLineListObject.put("Reason_for_premium_adjustment__c", obj.data.quoteLineList[0].Reason_for_premium_adjustment__c)
                        }
                        quoteLineList.add(quoteLineListObject)

                        dataObject.put("quoteLineList", quoteLineList)

                        root.put("data", dataObject)
                        root.put("brokerInfo", obj.data.brokerInfo)
                    } else {
                        errorMessage = "Redbook record not found"
                    }
                }
            } else {
                root.put("success", false)
            }
        } catch (JSONException | Exception e) {
            log.error "Error: ${e.message}", e
            errorMessage = e.message
            root.put("success", false)
        }

        RetrieveData retrieveData = new RetrieveData(opportunityNumber: opportunityNumber, opportunityNumberEncrypt: opportunityNumberEncrypt, jsonInput: jsonInput, jsonOutput: jsonOutput, errorMessage: errorMessage, created: new Date())
        retrieveData.save(flush: true)

        return root
    }

    boolean isInTimeCallMeBack() {
        boolean isCallMe = true
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"))
        def now = new Date()
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        def spcDay = systemconfigurationService.getValueByKey("onlinecontact.specialDates").equals("-") ? "" : systemconfigurationService.getValueByKey("onlinecontact.specialDates")
        def holDay = systemconfigurationService.getValueByKey("onlinecontact.holidayDates").equals("-") ? "" : systemconfigurationService.getValueByKey("onlinecontact.holidayDates")
        def specialDates = spcDay.split(",")
        def holidayDates = holDay.split(",")
        def currentDate = now.format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok'))
        if (Arrays.asList(specialDates).contains(currentDate) || now.getDay() == 6 || now.getDay() == 0) {
            def specialStartTime = systemconfigurationService.getValueByKey("onlinecontact.specialStartTime")
            def specialEndtime = systemconfigurationService.getValueByKey("onlinecontact.specialEndtime")
            String dateInString1 = now.format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok')) + " " + specialStartTime
            String dateInString2 = now.format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok')) + " " + specialEndtime
            Date dateStart = sdf.parse(dateInString1)
            Date dateEnd = sdf.parse(dateInString2)
            def chkDateStart = now.after(dateStart)
            def chkDateEnd = now.before(dateEnd)
            if (chkDateStart && chkDateEnd) {
                isCallMe = true
            } else {
                isCallMe = false
            }

        } else if (Arrays.asList(holidayDates).contains(currentDate)) {
            isCallMe = false
        } else {
            def startTime = systemconfigurationService.getValueByKey("onlinecontact.WorkingStartTime")
            def endtime = systemconfigurationService.getValueByKey("onlinecontact.WorkingEndTime")
            String dateInString1 = now.format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok')) + " " + startTime
            String dateInString2 = now.format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok')) + " " + endtime
            Date dateStart = sdf.parse(dateInString1)
            Date dateEnd = sdf.parse(dateInString2)
            def chkDateStart = now.after(dateStart)
            def chkDateEnd = now.before(dateEnd)
            def day = now.getDay()
            if (chkDateStart && chkDateEnd && day != 6 && day != 0) {
                isCallMe = true
            } else {
                isCallMe = false
            }
        }
        return isCallMe
    }

    JSONObject saveHealthQuote(JSONObject jsonRequest) {
        JSONObject jsonToken = saleforceService.getAccessToken()
        SaleforceData saleforceData = new SaleforceData()
        saleforceData.masterset_id = jsonRequest?.item?.quote?.mastersetId
        saleforceData.opportunity_number = jsonRequest?.item?.quote?.opportunityNumber
        saleforceData.send_email_flag = jsonRequest?.item?.quote?.sendEmailFlag?.toString()
        saleforceData.isupdate = jsonRequest?.item?.quote?.isUpdate?.toString()
        saleforceData.call_me_back = jsonRequest?.item?.quote?.callMeBack?.toString()

        if (jsonRequest?.item?.payment?.paymentID != null && jsonRequest?.item?.payment?.paymentID != 0) {
            PaymentData paymentData = paymentService.getPaymentData(jsonRequest.item.payment.paymentID, jsonRequest.item.quote.opportunityNumber)
            JSONObject paymentObject = new JSONObject(paymentData.json_input)
            jsonRequest.item.put("payments", paymentObject?.item?.payments)
            jsonRequest.item.put("payment", paymentObject?.item?.payment)
            jsonRequest.item.put("creditCard", paymentObject?.item?.creditCard)
        }

        HttpClient httpClient = HttpClientBuilder.create().build()
        HttpPost httpPost = new HttpPost(jsonToken.instance_url + "/services/apexrest/healthquoteapi")

        JSONObject item = new JSONObject()
        item.put("item", jsonRequest?.item)

        try {
            saleforceData.json_input = item?.toString()
            StringEntity stringEntity = new StringEntity(item?.toString(), "UTF-8")
            httpPost.setEntity(stringEntity)
        } catch (UnsupportedCharsetException e) {
            log.error "Error: ${e.message}", e
        }

        httpPost.addHeader("content-type", "application/json")
        httpPost.addHeader("Authorization", jsonToken.token_type + " " + jsonToken.access_token)
        httpPost.addHeader("charset", "UTF-8")

        StringBuffer stringBuffer = new StringBuffer()
        try {
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()))
            String line
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line)
            }
        } catch (IOException | ClientProtocolException | UnsupportedOperationException e) {
            log.error "Error: ${e.message}", e
        }

        try {
            httpClient.close()
        } catch (IOException e) {
            log.error "Error: ${e.message}", e
        }

        saleforceData.output = stringBuffer.toString()
        saleforceData.created = new Date()
        saleforceData.remote_addr = jsonRequest?.item?.quote?.customerIpAddress
        saleforceData.productType = jsonRequest?.productType

        if (!saleforceData.save()) {
            saleforceData.errors.allErrors.each {
                log.error it.toString()
            }
        }

        JSONObject jsonRespond
        try {
            jsonRespond = new JSONObject(stringBuffer.toString())
        } catch (JSONException e) {
            log.error "Error: ${e.message}", e
        }

        return jsonRespond
    }

    JSONObject retrieveHealthProduct(JSONObject data) {
        JSONObject jsonResponse = new JSONObject()

        JSONObject phaccount = new JSONObject()
        phaccount.put("firstName", data?.phaccount?.firstName ?: "")
        phaccount.put("lastName", data?.phaccount?.lastName ?: "")
        phaccount.put("email", data?.phaccount?.email ?: "")
        phaccount.put("PersonHasOptedOutOfEmail", data?.phaccount?.PersonHasOptedOutOfEmail ?: false)
        phaccount.put("thaiIdNumber", data?.phaccount?.thaiIdNumber ?: "")
        phaccount.put("phoneNumber", data?.phaccount?.phoneNumber ?: "")
        phaccount.put("residentStatus", data?.phaccount?.residentStatus ?: "")
        phaccount.put("passPortNumber", data?.phaccount?.passPortNumber ?: "")
        phaccount.put("countryOfIssuance", data?.phaccount?.countryOfIssuance ?: "")
        phaccount.put("dob", data?.phaccount?.dob ?: "")
        phaccount.put("driverAge", data?.phaccount?.driverAge?.toString() ?: "")
        phaccount.put("gender", data?.phaccount?.gender ?: "")
        phaccount.put("maritialStatus", data?.phaccount?.maritialStatus ?: "")
        phaccount.put("height", data?.phaccount?.height != null ? String.valueOf((int) data.phaccount.height) : "")
        phaccount.put("weight", data?.phaccount?.weight != null ? String.valueOf((int) data.phaccount.weight) : "")
        phaccount.put("smoker", data?.phaccount?.smoker ?: "")
        phaccount.put("postalCode", data?.phaccount?.postalCode ?: "")
        phaccount.put("province", data?.phaccount?.province ?: "")
        phaccount.put("provinceCode", data?.phaccount?.provinceCode ?: "")
        phaccount.put("district", data?.phaccount?.district ?: "")
        phaccount.put("districtCode", data?.phaccount?.districtCode ?: "")
        phaccount.put("subDistrictCode", data?.phaccount?.subDistrictCode ?: "")
        phaccount.put("subDistrict", data?.phaccount?.subDistrict ?: "")
        phaccount.put("soi", data?.phaccount?.soi ?: "")
        phaccount.put("village", data?.phaccount?.village ?: "")
        phaccount.put("houseNumber", data?.phaccount?.houseNumber ?: "")
        phaccount.put("moo", data?.phaccount?.moo ?: "")
        phaccount.put("companyNumber", data?.phaccount?.companyNumber ?: "")
        phaccount.put("businessRegNumber", data?.phaccount?.businessRegNumber ?: "")
        phaccount.put("occupation", occupationService.getOccupation(data.phaccount.occupation, data.quote.productType, data.quote.insurer))
        phaccount.put("salutation", data?.phaccount?.salutation ?: "")
        phaccount.put("countryOfResidence", data?.phaccount?.countryOfResidence ?: "")
        jsonResponse.put("phaccount", phaccount)

        JSONArray quoteLineList = new JSONArray()
        for (int i = 0; i < data?.quoteLineList?.length(); i++) {
            JSONObject quoteLine = new JSONObject()
            quoteLine.put("Cover_Code__c", data?.quoteLineList[i]?.Cover_Code__c ?: "")
            quoteLine.put("Sum_Assured__c", data?.quoteLineList[i]?.Sum_Assured__c ?: "")

            Iterator<String> it = data?.quoteLineList[i].keys()
            while(it.hasNext()) {
                String name = it.next()
                if(name.contains("Additional_Coverage_Name") || name.contains("Additional_Coverage_Sum_Assured")){
                    quoteLine.put(name, data.quoteLineList[i][name])
                }
            }
            quoteLineList.add(quoteLine)
        }
        jsonResponse.put("quoteLineList", quoteLineList)

        JSONObject quote = new JSONObject()
        quote.put("postalCode", data?.quote?.postalCode ?: "")
        quote.put("channel", data?.quote?.channel ?: "")
        quote.put("prefLang", data?.quote?.prefLang ?: "")
        quote.put("tentativeStartDate", data?.quote?.tentativeStartDate ?: "")
        quote.put("mastersetId", data?.quote?.mastersetId ?: "")
        quote.put("ePolicyDocument", data?.quote?.ePolicyDocument ?: "")
        quote.put("abTestingVersion", data?.quote?.abTestingVersion ?: "")
        quote.put("opportunityNumber", data?.quote?.opportunityNumber ?: "")
        quote.put("isActive", data?.quote?.isActive ?: false)
        quote.put("productType", data?.quote?.productType ?: "")
        quote.put("totalPremium", data?.quote?.totalPremium ?: 0)
        quote.put("Start_date", data?.quote?.Start_date?.substring(0, 10))
        quote.put("End_date", data?.quote?.End_date?.substring(0, 10))
        quote.put("questionSetCode", data?.quote?.questionSetCode ?: "")
        quote.put("paymentStatus", data?.quote?.paymentStatus ?: "")
        quote.put("salary", data?.quote?.salary ?: "")
        quote.put("approvedCode", data?.quote?.approvedCode ?: "")
        quote.put("referralNumber", data?.quote?.referralNumber ?: "")
        quote.put("policyPeriod", data?.quote?.policyPeriod ?: "")
        quote.put("departurePoint", data?.quote?.departurePoint ?: "")

        jsonResponse.put("quote", quote)

        JSONObject brokerInfo = new JSONObject()
        brokerInfo.put("intermediaryReptID", data?.brokerInfo?.intermediaryReptID ?: "")
        brokerInfo.put("intermediaryReptEmail", data?.brokerInfo?.intermediaryReptEmail ?: "")
        brokerInfo.put("intermediaryAccountID", data?.brokerInfo?.intermediaryAccountID ?: "")
        jsonResponse.put("brokerInfo", brokerInfo)

        JSONArray questionResponses = new JSONArray()
        for (int i = 0; i < data?.questionResponses?.length(); i++) {
            JSONObject questionResponse = new JSONObject()
            questionResponse.put("Question_Code__c", data?.questionResponses[i]?.Question_Code__c ?: "")
            questionResponse.put("Question_id__c", data?.questionResponses[i]?.Question_id__c ?: "")
            questionResponse.put("Response_Value__c", data?.questionResponses[i]?.Response_Value__c ?: "")
            questionResponses.add(questionResponse)
        }
        jsonResponse.put("questionResponses", questionResponses)

        jsonResponse.put("beneficiary", data?.beneficiary)
        jsonResponse.put("coverList", data?.coverList)

        return jsonResponse
    }
}

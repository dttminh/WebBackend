package com.roojai


import com.roojai.saleforce.Partner
import com.roojai.saleforce.PaymentType
import com.roojai.saleforce.ProductType
import com.roojai.saleforce.QuotationForm
import com.roojai.util.DateTimeUtil
import com.roojai.util.FileCacheUtil
import com.roojai.util.StatusUtil
import grails.gorm.transactions.Transactional
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONException
import org.grails.web.json.JSONObject

import java.time.DateTimeException
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException

@Transactional
class SaleforceService {
    def redBookService
    def SystemconfigurationService
    def SendMailData

    final Integer TokenExpireTime = 90 * 60// 1.5 hour
    final String SALEFORCE_CACHE = "SaleforceTokenV2"

    JSONObject getAccessToken() {
        String token = FileCacheUtil.getCache(SALEFORCE_CACHE, TokenExpireTime)
        if (token == null) {
            HttpClient client = HttpClientBuilder.create().build()
            def tokenEndPoint = SystemconfigurationService.getValueByKey("saleforce.url") + "/services/oauth2/token"
            //"https://test.salesforce.com/services/oauth2/token"
            HttpPost post = new HttpPost(tokenEndPoint)

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>()
            urlParameters.add(new BasicNameValuePair("grant_type", SystemconfigurationService.getValueByKey("saleforce.grant_type")))
            urlParameters.add(new BasicNameValuePair("client_id", SystemconfigurationService.getValueByKey("saleforce.client_id")))
            urlParameters.add(new BasicNameValuePair("client_secret", SystemconfigurationService.getValueByKey("saleforce.client_secret")))
            urlParameters.add(new BasicNameValuePair("username", SystemconfigurationService.getValueByKey("saleforce.username")))
            urlParameters.add(new BasicNameValuePair("password", SystemconfigurationService.getValueByKey("saleforce.password")))

            post.setEntity(new UrlEncodedFormEntity(urlParameters))

            HttpResponse response = client.execute(post)

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))

            StringBuffer result = new StringBuffer()
            String line = ""
            while ((line = rd.readLine()) != null) {
                result.append(line)
            }
            JSONObject json = new JSONObject(result.toString())
            FileCacheUtil.putCache(SALEFORCE_CACHE, result.toString())
            return json
        } else {
            JSONObject json = new JSONObject(token)
            return json
        }
    }

    @Deprecated
    JSONObject validateQuote(params) {
        String sfResponse
        JSONObject returnValue = new JSONObject()
        try {
            JSONObject root = new JSONObject()
            root.put("Make", params.Make)
            root.put("Model_Family", params.Model_Family)
            root.put("Year_of_Manufacture", params.Year_of_Manufacture)
            root.put("ThaiID_Number", params.ThaiID_Number)
            root.put("Company_Reg_Number", params.Company_Reg_Number)
            root.put("Passport_Number", params.Passport_Number)
            root.put("Car_plate_Province", params.Car_plate_Province)
            root.put("Car_plate_Number", params.Car_plate_Number)
            root.put("Flag_Validate_Customer", params.Flag_Validate_Customer.toBoolean())
            if (params.dob) {
                root.put("dob", DateTimeUtil.converseThaiDateToISODate(params.dob))
            }
            root.put("gender", params.gender)
            root.put("Opportunity_Number", params.Opportunity_Number)
            JSONObject request = new JSONObject()
            request.put("request", root)

            JSONObject jsonToken = getAccessToken()
            String token = jsonToken.access_token
            String tokenType = jsonToken.token_type
            String url = jsonToken.instance_url + "/services/apexrest/validateQuote"

            if (token != null && !token.equals("")) {
                HttpClient client = HttpClientBuilder.create().build()
                HttpPost post = new HttpPost(url)
                StringEntity entity = new StringEntity(request.toString(), "UTF-8")
                post.setEntity(entity)
                post.addHeader("content-type", "application/json")
                post.addHeader("Authorization", tokenType + " " + token)
                post.addHeader("charset", "UTF-8")
                HttpResponse response = client.execute(post)
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                StringBuffer result = new StringBuffer()
                String line = ""
                while ((line = rd.readLine()) != null)
                    result.append(line)

                sfResponse = result.toString()

                returnValue = new JSONObject(sfResponse)
            }
        } catch (JSONException e) {
            JSONObject jsonException = new JSONObject()
            jsonException.put("params", params)
            jsonException.put("sfResponse", sfResponse)
            log.error(jsonException.toString(), e)
        }
        return returnValue
    }

    JSONObject saveQuotation(QuotationForm quotationForm, String remoteAddr) {
        JSONObject root = createJsonQuotation(quotationForm, remoteAddr)
        JSONObject sfResponse = saveQuotationToSF(root)

        SaleforceData saleforceData = new SaleforceData(masterset_id: root?.item?.quote?.Masterset_Id, opportunity_number: sfResponse?.opportunityNumber ?: root?.item?.quote?.Opportunity_Number, send_email_flag: root?.item?.quote?.send_email_flag, isupdate: root?.item?.quote?.isUpdate, call_me_back: root?.item?.quote?.callMeBackRequest, json_input: root?.toString(), output: sfResponse?.toString(), created: new Date(), remote_addr: remoteAddr, productType: root?.item?.quote?.Product_Type)
        saleforceData.save(flush: true)

        return sfResponse
    }

    JSONObject saveQuotationToSF(JSONObject requestObject) {
        JSONObject jsonResponse
        String sfResponse
        try{
            JSONObject jsonToken = getAccessToken()
            String token = jsonToken.access_token
            String tokenType = jsonToken.token_type
            String url = jsonToken.instance_url + "/services/apexrest/quoteapi"

            if (token != null && !token.equals("")) {
                HttpClient client = HttpClientBuilder.create().build()
                HttpPost post = new HttpPost(url)
                StringEntity params = new StringEntity(requestObject.toString(), "UTF-8")
                post.setEntity(params)
                post.addHeader("content-type", "application/json")
                post.addHeader("Authorization", tokenType + " " + token)
                post.addHeader("charset", "UTF-8")
                HttpResponse response = client.execute(post)
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                StringBuffer result = new StringBuffer()
                String line
                while ((line = rd.readLine()) != null) {
                    result.append(line)
                }

                sfResponse = result.toString()
            }

            if (sfResponse != null) {
                jsonResponse = new JSONObject(sfResponse)
            }
        }catch(JSONException e){
            log.error "Error: ${e.message}", e
            jsonResponse = new JSONObject()
            jsonResponse.put("success", false)
            jsonResponse.put("errorMessage", sfResponse ?: e.message)
        }

        return jsonResponse
    }

    JSONObject createJsonQuotation(QuotationForm quotationForm, String remoteAddr) {
        JSONObject root = new JSONObject()
        try {
            JSONObject item = new JSONObject()
            // policyHolder
            JSONObject phaccount = new JSONObject()
            phaccount.put("residentStatus", StatusUtil.getResidentStatus(quotationForm.phaccount.residentStatus))
            phaccount.put("thaiIdNumber", quotationForm.phaccount.thaiIdNumber)
            phaccount.put("phoneNumber", quotationForm.phaccount.phoneNumber)
            phaccount.put("passPortNumber", quotationForm.phaccount.passPortNumber)
            phaccount.put("countryOfIssuance", quotationForm.phaccount.countryOfIssuance)
            phaccount.put("occupation", quotationForm.phaccount.occupation)
            phaccount.put("maritialStatus", quotationForm.phaccount.maritialStatus ?: "")
            phaccount.put("lastName", quotationForm.phaccount.lastName)
            phaccount.put("isMainDriver", Boolean.valueOf(quotationForm.phaccount.isMainDriver))
            phaccount.put("gender", quotationForm.phaccount.gender ?: "")
            phaccount.put("firstName", quotationForm.phaccount.firstName)
            phaccount.put("email", quotationForm.phaccount.email)
            phaccount.put("drivingExperience", quotationForm.phaccount.drivingExperience)
            phaccount.put("driverAge", StatusUtil.getDriverAge(quotationForm.phaccount.driverAge))
            phaccount.put("driverAccidents", quotationForm.phaccount.driverAccidents ? StatusUtil.getLastAccident(quotationForm.phaccount.driverAccidents) : "")
            phaccount.put("postalCode", quotationForm.phaccount.postalCode)
            phaccount.put("houseNumber", quotationForm.phaccount.houseNumber)
            phaccount.put("village", quotationForm.phaccount.village)
            phaccount.put("soi", quotationForm.phaccount.soi)
            phaccount.put("subDistrict", quotationForm.phaccount.subDistrict)
            phaccount.put("subDistrictCode", quotationForm.phaccount.subDistrictCode)
            phaccount.put("companyNumber", quotationForm.phaccount.companyNumber)
            phaccount.put("businessRegNumber", quotationForm.phaccount.businessRegNumber)
            String phAccountDOB = DateTimeUtil.converseThaiDateToISODate(quotationForm.phaccount.dob)
            phaccount.put("dob", phAccountDOB.equalsIgnoreCase("") ? null : phAccountDOB)
            phaccount.put("district", quotationForm.phaccount.district)
            phaccount.put("districtCode", quotationForm.phaccount.districtCode)
            phaccount.put("province", quotationForm.phaccount.province)
            phaccount.put("provinceCode", quotationForm.phaccount.provinceCode)
            phaccount.put("PersonHasOptedOutOfEmail", quotationForm.phaccount.PersonHasOptedOutOfEmail == 'Yes' ? true : false)

            item.put("phaccount", phaccount)

            // drivers
            JSONArray drivers = new JSONArray()

            for (int i = 0; i < quotationForm.drivers.size(); i++) {
                JSONObject driver = new JSONObject()
                driver.put("residentStatus", StatusUtil.getResidentStatus(quotationForm.drivers[i].residentStatus))
                driver.put("thaiIdNumber", quotationForm.drivers[i].thaiIdNumber ? quotationForm.drivers[i].thaiIdNumber : "")
                driver.put("phoneNumber", quotationForm.drivers[i].phoneNumber ? quotationForm.drivers[i].phoneNumber : "")
                driver.put("passPortNumber", quotationForm.drivers[i].passPortNumber ? quotationForm.drivers[i].passPortNumber : "")
                driver.put("countryOfIssuance", quotationForm.drivers[i].countryOfIssuance ? quotationForm.drivers[i].countryOfIssuance : "")
                driver.put("occupation", quotationForm.drivers[i].occupation ? quotationForm.drivers[i].occupation : "")
                driver.put("maritialStatus", quotationForm.drivers[i].maritialStatus ?: "")
                driver.put("lastName", quotationForm.drivers[i].lastName ? quotationForm.drivers[i].lastName : "")
                driver.put("isMainDriver", quotationForm.drivers[i].isMainDriver ? Boolean.valueOf(quotationForm.drivers[i].isMainDriver) : null)
                driver.put("gender", quotationForm.drivers[i].gender ?: "")
                driver.put("firstName", quotationForm.drivers[i].firstName ? quotationForm.drivers[i].firstName : "")
                driver.put("email", quotationForm.drivers[i].email ? quotationForm.drivers[i].email : "")
                driver.put("drivingExperience", quotationForm.drivers[i].drivingExperience ? quotationForm.drivers[i].drivingExperience : "")
                driver.put("driverAge", StatusUtil.getDriverAge(quotationForm.drivers[i].driverAge ? quotationForm.drivers[i].driverAge : ""))
                driver.put("driverAccidents", quotationForm.drivers[i].driverAccidents ? StatusUtil.getLastAccident(quotationForm.drivers[i].driverAccidents) : "")
                driver.put("dob", quotationForm.drivers[i].dob ? DateTimeUtil.converseThaiDateToISODate(quotationForm.drivers[i].dob) : null)

                drivers.put(driver)
            }
            item.put("drivers", drivers)

            // quoteLineList
            JSONArray quoteLineList = new JSONArray()
            //quote1
            JSONObject quoteLine = new JSONObject()
            quoteLine.put("Basic_premium__c", quotationForm.quoteLine.Basic_premium__c)
            quoteLine.put("PA_passenger_SI__c", quotationForm.quoteLine.PA_passenger_SI__c)
            quoteLine.put("PA_Driver_SI__c", quotationForm.quoteLine.PA_Driver_SI__c)
            quoteLine.put("Bail_Bond_SI__c", quotationForm.quoteLine.Bail_Bond_SI__c)
            quoteLine.put("Medical_Expenses_SI__c", quotationForm.quoteLine.Medical_Expenses_SI__c)
            quoteLine.put("Cover_Code__c", "VOL")// fixed
            quoteLine.put("Adjusted_technical_premium__c", quotationForm.quoteLine.Adjusted_technical_premium__c != null && quotationForm.quoteLine.Adjusted_technical_premium__c != "" ? Double.parseDouble(quotationForm.quoteLine.Adjusted_technical_premium__c) : 0)
            quoteLine.put("Reason_for_premium_adjustment__c", quotationForm.quoteLine.Reason_for_premium_adjustment__c)
            quoteLine.put("Premium_adjustment_pct__c", quotationForm.quoteLine.Premium_adjustment_pct__c != null && quotationForm.quoteLine.Premium_adjustment_pct__c != "" ? Double.parseDouble(quotationForm.quoteLine.Premium_adjustment_pct__c) : 0)

            quoteLineList.put(quoteLine)
            item.put("quoteLineList", quoteLineList)

            // quote
            JSONObject quote = new JSONObject()
            quote.put("Year_of_Manufacture", quotationForm.quote.Year_of_Manufacture)
            quote.put("Make", quotationForm.quote.Make)
            quote.put("Is_Main_Driver_PH", true)// fixed
            quote.put("Model_Family", quotationForm.quote.carModel)// request by sarin
            quote.put("carAccessoriesSI", quotationForm.quote.carAccessoriesSI ? Integer.valueOf(quotationForm.quote.carAccessoriesSI) : 0)
            // redbook
            JSONObject redBook = null
            if (quotationForm.quote.channel != null && (quotationForm.quote.channel.equalsIgnoreCase(Partner.Gobear.text()) || quotationForm.quote.channel.equalsIgnoreCase(Partner.Priceza.text()))) {
                if (quotationForm.quote.Make && quotationForm.quote.carModel && quotationForm.quote.Year_of_Manufacture && quotationForm.quote.Model_Description) {
                    redBook = redBookService.searchRedBookDetail(quotationForm.quote.Make, quotationForm.quote.carModel, Integer.valueOf(quotationForm.quote.Year_of_Manufacture), quotationForm.quote.Model_Description)
                } else if (quotationForm.quote.Make && quotationForm.quote.carModel && quotationForm.quote.Year_of_Manufacture && quotationForm.quote.Engine_size) {
                    redBook = redBookService.searchRedBookByPartnerModel(Partner.Gobear, quotationForm.quote.Make, quotationForm.quote.carModel, Integer.valueOf(quotationForm.quote.Year_of_Manufacture), Integer.valueOf(quotationForm.quote.Engine_size))
                }
                quote.put("Final_Premium_Class", quotationForm.quote.Final_Premium_Class ?: redBook.gobearFinalPremCl)
                quote.put("Final_Premium_Group", quotationForm.quote.Final_Premium_Group ?: redBook.gobearFinalPremGr)
                quote.put("Final_UW_Class", quotationForm.quote.Final_UW_Class ?: redBook.gobearFinalUwCl)
                quote.put("Final_UW_Group", quotationForm.quote.Final_UW_Group ?: redBook.gobearFinalUwGr)
                quote.put("Vehicle_Sum_Insured", redBook.gobearFinalValue)
            } else {// web , Cars24
                redBook = redBookService.searchRedBookDetail(quotationForm.quote.Make, quotationForm.quote.carModel, Integer.valueOf(quotationForm.quote.Year_of_Manufacture), quotationForm.quote.Model_Description)
                quote.put("Final_Premium_Class", quotationForm.quote.Final_Premium_Class ?: redBook.finalPremCl)
                quote.put("Final_Premium_Group", quotationForm.quote.Final_Premium_Group ?: redBook.finalPremGr)
                quote.put("Final_UW_Class", quotationForm.quote.Final_UW_Class ?: redBook.finalUwCl)
                quote.put("Final_UW_Group", quotationForm.quote.Final_UW_Group ?: redBook.finalUwGr)
                quote.put("Vehicle_Sum_Insured", quotationForm.quote.Vehicle_Sum_Insured ?: redBook.finalValue)
            }
            quote.put("Adjusted_vehicle_Sum_Insured", quotationForm.quote.Adjusted_vehicle_Sum_Insured != null && quotationForm.quote.Adjusted_vehicle_Sum_Insured != "" ? Double.parseDouble(quotationForm.quote.Adjusted_vehicle_Sum_Insured) : 0)
            quote.put("Model_Family", redBook.modelFamilyDesc)// read our modelFamilyFrom
            quote.put("Seat_Capacity", redBook.seatCapacity)
            quote.put("Engine_size", redBook.engineSize)
            quote.put("Tariff_Group", quotationForm.quote.Tariff_Group ?: redBook.tariffGroup)
            if (quotationForm.quote.Vehicle_Usage != "") {
                quote.put("Vehicle_Usage", quotationForm.quote.Vehicle_Usage)
            }
            quote.put("taxiVehForHire", quotationForm.quote.taxiVehForHire)
            quote.put("rentalVehicle", quotationForm.quote.rentalVehicle)
            quote.put("goodsTransportVehicle", quotationForm.quote.goodsTransportVehicle)
            quote.put("goodsTransportRoute", quotationForm.quote.goodsTransportRoute)
            quote.put("whoseGoods", quotationForm.quote.whoseGoods)
            quote.put("Voluntary_Type", quotationForm.quote.Voluntary_Type ?: String.valueOf(redBook.voluntaryType))
            quote.put("Vehicle_Key", redBook.vehicleKey)
            quote.put("Declared_NCB", quotationForm.quote.Declared_NCB ? (quotationForm.quote.Declared_NCB == "N" ? "I don't know" : quotationForm.quote.Declared_NCB + "%") : "")
            quote.put("Excess", quotationForm.quote.Excess)
            quote.put("Compulsory_Plan", StatusUtil.getSaleForceCompulsoryValue(quotationForm.quote.Compulsory_Plan))
            quote.put("Driver_Plan", StatusUtil.driverNamedConvert(quotationForm.quote.Driver_Plan))
            quote.put("Workshop_Type", StatusUtil.getWorkshopType(quotationForm.quote.Workshop_Type))
            quote.put("Plan_Type", StatusUtil.getCoverTypeConvert(quotationForm.quote.Plan_Type))
            quote.put("Masterset_Id", quotationForm.quote.Masterset_Id)
            quote.put("Created_From", "Web")// Fixed
            quote.put("send_email_flag", Boolean.valueOf(quotationForm.quote.send_email_flag))
            if (quotationForm.quote.Opportunity_Number == null || quotationForm.quote.Opportunity_Number == "" || quotationForm.quote.Opportunity_Number.contains("TBA")) {
                quote.put("Opportunity_Number", "TBA")
            } else {
                quote.put("Opportunity_Number", quotationForm.quote.Opportunity_Number)
            }
            quote.put("isUpdate", Boolean.valueOf(quotationForm.quote.isUpdate))

            if (quotationForm.quote.callMeBackRequest != "") {
                quote.put("callMeBackRequest", quotationForm.quote.callMeBackRequest)
            } else if (quotationForm.quote.callMeBack == "true") {
                quote.put("callMeBackRequest", "Yes")
            }

            quote.put("abTestingVersion", quotationForm.quote.abTestingVersion)
            quote.put("carAccessoryCarCamera", quotationForm.quote.carAccessoryCarCamera)
            quote.put("validateSourceCode", quotationForm.quote.validateSourceCode)
            quote.put("referralNumber", quotationForm.quote.referralNumber.equals("") ? null : quotationForm.quote.referralNumber)
            quote.put("Google_Client_ID", quotationForm.quote.Google_Client_ID)
            quote.put("prefLang", quotationForm.quote.prefLang.equals("") ? null : quotationForm.quote.prefLang.toUpperCase())
            quote.put("Online_Screen", quotationForm.quote.Online_Screen)
            quote.put("Online_Stage", quotationForm.quote.Online_Stage)
            quote.put("Renewal_Adjustment_Value", quotationForm.quote.Renewal_Adjustment_Value)
            quote.put("FamilyCode", quotationForm.quote.FamilyCode ?: redBook.familyCode)
            quote.put("KerbWeight", quotationForm.quote.KerbWeight ?: String.format("%.2f", Double.valueOf(redBook.kerbWeight)))
            quote.put("Power", quotationForm.quote.Power ?: String.format("%.2f", Double.valueOf(redBook.power)))
            quote.put("NewPrice", quotationForm.quote.NewPrice ?: redBook.newPrice)
            quote.put("ePolicyDocument", quotationForm.quote.ePolicyDocument)
            quote.put("ePolicyDocument", quotationForm.quote.ePolicyDocument)
            quote.put("approvedCode", quotationForm.quote.approvedCode)
            quote.put("extendedWarrantyProductOptionNo", quotationForm.quote.extendedWarrantyProductOptionNo)
            quote.put("versionId", quotationForm.quote.versionId)

            String hdhowlongbeeninsured = ""

            switch (quotationForm.quote.hdhowlongbeeninsured) {
                case "1":
                    hdhowlongbeeninsured = "1 year"
                    break
                case "2":
                    hdhowlongbeeninsured = "2 years"
                    break
                case "M2":
                    hdhowlongbeeninsured = ">2 years"
                    break
            }

            quote.put("howLongInsured", hdhowlongbeeninsured)
            quote.put("engineNumber", "")
            quote.put("chassisNumber", quotationForm.quote.hdChassisNumber)
            quote.put("carPlateProvince", quotationForm.quote.hdCarPlateProvince)
            quote.put("carPlateNumber", quotationForm.quote.hdCarPlateNumber)
            quote.put("carOwnerShip", StatusUtil.getCarOwnerShip(quotationForm.quote.hdCarOwnership))
            quote.put("derivedNCB", quotationForm.quote.Declared_NCB && quotationForm.drivers[0].driverAccidents ? StatusUtil.getDerivedNCB(quotationForm.quote.Declared_NCB.toString(), quotationForm.drivers[0].driverAccidents.toString()) : 0)

            String stringStartDate = DateTimeUtil.converseThaiDateToISODate(quotationForm.quote.Start_date),
                   stringEndDate = DateTimeUtil.converseThaiDateToISODate(quotationForm.quote.End_date)

            if (!stringStartDate.equals("") && !stringEndDate.equals("")) {
                try {
                    ZoneId asiaBangkok = ZoneId.of("Asia/Bangkok")
                    LocalDate nowAsiaBangkok = LocalDate.now(asiaBangkok),
                              startDate = LocalDate.parse(stringStartDate)
                    if (startDate.isBefore(nowAsiaBangkok)) {
                        stringStartDate = nowAsiaBangkok.toString()
                        stringEndDate = nowAsiaBangkok.plusYears(1).toString()
                    }
                } catch (DateTimeParseException | DateTimeException e) {
                    stringStartDate = null
                    stringEndDate = null
                    log.error(e.getMessage(), e)
                }
            } else {
                stringStartDate = null
                stringEndDate = null
            }

            quote.put("Start_date", stringStartDate)
            quote.put("End_date", stringEndDate)
            quote.put("whoDriveTheCar", quotationForm.quote.whoDriveTheCar ? quotationForm.quote.whoDriveTheCar : "0")
            quote.put("previousInsurer", quotationForm.quote.previousInsurer ? quotationForm.quote.previousInsurer : null)
            quote.put("previousCoverType", quotationForm.quote.previousCoverType ? quotationForm.quote.previousCoverType : null)
            quote.put("channel", quotationForm.quote.channel ? StatusUtil.getLead(quotationForm.quote.channel) : null)
            quote.put("PSN_value", quotationForm.quote.PSN_value)
            quote.put("isActivatePolicy", Boolean.valueOf(quotationForm.quote.isActivatePolicy))
            String currentPolicyExpiryDate = ""
            if (quotationForm.quote.currentPolicyExpiryDate != null) {
                currentPolicyExpiryDate = DateTimeUtil.converseThaiDateToISODate(quotationForm.quote.currentPolicyExpiryDate)
            }

            quote.put("currentPolicyExpiryDate", currentPolicyExpiryDate.equalsIgnoreCase("") ? null : currentPolicyExpiryDate)
            quote.put("isValidSourceCode", Boolean.valueOf(quotationForm.quote.isValidSourceCode))
            quote.put("tentativeStartDate", quotationForm.quote.tentativeStartDate)
            quote.put("Rating_Factor_Defaulted", Boolean.valueOf(quotationForm.quote.Rating_Factor_Defaulted))
            quote.put("Product_Type", quotationForm.quote.Product_Type)
            quote.put("GoogleClickId", quotationForm.quote.GoogleClickId)
            quote.put("odoMeterReading", quotationForm.quote.odoMeterReading ? quotationForm.quote.odoMeterReading : null)
            //quotationForm.quote.odoMeterReading ? quotationForm.quote.odoMeterReading : "null"
            quote.put("postalCode", quotationForm.quote.postalCode)
            quote.put("rsaProduct", quotationForm.quote.rsaProduct ?: "No")
            String compulsoryType = "0"
            if (quotationForm.quote.Compulsory_type != null && !quotationForm.quote.Compulsory_type.equals("")) {
                compulsoryType = quotationForm.quote.Compulsory_type
            } else if ((redBook.compulsoryType != null && !redBook.compulsoryType.equals(""))) {
                compulsoryType = redBook.compulsoryType
            }
            quote.put("Compulsory_type", compulsoryType)
            quote.put("Vehicle_Type_Update_Flag", quotationForm.quote.Vehicle_Type_Update_Flag)
            quote.put("Voluntary_TPBI", quotationForm.quote.Voluntary_TPBI)
            quote.put("Beneficiary", quotationForm.quote.Beneficiary)
            quote.put("customerIpAddress", remoteAddr)
            quote.put("QuoteToOppScored", quotationForm.quote.QuoteToOppScored)
            quote.put("ShowMrKumkaInfo", quotationForm.quote.ShowMrKumkaInfo)
            quote.put("carReplacementProductName", quotationForm.quote.carReplacementProductName)
            quote.put("carReplacementProductOptionNo", quotationForm.quote.carReplacementProductOptionNo)
            if (quotationForm.quote.Product_Type == ProductType.MotorBike.text()) {
                quote.put("additionalPASI", quotationForm.quote.additionalPASI)
                quote.put("additionalMESI", quotationForm.quote.additionalMESI)
                quote.put("policyInsurer", quotationForm.quote.Policy_Insurer)
            } else {
                quote.put("Additional_Benefit_PA_ME_SI", quotationForm.quote.Additional_Benefit_PA_ME_SI)
            }
            quote.put("CarFinancing", quotationForm.quote.carFinancing ? quotationForm.quote.carFinancing : "")
            quote.put("isGAMarketeerlink", quotationForm.quote.isGAMarketeerlink ? quotationForm.quote.isGAMarketeerlink : "")
            quote.put("paymentFrequency", quotationForm.quote.paymentFrequency ? quotationForm.quote.paymentFrequency : "")
            quote.put("totalPremium", quotationForm.quote.totalPremium ? Double.parseDouble(quotationForm.quote.totalPremium) : null)

            item.put("quote", quote)

            JSONArray accessories = new JSONArray()
            if (quotationForm.accessories) {
                for (int i = 0; i < quotationForm.accessories.size(); i++) {
                    JSONObject accessory = new JSONObject()
                    accessory.put("accessoryName", quotationForm.accessories[i].accessoryName)
                    accessory.put("accessoryValue", quotationForm.accessories[i].accessoryValue)

                    accessories.put(accessory)
                }
            }
            item.put("accessories", accessories)

            JSONObject brokerInfo = new JSONObject()
            brokerInfo.put("intermediaryAccountID", quotationForm.brokerInfo.intermediaryAccountID)
            brokerInfo.put("intermediaryReptID", quotationForm.brokerInfo.intermediaryReptID)
            item.put("brokerInfo", brokerInfo)

            if (quotationForm.payment && quotationForm.payment.paymentID) {
                PaymentData paymentData = PaymentData.get(quotationForm.payment.paymentID)
                if (paymentData?.json_input) {
                    try {
                        JSONObject jsonObject = new JSONObject(paymentData.json_input)
                        jsonObject.item.payment.put("versionId", quotationForm.quote.versionId)

                        item.put("payment", jsonObject.item.payment)
                        item.put("payments", jsonObject.item.payments)
                        item.put("creditCard", jsonObject.item.creditCard)
                    } catch (JSONException ex) {
                        log.error "[Exc]createJsonQuotation: ${ex.message}", ex
                    }
                }
            }

            root.put("item", item)
        } catch (Exception e) {
            JSONObject jsonException = new JSONObject()
            jsonException.put("quotationForm", quotationForm)

            log.error(jsonException.toString(), e)
        }

        return root
    }

    JSONObject createPayment(Map params){
        JSONObject payment = new JSONObject()
        payment.put("isPmntInitiatedBySF", params.agentId != null)
        if(params.transactionType == "nb" || params.transactionType == "ren"){
            payment.put("isActivatePolicy", params?.quote?.isActivatePolicy != null ? Boolean.valueOf(params.quote.isActivatePolicy)  : false)
        }
        payment.put("deferredPaymentFlag", false)
        payment.put("opportunityNumber", params.quote.Opportunity_Number)
        payment.put("Online_Stage", "")//Will be replaced by Roojai services
        payment.put("Online_Screen", params.quote.Online_Screen)
        payment.put("agentId", params.agentId)
        if(params.transactionType == "endt"){
            payment.put("transactionNumber", params.quote.transactionNumber)
        }
        return payment
    }

    JSONArray createPayments(Map params){
        JSONArray payments = new JSONArray()
        JSONObject paymentItem = new JSONObject()
        paymentItem.put("Payment_Fee__c", "0")
        paymentItem.put("Payment_date__c", "")//Will be replaced by Roojai services
        paymentItem.put("Due_Date__c", "")//Will be replaced by Roojai services
        paymentItem.put("Bank__c", "Omise")
        paymentItem.put("Type__c", "Receivable")
        paymentItem.put("WHT_certificate_number__c", "")
        paymentItem.put("Installment_number__c", params.installmentNumber == null ? "1" : params.installmentNumber)
        paymentItem.put("Credit_Amount__c", "")//Will be replaced by Roojai services
        paymentItem.put("Payment_frequency__c", params.quote.paymentFrequency)
        paymentItem.put("Status__c", "")//Will be replaced by Roojai services
        paymentItem.put("Payment_method__c", params.sourceType == null ? "PromptPay" : PaymentType[params.sourceType].text())
        paymentItem.put("Charge_Id__c", "")//Will be replaced by Roojai services

        payments.add(paymentItem)

        return payments
    }
}

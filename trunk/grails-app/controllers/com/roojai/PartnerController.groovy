package com.roojai

import com.roojai.saleforce.Driver
import com.roojai.saleforce.Partner
import com.roojai.saleforce.QuotationForm
import com.roojai.util.DateTimeUtil
import com.roojai.util.StatusUtil
import com.roojai.util.StringUtil
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.grails.web.json.JSONObject
import org.hibernate.HibernateException

@Secured(['permitAll'])
class PartnerController {
    def redBookService
    def saleforceService
    def callMeBackNonceService

    def callMeBack() {
        def nonce = params.nonce
        String pageName = "";
        if (nonce && !nonce.trim().equals("")) {
            if (nonce.equalsIgnoreCase(Partner.Gobear.text())) {
                JSONObject redBook = redBookService.searchRedBookByPartnerModel(Partner.Gobear, params.brand, params.model, Integer.valueOf(params.year), Integer.valueOf(params.cc));
                params.model = redBook.modelFamilyDesc
                params.isPanelWorkshop = params.boolean("isPanelWorkshop") ? "false" : "true";
                params.url = getURL(params, pageName)
                [params: params]
                render(view: "redirect")

            } else if (nonce.equalsIgnoreCase(Partner.Priceza.text()) || nonce.equalsIgnoreCase(Partner.MrKumkaB.text())) {
                JSONObject json = new JSONObject()

                String validateMessage = validateRequest();
                def opportunityNumber = "";

                if (validateMessage == null) {

                    try {
                        // saveQuotation
                        QuotationForm form = new QuotationForm();
                        form.phaccount.email = params.customerEmail;
                        form.phaccount.phoneNumber = params.customerPhoneNumber;

                        def gender = params.boolean("isMale") ? "Male" : "Female";
                        form.phaccount.gender = gender
                        form.phaccount.drivingExperience = params.yrOfDriving.replace("6", "6+");
                        form.phaccount.lastName = params.name;
                        form.phaccount.driverAccidents = 0;

                        form.phaccount.isMainDriver = true;
                        form.phaccount.driverAge = "34";
                        def maritialStatus = params.boolean("isSingle") ? "Single" : "Married";
                        form.phaccount.maritialStatus = maritialStatus

                        form.quote.Make = params.brand;
                        form.quote.Year_of_Manufacture = params.year;
                        form.quote.carModel = params.model;
                        form.quote.Engine_size = params.cc;
                        form.quote.Model_Description = params.subModel;
                        form.quote.prefLang = params.lang;
                        form.quote.Plan_Type = StatusUtil.getCoverTypeConvert(params.planType);
                        form.quote.Excess = params.excessAmount;
                        form.quote.Adjusted_vehicle_Sum_Insured = params.vehiclesumInsuredAmount != null ? Integer.parseInt(params.vehiclesumInsuredAmount) : 0;
                        form.quote.Workshop_Type = params.boolean("isPanelWorkshop") ? "PanelWorkshop" : "AnyWorkshop"
                        form.quote.Vehicle_Usage = "Driving to and back from work";
                        form.quote.Compulsory_Plan = "NoComp";
                        form.quote.carAccessoryCarCamera = params.boolean("isDashcam") ? "Yes" : "No";
                        form.quote.channel = Partner.Priceza.toString();
                        form.quote.Declared_NCB = "N";
                        form.quote.Created_From = "Web";
                        form.quote.callMeBack = true;
                        form.quote.Is_Main_Driver_PH = true
                        form.quote.hdCarOwnership = 0
                        form.quote.Online_Stage = "undefined"
                        form.quote.tentativeStartDate = "Other"

                        Driver driver = new Driver();
                        driver.lastName = params.name;
                        driver.gender = gender
                        driver.drivingExperience = params.yrOfDriving.replace("6", "6+");
                        driver.phoneNumber = params.customerPhoneNumber;
                        driver.driverAccidents = "0";
                        driver.isMainDriver = true;
                        driver.maritialStatus = maritialStatus;
                        driver.driverAge = "34"; //fixed
                        driver.email = params.customerEmail;
                        driver.dob = DateTimeUtil.toThaiDateInString(StatusUtil.getDOBOver30());


//						form.quote.Vehicle_Sum_Insured = params.vehiclesumInsuredAmount != null ? Integer.parseInt(params.vehiclesumInsuredAmount) : 0;
                        form.quoteLine.Basic_premium__c = params.pricePerYear;
                        form.quoteLine.PA_passenger_SI__c = "100000"; //fixed
                        form.quoteLine.PA_Driver_SI__c = "100000"; //fixed
                        form.quoteLine.Cover_Code__c = "VOL"; //fixed
                        form.quoteLine.Medical_Expenses_SI__c = "100000"; //fixed
                        form.quoteLine.Adjusted_technical_premium__c = 0; //fixed
                        form.quoteLine.Bail_Bond_SI__c = "300000"; //fixed
                        form.quoteLine.Reason_for_premium_adjustment__c = ""; //fixed
                        form.quote.Renewal_Adjustment_Value = "";
                        form.quote.Driver_Plan = "AnyOver30";

                        if (nonce.equalsIgnoreCase(Partner.MrKumkaB.text())) {
                            String age = StatusUtil.getAgeFromDOB(params.dob).toString();
                            form.quote.channel = params.leadSource ? params.leadSource : Partner.MrKumka.text()
                            form.phaccount.driverAge = age;
                            driver.driverAge = age;
                            driver.dob = DateTimeUtil.toThaiDateInString(params.dob);
                            form.quote.Driver_Plan = params.driverPlan.equals("0") ? "AnyDriver" : "Named";
                            form.quote.Declared_NCB = params.ncbRate
                            form.quote.Vehicle_Usage = params.boolean("isGoToWorkOnly") ? "Driving to and back from work" : "Social, pleasure and travelling";
                            form.phaccount.driverAccidents = params.noOfClaim;
                            driver.driverAccidents = params.noOfClaim;
                            form.quote.Online_Stage = ""
                            form.quote.Vehicle_Type_Update_Flag = "NO"
                            form.quote.Adjusted_vehicle_Sum_Insured = 0;
                            form.quote.Compulsory_Plan = params.boolean("isCompulsoryAvailable") ? "Comp" : "NoComp"
                            form.phaccount.postalCode = params.postalCode;
                            form.quote.postalCode = params.postalCode;
                            form.quote.odoMeterReading = params.odoMeter ? params.odoMeter : null;
                            form.quote.PSN_value = params.psn ? params.psn : null;
                        } else {
                            form.phaccount.dob = DateTimeUtil.toThaiDateInString(StatusUtil.getDOBOver30());
                        }

                        form.drivers.add(driver);

                        String saleforceResponse = saleforceService.saveQuotation(form, StringUtil.getIpAdress(request));
                        JSONObject saleforceJSON = JSON.parse(saleforceResponse);
                        opportunityNumber = saleforceJSON.get("opportunityNumber") ? saleforceJSON.get("opportunityNumber") : "";

                    } catch (HibernateException | IndexOutOfBoundsException | ClassCastException | NullPointerException e) {
                        log.error "Error: ${e.message}", e
                        json.put("success", false)
                        json.put("errorMessage", e.getMessage())
                    } finally {
                        json = callMeBackNonceService.saveRequest(params, request, opportunityNumber);
                    }
                } else {
                    json.put("success", false)
                    json.put("errorMessage", validateMessage)
                }

                render json

            } else {
                pageName = "quotationResultPackages"
                params.url = getURL(params, pageName)
                [params: params]
                render(view: "redirect")
            }

        } else {
            render "integration error"
        }
    }

    def buyNow() {
        def nonce = params.nonce
        params.isBuyNow = "true"
        if (nonce && !nonce.trim().equals("")) {
            params.url = getURL(params, "quotationResultPackages")
            [params: params]
            render(view: "redirect")
        } else {
            render "integration error"
        }
    }

    def getURL(params, pageName) {
        String rootUrl = request.getRequestURL().replaceAll(request.getServletPath(), "").replaceAll(request.getContextPath(), "") + "/#/?" + "pageName=" + pageName
        params.each { name, value ->
            if (!name.equals("controller") && !name.equals("format") && !name.equals("action")) {
                rootUrl += "&" + name + "=" + URLEncoder.encode(value, "UTF-8")
            }
        }
        return rootUrl
    }

    def validateRequest() {
        if (params.username == null || params.username == "") {
            return "username is invalid"
        } else if (params.password == null || params.password == "") {
            return "password is invalid"
        } else if (params.nonce == null || params.nonce == "") {
            return "nonce is invalid"
        } else if (params.planID == null || params.planID == "") {
            return "planID is invalid"
        } else if (params.brand == null || params.brand == "") {
            return "brand is invalid"
        } else if (params.year == null || params.year == "" || !params.year.isNumber()) {
            return "year is invalid"
        } else if (params.model == null || params.model == "") {
            return "model is invalid"
        } else if (!params.nonce.equalsIgnoreCase(Partner.MrKumkaB.text()) && (params.cc == null || params.cc == "" || !params.cc.isNumber())) {
            return "cc is invalid"
        } else if (params.lang == null || params.lang == "") {
            return "lang is invalid"
        } else if (params.planType == null || params.planType == "") {
            return "planType is invalid"
        } else if (params.excessAmount == null || params.excessAmount == "" || !params.excessAmount.isNumber()) {
            return "excessAmount is invalid"
        } else if (params.isPanelWorkshop == null || params.isPanelWorkshop == "" || !(params.isPanelWorkshop == "true" || params.isPanelWorkshop == "false" || params.isPanelWorkshop == "TRUE" || params.isPanelWorkshop == "FALSE")) {
            return "isPanelWorkshop is invalid"
        } else if (params.isCompulsoryAvailable == null || params.isCompulsoryAvailable == "" || !(params.isCompulsoryAvailable == "true" || params.isCompulsoryAvailable == "false" || params.isCompulsoryAvailable == "TRUE" || params.isCompulsoryAvailable == "FALSE")) {
            return "isCompulsoryAvailable is invalid"
        } else if (!params.nonce.equalsIgnoreCase(Partner.MrKumkaB.text()) && (params.vehiclesumInsuredAmount == null || params.vehiclesumInsuredAmount == "" || !(params.vehiclesumInsuredAmount.isNumber() || params.vehiclesumInsuredAmount.isFloat()))) {
            return "vehiclesumInsuredAmount is invalid"
        } else if (params.pricePerYear == null || params.pricePerYear == "" || !(params.pricePerYear.isNumber() || params.pricePerYear.isFloat())) {
            return "pricePerYear is invalid"
        } else if (params.customerPhoneNumber == null || params.customerPhoneNumber == "") {
            return "customerPhoneNumber is invalid"
        } else if (params.name == null || params.name == "") {
            return "name is invalid"
        } else if (params.prefix == null || params.prefix == "") {
            return "prefix is invalid"
        } else if (params.isMale == null || params.isMale == "" || !(params.isMale == "true" || params.isMale == "false" || params.isMale == "TRUE" || params.isMale == "FALSE")) {
            return "isMale is invalid"
        } else if (params.isSingle == null || params.isSingle == "" || !(params.isSingle == "true" || params.isSingle == "false" || params.isSingle == "TRUE" || params.isSingle == "FALSE")) {
            return "isSingle is invalid"
        } else if (params.dob == null || params.dob == "") {
            return "dob is invalid"
        } else if (params.yrOfDriving == null || params.yrOfDriving == "" || !params.yrOfDriving.isNumber()) {
            return "yrOfDriving is invalid"
        } else if (params.noOfClaim == null || params.noOfClaim == "" || !params.noOfClaim.isNumber()) {
            return "noOfClaim is invalid"
        } else if (params.isGoToWorkOnly == null || params.isGoToWorkOnly == "" || !(params.isGoToWorkOnly == "true" || params.isGoToWorkOnly == "false" || params.isGoToWorkOnly == "TRUE" || params.isGoToWorkOnly == "FALSE")) {
            return "isGoToWorkOnly is invalid"
        } else if (params.isDashcam == null || params.isDashcam == "" || !(params.isDashcam == "true" || params.isDashcam == "false" || params.isDashcam == "TRUE" || params.isDashcam == "FALSE")) {
            return "isDashcam is invalid"
        } else if (params.ncbRate == null || params.ncbRate == "") {
            return "ncbRate is invalid"
        }

    }

    def pricezaPlanList() {


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api2.uat-roojai.com/api/RoojaiPricing/getPlanList");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("username", "priceza"));
        urlParameters.add(new BasicNameValuePair("password", "XxWam?D<Zre?X9W%"));
        urlParameters.add(new BasicNameValuePair("planType", ""));
        urlParameters.add(new BasicNameValuePair("isMale", "true"));
        urlParameters.add(new BasicNameValuePair("isSingle", "false"));
        urlParameters.add(new BasicNameValuePair("yrOfDriving", "6"));
        urlParameters.add(new BasicNameValuePair("ncbRate", "N"));
        urlParameters.add(new BasicNameValuePair("isDashcam", "true"));
        urlParameters.add(new BasicNameValuePair("isGoToWorkOnly", "false"));
        urlParameters.add(new BasicNameValuePair("dob", "01/01/1985"));
        urlParameters.add(new BasicNameValuePair("noOfClaim", "0"));
        urlParameters.add(new BasicNameValuePair("brand", params.brand));
        urlParameters.add(new BasicNameValuePair("model", params.model));
        urlParameters.add(new BasicNameValuePair("year", params.year));
        urlParameters.add(new BasicNameValuePair("engineSize", params.cc));
        urlParameters.add(new BasicNameValuePair("subModel", params.subModel));

        post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        JSONObject resultJson = JSON.parse(result.toString())
        resultJson.put("brand", params.brand)
        resultJson.put("model", params.model)
        resultJson.put("year", params.year)
        resultJson.put("cc", params.cc)
        resultJson.put("subModel", params.subModel)
        [result: resultJson]


    }

    def getBrokerDetails() {
        def result
        try {
            def brokerDetails
            if (request.JSON.intermediaryReptEmail?.trim()) {
                brokerDetails = BrokerDetails.findByIntermediaryReptEmail(request.JSON.intermediaryReptEmail)
            }else if(request.JSON.rid?.trim()){
                brokerDetails = BrokerDetails.findByIntermediaryReptID(StringUtil.decrypt(request.JSON.rid))
            }

            if (brokerDetails) {
                result = [status: true, intermediaryReptID: brokerDetails.intermediaryReptID, intermediaryReptEmail: brokerDetails.intermediaryReptEmail, intermediaryAccountID: brokerDetails.intermediaryAccountID]
            } else {
                result = [status: false]
            }
        } catch (MissingPropertyException | HibernateException ex) {
            log.error "[Exc]index: ${ex.message}", ex
        } finally {
            result = result ?: [status: false]
        }

        render text: (result as JSON)
    }
}

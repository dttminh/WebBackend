package com.roojai

import com.roojai.saleforce.Driver
import com.roojai.saleforce.Payment
import com.roojai.saleforce.QuotationForm
import com.roojai.saleforce.QuoteLine
import com.roojai.util.DateTimeUtil
import com.roojai.util.StringUtil
import grails.async.Promise
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import javax.servlet.http.HttpServletRequest

import static grails.async.Promises.task

@Secured(['ROLE_API'])
class PaymentController {

    static allowedMethods = [paymentdata: "POST", webhook: "GET"]

    PaymentService paymentService
    SystemconfigurationService systemconfigurationService
    SaleforceService saleforceService
    HttpService httpService

    def getOnlineBankingRefId() {
        String amt = String.format("%.02f", (double) request.JSON.amt)
        boolean isQuote = false;
        if(request.JSON.isQuote != null && request.JSON.isQuote){
            isQuote = request.JSON.isQuote;
        }

        render paymentService.createInternetBankingRefId(request.JSON.inv, amt, request.JSON.selectedBank, request.JSON.customerName, request.JSON.customerMobile, request.JSON.customerEmail, request.JSON.productType, request.JSON.isPmntInitiatedBySF, request.JSON.agentId, isQuote, request.JSON.paymentFrequency)
    }

    def creditCardPayment() {
        JSONObject paymentResult
        int httpResponseStatus = 200

        if (request.JSON.agentId != null && request.JSON.autoRenewFlag != null && request.JSON.customerName != null && request.JSON.isPmntInitiatedBySF != null && request.JSON.issuingBank != null && request.JSON.merchantTotalAmount != null && request.JSON.opportunityNumber != null && request.JSON.paymentFrequency != null && request.JSON.cardId != null && request.JSON.tokenId != null) {
            String merchantTotalAmount = String.format("%.02f", Double.parseDouble(request.JSON.merchantTotalAmount))
            paymentResult = paymentService.creditCardPayment(request.JSON.tokenId, request.JSON.cardId, request.JSON.opportunityNumber, merchantTotalAmount, request.JSON.payerEmail, request.JSON.issuingBank, request.JSON.paymentFrequency, request.JSON.payerMobile, request.JSON.customerName, request.JSON.autoRenewFlag, request.JSON.isPmntInitiatedBySF, request.JSON.agentId)

            httpResponseStatus = paymentResult.success == true ? 200 : 500
        } else if (params.roojaiTokenId != null && params.roojaiCardId != null && params.opportunityNumber != null && params.merchantTotalAmount != null && params.payerEmail != null && params.issuingBank != null && params.paymentFrequency != null && params.payerMobile != null && params.customerName != null && params.autoRenewFlag != null && params.isPmntInitiatedBySF != null && params.agentId != null) {
            String merchantTotalAmount = String.format("%.02f", Double.parseDouble(params.merchantTotalAmount))
            paymentResult = paymentService.creditCardPayment(params.roojaiTokenId, params.roojaiCardId, params.opportunityNumber, merchantTotalAmount, params.payerEmail, params.issuingBank, params.paymentFrequency, params.payerMobile, params.customerName, params.autoRenewFlag, params.isPmntInitiatedBySF, params.agentId)

            params.put("quote.Online_Stage", paymentResult.paymentStatus == "00" ? "success" : "failure")
            params.put("payment.paymentStatus", paymentResult.paymentStatus)
            params.put("payment.paymentID", paymentResult.paymentID)

            QuotationForm quotationForm = createQuotationForm(params)

            HttpServletRequest requestObject = request

            Promise p = task {
                saleforceService.saveQuotation(quotationForm, StringUtil.getIpAdress(requestObject))
            }
        } else {
            paymentResult = new JSONObject()
            paymentResult.put("message", "Bad Request")
            httpResponseStatus = 400
        }

        respond(paymentResult, status: httpResponseStatus, formats: ['json'])
    }

    def installmentPayment() {
        double netCompulsory = request.JSON.compulsory.equals(null) ? 0 : request.JSON.compulsory
        double stampDutyCompulsory = Math.ceil(0.004 * netCompulsory)
        double vatCompulsory = (0.07 * (stampDutyCompulsory + netCompulsory)).round(2)
        double totalCompulsory = netCompulsory + stampDutyCompulsory + vatCompulsory
        double netRSA = request.JSON.rsa.equals(null) ? 0 : request.JSON.rsa
        double vatRSA = (0.07 * netRSA).round(2)
        double totalRSA = netRSA + vatRSA
        double carReplacement = request.JSON.carReplacement.equals(null) ? 0 : request.JSON.carReplacement
        double PA = request.JSON.PA.equals(null) ? 0 : request.JSON.PA
        double ME = request.JSON.ME.equals(null) ? 0 : request.JSON.ME
        double TPBI = request.JSON.TPBI.equals(null) ? 0 : request.JSON.TPBI

        JSONObject returnValue = new JSONObject()
        double netVoluntarty = 0
        double totalCarReplacement = 0
        double stampDutyVoluntary = 0
        double vatVoluntary = 0
        double totalVoluntary = 0
        double instalment = 0
        double firstInstalment = 0

        if (request.JSON.nonAnnualPaymentLoadingFactor != null) {
            netVoluntarty = (request.JSON.voluntary + PA + ME + TPBI) * request.JSON.nonAnnualPaymentLoadingFactor
            totalCarReplacement = getCarReplacementPrice(carReplacement, (double) request.JSON.nonAnnualPaymentLoadingFactor)
            stampDutyVoluntary = Math.ceil(0.004 * netVoluntarty)
            vatVoluntary = (0.07 * (stampDutyVoluntary + netVoluntarty)).round(2)
            totalVoluntary = netVoluntarty + stampDutyVoluntary + vatVoluntary
            instalment = ((totalVoluntary + totalCompulsory + totalCarReplacement) / 12).round(2)
            firstInstalment = ((totalVoluntary + totalCompulsory + totalCarReplacement + totalRSA) - (instalment * 9)).round(2)

            JSONObject monthlyDeposit = new JSONObject()
            monthlyDeposit.put("firstInstalment", firstInstalment)
            monthlyDeposit.put("instalment", instalment)
            monthlyDeposit.put("totalPrice", (firstInstalment + (instalment * 9)).round(2))
            returnValue.put("monthlyDeposit", monthlyDeposit)
        }

        if (request.JSON.monthlyNoDepositPaymentLoadingFactor != null) {
            netVoluntarty = (request.JSON.voluntary + PA + ME + TPBI) * request.JSON.monthlyNoDepositPaymentLoadingFactor
            totalCarReplacement = getCarReplacementPrice(carReplacement, (double) request.JSON.monthlyNoDepositPaymentLoadingFactor)
            stampDutyVoluntary = Math.ceil(0.004 * netVoluntarty)
            vatVoluntary = (0.07 * (stampDutyVoluntary + netVoluntarty)).round(2)
            totalVoluntary = netVoluntarty + stampDutyVoluntary + vatVoluntary
            instalment = ((totalVoluntary + totalCompulsory + totalCarReplacement) / 10).round(2)
            firstInstalment = (instalment + totalRSA).round(2)

            JSONObject monthlyNoDeposit = new JSONObject()
            monthlyNoDeposit.put("firstInstalment", firstInstalment)
            monthlyNoDeposit.put("instalment", instalment)
            monthlyNoDeposit.put("totalPrice", (firstInstalment + (instalment * 9)).round(2))
            returnValue.put("monthlyNoDeposit", monthlyNoDeposit)
        }

        if (request.JSON.quarterlyPaymentLoadingFactor != null) {
            netVoluntarty = (request.JSON.voluntary + PA + ME + TPBI) * request.JSON.quarterlyPaymentLoadingFactor
            totalCarReplacement = getCarReplacementPrice(carReplacement, (double) request.JSON.quarterlyPaymentLoadingFactor)
            stampDutyVoluntary = Math.ceil(0.004 * netVoluntarty)
            vatVoluntary = (0.07 * (stampDutyVoluntary + netVoluntarty)).round(2)
            totalVoluntary = netVoluntarty + stampDutyVoluntary + vatVoluntary
            instalment = ((totalVoluntary + totalCompulsory + totalCarReplacement) / 4).round(2)
            firstInstalment = (instalment + totalRSA).round(2)

            JSONObject quarterly = new JSONObject()
            quarterly.put("firstInstalment", firstInstalment)
            quarterly.put("instalment", instalment)
            quarterly.put("totalPrice", (firstInstalment + (instalment * 3)).round(2))
            returnValue.put("quarterly", quarterly)
        }

        render returnValue
    }

    def getCarReplacementPrice(double carReplacement, double loadingFactor) {
        double netCarReplacement = carReplacement * loadingFactor
        double vatCarReplacement = (0.07 * netCarReplacement).round(2)
        return netCarReplacement + vatCarReplacement
    }

    @Secured(['permitAll'])
    def roojaiPaymentHandle() {
        JSONObject jsonRequest = request.JSON
        int httpResponseStatus = 200

        try {
            if (jsonRequest.data.object.equals("charge") && (jsonRequest.data.status.equals("successful") || jsonRequest.data.status.equals("failed"))) {

                OmiseWebHooksLog omiseWebHooksLog = new OmiseWebHooksLog(opportunityNumber: jsonRequest.data.description, webhooksData: jsonRequest.toString(), created: new Date(), refNo: jsonRequest.data?.metadata?.refNo)
                omiseWebHooksLog.save(flush: true)

                Promise p = task {
                    if(jsonRequest.data.metadata.reference1.equals("01") && jsonRequest.data.source?.type?.contains("internet_banking")){
                        paymentService.internetBankingHandle(jsonRequest)
                    } else if(jsonRequest.data.metadata.reference1.equals("01") && (jsonRequest.data.metadata.refid != null || jsonRequest.data.metadata.refid != "") && jsonRequest.data.metadata.tp == null){
                        paymentService.updatePaymentData(jsonRequest)
                    } else if(jsonRequest.data.source?.type?.equals("promptpay") || jsonRequest.data.metadata.tp != null) {
                        String url = systemconfigurationService.getValueByKey("microservice.url") + "/payments/paymentHandler"
                        httpService.requestMicroServiceHttpPost(url, jsonRequest)
                    }
                }
            }else if(jsonRequest.data.object.equals("transfer") && jsonRequest.key.equals("transfer.pay")){
                OmiseWebHooksLog omiseWebHooksLog = new OmiseWebHooksLog(opportunityNumber: null, webhooksData: jsonRequest.toString(), created: new Date(), refNo: (jsonRequest.data.id).toString())
                omiseWebHooksLog.save(flush: true)
                Promise p = task {
                    String url = systemconfigurationService.getValueByKey("microservice.url") + "/payments/updateGATransferPayment"
                    httpService.requestMicroServiceHttpPost(url, jsonRequest)
                }
            }
        } catch (Exception e) {
            httpResponseStatus = 500
            log.error "Error: ${e.message}", e
        }

        respond({}, status: httpResponseStatus, formats: ["json"])
    }

    def isShopee99Complete() {
        render paymentService.isShopee99Complete(params.quotationId)
    }

    @Deprecated
    def show() {
        JSONObject responseObject = new JSONObject()
        JSONObject data = new JSONObject()
        int status = 200
        try {
            String opportunityNumber = StringUtil.decrypt(params.quotationId)
            println("opportunityNumber : " + opportunityNumber);
            String[] errorCode = PaymentData.executeQuery("select p.errorCode from PaymentData p where p.opportunityNumber = :opportunityNumber order by p.created desc", [opportunityNumber: opportunityNumber])

            if (errorCode.length == 0) {
                status = 404
            } else {
                Set<String> set = new HashSet<String>(Arrays.asList(errorCode))

                if (set.contains("00")) {
                    data.put("errorCode", "00")
                } else {
                    data.put("errorCode", errorCode[0])
                }
            }
        } catch (Exception e) {
            log.error "Error: ${e.message}", e
        }
        responseObject.put("success", status.equals(200))
        responseObject.put("data", data)

        respond(responseObject, status: status, formats: ['json'])
    }

    def webhook() {
        JSONObject jsonResponse = new JSONObject()
        int statusCode = 200
        try {
            String opportunityNumber = StringUtil.decrypt(params.quotationId)

            OmiseWebHooksLog omiseWebHooksLog

            if(params.refNo == null){
                omiseWebHooksLog = OmiseWebHooksLog.find("from OmiseWebHooksLog where opportunityNumber=:opportunityNumber order by created desc", [opportunityNumber: opportunityNumber])
            }else{
                omiseWebHooksLog = OmiseWebHooksLog.find("from OmiseWebHooksLog where opportunityNumber=:opportunityNumber and refNo=:refNo order by created desc", [opportunityNumber: opportunityNumber, refNo: params.refNo])
            }

            if(omiseWebHooksLog == null){
                statusCode = 404
            }else{
                JSONObject webhook = new JSONObject(omiseWebHooksLog.webhooksData)

                if(webhook != null && webhook.data.object == "charge"){
                    JSONObject source = new JSONObject()
                    source.put("type", webhook.data.source.type)

                    jsonResponse.put("opportunityNumber", opportunityNumber)
                    jsonResponse.put("status", webhook.data.status)
                    jsonResponse.put("tp", webhook.data.metadata.tp)
                    jsonResponse.put("source", source)
                }else{
                    statusCode = 404
                }
            }
        } catch (Exception e) {
            statusCode = 500
            log.error "Error: ${e.message}", e
        }

        respond(jsonResponse, status: statusCode, formats: ['json'])
    }

    def covidRenewal() {
        String endDateEncrypt = request.JSON.endDate
        JSONObject responseObject = new JSONObject()
        int status = 200
        if (endDateEncrypt != null && !endDateEncrypt.isEmpty()) {
            String enddate = StringUtil.decrypt(endDateEncrypt)
            long diffToday = DateTimeUtil.diffTodayDate(enddate, "yyyy-MM-dd")
            responseObject.put("status", diffToday > 0)
            responseObject.put("message", diffToday > 0 ? null : "Policy Expired")
        } else {
            status = 400
        }

        respond(responseObject, status: status, formats: ['json'])
    }

    def qrcode() {
        JSONObject jsonResponse = new JSONObject()
        int statusCode = 200

        if (params.quote.Opportunity_Number != null && params.amount != null && params.transactionType != null) {
            try{
                QuotationForm quotationForm = createQuotationForm(params)

                JSONObject promptPayData = new JSONObject()

                if(params.transactionType == "ren" || params.transactionType == "rec" || params.transactionType == "endt" || params.transactionType == "nb"){
                    promptPayData.put("amount", params.amount)
                    promptPayData.put("opportunityNumber", params.quote.Opportunity_Number)
                    promptPayData.put("channel", params.channel)
                    promptPayData.put("transactionType", params.transactionType)

                    JSONObject payment = saleforceService.createPayment(params)

                    JSONArray payments = saleforceService.createPayments(params)

                    JSONObject creditCard = new JSONObject()

                    JSONObject root
                    if(params.transactionType == "nb"){
                        root = saleforceService.createJsonQuotation(quotationForm, StringUtil.getIpAdress(request))
                    } else {
                        root = new JSONObject()
                        JSONObject item = new JSONObject()
                        root.put("item", item)
                    }

                    root.item.put("payments", payments)
                    root.item.put("payment", payment)
                    root.item.put("creditCard", creditCard)

                    promptPayData.put("jsonRequest", root.toString())
                }

                String url = systemconfigurationService.getValueByKey("microservice.url") + "/payments/qrcode"
                String roojaiServicesResponse = httpService.requestMicroServiceHttpPost(url, promptPayData)

                JSONObject qrcodeObject = new JSONObject(roojaiServicesResponse)

                if(qrcodeObject.qrcode != null){
                    jsonResponse.put("qrcode", qrcodeObject.qrcode)
                } else if(qrcodeObject.downloadUri != null){
                    jsonResponse.put("qrcode", qrcodeObject.downloadUri)
                }
            }catch(Exception e){
                statusCode = 500
                log.error "Error: ${e.message}", e
            }
        } else {
            statusCode = 400
        }

        respond(jsonResponse, status: statusCode, formats: ['json'])
    }

    def paymentdata(){
        JSONObject responseObject = new JSONObject()
        int httpResponseStatus = 200

        QuotationForm quotationForm = createQuotationForm(params)

        JSONObject jsonQuotation = saleforceService.createJsonQuotation(quotationForm, StringUtil.getIpAdress(request))

        PaymentData paymentData = new PaymentData(opportunityNumber: quotationForm.quote.Opportunity_Number, created: new Date(), json_input: jsonQuotation.toString())
        paymentData.save(flush: true)

        String refid = StringUtil.encrypt(paymentData.id.toString())
        String quotationid = StringUtil.encrypt(quotationForm.quote.Opportunity_Number)

        if(refid == "" || quotationid == ""){
            httpResponseStatus = 500
        }else{
            responseObject.put("refid", refid)
            responseObject.put("quotationid", quotationid)
        }

        respond(responseObject, status: httpResponseStatus, formats: ["json"])
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

    def create() {
        JSONObject jsonResponse
        int statusCode = 200

        if (params.quote.Opportunity_Number != null && params.transactionType != null) {
            try{
                QuotationForm quotationForm = createQuotationForm(params)
                JSONObject payment = saleforceService.createPayment(params)
                JSONArray payments = saleforceService.createPayments(params)
                JSONObject creditCard = new JSONObject()
                JSONObject jsonQuotation
                if(params.transactionType == "nb"){
                    jsonQuotation = saleforceService.createJsonQuotation(quotationForm, StringUtil.getIpAdress(request))
                } else {
                    jsonQuotation = new JSONObject()
                    JSONObject item = new JSONObject()
                    jsonQuotation.put("item", item)
                }

                jsonQuotation.item.put("payments", payments)
                jsonQuotation.item.put("payment", payment)
                jsonQuotation.item.put("creditCard", creditCard)

                String url = systemconfigurationService.getValueByKey("microservice.url") + "/quotations/save"
                String roojaiServicesResponse = httpService.requestMicroServiceHttpPost(url, jsonQuotation)

                jsonResponse = new JSONObject(roojaiServicesResponse)

                if(jsonResponse.length() == 0){
                    statusCode = 500
                }
            }catch(Exception e){
                statusCode = 500
                log.error "Error: ${e.message}", e
            }
        } else {
            statusCode = 400
        }

        respond(jsonResponse, status: statusCode, formats: ['json'])
    }
}

package com.roojai

import co.omise.Client
import co.omise.models.*
import co.omise.requests.Request
import com.roojai.saleforce.PaymentActionType
import com.roojai.saleforce.PaymentType
import com.roojai.util.StatusUtil
import com.roojai.util.StringUtil
import grails.gorm.transactions.Transactional
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.hibernate.HibernateException

import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    SystemconfigurationService systemconfigurationService
    SaleforceService saleforceService
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd")

    JSONObject createInternetBankingRefId(opportunityNumber, merchant_total_amount, offsite, customerName, customerMobile, customerEmail, productType, isPmntInitiatedBySF, agentId, isQuote, String paymentFrequency) {
        JSONObject returnResult = new JSONObject()
        OmiseData omise
        if (opportunityNumber && merchant_total_amount && offsite && customerName && customerMobile) {
            Date now = new Date()
            String merchant_invoice_no = opportunityNumber + "_" + now.format("yyyyMMddHHmm", TimeZone.getTimeZone('Asia/Bangkok'))

            SourceType sourceType = this.getSourceType(offsite)
            omise = new OmiseData(opportunityNumber: opportunityNumber, invoiceNo: merchant_invoice_no, payerName: customerName, payerMobile: customerMobile, payerEmail: customerEmail, amount: merchant_total_amount, hdIssuingBank: sourceType.toString(), paymentType: "Online Banking")

            try {
                String encryptQID = java.net.URLEncoder.encode(StringUtil.encrypt(opportunityNumber), "UTF-8")
                String secretKey = systemconfigurationService.getValueByKey("payment.secretKeyOmise")
                String publicKey = systemconfigurationService.getValueByKey("payment.publicKeyOmise")
                int amount = Integer.parseInt(merchant_total_amount.replaceAll("\\.", ""))
                String returnURI

                if (productType != null && productType.equalsIgnoreCase(com.roojai.saleforce.ProductType.MotorBike.text()) && !isQuote) {
                    returnURI = systemconfigurationService.getValueByKey("payment.internetBankingReturnURIMotorbike")
                } else if (productType != null && productType.equalsIgnoreCase(com.roojai.saleforce.ProductType.MotorBike.text()) && isQuote) {
                    returnURI = systemconfigurationService.getValueByKey("payment.internetBankingReturnURIMotorbike_quote")
                }else {
                    returnURI = systemconfigurationService.getValueByKey("payment.internetBankingReturnURIMotorCar")
                }

                String refNo = now.format("yyyyMMddHHmmss", TimeZone.getTimeZone('Asia/Bangkok'))

                returnURI += "?quotationId=" + encryptQID + "&isPmntInitiatedBySF=" + isPmntInitiatedBySF + "&agentId=" + agentId + "&refNo=" + refNo

                Client client = new Client.Builder()
                        .publicKey(publicKey)
                        .secretKey(secretKey)
                        .build()

                Request<Source> requestSource = new Source.CreateRequestBuilder()
                        .amount(amount)
                        .currency("THB")
                        .type(sourceType)
                        .build()

                Source source = client.sendRequest(requestSource)

                omise.sourceId = source.getObject().equals("source") ? source.id : ""
                omise.sourceType = sourceType.toString()

                HashMap<String, Object> chargeMetadata = new HashMap<String, Object>()
                chargeMetadata.put("reference1", "01")
                chargeMetadata.put("isPmntInitiatedBySF", isPmntInitiatedBySF)
                chargeMetadata.put("agentId", agentId)
                chargeMetadata.put("refNo", refNo)
                chargeMetadata.put("tp", "nb")
                chargeMetadata.put("paymentFrequency", paymentFrequency != null ? paymentFrequency : "")

                Request<Charge> requestCharge = new Charge.CreateRequestBuilder()
                        .source(source.id)
                        .description(opportunityNumber)
                        .amount(source.getAmount())
                        .currency("THB")
                        .returnUri(returnURI)
                        .metadata(chargeMetadata)
                        .build()

                Charge charge = client.sendRequest(requestCharge)

                if (charge.object.equals("charge")) {
                    omise.chargeId = charge.id
                    returnResult.put("code", "00")
                    returnResult.put("refid", charge.authorizeUri)
                } else {
                    returnResult.put("code", "99")
                    returnResult.put("refid", "")
                }
            } catch (Exception e) {
                log.error "Error: ${e.message}", e
                returnResult.put("code", "99")
                returnResult.put("refid", "")
                omise.errorMessage = e.getMessage()
                SendMailData sm = new SendMailData(messageType: "Paysbuy", opportunityNumber: opportunityNumber, created: new Date(), customerName: customerName, phoneNumber: customerMobile, email: customerEmail, amount: merchant_total_amount, status: "N", method: PaymentType.InternetBanking.text())
                sm.save(flush: true)
            }

            if (omise != null) {
                omise.created = new Date()
                omise.save(flush: true)
            }

        } else {
            returnResult.put("code", "99")
            returnResult.put("refid", "")
        }
        return returnResult
    }

    SourceType getSourceType(String bank) {
        SourceType sourceType
        if ("bblpn".equalsIgnoreCase(bank)) {
            sourceType = SourceType.InternetBankingBbl
        } else if ("scbpn".equalsIgnoreCase(bank)) {
            sourceType = SourceType.InternetBankingScb
        } else if ("ktbpn".equalsIgnoreCase(bank)) {
            sourceType = SourceType.InternetBankingKtb
        } else if ("baypn".equalsIgnoreCase(bank)) {
            sourceType = SourceType.InternetBankingBay
        } else {
            sourceType = null
        }
        return sourceType
    }

    JSONObject creditCardPayment(roojaiTokenId, roojaiCardId, opportunityNumber, merchantTotalAmount, payerEmail, issuingBank, paymentFrequency, payerMobile, customerName, autoRenewFlag, isPmntInitiatedBySF, agentId) {
        JSONObject jsonInput = new JSONObject()
        JSONObject returnResult = new JSONObject()
        returnResult.put("success", true)
        OmiseData omiseData = new OmiseData(opportunityNumber: opportunityNumber, amount: merchantTotalAmount, payerName: customerName, payerMobile: payerMobile, payerEmail: payerEmail, tokenId: roojaiTokenId, hdIssuingBank: issuingBank, hdPaymentFrequency: paymentFrequency, paymentType: PaymentType.CreditCard.text(), cardId: roojaiCardId, kpiTokenId: '', kpiCardId: '')
        def errorCode
        def errMsg
        try {
            def roojaiPublicKey = systemconfigurationService.getValueByKey("payment.publicKeyOmise")
            def roojaiSecretKey = systemconfigurationService.getValueByKey("payment.secretKeyOmise")
            def Status__c
            def Payment_date__c
            def now = new Date()
            def merchant_invoice_no = opportunityNumber + "_" + now.format("yyyyMMddHHmm", TimeZone.getTimeZone('Asia/Bangkok'))

            omiseData.invoiceNo = merchant_invoice_no

            Client client = new Client.Builder()
                    .publicKey(roojaiPublicKey)
                    .secretKey(roojaiSecretKey)
                    .build()

            Request<Customer> requestCustomer = new Customer.CreateRequestBuilder()
                    .email(payerEmail)
                    .description(customerName)
                    .card(roojaiTokenId)
                    .build()
            Customer customer = client.sendRequest(requestCustomer)

            if (customer.object.equals("customer")) {
                Map<String, Object> metadata = new HashMap<String, Object>()
                metadata.put("RefNumber", "REF_" + opportunityNumber + "_DP")
                metadata.put("InstNumber", opportunityNumber + "_DP")
                metadata.put("Amount", (int) Double.parseDouble(merchantTotalAmount))
                metadata.put("Channel", "Web")
                metadata.put("DownPayment", "Yes")
                metadata.put("PolicyNumber", opportunityNumber)

                Request<Charge> requestCharge = new Charge.CreateRequestBuilder()
                        .amount(Integer.parseInt(merchantTotalAmount.replaceAll("\\.", "")))
                        .currency("thb")
                        .description(merchant_invoice_no)
                        .customer(customer.id)
                        .capture(true)
                        .metadata(metadata)
                        .build()

                Charge roojaiCharge = client.sendRequest(requestCharge)

                if (roojaiCharge.object.equals("charge")) {
                    errorCode = roojaiCharge.getStatus().equals(ChargeStatus.Failed) == true ? "99" : null
                    errMsg = roojaiCharge.failureMessage
                    Status__c = roojaiCharge.getStatus().equals(ChargeStatus.Successful) == true && roojaiCharge.isPaid() == true ? "Paid" : "Pending"
                    Payment_date__c = now.format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok'))

                    JSONObject payment = new JSONObject()
                    payment.put("deferredPaymentFlag", false)
                    payment.put("isPmntInitiatedBySF", isPmntInitiatedBySF.toBoolean())
                    payment.put("agentId", agentId)
                    payment.put("autoRenewFlag", autoRenewFlag ?: 'Yes')
                    payment.put("errorCode", errorCode)
                    payment.put("errorMessage", errMsg)

                    JSONObject item = new JSONObject()
                    item.put("payment", payment)

                    JSONObject paymentItem = new JSONObject()
                    paymentItem.put("Payment_Fee__c", "0")
                    paymentItem.put("Payment_date__c", Payment_date__c)
                    paymentItem.put("Bank__c", "Omise")
                    paymentItem.put("Type__c", "Receivable")
                    paymentItem.put("WHT_certificate_number__c", "")
                    paymentItem.put("Installment_number__c", "1")
                    paymentItem.put("Charge_Id__c", roojaiCharge?.id)
                    paymentItem.put("Credit_Amount__c", merchantTotalAmount)
                    paymentItem.put("Payment_frequency__c", paymentFrequency)
                    paymentItem.put("Status__c", Status__c)
                    paymentItem.put("Payment_method__c", PaymentType.CreditCard.text())

                    JSONArray payments = new JSONArray()
                    payments.put(paymentItem)
                    item.put("payments", payments)

                    def CardNumber = (roojaiCharge && roojaiCharge.card && roojaiCharge.card.lastDigits) ? StatusUtil.convertCreditNo(roojaiCharge.card.lastDigits) : ""
                    String rps = "%2s"
                    String Credit_Card_Expire_month__c = (roojaiCharge && roojaiCharge.card && roojaiCharge.card.expirationMonth) ? String.format(rps, String.valueOf(roojaiCharge.card.expirationMonth)).replace(' ', '0') : ""

                    JSONObject creditCard = new JSONObject()
                    creditCard.put("Card_Id__c", roojaiCardId)
                    creditCard.put("Issuing_bank__c", issuingBank)
                    creditCard.put("Name_on_card__c", roojaiCharge?.card?.name)
                    creditCard.put("Customer_Id__c", customer?.id)
                    creditCard.put("Card_Number__c", CardNumber)
                    creditCard.put("Expiry_Year__c", roojaiCharge?.card?.expirationYear)
                    creditCard.put("Card_Type__c", roojaiCharge?.card?.brand)
                    creditCard.put("Credit_Card_Expire_month__c", Credit_Card_Expire_month__c)
                    item.put("creditCard", creditCard)

                    jsonInput.put("item", item)
                }
            }
        } catch (OmiseException | HibernateException | NullPointerException | NumberFormatException e) {
            errorCode = "99"
            omiseData.errorMessage = e.getMessage()
            returnResult.put("success", false)
            returnResult.put("errorMessage", "Other Error")
            log.error(e.getMessage(), e)
        } finally {
            omiseData.created = new Date()
            if (!omiseData.save(flush: true)) {
                omiseData.errors.allErrors.each {
                    log.error "[Exc]creditCardPayments: ${it}"
                }
            }

            PaymentData paymentData = new PaymentData(opportunityNumber: opportunityNumber, payment_type: PaymentType.CreditCard.text(), errorCode: (errorCode ? errorCode : "00"), errorMessage: errMsg, created: new Date(), json_input: jsonInput.toString())

            if (!paymentData.save(flush: true)) {
                paymentData.errors.allErrors.each {
                    log.error "[Exc]creditCardPayments: ${it}"
                }
            }

            returnResult.put("paymentStatus", errorCode ? errorCode : "00")
            returnResult.put("paymentID", paymentData.id)
        }
        return returnResult
    }

    String putPaymentToSaleforce(String json_input) {
        JSONObject jsonToken = saleforceService.getAccessToken()
        String token = jsonToken.access_token
        String url = jsonToken.instance_url + "/services/apexrest/quotepaymentapi"
        String tokenType = jsonToken.token_type
        StringBuffer result = new StringBuffer()
        try {
            HttpClient client = HttpClientBuilder.create().build()
            HttpPost post = new HttpPost(url)
            StringEntity params = new StringEntity(json_input, "utf-8")
            post.setEntity(params)
            post.addHeader("Authorization", tokenType + " " + token)
            post.addHeader("content-type", "application/json")
            post.addHeader("charset", "UTF-8")
            HttpResponse response = client.execute(post)
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"))

            String line
            while ((line = rd.readLine()) != null) {
                result.append(line)
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e)
        }
        return result.toString()
    }

    JSONObject internetBankingHandle(JSONObject jsonRequest) {
        JSONObject jsonResponse = new JSONObject()
        try {

            String errorCode
            String errMsg = jsonRequest.data.failure_message
            String Status__c
            String Online_Stage
            String amt = String.valueOf(jsonRequest.data.amount)
            String amount = amt.substring(0, amt.length() - 2) + "." + amt.substring(amt.length() - 2)
            String Payment_date__c = new Date().format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok'))

            if (jsonRequest.data.status.equals("successful")) {
                Status__c = "Paid"
                Online_Stage = "Success"
            } else {
                Status__c = "Pending"
                errorCode = "99"
                Online_Stage = "Failed"
            }

            JSONObject item = new JSONObject()
            JSONObject payment = new JSONObject()
            JSONArray payments = new JSONArray()
            JSONObject paymentItem = new JSONObject()
            JSONObject root = new JSONObject()
            JSONObject creditCard = new JSONObject()

            payment.put("opportunityNumber", jsonRequest.data.description)
            payment.put("errorCode", errorCode)
            payment.put("errorMessage", errMsg)
            payment.put("isActivatePolicy", false)
            payment.put("Online_Screen", "payment")
            payment.put("Online_Stage", Online_Stage)
            payment.put("isPmntInitiatedBySF", Boolean.valueOf(jsonRequest.data.metadata.isPmntInitiatedBySF))
            payment.put("agentId", jsonRequest.data.metadata.agentId)
            item.put("payment", payment)

            paymentItem.put("Bank__c", "Omise")
            paymentItem.put("WHT_certificate_number__c", "")
            paymentItem.put("Status__c", Status__c)
            paymentItem.put("Installment_number__c", "1")
            paymentItem.put("Due_Date__c", Status__c.equals("Paid") ? sdf1.format(new Date()) : null)
            paymentItem.put("Credit_Amount__c", amount)
            paymentItem.put("Payment_date__c", Payment_date__c)
            paymentItem.put("Payment_frequency__c", jsonRequest.data.metadata.paymentFrequency != null ? jsonRequest.data.metadata.paymentFrequency : "")
            paymentItem.put("Payment_method__c", PaymentType.InternetBanking.text())
            paymentItem.put("Type__c", "Receivable")
            paymentItem.put("Payment_Fee__c", "0")
            paymentItem.put("Charge_Id__c", jsonRequest.data.id)
            payments.put(paymentItem)
            item.put("payments", payments)

            item.put("creditCard", creditCard)

            root.put("item", item)

            String jsonOutput = putPaymentToSaleforce(root.toString())

            PaymentData paymentData = new PaymentData(opportunityNumber: jsonRequest.data.description, payment_type: PaymentType.InternetBanking.text(), errorCode: (errorCode ? errorCode : "00"), errorMessage: errMsg, created: new Date(), json_input: root.toString(), json_output: jsonOutput)

            if (!paymentData.save()) {
                paymentData.errors.allErrors.each {
                    log.error it
                }
            }

        } catch (Exception e) {
            log.error "Error: ${e.message}", e
        }

        return jsonResponse
    }

    PaymentData getPaymentData(int id, String opportunityNumber) {
        return PaymentData.findByIdAndOpportunityNumber(id, opportunityNumber)
    }

    JSONObject updatePaymentData(JSONObject requestObject) {
        JSONObject responseObject = new JSONObject()
        try {
            String errorCode
            String errMsg = requestObject.data.failure_message
            String Status__c
            String Online_Stage
            String amt = String.valueOf(requestObject.data.amount)
            String amount = amt.substring(0, amt.length() - 2) + "." + amt.substring(amt.length() - 2)
            String Payment_date__c = new Date().format("yyyy-MM-dd", TimeZone.getTimeZone('Asia/Bangkok'))

            if (requestObject.data.status.equals("successful")) {
                Status__c = "Paid"
                Online_Stage = "Success"
            } else {
                Status__c = "Pending"
                errorCode = "99"
                Online_Stage = "Failed"
            }

            JSONObject payment = new JSONObject()
            JSONArray payments = new JSONArray()
            JSONObject paymentItem = new JSONObject()
            JSONObject creditCard = new JSONObject()

            String refId = StringUtil.decrypt(requestObject.data.metadata.refid)

            PaymentData paymentData = PaymentData.get(refId)

            JSONObject jsonInput = new JSONObject(paymentData.json_input)

            jsonInput.item.quote.Online_Stage = Online_Stage

            if (requestObject.data.status.equals("successful")) {
                payment.put("isActivatePolicy", true)
            }
            payment.put("deferredPaymentFlag", false)
            payment.put("opportunityNumber", requestObject.data.description)
            payment.put("errorCode", errorCode)
            payment.put("errorMessage", errMsg)
            payment.put("Online_Screen", jsonInput.item.quote.Online_Screen)
            payment.put("Online_Stage", Online_Stage)
            payment.put("isPmntInitiatedBySF", Boolean.valueOf(requestObject.data.metadata.isPmntInitiatedBySF))
            payment.put("agentId", requestObject.data.metadata.agentId)

            paymentItem.put("Bank__c", "Omise")
            paymentItem.put("WHT_certificate_number__c", "")
            paymentItem.put("Status__c", Status__c)
            paymentItem.put("Installment_number__c", "1")
            paymentItem.put("Due_Date__c", Status__c.equals("Paid") ? sdf1.format(new Date()) : null)
            paymentItem.put("Credit_Amount__c", amount)
            paymentItem.put("Payment_date__c", Payment_date__c)
            paymentItem.put("Payment_frequency__c", jsonInput.item.quote.paymentFrequency)
            paymentItem.put("Payment_method__c", PaymentType[requestObject.data.source.type].text())
            paymentItem.put("Type__c", "Receivable")
            paymentItem.put("Payment_Fee__c", "0")
            paymentItem.put("Charge_Id__c", requestObject.data.id)
            payments.add(paymentItem)

            jsonInput.item.put("payment", payment)
            jsonInput.item.put("payments", payments)
            jsonInput.item.put("creditCard", creditCard)

            paymentData.payment_type = PaymentType[requestObject.data.source.type].text()
            paymentData.errorCode = (errorCode ? errorCode : "00")
            paymentData.errorMessage = errMsg
            paymentData.json_input = jsonInput.toString()

            JSONObject sfResponse = saleforceService.saveQuotationToSF(jsonInput)

            paymentData.json_output = sfResponse.toString()
            paymentData.save(flush: true)

            responseObject.put("item", jsonInput)
        } catch (Exception e) {
            log.error "Error: ${e.message}", e
        }

        return responseObject
    }
}

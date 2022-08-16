package com.roojai

import com.roojai.saleforce.ProductType
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class ConfigurationController {
    static responseFormats = ['json']

    def systemconfigurationService
    def abTestingService

    def index() {
        JSONObject jsonResponse = new JSONObject()
        int httpStatusCode = 200
        boolean foundProductType = false

        for(ProductType productType: ProductType.values()){
            if(productType.text() == params.product_type){
                foundProductType = true;
                break;
            }
        }
        
        if(foundProductType == false){
            httpStatusCode = 400
        }else{
            if(params.query == null){
                jsonResponse.put("serverTime", getServerTime())
                Systemconfiguration[] systemconfigurations = systemconfigurationService.getSystemconfigurationsByProductType(params.product_type)
                for(int i=0; i<systemconfigurations.size(); i++){
                    if(systemconfigurations[i].value.equalsIgnoreCase("true") || systemconfigurations[i].value.equalsIgnoreCase("false")){
                        jsonResponse.put(systemconfigurations[i].name, Boolean.parseBoolean(systemconfigurations[i].value))
                    }else{
                        jsonResponse.put(systemconfigurations[i].name, systemconfigurations[i].value)
                    }
                }
                if(params.product_type == ProductType.MotorCar.text() && params.isGA != "true"){
                    jsonResponse.put("ABTesting", abTestingService.getABTestingValue(params.ABTesting, ProductType.MotorCar.text()))
                } else if (params.product_type == ProductType.Health.text() && params.isGA != "true") {
                    jsonResponse.put("ABTesting", abTestingService.getABTestingValue(params.ABTesting, ProductType.Health.text()))
                } else if (params.isGA == "true" && params.isAABroker == "true"){
                    jsonResponse.put("AABroker", systemconfigurationService.getValueByCategory("AABroker"))
                }
            }else{
                String[] query
                if(params.query.getClass().isArray()){
                    query = params.query
                }else {
                    query = [params.query]
                }
                for(String q: query){
                    if(q == "serverTime"){
                        jsonResponse.put("serverTime", getServerTime())
                    }else if(q == "omisePublicKey"){
                        jsonResponse.put("omisePublicKey", systemconfigurationService.getOmisePublicKey())
                    }else if(q == "productPage"){
                        jsonResponse.put("productPage", systemconfigurationService.getValueByCategory("productPage"))
                    }else if(q == "partnerRefuseModal"){
                        jsonResponse.put("partnerRefuseModal", systemconfigurationService.getValueByCategory("partnerRefuseModal"))
                    }

                }
            }
        }

        respond(jsonResponse, status: httpStatusCode)
    }

    String getServerTime(){
        Date now = new Date()
        return now.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Bangkok'))
    }
}

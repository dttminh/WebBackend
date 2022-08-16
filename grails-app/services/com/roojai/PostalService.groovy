package com.roojai

import grails.gorm.transactions.Transactional
import grails.plugin.cache.Cacheable
import org.grails.web.json.JSONObject

@Transactional
class PostalService {
    def httpService
    def systemconfigurationService

    @Cacheable('PostalService_searchProvinceByPostalCode')
    def searchProvinceByPostalCode(String postalCode) {
        String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/getPostalInformation"
        JSONObject request = new JSONObject();
        request.put("postalCode", postalCode);
        def res = httpService.requestMicroServiceHttpPost(url, request);

        return res;
    }

    @Cacheable('PostalService_searchDistrictByProvince')
    def searchDistrictByProvince(String provinceCode) {
        String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/searchDistrictByProvince"
        JSONObject request = new JSONObject();
        request.put("provinceCode", provinceCode);
        def res = httpService.requestMicroServiceHttpPost(url, request);

        return res;
    }

    @Cacheable('PostalService_searchSubDistrictByProvinceDistrict')
    def searchSubDistrictByProvinceDistrict(String provinceCode, String districtCode) {
        String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/searchSubDistrictByProvinceDistrict"
        JSONObject request = new JSONObject();
        request.put("provinceCode", provinceCode);
        request.put("districtCode", districtCode);
        def res = httpService.requestMicroServiceHttpPost(url, request);

        return res;
    }

    @Cacheable('PostalService_getProvince')
    def getProvince() {
        String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/getProvince"
        JSONObject request = new JSONObject();
        def res = httpService.requestMicroServiceHttpPost(url, request);

        return res;
    }
}

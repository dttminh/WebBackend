package com.roojai

import grails.gorm.transactions.Transactional
import grails.plugin.cache.Cacheable
import groovy.json.JsonSlurper
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional(readOnly = true)
class RetrieveService {
    def systemconfigurationService
    def httpService

    def getOccupation(nameEn) {
        if (nameEn != null) {
            Occupation occ = Occupation.find("from Occupation where nameEn = ?", [nameEn])
            JSONObject js = new JSONObject()
            js.put("id", occ.id)
            js.put("nameEn", occ.nameEn)
            js.put("nameTh", occ.nameTh)
            js.put("occupationGroup", occ.occupationGroup)
            return js
        } else {
            return ""
        }
    }

    def getSubDistrict(subDistrictEn, postalCode) {
        if (subDistrictEn != null && postalCode != null) {
            String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/getSubDistrictByName"
            JSONObject request = new JSONObject();
            request.put("postalCode", postalCode);
            request.put("subDistrict", subDistrictEn);
            def res = httpService.requestMicroServiceHttpPost(url, request);

            def jsonRes = new JsonSlurper().parseText(res)
            JSONArray js = new JSONArray()
            if (subDistrictEn != null && postalCode != null) {
                if (jsonRes != null && jsonRes.model != null && jsonRes.model.subDistricts != null && jsonRes.model.subDistricts.size() > 0) {
                    js.put(jsonRes.model.subDistricts[0].id ? jsonRes.model.subDistricts[0].id : "")
                    js.put(jsonRes.model.subDistricts[0].name[1] ? jsonRes.model.subDistricts[0].name[1] : "")
                    js.put(jsonRes.model.subDistricts[0].name[0] ? jsonRes.model.subDistricts[0].name[0] : "")
                    return js
                } else {
                    return ""
                }
            } else {
                return ""
            }
        }

    }

    @Cacheable('RetrieveService_getProvinceDetail')
    def getProvinceDetail(province, district, subDistrict, postalCode) {

        JSONObject responseJson = null;

        String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/getProvinceDetail"
        JSONObject request = new JSONObject();
        request.put("provinceCode", province);
        request.put("districtCode", district);
        request.put("subDistrictCode", subDistrict);
        request.put("postalCode", postalCode);

        def responseStr = httpService.requestMicroServiceHttpPost(url, request);

        def provinceDetailRes = new JsonSlurper().parseText(responseStr)

        JSONArray resProvince = new JSONArray()
        JSONArray resdistrict = new JSONArray()
        JSONArray resSubDistrict = new JSONArray()

        if (provinceDetailRes != null && provinceDetailRes.model != null) {
            def model = provinceDetailRes.model
            if (model.provinces != null && model.provinces.size() > 0) {
                resProvince.put(model.provinces[0].id)
                resProvince.put(model.provinces[0].name[1])
                resProvince.put(model.provinces[0].name[0])
            }

            if (model.districts != null && model.districts.size() > 0) {
                resdistrict.put(model.districts[0].id)
                resdistrict.put(model.districts[0].name[1])
                resdistrict.put(model.districts[0].name[0])
            }

            if (model.subDistricts != null && model.subDistricts.size() > 0) {
                resSubDistrict.put(model.subDistricts[0].id)
                resSubDistrict.put(model.subDistricts[0].name[1])
                resSubDistrict.put(model.subDistricts[0].name[0])
            }

            responseJson = new JSONObject();
            responseJson.put("province", resProvince)
            responseJson.put("district", resdistrict)
            responseJson.put("subDistrict", resSubDistrict)
        }

        return responseJson
    }

    @Cacheable('RetrieveService_getProvince')
    def getProvince(province) {
        String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/getProvinceByName"
        JSONObject request = new JSONObject();
        request.put("province", province);
        def res = httpService.requestMicroServiceHttpPost(url, request);

        def jsonRes = new JsonSlurper().parseText(res)

        JSONArray js = new JSONArray()
        if (province != null) {
            if (jsonRes != null && jsonRes.model != null && jsonRes.model.provinces != null && jsonRes.model.provinces.size() > 0) {
                js.put(jsonRes.model.provinces[0].id)
                js.put(jsonRes.model.provinces[0].name[1])
                js.put(jsonRes.model.provinces[0].name[0])
                return js
            } else {
                return "";
            }
        } else {
            return "";
        }

    }

    def getDistrict(district, province) {
        String url = systemconfigurationService.getValueByKey("microservice.url") + "/postalCode/getDistrictByName"
        JSONObject request = new JSONObject();
        request.put("province", province);
        request.put("district", district);
        def res = httpService.requestMicroServiceHttpPost(url, request);

        def jsonRes = new JsonSlurper().parseText(res)
        JSONArray js = new JSONArray()
        if (province != null) {
            if (jsonRes != null && jsonRes.model != null && jsonRes.model.districts != null && jsonRes.model.districts.size() > 0) {
                js.put(jsonRes.model.districts[0].id)
                js.put(jsonRes.model.districts[0].name[1])
                js.put(jsonRes.model.districts[0].name[0])
                return js
            } else {
                return "";
            }
        } else {
            return "";
        }

    }

    def getCountry(name) {
        if (name != null) {
            JSONObject js = new JSONObject()
            Country ct = Country.find("from Country where name = ?", [name])
            js.put("id", ct.id)
            js.put("name", ct.name)
            return js
        } else {
            return ""
        }
    }

    def getInsurer(name) {
        if (name != null) {
            JSONObject js = new JSONObject()
            Insurer ins = Insurer.find("from Insurer where nameEn = ? ", [name])
            js.put("id", ins.id)
            js.put("nameTh", ins.nameTh)
            js.put("nameEn", ins.nameEn)
            return js
        } else {
            return ""
        }
    }

    def getCoverType(coverType) {
        if (coverType != null) {
            JSONObject js = new JSONObject()
            CoverType ins = CoverType.find("from CoverType where nameEn = ? ", [coverType])
            if (ins != null) {
                js.put("id", ins.id)
                js.put("nameTh", ins.nameTh)
                js.put("nameEn", ins.nameEn)//js.put("nameEn",StatusUtil.getCoverTypeConvert(ins.nameEn))
            }
            return js
        } else {
            return ""
        }
    }

    String converseNullTostring(String value) {
        return value == null ? "" : value
    }

    int converseNullTo0(int value) {
        return value == null ? 0 : value
    }

    def getCountry(String value) {
        if (value != null) {
            JSONObject js = new JSONObject()
            Country con = Country.find("from Country where name = ?", [value])
            js.put("id", con.id)
            js.put("name", con.name)
            return js
        } else {
            return ""
        }
    }
}

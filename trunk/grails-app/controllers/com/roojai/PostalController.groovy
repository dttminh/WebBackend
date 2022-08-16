package com.roojai

import grails.rest.RestfulController;
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject
import grails.converters.JSON

@Secured(['ROLE_API'])
class PostalController extends RestfulController {
	static responseFormats = ['json', 'xml']
	def postalService
	PostalController(){
		super(Postal)
	}
    def index() {
		def search = params.search?.toLowerCase()
		def keyword = params.keyword
		def districtEn = params.districtEn
        def provinceCode = params.provinceCode
        def districtCode = params.districtCode
		JSONObject jsonResponse = new JSONObject()

		if( search == "province" ){
//			keyword = Integer.valueOf(keyword)
//			objs = Postal.executeQuery(" select kpiProvinceCode,provinceTh,provinceEn from Postal where postalCode=? group by kpiProvinceCode,provinceTh,provinceEn order by provinceTh,provinceEn  ",[keyword])
			jsonResponse = new JSONObject(postalService.searchProvinceByPostalCode(keyword));
		}else if( search =="district" ){
			keyword = Integer.valueOf(keyword)
			//objs = Postal.executeQuery(" select kpiDistrictCode,districtTh,districtEn from Postal where postalCode=? group by kpiDistrictCode,districtTh,districtEn order by districtTh,districtEn  ",[keyword])
			objs = Postal.executeQuery(" select kpiDistrictCode, districtTdistrict-by-provinceh,districtEn from Postal where postalCode=? group by kpiDistrictCode, districtTh,districtEn order by districtTh,districtEn  ",[keyword])
		}else if( search =="sub-district" ){
			keyword = Integer.valueOf(keyword)
			//objs = Postal.executeQuery(" select kpiSubDistrictCode,subDistrictTh,subDistrictEn from Postal where postalCode=? and kpiDistrictCode=?  group by kpiSubDistrictCode,subDistrictTh,subDistrictEn order by subDistrictTh,subDistrictEn  ",[keyword,kpiDistrictCode])
			objs = Postal.executeQuery(" select kpiSubDistrictCode,subDistrictTh,subDistrictEn from Postal where postalCode=? and districtEn=?  group by kpiSubDistrictCode,subDistrictTh,subDistrictEn order by subDistrictTh,subDistrictEn  ",[keyword,districtEn])
		}else if( search =="sub-district-by-province-district" ){
//			objs = Postal.executeQuery(" select kpiSubDistrictCode,subDistrictTh,subDistrictEn from Postal where provinceEn=? and districtEn=?  group by kpiSubDistrictCode,subDistrictTh,subDistrictEn order by subDistrictTh,subDistrictEn  ",[provinceEn,districtEn])
            jsonResponse = new JSONObject(postalService.searchSubDistrictByProvinceDistrict(provinceCode, districtCode));
		}else if( search =="district-by-province" ){
//			objs = Postal.executeQuery(" select kpiDistrictCode, districtTh,districtEn from Postal where provinceEn=? group by kpiDistrictCode, districtTh,districtEn order by districtTh,districtEn  ",[keyword])
            jsonResponse = new JSONObject(postalService.searchDistrictByProvince(provinceCode));
        }else{
//			objs = Postal.executeQuery(" select kpiProvinceCode,provinceTh,provinceEn from Postal group by kpiProvinceCode,provinceTh,provinceEn order by provinceTh,provinceEn ")
			jsonResponse = new JSONObject(postalService.getProvince());
		}
		render jsonResponse as JSON
	}
}

package com.roojai

import com.roojai.saleforce.Partner
import com.roojai.saleforce.ProductType
import com.roojai.util.Option
import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper
import org.grails.web.json.JSONObject
import org.hibernate.HibernateException

import java.sql.SQLException

@Transactional(readOnly = true)
class RedBookService {
	SystemconfigurationService systemconfigurationService
	HttpService httpService
	def getMakeCode(String productType){
		productType = URLEncoder.encode(productType, "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicles?product=" + productType
		def res = httpService.requestMicroServiceHttpGet(url);
		return new JSONObject(res)
	}
	def searchModelFamilyDesc(String brand,String productType) {
		brand = URLEncoder.encode(brand, "UTF-8")
		productType = URLEncoder.encode(productType, "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicles?product=" + productType + "&brand=" + brand
		def res = httpService.requestMicroServiceHttpGet(url);
		return new JSONObject(res)
	}
	def searchYearGroup(String brand, String model, String productType){
		brand = URLEncoder.encode(brand, "UTF-8")
		model = URLEncoder.encode(model, "UTF-8")
		productType = URLEncoder.encode(productType, "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicles?product=" + productType + "&brand=" + brand + "&model=" + model
		def res = httpService.requestMicroServiceHttpGet(url);
		return new JSONObject(res)
	}
	def searchFinalModelDesc(String brand, String model, Integer year, String productType, String nonce){
		brand = URLEncoder.encode(brand, "UTF-8")
		model = URLEncoder.encode(model, "UTF-8")
		String paramYear = URLEncoder.encode(year.toString(), "UTF-8")
		productType = URLEncoder.encode(productType, "UTF-8")
		nonce = URLEncoder.encode(nonce, "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicles?product=" + productType + "&brand=" + brand + "&model=" + model + "&year=" + paramYear + "&nonce=" + nonce
		def res = httpService.requestMicroServiceHttpGet(url);
		return new JSONObject(res)
	}
	def searchRedBookDetail(String brand, String model, Integer year, String subModel){
		brand = URLEncoder.encode(brand, "UTF-8")
		model = URLEncoder.encode(model, "UTF-8")
		String paramYear = URLEncoder.encode(year.toString(), "UTF-8")
		subModel = URLEncoder.encode(subModel, "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicle-details?brand=" + brand + "&model=" + model + "&year=" + paramYear + "&subModel=" + subModel
		def res = httpService.requestMicroServiceHttpGet(url);
		return new JSONObject(res).data
	}
	def searchRedBookByPartnerModel(Partner partner,String brand, String model, Integer year, Integer engineSize){
		brand = URLEncoder.encode(brand, "UTF-8")
		model = URLEncoder.encode(model, "UTF-8")
		String paramYear = URLEncoder.encode(year.toString(), "UTF-8")
		String paramEngineSize = URLEncoder.encode(engineSize.toString(), "UTF-8")
		String paramPartner = URLEncoder.encode(partner.text(), "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicle-details?brand=" + brand + "&model=" + model + "&year=" + paramYear + "&engineSize=" + paramEngineSize + "&partner=" + paramPartner
		def res = httpService.requestMicroServiceHttpGet(url);
		def dataResponse = new JSONObject(res).data
		return dataResponse[0]
	}
	def searchVehicleKey(vehicleKey){
		vehicleKey = URLEncoder.encode(vehicleKey, "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicle-details?vehicleKey=" + vehicleKey
		def res = httpService.requestMicroServiceHttpGet(url);
		return new JSONObject(res).data
	}
	def searchVehicleKeyGobear(vehicleKey){
		vehicleKey = URLEncoder.encode(vehicleKey, "UTF-8")
		String url = systemconfigurationService.getValueByKey("microservice.url") + "vehicle-details?vehicleKey=" + vehicleKey + "&partner=gobear"
		def res = httpService.requestMicroServiceHttpGet(url);
		return new JSONObject(res).data
	}
}

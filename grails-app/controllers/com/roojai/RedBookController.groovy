package com.roojai

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class RedBookController {
	static responseFormats = ['json']
	def redBookService

    def index() {
		def brand = params.brand
		def model = params.model
		def year = Integer.parseInt(params.year)
		def productType = params.productType
		def response = null;

		if(productType != null && brand != null && model != null && year != null){
			response = redBookService.searchFinalModelDesc(brand, model, year, productType, params.nonce)
		}else if(productType != null && brand != null && model != null){
			response = redBookService.searchYearGroup(brand, model, productType)
		}else if(productType != null && brand != null){
			response = redBookService.searchModelFamilyDesc(brand, productType)
		}else if(productType != null){
			response = redBookService.getMakeCode(productType);
		}

		respond response;
	}
}

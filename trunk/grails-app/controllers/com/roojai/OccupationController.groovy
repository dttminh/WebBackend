package com.roojai

import grails.rest.RestfulController;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_API'])
class OccupationController extends RestfulController {

	static responseFormats = ['json']

	OccupationService occupationService

	OccupationController(){
		super(Occupation)
	}

    def index() {
		respond occupationService.getOccupations(params.product_type, params.insurer)
	}
}

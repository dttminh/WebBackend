package com.roojai

import grails.rest.RestfulController;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_API'])
class CountryController extends RestfulController {
	static responseFormats = ['json', 'xml']
	CountryController(){
		super(Country)
	}

    def index() { 
		params.max = 10000;
		params.sort = "name";
		respond Country.list(params)
	}
}

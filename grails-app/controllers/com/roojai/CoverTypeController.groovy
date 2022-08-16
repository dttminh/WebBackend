package com.roojai

import grails.rest.RestfulController;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_API'])
class CoverTypeController extends RestfulController {
	static responseFormats = ['json', 'xml']
	CoverTypeController(){
		super(CoverType)
	}

    def index() { 
		params.max = 10000;
		respond CoverType.list(params)
	}
}

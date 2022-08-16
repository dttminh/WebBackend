package com.roojai

import grails.rest.RestfulController;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_API'])
class InsurerController extends RestfulController {
	static responseFormats = ['json', 'xml']
	InsurerController(){
		super(Insurer)
	}

    def index() {
		params.max = 10000;
		respond Insurer.list(params)
	}
}

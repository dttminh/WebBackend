package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

import com.roojai.util.StatusUtil;
@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class LeadDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		Integer max
        params.max = Math.min(max ?: 10, 100)
        respond LeadData.list(params), model:[leadDataCount: LeadData.count()]
		*/
		def hql = " from LeadData where 1=1 "
		if(params.firstName){
			hql += " and firstName like '%${params.firstName}%' "
		}
		if(params.lastName){
			hql += " and lastName like '%${params.lastName}%' "
		}
		if(params.phone){
			hql += " and phone like '%${params.phone}%' "
		}
		def objCount = LeadData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = LeadData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[leadDataCount: objCount]
    }

    def show(LeadData leadData) {
		leadData.jsonInput = leadData.jsonInput ?  StatusUtil.convertJsonToHtmlFormat(leadData.jsonInput):leadData.jsonInput
		leadData.jsonOutput = leadData.jsonOutput ?  StatusUtil.convertJsonToHtmlFormat(leadData.jsonOutput):leadData.jsonOutput
        respond leadData
    }

    def create() {
        respond new LeadData(params)
    }

    @Transactional
    def save(LeadData leadData) {
        if (leadData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (leadData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond leadData.errors, view:'create'
            return
        }

        leadData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'leadData.label', default: 'LeadData'), leadData.id])
                redirect leadData
            }
            '*' { respond leadData, [status: CREATED] }
        }
    }

    def edit(LeadData leadData) {
        respond leadData
    }

    @Transactional
    def update(LeadData leadData) {
        if (leadData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (leadData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond leadData.errors, view:'edit'
            return
        }

        leadData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'leadData.label', default: 'LeadData'), leadData.id])
                redirect leadData
            }
            '*'{ respond leadData, [status: OK] }
        }
    }

    @Transactional
    def delete(LeadData leadData) {

        if (leadData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        leadData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'leadData.label', default: 'LeadData'), leadData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadData.label', default: 'LeadData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

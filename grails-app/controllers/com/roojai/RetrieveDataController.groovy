package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

import com.roojai.util.StatusUtil;
@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class RetrieveDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		Integer max
        params.max = Math.min(max ?: 10, 100)
        respond RetrieveData.list(params), model:[retrieveDataCount: RetrieveData.count()]
		*/
		
		def hql = " from RetrieveData where 1=1 "
		if(params.opportunityNumber){
			hql += " and opportunityNumber like '%${params.opportunityNumber}%' "
		}
		def objCount = RetrieveData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = RetrieveData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[retrieveDataCount: objCount]
    }

    def show(RetrieveData retrieveData) {
		retrieveData.jsonOutput = retrieveData.jsonOutput ?  StatusUtil.convertJsonToHtmlFormat(retrieveData.jsonOutput):retrieveData.jsonOutput
        respond retrieveData
    }

    def create() {
        respond new RetrieveData(params)
    }

    @Transactional
    def save(RetrieveData retrieveData) {
        if (retrieveData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (retrieveData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond retrieveData.errors, view:'create'
            return
        }

        retrieveData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'retrieveData.label', default: 'RetrieveData'), retrieveData.id])
                redirect retrieveData
            }
            '*' { respond retrieveData, [status: CREATED] }
        }
    }

    def edit(RetrieveData retrieveData) {
        respond retrieveData
    }

    @Transactional
    def update(RetrieveData retrieveData) {
        if (retrieveData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (retrieveData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond retrieveData.errors, view:'edit'
            return
        }

        retrieveData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'retrieveData.label', default: 'RetrieveData'), retrieveData.id])
                redirect retrieveData
            }
            '*'{ respond retrieveData, [status: OK] }
        }
    }

    @Transactional
    def delete(RetrieveData retrieveData) {

        if (retrieveData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        retrieveData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'retrieveData.label', default: 'RetrieveData'), retrieveData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'retrieveData.label', default: 'RetrieveData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

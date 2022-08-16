package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class RateUsDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		 * Integer max
        params.max = Math.min(max ?: 10, 100)
        respond RateUsData.list(params), model:[rateUsDataCount: RateUsData.count()]
		*/
	/*	def data = RateUsData.createCriteria().list(){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			if(params.socialMediaFName){
				eq("socialMediaFName",params.socialMediaFName)
			}
			if(params.socialMediaLName){
				eq("socialMediaLName",params.socialMediaLName)
			}
			order("created","desc")
		}
		List<RateUsData> list = new ArrayList<RateUsData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[rateUsDataCount: data.size()]*/
		
		def hql = " from RateUsData where 1=1 "
		
		if(params.opportunityNumber){
			hql += " and opportunityNumber like '%${params.opportunityNumber}%' "
		}
		if(params.socialMediaFName){
			hql += " and socialMediaFName like '%${params.socialMediaFName}%' "
		}
		if(params.socialMediaLName){
			hql += " and socialMediaLName like '%${params.socialMediaLName}%' "
		}
		
		def objCount = RateUsData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = RateUsData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[rateUsDataCount: objCount]
    }
	
	def search(){
		def data = RateUsData.createCriteria().list(params){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			if(params.socialMediaFName){
				eq("socialMediaFName",params.socialMediaFName)
			}
			if(params.socialMediaLName){
				eq("socialMediaLName",params.socialMediaLName)
			}
			order("created","desc")
		}
		respond data, model:[rateUsDataCount: data.size()], view:"index"
	}

    def show(RateUsData rateUsData) {
        respond rateUsData
    }

    def create() {
        respond new RateUsData(params)
    }

    @Transactional
    def save(RateUsData rateUsData) {
        if (rateUsData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (rateUsData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond rateUsData.errors, view:'create'
            return
        }

        rateUsData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'rateUsData.label', default: 'RateUsData'), rateUsData.id])
                redirect rateUsData
            }
            '*' { respond rateUsData, [status: CREATED] }
        }
    }

    def edit(RateUsData rateUsData) {
        respond rateUsData
    }

    @Transactional
    def update(RateUsData rateUsData) {
        if (rateUsData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (rateUsData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond rateUsData.errors, view:'edit'
            return
        }

        rateUsData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'rateUsData.label', default: 'RateUsData'), rateUsData.id])
                redirect rateUsData
            }
            '*'{ respond rateUsData, [status: OK] }
        }
    }

    @Transactional
    def delete(RateUsData rateUsData) {

        if (rateUsData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        rateUsData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'rateUsData.label', default: 'RateUsData'), rateUsData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'rateUsData.label', default: 'RateUsData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

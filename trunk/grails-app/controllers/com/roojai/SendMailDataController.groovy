package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class SendMailDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		Integer max
        params.max = Math.min(max ?: 10, 100)
        respond SendMailData.list(params), model:[sendMailDataCount: SendMailData.count()]
        */
		/*def data = SendMailData.createCriteria().list(){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			if(params.messageType){
				eq("messageType",params.messageType)
			}
			if(params.method){
				eq("method",params.method)
			}
			if(params.customerName){
				eq("customerName",params.customerName)
			}
			if(params.phoneNumber){
				eq("phoneNumber",params.phoneNumber)
			}
			if(params.email){
				eq("email",params.email)
			}
			if(params.status){
				eq("status",params.status)
			}
			order("created","desc")
		}
		List<SendMailData> list = new ArrayList<SendMailData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[sendMailDataCount: data.size()]*/
		
		
		def hql = " from SendMailData where 1=1 "
		
		if(params.opportunityNumber){
			hql += " and opportunityNumber like '%${params.opportunityNumber}%' "
		}
		if(params.messageType){
			hql += " and messageType like '%${params.messageType}%' "
		}
		if(params.method){
			hql += " and method like '%${params.method}%' "
		}
		if(params.customerName){
			hql += " and customerName like '%${params.customerName}%' "
		}
		if(params.phoneNumber){
			hql += " and phoneNumber like '%${params.phoneNumber}%' "
		}
		if(params.email){
			hql += " and email like '%${params.email}%' "
		}
		if(params.status){
			hql += " and status like '%${params.status}%' "
		}
		
		def objCount = SendMailData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = SendMailData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[sendMailDataCount: objCount]
    }
/*	
	def search(){
		def data = SendMailData.createCriteria().list(params){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			if(params.messageType){
				eq("messageType",params.messageType)
			}
			if(params.method){
				eq("method",params.method)
			}
			if(params.customerName){
				eq("customerName",params.customerName)
			}
			if(params.phoneNumber){
				eq("phoneNumber",params.phoneNumber)
			}
			if(params.email){
				eq("email",params.email)
			}
			if(params.status){
				eq("status",params.status)
			}
			order("created","desc")
		}
		respond data, model:[sendMailDataCount: data.size()], view:"index"
	}
*/	
    def show(SendMailData sendMailData) {
        respond sendMailData
    }

    def create() {
        respond new SendMailData(params)
    }

    @Transactional
    def save(SendMailData sendMailData) {
        if (sendMailData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (sendMailData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond sendMailData.errors, view:'create'
            return
        }

        sendMailData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'sendMailData.label', default: 'SendMailData'), sendMailData.id])
                redirect sendMailData
            }
            '*' { respond sendMailData, [status: CREATED] }
        }
    }

    def edit(SendMailData sendMailData) {
        respond sendMailData
    }

    @Transactional
    def update(SendMailData sendMailData) {
        if (sendMailData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (sendMailData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond sendMailData.errors, view:'edit'
            return
        }

        sendMailData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'sendMailData.label', default: 'SendMailData'), sendMailData.id])
                redirect sendMailData
            }
            '*'{ respond sendMailData, [status: OK] }
        }
    }

    @Transactional
    def delete(SendMailData sendMailData) {

        if (sendMailData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        sendMailData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'sendMailData.label', default: 'SendMailData'), sendMailData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'sendMailData.label', default: 'SendMailData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

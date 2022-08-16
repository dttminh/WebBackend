package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class CallMeBackDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
		//params.order = "desc"
        //respond CallMeBackData.list(params), model:[callMeBackDataCount: CallMeBackData.count()]
		//respond CallMeBackData.listOrderBycreated(params), model:[callMeBackDataCount: CallMeBackData.count()]
		
		/*def data = CallMeBackData.createCriteria().list(){
			if(params.customerName){
				like("customerName","%"+params.customerName+"%")
			}
			if(params.phoneNumber){
				eq("phoneNumber",params.phoneNumber)
			}
			order("created","desc")
		}
		List<CallMeBackData> list = new ArrayList<CallMeBackData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[callMeBackDataCount: data.size()]*/
		def hql = " from CallMeBackData where 1=1 "
		if(params.customerName){
			hql += " and customerName like '%${params.customerName}%' "
		}
		if(params.phoneNumber){
			hql += " and phoneNumber like '%${params.phoneNumber}%' "
		}
		def objCount = CallMeBackData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = CallMeBackData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[callMeBackDataCount: objCount]
    }

    def show(CallMeBackData callMeBackData) {
        respond callMeBackData
    }

    def create() {
        respond new CallMeBackData(params)
    }

    @Transactional
    def save(CallMeBackData callMeBackData) {
        if (callMeBackData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (callMeBackData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond callMeBackData.errors, view:'create'
            return
        }

        callMeBackData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'callMeBackData.label', default: 'CallMeBackData'), callMeBackData.id])
                redirect callMeBackData
            }
            '*' { respond callMeBackData, [status: CREATED] }
        }
    }

    def edit(CallMeBackData callMeBackData) {
        respond callMeBackData
    }

    @Transactional
    def update(CallMeBackData callMeBackData) {
        if (callMeBackData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (callMeBackData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond callMeBackData.errors, view:'edit'
            return
        }

        callMeBackData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'callMeBackData.label', default: 'CallMeBackData'), callMeBackData.id])
                redirect callMeBackData
            }
            '*'{ respond callMeBackData, [status: OK] }
        }
    }

    @Transactional
    def delete(CallMeBackData callMeBackData) {

        if (callMeBackData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        callMeBackData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'callMeBackData.label', default: 'CallMeBackData'), callMeBackData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'callMeBackData.label', default: 'CallMeBackData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

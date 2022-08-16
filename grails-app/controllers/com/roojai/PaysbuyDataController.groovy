package com.roojai

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class PaysbuyDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		Integer max
        params.max = Math.min(max ?: 10, 100)
		params.sort="event_date"
		params.order="desc"
        respond PaysbuyData.list(params), model:[paysbuyDataCount: PaysbuyData.count()]
        */
		/*def data = PaysbuyData.createCriteria().list(){
			if(params.column_value){
				like(params.column_name,"%"+params.column_value+"%")
			}
			if(params.parameter_type){
				if(params.parameter_type != "all"){
					eq("parameter_type",params.parameter_type)
				}
			}
			order("event_date","desc")
		}
		List<PaysbuyData> list = new ArrayList<PaysbuyData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[paysbuyDataCount: data.size()]*/
		def hql = " from PaysbuyData where 1=1 "
		if(params.column_value){
			hql += " and "+params.column_name+" like '%${params.column_value}%' "
		}
		if(params.parameter_type){
			if(params.parameter_type != "all"){
				hql += " and parameter_type like '%${params.parameter_type}%' "
			}
		}
		
		def objCount = PaysbuyData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by event_date desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = PaysbuyData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[paysbuyDataCount: objCount]
    }
/*
	def search(){
		def data = PaysbuyData.createCriteria().list(params){
			if(params.column_value){
				like(params.column_name,"%"+params.column_value+"%")
			}
			if(params.parameter_type){
				if(params.parameter_type != "all"){
					eq("parameter_type",params.parameter_type)
				}
			}
			order("event_date","desc")
		}
		respond data, model:[paysbuyDataCount: data.size()], view:"index"
	}
*/
    def show(PaysbuyData paysbuyData) {
        respond paysbuyData
    }

    def create() {
        respond new PaysbuyData(params)
    }

    @Transactional
    def save(PaysbuyData paysbuyData) {
        if (paysbuyData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (paysbuyData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond paysbuyData.errors, view:'create'
            return
        }

        paysbuyData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'paysbuyData.label', default: 'PaysbuyData'), paysbuyData.id])
                redirect paysbuyData
            }
            '*' { respond paysbuyData, [status: CREATED] }
        }
    }

    def edit(PaysbuyData paysbuyData) {
        respond paysbuyData
    }

    @Transactional
    def update(PaysbuyData paysbuyData) {
        if (paysbuyData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (paysbuyData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond paysbuyData.errors, view:'edit'
            return
        }

        paysbuyData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'paysbuyData.label', default: 'PaysbuyData'), paysbuyData.id])
                redirect paysbuyData
            }
            '*'{ respond paysbuyData, [status: OK] }
        }
    }

    @Transactional
    def delete(PaysbuyData paysbuyData) {

        if (paysbuyData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        paysbuyData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'paysbuyData.label', default: 'PaysbuyData'), paysbuyData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'paysbuyData.label', default: 'PaysbuyData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

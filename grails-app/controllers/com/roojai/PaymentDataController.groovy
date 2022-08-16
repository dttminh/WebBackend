package com.roojai

import com.roojai.util.StatusUtil
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class PaymentDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		 * Integer max
        params.max = Math.min(max ?: 10, 100)
        respond PaymentData.list(params), model:[paymentDataCount: PaymentData.count()]
        */
		/*def data = PaymentData.createCriteria().list(){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			if(params.payment_type){
				eq("payment_type",params.payment_type)
			}
			order("created","desc")
		}
		List<PaymentData> list = new ArrayList<PaymentData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[paymentDataCount: data.size()]*/
		
		def hql = " from PaymentData where 1=1 "
		if(params.opportunityNumber){
			hql += " and opportunityNumber like '%${params.opportunityNumber}%' "
		}
		if(params.payment_type){
			hql += " and payment_type like '%${params.payment_type}%' "
		}
		def objCount = PaymentData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = PaymentData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[paymentDataCount: objCount]
    }
/*
	def search(){
		def data = PaymentData.createCriteria().list(params){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			if(params.payment_type){
				eq("payment_type",params.payment_type)
			}
			order("created","desc")
		}
		respond data, model:[paymentDataCount: data.size()], view:"index"
	}
*/	
    def show(PaymentData paymentData) {
		paymentData.json_input = StatusUtil.convertJsonToHtmlFormat(paymentData.json_input)
		paymentData.json_output = StatusUtil.convertJsonToHtmlFormat(paymentData.json_output)
        respond paymentData
    }

    def create() {
        respond new PaymentData(params)
    }

    @Transactional
    def save(PaymentData paymentData) {
        if (paymentData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (paymentData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond paymentData.errors, view:'create'
            return
        }

        paymentData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'paymentData.label', default: 'PaymentData'), paymentData.id])
                redirect paymentData
            }
            '*' { respond paymentData, [status: CREATED] }
        }
    }

    def edit(PaymentData paymentData) {
        respond paymentData
    }

    @Transactional
    def update(PaymentData paymentData) {
        if (paymentData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (paymentData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond paymentData.errors, view:'edit'
            return
        }

        paymentData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'paymentData.label', default: 'PaymentData'), paymentData.id])
                redirect paymentData
            }
            '*'{ respond paymentData, [status: OK] }
        }
    }

    @Transactional
    def delete(PaymentData paymentData) {

        if (paymentData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        paymentData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'paymentData.label', default: 'PaymentData'), paymentData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'paymentData.label', default: 'PaymentData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

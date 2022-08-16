package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

import com.roojai.util.StatusUtil;
@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class OmiseDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //respond OmiseData.list(params), model:[omiseDataCount: OmiseData.count()]
		/*def data = OmiseData.createCriteria().list(){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			order("created","desc")
		}
		List<OmiseData> list = new ArrayList<OmiseData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[omiseDataCount: data.size()]
		*/
		def hql = " from OmiseData where 1=1 "
		if(params.opportunityNumber){
			hql += " and opportunityNumber like '%${params.opportunityNumber}%' "
		}
		def objCount = OmiseData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = OmiseData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[omiseDataCount: objCount]
    }

    def show(OmiseData omiseData) {
		omiseData.jsonRequestCustomer = omiseData.jsonRequestCustomer ? StatusUtil.convertJsonToHtmlFormat(omiseData.jsonRequestCustomer):omiseData.jsonRequestCustomer
		omiseData.jsonResponseCustomer = omiseData.jsonResponseCustomer ? StatusUtil.convertJsonToHtmlFormat(omiseData.jsonResponseCustomer):omiseData.jsonResponseCustomer
		omiseData.jsonRequestCharge = omiseData.jsonRequestCharge ? StatusUtil.convertJsonToHtmlFormat(omiseData.jsonRequestCharge):omiseData.jsonRequestCharge
		omiseData.jsonResponseCharge = omiseData.jsonResponseCharge ? StatusUtil.convertJsonToHtmlFormat(omiseData.jsonResponseCharge):omiseData.jsonResponseCharge
		omiseData.kpiJsonRequestCustomer = omiseData.kpiJsonRequestCustomer ? StatusUtil.convertJsonToHtmlFormat(omiseData.kpiJsonRequestCustomer):omiseData.kpiJsonRequestCustomer
		omiseData.kpiJsonResponseCustomer = omiseData.kpiJsonResponseCustomer ? StatusUtil.convertJsonToHtmlFormat(omiseData.kpiJsonResponseCustomer):omiseData.kpiJsonResponseCustomer
		omiseData.kpiJsonRequestCharge = omiseData.kpiJsonRequestCharge ? StatusUtil.convertJsonToHtmlFormat(omiseData.kpiJsonRequestCharge):omiseData.kpiJsonRequestCharge
		omiseData.kpiJsonResponseCharge = omiseData.kpiJsonResponseCharge ? StatusUtil.convertJsonToHtmlFormat(omiseData.kpiJsonResponseCharge):omiseData.kpiJsonResponseCharge
        respond omiseData
    }

    def create() {
        respond new OmiseData(params)
    }

    @Transactional
    def save(OmiseData omiseData) {
        if (omiseData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (omiseData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond omiseData.errors, view:'create'
            return
        }

        omiseData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'omiseData.label', default: 'OmiseData'), omiseData.id])
                redirect omiseData
            }
            '*' { respond omiseData, [status: CREATED] }
        }
    }

    def edit(OmiseData omiseData) {
        respond omiseData
    }

    @Transactional
    def update(OmiseData omiseData) {
        if (omiseData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (omiseData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond omiseData.errors, view:'edit'
            return
        }

        omiseData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'omiseData.label', default: 'OmiseData'), omiseData.id])
                redirect omiseData
            }
            '*'{ respond omiseData, [status: OK] }
        }
    }

    @Transactional
    def delete(OmiseData omiseData) {

        if (omiseData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        omiseData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'omiseData.label', default: 'OmiseData'), omiseData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'omiseData.label', default: 'OmiseData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

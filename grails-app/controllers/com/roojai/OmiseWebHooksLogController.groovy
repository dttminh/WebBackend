package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import com.roojai.util.StatusUtil;

@Transactional(readOnly = true)
class OmiseWebHooksLogController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //respond OmiseWebHooksLog.list(params), model:[omiseWebHooksLogCount: OmiseWebHooksLog.count()]
		/*def data = OmiseWebHooksLog.createCriteria().list(){
			if(params.opportunityNumber){
				eq("opportunityNumber",params.opportunityNumber)
			}
			order("created","desc")
		}
		List<OmiseWebHooksLog> list = new ArrayList<OmiseWebHooksLog>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[omiseWebHooksLogCount: data.size()]*/
		
		def hql = " from OmiseWebHooksLog where 1=1 "
		if(params.opportunityNumber){
			hql += " and opportunityNumber like '%${params.opportunityNumber}%' "
		}
		def objCount = OmiseWebHooksLog.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = OmiseWebHooksLog.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[omiseWebHooksLogCount: objCount]
    }

    def show(OmiseWebHooksLog omiseWebHooksLog) {
		omiseWebHooksLog.webhooksData = omiseWebHooksLog.webhooksData ? StatusUtil.convertJsonToHtmlFormat(omiseWebHooksLog.webhooksData):omiseWebHooksLog.webhooksData
        respond omiseWebHooksLog
    }

    def create() {
        respond new OmiseWebHooksLog(params)
    }

    @Transactional
    def save(OmiseWebHooksLog omiseWebHooksLog) {
        if (omiseWebHooksLog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (omiseWebHooksLog.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond omiseWebHooksLog.errors, view:'create'
            return
        }

        omiseWebHooksLog.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'omiseWebHooksLog.label', default: 'OmiseWebHooksLog'), omiseWebHooksLog.id])
                redirect omiseWebHooksLog
            }
            '*' { respond omiseWebHooksLog, [status: CREATED] }
        }
    }

    def edit(OmiseWebHooksLog omiseWebHooksLog) {
        respond omiseWebHooksLog
    }

    @Transactional
    def update(OmiseWebHooksLog omiseWebHooksLog) {
        if (omiseWebHooksLog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (omiseWebHooksLog.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond omiseWebHooksLog.errors, view:'edit'
            return
        }

        omiseWebHooksLog.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'omiseWebHooksLog.label', default: 'OmiseWebHooksLog'), omiseWebHooksLog.id])
                redirect omiseWebHooksLog
            }
            '*'{ respond omiseWebHooksLog, [status: OK] }
        }
    }

    @Transactional
    def delete(OmiseWebHooksLog omiseWebHooksLog) {

        if (omiseWebHooksLog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        omiseWebHooksLog.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'omiseWebHooksLog.label', default: 'OmiseWebHooksLog'), omiseWebHooksLog.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'omiseWebHooksLog.label', default: 'OmiseWebHooksLog'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import com.roojai.util.StatusUtil;

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class SaleforceDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		Integer max
        params.max = Math.min(max ?: 10, 100)
        respond SaleforceData.list(params), model:[saleforceDataCount: SaleforceData.count()]
		*/
		/*def data =  SaleforceData.createCriteria().list(){
			if(params.column_value){
				eq(params.column_name,params.column_value)
			}
			order("created","desc")
		}
		List<SaleforceData> list = new ArrayList<SaleforceData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}*/
		
		def hql = " from SaleforceData where 1=1 "
		if(params.column_value){
			hql += " and "+params.column_name+" like '%${params.column_value}%' "
		}
		def objCount = SaleforceData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by created desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = SaleforceData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[saleforceDataCount: objCount]
    }
/*	
	def search(){
		def data =  SaleforceData.createCriteria().list(params){
			if(params.column_value){
				eq(params.column_name,params.column_value)
			}
			order("created","desc")
		}
		respond data, model:[saleforceDataCount: data.size()], view:"index"
	}
*/
    def show(SaleforceData saleforceData) {
		saleforceData.json_input = StatusUtil.convertJsonToHtmlFormat(saleforceData.json_input)
		saleforceData.output = StatusUtil.convertJsonToHtmlFormat(saleforceData.output)
		respond saleforceData
    }

    def create() {
        respond new SaleforceData(params)
    }

    @Transactional
    def save(SaleforceData saleforceData) {
        if (saleforceData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (saleforceData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond saleforceData.errors, view:'create'
            return
        }

        saleforceData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'saleforceData.label', default: 'SaleforceData'), saleforceData.id])
                redirect saleforceData
            }
            '*' { respond saleforceData, [status: CREATED] }
        }
    }

    def edit(SaleforceData saleforceData) {
        respond saleforceData
    }

    @Transactional
    def update(SaleforceData saleforceData) {
        if (saleforceData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (saleforceData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond saleforceData.errors, view:'edit'
            return
        }

        saleforceData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'saleforceData.label', default: 'SaleforceData'), saleforceData.id])
                redirect saleforceData
            }
            '*'{ respond saleforceData, [status: OK] }
        }
    }

    @Transactional
    def delete(SaleforceData saleforceData) {

        if (saleforceData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        saleforceData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'saleforceData.label', default: 'SaleforceData'), saleforceData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'saleforceData.label', default: 'SaleforceData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	def download(){
		Long id = params.long("id")
		String field = params.field
		SaleforceData object = SaleforceData.get(id)
		if( object ){
			response.setContentType("APPLICATION/OCTET-STREAM")
			response.setHeader("Content-Disposition", "Attachment;Filename="+object.opportunity_number+"_"+field+".txt");
			def outputStream = response.getOutputStream()

            outputStream << field.equals("output") ? StatusUtil.convertJsonToFileformat(object.output).getBytes("UTF-8") : StatusUtil.convertJsonToFileformat(object.json_input).getBytes("UTF-8")
			
			outputStream.flush()
			outputStream.close()
			render "";
		}
	}

}

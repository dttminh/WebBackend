package com.roojai

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import com.roojai.util.StatusUtil;

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class RadarDataController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		/*
		Integer max
        params.max = Math.min(max ?: 10, 100)
        respond RadarData.list(params), model:[radarDataCount: RadarData.count()]
        */
		/*def data =  RadarData.createCriteria().list(){
			if(params.column_value){
				if(params.column_name == "ownerIs" || params.column_name == "carYear" || params.column_name == "age" || params.column_name == "lastAccident"){
					eq(params.column_name,params.int("column_value"))
				}else{
					eq(params.column_name,params.column_value)
				}
			}
			order("createDate","desc")
		}
		List<RadarData> list = new ArrayList<RadarData>();
		int start = params.offset ? params.int("offset"):0
		int end = start+10 > data.size() ? data.size():start+10
		for(int i=start;i<end;i++){
			list.add(data.get(i))
		}
		respond list , model:[radarDataCount: data.size()]*/
		def hql = " from RadarData where 1=1 "
		if(params.column_value){
			hql += " and "+params.column_name+" like '%${params.column_value}%' "
		}
		def objCount = RadarData.executeQuery("select count(*) "+ hql)[0]
		// order by
		hql +=" order by createDate desc "
		int offset = params.offset ? params.int("offset"):0
		def objList = RadarData.executeQuery(hql,[offset:offset, max:10])
		
		respond objList , model:[radarDataCount: objCount]
    }
/*	
	def search(){
		def data =  RadarData.createCriteria().list(params){
			if(params.column_value){
				if(params.column_name == "ownerIs" || params.column_name == "carYear" || params.column_name == "age" || params.column_name == "lastAccident"){
					eq(params.column_name,params.int("column_value"))
				}else{
					eq(params.column_name,params.column_value)
				}
			}
			order("createDate","desc")
		}
		respond data, model:[radarDataCount: data.size()], view:"index"
	}
*/	
    def show(RadarData radarData) {
		radarData.xmlInput = StatusUtil.convertXMLToHtmlFormat(radarData.xmlInput)
		radarData.xmlData = StatusUtil.convertXMLToHtmlFormat(radarData.xmlData)
        respond radarData
    }

    def create() {
        respond new RadarData(params)
    }

    @Transactional
    def save(RadarData radarData) {
        if (radarData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (radarData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond radarData.errors, view:'create'
            return
        }

        radarData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'radarData.label', default: 'RadarData'), radarData.id])
                redirect radarData
            }
            '*' { respond radarData, [status: CREATED] }
        }
    }

    def edit(RadarData radarData) {
        respond radarData
    }

    @Transactional
    def update(RadarData radarData) {
        if (radarData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (radarData.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond radarData.errors, view:'edit'
            return
        }

        radarData.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'radarData.label', default: 'RadarData'), radarData.id])
                redirect radarData
            }
            '*'{ respond radarData, [status: OK] }
        }
    }

    @Transactional
    def delete(RadarData radarData) {

        if (radarData == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        radarData.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'radarData.label', default: 'RadarData'), radarData.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
	
	def download(){
		Long id = params.long("id")
		String field = params.field
		RadarData object = RadarData.get(id)
		if( object ){
			response.setContentType("APPLICATION/OCTET-STREAM")
			response.setHeader("Content-Disposition", "Attachment;Filename="+field+".txt");
			
			def outputStream = response.getOutputStream()

            outputStream << field.equals("output") ? object.xmlData : object.xmlInput
			
			outputStream.flush()
			outputStream.close()
			render "";
		}
	}

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'radarData.label', default: 'RadarData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

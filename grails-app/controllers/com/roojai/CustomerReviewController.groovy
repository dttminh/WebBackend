package com.roojai

import static org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.annotation.Secured;
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class CustomerReviewController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CustomerReview.list(params), model:[customerReviewCount: CustomerReview.count()]
    }

    def show(CustomerReview customerReview) {
        respond customerReview
    }

    def create() {
        respond new CustomerReview(params)
    }

    @Transactional
    def save(CustomerReview customerReview) {
        if (customerReview == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (customerReview.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond customerReview.errors, view:'create'
            return
        }

        customerReview.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'customerReview.label', default: 'CustomerReview'), customerReview.id])
                redirect customerReview
            }
            '*' { respond customerReview, [status: CREATED] }
        }
    }

    def edit(CustomerReview customerReview) {
        respond customerReview
    }

    @Transactional
    def update(CustomerReview customerReview) {
        if (customerReview == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (customerReview.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond customerReview.errors, view:'edit'
            return
        }

        customerReview.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'customerReview.label', default: 'CustomerReview'), customerReview.id])
                redirect customerReview
            }
            '*'{ respond customerReview, [status: OK] }
        }
    }

    @Transactional
    def delete(CustomerReview customerReview) {

        if (customerReview == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        customerReview.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'customerReview.label', default: 'CustomerReview'), customerReview.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerReview.label', default: 'CustomerReview'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	@Secured(['permitAll'])
	def addCustomerReview(){
		def customerReview = new CustomerReview(params)
		customerReview.save(flush:true)
		render "sucess"
	}
	
	@Secured(['permitAll'])
	def loadMoreData(){
		
	}
}

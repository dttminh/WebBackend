package com.roojai

import grails.gorm.transactions.Transactional

import org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)	
@Secured(['ROLE_ADMIN'])
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userCount: User.count()]
    }

    def show(User user) {
        respond user
    }

    def create() {
        respond new User(params)
    }

    @Transactional
    def save(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(User user) {
        respond user
    }

    @Transactional
    def update(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    @Transactional
    def delete(User user) {

        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        user.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def springSecurityService
	def changePassword(){
		def username = springSecurityService.principal.username
		User user = User.findByUsername(username)
		respond user
	}
	
	def updatePassword(User user){
		if (user != null) {
			int passwordLength = user.password.length()
			String varidateNumber = user.password.replaceAll("[^0-9]", "");
			String varidateString = user.password.replaceAll("[^a-z,^A-Z]", "");
			if(passwordLength >= 8 && !varidateNumber.equals("") && !varidateString.equals("")){
				user.save flush:true
				flash.message = "Complete"
				redirect(controller: "backOffice", action: "index")
			}else{
				flash.message = "Password must be at least 8 characters long contain a number."
				respond user ,view:"changePassword"
			}
		}else{
			flash.message = "Please check username or password"
			respond user ,view:"changePassword"
			//render view:"changePassword"
		}
		
	}
}

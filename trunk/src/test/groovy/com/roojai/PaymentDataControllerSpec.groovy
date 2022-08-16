package com.roojai

import grails.test.mixin.*
import spock.lang.*

@TestFor(PaymentDataController)
@Mock(PaymentData)
class PaymentDataControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.paymentDataList
            model.paymentDataCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.paymentData!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def paymentData = new PaymentData()
            paymentData.validate()
            controller.save(paymentData)

        then:"The create view is rendered again with the correct model"
            model.paymentData!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            paymentData = new PaymentData(params)

            controller.save(paymentData)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/paymentData/show/1'
            controller.flash.message != null
            PaymentData.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def paymentData = new PaymentData(params)
            controller.show(paymentData)

        then:"A model is populated containing the domain instance"
            model.paymentData == paymentData
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def paymentData = new PaymentData(params)
            controller.edit(paymentData)

        then:"A model is populated containing the domain instance"
            model.paymentData == paymentData
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/paymentData/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def paymentData = new PaymentData()
            paymentData.validate()
            controller.update(paymentData)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.paymentData == paymentData

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            paymentData = new PaymentData(params).save(flush: true)
            controller.update(paymentData)

        then:"A redirect is issued to the show action"
            paymentData != null
            response.redirectedUrl == "/paymentData/show/$paymentData.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/paymentData/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def paymentData = new PaymentData(params).save(flush: true)

        then:"It exists"
            PaymentData.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(paymentData)

        then:"The instance is deleted"
            PaymentData.count() == 0
            response.redirectedUrl == '/paymentData/index'
            flash.message != null
    }
}

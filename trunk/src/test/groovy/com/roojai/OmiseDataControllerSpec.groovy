package com.roojai

import grails.test.mixin.*
import spock.lang.*

@TestFor(OmiseDataController)
@Mock(OmiseData)
class OmiseDataControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.omiseDataList
            model.omiseDataCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.omiseData!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def omiseData = new OmiseData()
            omiseData.validate()
            controller.save(omiseData)

        then:"The create view is rendered again with the correct model"
            model.omiseData!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            omiseData = new OmiseData(params)

            controller.save(omiseData)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/omiseData/show/1'
            controller.flash.message != null
            OmiseData.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def omiseData = new OmiseData(params)
            controller.show(omiseData)

        then:"A model is populated containing the domain instance"
            model.omiseData == omiseData
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def omiseData = new OmiseData(params)
            controller.edit(omiseData)

        then:"A model is populated containing the domain instance"
            model.omiseData == omiseData
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/omiseData/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def omiseData = new OmiseData()
            omiseData.validate()
            controller.update(omiseData)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.omiseData == omiseData

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            omiseData = new OmiseData(params).save(flush: true)
            controller.update(omiseData)

        then:"A redirect is issued to the show action"
            omiseData != null
            response.redirectedUrl == "/omiseData/show/$omiseData.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/omiseData/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def omiseData = new OmiseData(params).save(flush: true)

        then:"It exists"
            OmiseData.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(omiseData)

        then:"The instance is deleted"
            OmiseData.count() == 0
            response.redirectedUrl == '/omiseData/index'
            flash.message != null
    }
}

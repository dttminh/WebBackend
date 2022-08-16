package com.roojai

import grails.test.mixin.*
import spock.lang.*

@TestFor(RetrieveDataController)
@Mock(RetrieveData)
class RetrieveDataControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.retrieveDataList
            model.retrieveDataCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.retrieveData!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def retrieveData = new RetrieveData()
            retrieveData.validate()
            controller.save(retrieveData)

        then:"The create view is rendered again with the correct model"
            model.retrieveData!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            retrieveData = new RetrieveData(params)

            controller.save(retrieveData)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/retrieveData/show/1'
            controller.flash.message != null
            RetrieveData.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def retrieveData = new RetrieveData(params)
            controller.show(retrieveData)

        then:"A model is populated containing the domain instance"
            model.retrieveData == retrieveData
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def retrieveData = new RetrieveData(params)
            controller.edit(retrieveData)

        then:"A model is populated containing the domain instance"
            model.retrieveData == retrieveData
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/retrieveData/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def retrieveData = new RetrieveData()
            retrieveData.validate()
            controller.update(retrieveData)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.retrieveData == retrieveData

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            retrieveData = new RetrieveData(params).save(flush: true)
            controller.update(retrieveData)

        then:"A redirect is issued to the show action"
            retrieveData != null
            response.redirectedUrl == "/retrieveData/show/$retrieveData.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/retrieveData/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def retrieveData = new RetrieveData(params).save(flush: true)

        then:"It exists"
            RetrieveData.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(retrieveData)

        then:"The instance is deleted"
            RetrieveData.count() == 0
            response.redirectedUrl == '/retrieveData/index'
            flash.message != null
    }
}

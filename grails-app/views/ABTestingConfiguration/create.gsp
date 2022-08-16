<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ABTestingConfiguration.label', default: 'ABTestingConfiguration')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-ABTestingConfiguration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-ABTestingConfiguration" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.ABTestingConfiguration}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.ABTestingConfiguration}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <!-- <f:all bean="ABTestingConfiguration"/>-->
           			<div class="fieldcontain required">
					  <label for="testingPercent">Testing Percent
					    <span class="required-indicator">*</span>
					  </label><input type="number" name="testingPercent" value="0" required="" id="testingPercent" min="0" max="100">
					</div>
					<div class="fieldcontain required">
					  <label for="returnValue">Return Value
					    <span class="required-indicator">*</span>
					  </label><input type="text" name="returnValue" value="" required="" id="returnValue">
					</div>
					<div class="fieldcontain required">
					  <label for="testingVersion">Testing Version
					    <span class="required-indicator">*</span>
					  </label><input type="text" name="testingVersion" value="" required="" id="testingVersion">
					</div>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>

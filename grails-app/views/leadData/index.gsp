<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'leadData.label', default: 'LeadData')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-leadData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-leadData" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="form">
			    <g:form  url="[action:'index',controller:'LeadData']" method="GET">
			    	<div class="fieldcontain">
			            <label for="firstName">First Name:</label>
			            <g:textField name="firstName" value="${params.firstName}"/>
			        </div>
			        <div class="fieldcontain">
			            <label for="lastName">Last Name:</label>
			            <g:textField name="lastName" value="${params.lastName}"/>
			        </div>
			    	<div class="fieldcontain">
			            <label for="phone">Phone Number:</label>
			            <g:textField name="phone" value="${params.phone}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label>&nbsp;</label>
			        	<g:submitButton name="search" value="Search" />
			        </div>
			    </g:form>
			</fieldset>
            <f:table collection="${leadDataList}" />

            <div class="pagination">
                <g:paginate total="${leadDataCount ?: 0}" params="[phone:params.phone,firstName:params.firstName,lastName:params.lastName]"/>
            </div>
        </div>
    </body>
</html>
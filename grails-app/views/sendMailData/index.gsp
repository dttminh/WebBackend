<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sendMailData.label', default: 'SendMailData')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-sendMailData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-sendMailData" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="form">
			    <g:form  url="[action:'index',controller:'SendMailData']" method="GET">
			    	<div class="fieldcontain">
			            <label for="opportunityNumber">Opportunity Number:</label>
			            <g:textField name="opportunityNumber" value="${params.opportunityNumber}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="messageType">Message Type:</label>
			            <g:textField name="messageType" value="${params.messageType}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="method">Method:</label>
			            <g:textField name="method" value="${params.method}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="customerName">Customer Name:</label>
			            <g:textField name="customerName" value="${params.customerName}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="phoneNumber">Phone Number:</label>
			            <g:textField name="phoneNumber" value="${params.phoneNumber}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="email">Email:</label>
			            <g:textField name="email" value="${params.email}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="status">Status:</label>
			            <g:textField name="status" value="${params.status}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label>&nbsp;</label>
			        	<g:submitButton name="search" value="Search" />
			        </div>
			    </g:form>
			</fieldset>
            <f:table collection="${sendMailDataList}" />

            <div class="pagination">
                <g:paginate total="${sendMailDataCount ?: 0}" params="[opportunityNumber:params.opportunityNumber,messageType:params.messageType,method:params.method,customerName:params.customerName,phoneNumber:params.phoneNumber,email:params.email,status:params.status]"/>
            </div>
        </div>
    </body>
</html>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rateUsData.label', default: 'RateUsData')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-rateUsData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-rateUsData" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            
            <fieldset class="form">
			    <g:form  url="[action:'index',controller:'RateUsData']" method="GET">
			    	<div class="fieldcontain">
			            <label for="opportunityNumber">Opportunity Number:</label>
			            <g:textField name="opportunityNumber" value="${params.opportunityNumber}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="socialMediaFName">First Name:</label>
			            <g:textField name="socialMediaFName" value="${params.socialMediaFName}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="socialMediaLName">Last Name:</label>
			            <g:textField name="socialMediaLName" value="${params.socialMediaLName}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label>&nbsp;</label>
			        	<g:submitButton name="search" value="Search" />
			        </div>
			    </g:form>
			</fieldset>
            <f:table collection="${rateUsDataList}" />
			
            <div class="pagination">
                <g:paginate total="${rateUsDataCount ?: 0}" params="[opportunityNumber:params.opportunityNumber,socialMediaFName:params.socialMediaFName,socialMediaLName:params.socialMediaLName]"/>
            </div>
        </div>
    </body>
</html>
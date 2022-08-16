<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'systemconfiguration.label', default: 'Systemconfiguration')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <asset:javascript src="application.js"/>
        <asset:stylesheet src="application.css"/>
    </head>
    <body>
        <a href="#list-systemconfiguration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-systemconfiguration" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="form">
			    <g:form  url="[action:'index',controller:'Systemconfiguration']" method="GET">
			    	<div class="fieldcontain">
			            <label for="name">Name:</label>
			            <g:textField name="name" value="${params.name}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="category">Category:</label>
			            <g:textField name="category" value="${params.category}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label>&nbsp;</label>
			        	<g:submitButton name="search" value="Search" />
			        </div>
			    </g:form>
			</fieldset>
            <f:table collection="${systemconfigurationList}" properties="['name','category','value','description']"/>

            <div class="pagination">
                <g:paginate  total="${systemconfigurationCount ?: 0}" params="[name:params.name,category:params.category]"/>
            </div>
        </div>
    </body>
</html>
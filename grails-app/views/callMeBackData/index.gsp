<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'callMeBackData.label', default: 'CallMeBackData')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-callMeBackData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-callMeBackData" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="form">
            	<g:form  url="[action:'index',controller:'CallMeBackData']" method="GET">
            		<div class="fieldcontain">
					  <label for="customerName">Customer Name
					  </label><input type="text" name="customerName" value="${params.customerName}" id="customerName">
					</div>
					<div class="fieldcontain">
					  <label for="phoneNumber">Phone Number
					  </label><input type="text" name="phoneNumber" value="${params.phoneNumber}" id="phoneNumber">
					</div>
					<div class="fieldcontain">
			        	<label>&nbsp;</label>
			        	<g:submitButton name="search" value="Search" />
			        </div>
            	</g:form>
            </fieldset>
            <f:table collection="${callMeBackDataList}" />

            <div class="pagination">
                <%--<g:paginate total="${callMeBackDataCount ?: 0}" />--%>
                <g:paginate total="${callMeBackDataCount ?: 0}" params="[customerName:params.customerName,phoneNumber:params.phoneNumber]"/>
            </div>
        </div>
    </body>
</html>
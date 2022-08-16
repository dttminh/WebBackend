<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'saleforceData.label', default: 'SaleforceData')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-saleforceData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-saleforceData" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="form">
			    <g:form  url="[action:'index',controller:'SaleforceData']" method="GET">
			    	<div class="fieldcontain">
			            <label for="column_name">Column Name:</label>
			            <g:select name="column_name" value="${params.column_name}" from="${['masterset_id',	'opportunity_number',	'send_email_flag',	'isupdate',	'call_me_back',	'json_input',	'output']}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label for="column_value">Value:</label>
			            <g:textField name="column_value" value="${params.column_value}"/>
			        </div>
			        <div class="fieldcontain">
			        	<label>&nbsp;</label>
			        	<g:submitButton name="search" value="Search" />
			        </div>
			    </g:form>
			</fieldset>
            <f:table collection="${saleforceDataList}" properties="['opportunity_number','masterset_id','created','json_input']"/>

            <div class="pagination">
                <g:paginate total="${saleforceDataCount ?: 0}" params="[column_name:params.column_name,column_value:params.column_value]"/>
            </div>
        </div>
    </body>
</html>
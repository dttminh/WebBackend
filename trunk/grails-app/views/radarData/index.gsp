<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'radarData.label', default: 'RadarData')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-radarData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-radarData" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="form">
			    <g:form  url="[action:'index',controller:'RadarData']" method="GET">
			    	<div class="fieldcontain">
			            <label for="column_name">Column Name:</label>
			            <g:select name="column_name" value="${params.column_name}" from="${['opportunityNumber','ownerIs',	'carBrand',	'carModel',	'carYear',	'carDesc',	'gender',	'age',	'drivingExp',	'lastAccident',	'carFromHomeToWork',	'carInTheCourseOfWork',	'carUsageType',	'declareNCB',	'howlongbeeninsured',	'masterSetId']}"/>
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
            <f:table collection="${radarDataList}" properties="['opportunityNumber','carBrand','carModel','carYear','carDesc','gender','age','masterSetId','createDate']"/>

            <div class="pagination">
                <g:paginate total="${radarDataCount ?: 0}" params="[column_name:params.column_name,column_value:params.column_value]"/>
            </div>
        </div>
    </body>
</html>
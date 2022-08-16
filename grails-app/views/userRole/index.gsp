<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userRole.label', default: 'UserRole')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-userRole" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/backOffice/index')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-userRole" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
             <table>
				<thead>
			         <tr>
			                <th class="sortable">User Id</th>
			                <th class="sortable">Role Id</th>
			        </tr>
			    </thead>
				<g:each in="${userRoleList}">
			    	<tr>
			    		<td><a href="">${it.user.username}</a></td>
			    		<td>${it.role.authority}</td>
			    	</tr>
				</g:each>
			</table>
            <div class="pagination">
                <g:paginate total="${userRoleCount ?: 0}" />
            </div>
        </div>
    </body>
</html>
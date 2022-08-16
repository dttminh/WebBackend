<html>
<head>
	<meta name="layout" content="main"/>
	<title>Administrator</title>
</head>
<body>
	<g:if test="${flash.message}">
    	<div class="message" role="status">${flash.message}</div>
    </g:if>
	<g:link url="[action:'index',controller:'Systemconfiguration']">Systemconfiguration</g:link>
	<br>
	<g:link url="[action:'index',controller:'PaysbuyData']">PaysbuyData</g:link>
	<br>
	<g:link url="[action:'index',controller:'PaymentData']">PaymentData</g:link>
	<br>
	<g:link url="[action:'index',controller:'RadarData']">RadarData</g:link>
	<br>
	<g:link url="[action:'index',controller:'SaleforceData']">SaleforceData</g:link>
	<br/>
	<g:link url="[action:'index',controller:'SendMailData']">SendMailData</g:link>
	<br/>
	<g:link url="[action:'index',controller:'RateUsData']">RateUsData</g:link>
	<br/>
	<g:link url="[action:'index',controller:'CallMeBackData']">CallMeBackData</g:link>
	<br/>
	<g:link url="[action:'index',controller:'OmiseData']">OmiseData</g:link>
	<br/>
	<g:link url="[action:'index',controller:'OmiseWebHooksLog']">OmiseWebHooksLog</g:link>
	<br/>
	<g:link url="[action:'index',controller:'RetrieveData']">RetrieveData</g:link>
	<br/>
	<g:link url="[action:'index',controller:'LeadData']">LeadData</g:link>
	<br/>
	<g:link url="[action:'index',controller:' AccessLog']">AccessLog</g:link>
	<br/>
	<g:link url="[action:'index',controller:'Role']">Role</g:link>
	<br/>
	<g:link url="[action:'index',controller:'User']">User</g:link>
	<br/>
	<g:link url="[action:'index',controller:'userRole']">UserRole</g:link>
	<br/>
	<g:link url="[action:'index',controller:'ABTestingConfiguration']">ABTestingConfiguration</g:link>
	<br/>
	<g:link url="[action:'index',controller:'ABTestingCounter']">ABTestingCounter</g:link>
	<br/>
	<g:link url="[action:'viewLog',controller:'backOffice']">view Application Log</g:link>
	<br/>
	<g:link url="[action:'viewServerLog',controller:'backOffice']">view Server Log</g:link>
	<br/>
	<g:link url="[action:'clearCache',controller:'backOffice']">Flush All Cache</g:link>
	<br/>
	<g:link url="[action:'clearSalesforceToken',controller:'backOffice']">Clear Salesforce Token</g:link>
	<br/>
	<g:link url="[action:'changePassword',controller:'User']">Change Your Password</g:link>
	<br/>
	<g:link url="[controller:'Monitoring']">Monitoring Java</g:link>
	<br/>
	<g:link url="[controller:'AuthenticationToken']">Authentication Token</g:link>
	<br/>
	<g:link url="[action:'generateRetriveUrl']">generateRetriveUrl</g:link>
	<br/>
	<g:link url="[action:'index',controller:'cache']">Caching Manager</g:link>
	<br/>
	<g:link url="[controller:'logout']">Logout</g:link>

</body>
</html>
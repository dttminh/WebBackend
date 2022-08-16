<html>
<head>
<!-- <meta name="layout" content="quotation" />-->
<meta name="layout" content="main"/>
</head>
<body>
	<g:form class="simpleform" url="[controller:'BackOffice',action:'login']">
		<fieldset>
			<legend>Login</legend>
			<p class="info">
				Please login with your username and password. <br /> Don't have an account?
				<g:link controller="user" action="register">Sign up now!</g:link>
			</p>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<p>
				<label for="username">Username</label>
				<g:textField name="login" />
			</p>
			<p>
				<label for="password">Password</label>
				<g:passwordField name="password" />
			</p>
			<p class="button">
				<label>&nbsp;</label>
				<g:submitButton class="button" name="submitButton" value="Login" />
			</p>
		</fieldset>
	</g:form>
</body>
</html>
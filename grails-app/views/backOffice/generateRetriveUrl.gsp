<html>
<head>
	<meta name="layout" content="main"/>
	<title>Administrator</title>
</head>
<body>
	<g:if test="${flash.message}">
    	<div class="message" role="status">${flash.message}</div>
    </g:if>
	<g:form action="generateRetriveUrl" name="myForm">
         <fieldset class="form">
               Quotation Number :<g:field type="text" size="100" name="qoutationNumber" min="5" max="10" required="" value="${params.qoutationNumber}"/>
          </fieldset>
          <fieldset class="buttons">
               <input type="button" value="Generate Retrive Url" onclick="selectMethod('genRetriveUrl')">
               <input type="button" value="Decrypt Qoutation" onclick="selectMethod('decryptQoutation')"/>
          </fieldset>
          <g:hiddenField name="method" value="retrive" />
    </g:form>
    <g:if test="${results}">
    	<g:if test="${method=='genRetriveUrl'}">
	    	<g:each in="${results}">
			    <p><a href="${it}">${it}</a></p>
			</g:each>
		</g:if>
		<g:else>
			Decrypt Qoutation Number : ${results[0]}
		</g:else>
    </g:if>
    <script>
    	function selectMethod(method){
        	$('#method').val(method);3
        	$('#myForm').submit();
        }
    </script>
</body>
</html>
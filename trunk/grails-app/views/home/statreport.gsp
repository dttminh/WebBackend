<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<%--<meta name="layout" content="main"/>--%>
<title>Stat Report</title>
</head>
<body>
  	<table border=1>
		<tr bgcolor="#A0D8F1"><td colspan="4">Over All</td></tr>
		<tr><td> Quote</td><td>Credit</td><td>OnlineBank</td><td>Cash</td></tr>
		<tr align="center"><td>${overAllQuote}</td><td>${overAllCredit}</td><td>${overAllOnlineBank}</td><td>${overAllCash}</td></tr>
	</table>
	<br/>
	<table border=1>
		<tr bgcolor="#A0D8F1"><td colspan="4">Last 7 day</td></tr>
		<tr><td> Quote</td><td>Credit</td><td>OnlineBank</td><td>Cash</td></tr>
		<tr align="center"><td>${last7dayQuote}</td><td>${creditLast7day}</td><td>${onlineBankLast7day}</td><td>${cashLast7day}</td></tr>
	</table>
	<br/>
	<table border=1>
		<tr bgcolor="#A0D8F1"><td colspan="4">Last day</td></tr>
		<tr><td> Quote</td><td>Credit</td><td>OnlineBank</td><td>Cash</td></tr>
		<tr align="center"><td>${todayQuote}</td><td>${creditLastToday}</td><td>${onlineBankLastToday}</td><td>${cashLastToday}</td></tr>
	</table>
</body>
</html>
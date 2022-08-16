<%--<table border=1>
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
--%>
<table border=1>
	<tr bgcolor="#A0D8F1"><td colspan="4">Today ${new Date()}</td></tr>
	<tr><td> Quote(TBA/Oppotunity) </td><td>Credit(Success/Failed)</td><td>OnlineBank(Success/Failed)</td><td>Cash(Request/Success)</td></tr>
	<tr align="center"><td>${todayTBAQuote}/${todayQuote}</td><td>${successCredit}/${failedCredit}</td><td>${successInetBank}/${failedInetBank}</td><td>${requestCash}/${successCash}</td></tr>
</table>
<!--  
<g:link controller="home" action="last7day">Last 7 day</g:link>
-->
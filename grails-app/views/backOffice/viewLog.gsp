<%@ page import="grails.util.Environment" %>
<%@ page import="com.roojai.util.ConstantUtil" %>
<%@ page import="java.io.*" %>
<% 
String userHome = ConstantUtil.getAppLogPath();
out.println("Log Path:"+userHome+"<hr/>");

File f = new File(userHome); // current directory

File[] files = f.listFiles();
for (File file : files) {
	if (file.isDirectory()) {
		//System.out.print("directory:");
	} else {
		%>
			<%=file.getName() %> <a href="${createLink(uri: '/backOffice/viewLog')}?f=<%=file.getName()%>&cmd=view">view</a> |
			<a href="${createLink(uri: '/backOffice/viewLog')}?f=<%=file.getName()%>&cmd=download">download</a><br/>
	<%
	}
}
String fileName = request.getParameter("f");
String cmd = request.getParameter("cmd");
if( cmd.equals("view") ) {
	 println "<hr/>";
	 //println fileName
	 BufferedReader reader = new BufferedReader(new FileReader(userHome+fileName));
	 StringBuilder sb = new StringBuilder();
	 String line;
	 while((line = reader.readLine())!= null){
		 sb.append(line+"<br/>");
	 }
	 out.println(sb.toString());
}
%>

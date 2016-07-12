<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%
	String contextPath = this.getServletContext().getContextPath();
%><head>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="mobile-web-app-capable" content="yes">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/handheld.css"
	media="only screen and (max-device-width: 6440px)" />
<title>MastekPark</title>
</head>
<body>

	<p>

		<%
			if (request.getAttribute("message") != null) {
		%>
		<%=request.getAttribute("message")%>
		<%
			}
		%>

	</p>
	<br />

	<form action="<%=contextPath%>/Main/doLogin" method="post">
		<p>Email Address:</p>
		<p>
			<input type="text" name="username" value="">
		</p>
		<p>Password:</p>
		<p>
			<input type="password" name="password" value="">
		</p>
		<button class="fsSubmitButton" type="submit">Login</button>
		<p>
			<img
				src="/ParkMe/QRCode?string=http://<%=InetAddress.getLocalHost().getHostAddress()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/Main" />
		</p>
	</form>
</body>
</html>
<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String contextPath = this.getServletContext().getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/handheld.css"
	media="only screen and (max-device-width: 6440px)" />

<title>MastekPark</title>
</head>
<body>
	<h1>Oops!</h1>
	<a href="<%=contextPath%>/Main/showAllocations"><button
			class="fsSubmitButton fsButtonBlue" type="submit">Click here
			to continue</button></a>
</body>
</html>
<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MastekPark</title>
</head>
<body>

<%



%>
	<img src="/CarParkApp/QRCode?string=http://<%=InetAddress.getLocalHost().getHostAddress()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/Main" />
</body>
</html>
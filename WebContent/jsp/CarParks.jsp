<%@page import="com.cullumg.carpark.data.CarPark"%>
<%@page import="com.cullumg.carpark.data.Space"%>
<%@page import="com.cullumg.carpark.dataaccess.UserManager"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cullumg.carpark.data.Request"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.cullumg.carpark.data.Allocation"%>
<%@ page import="com.cullumg.carpark.data.User"%>


<%
	String contextPath = this.getServletContext().getContextPath();
	User theUser = (User) request.getSession().getAttribute("user");
	
	@SuppressWarnings("unchecked")
	List<CarPark> carParkList = (List<CarPark>)request.getSession().getAttribute("carparklist");
		
%>
<!DOCTYPE html>
<html>
<head>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="mobile-web-app-capable" content="yes">

<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/handheld.css"
	media="only screen and (max-device-width: 6440px)" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Space List</title>
</head>
<body onload="StartTime();">
<% if (theUser.isAdmin()==1) { %>
	<table>
		<tr>
			<td>
				<form method="post" action="<%=contextPath%>/Main/listUsers">
					<button class="fsSubmitButton fsButtonSmall" type="submit">Users</button>
				</form>
			</td>
			<td>
				<form method="post" action="<%=contextPath%>/Main/listCarParks">
					<button disabled class="fsSubmitButton fsButtonSmall" type="submit">Car Parks</button>
				</form>
			</td>
			<td>
				<form method="post" action="<%=contextPath%>/Main/listSpaces">
					<button class="fsSubmitButton fsButtonSmall" type="submit">Spaces</button>
				</form>
			</td>
			<td>
				<form method="post" action="<%=contextPath%>/Main/showAllocations">
					<button class="fsSubmitButton fsButtonSmall" type="submit">Allocations</button>
				</form>
			</td>
		</tr>
	</table>
<% } %>
			<p>Car Park List<p>

		<table>

<%
			if (carParkList!=null) {
				Iterator<CarPark> it = carParkList.iterator();
				while (it.hasNext()) {
					CarPark thisCarPark = (CarPark) it.next();
%>
			<tr>
				<td><p>
						<%=thisCarPark.getCarparkname()%></p></td>
				<td>
					<form method="post" action="<%=contextPath%>/Main/showCarParkForm">
						<input type="hidden" name="method" value="edit"/>
						<input type="hidden" name="carparkid"
							value="<%=thisCarPark.getCarparkid()%>" />
						<button class="fsSubmitButton fsSubmitButtonGreen" type="submit">Edit</button>
					</form> 
				</td>
				<td>
					<form method="post" action="<%=contextPath%>/Main/deleteCarPark">
						<input type="hidden" name="carparkid"
							value="<%=thisCarPark.getCarparkid()%>" />
						<button class="fsSubmitButton fsSubmitButtonRed" type="submit">Delete</button>
					</form> 
				</td>
			</tr>
			<%
				}
			}
			%>
		</table>
				<form action="<%=contextPath%>/Main/showCarParkForm" method="post">
				<input type="hidden" name="method" value="add">
				<button class="fsSubmitButton fsSubmitButtonBlue" type="submit">Add New Car Park</button>
				</form>


</body>
</html>
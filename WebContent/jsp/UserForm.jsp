<%@page import="com.cullumg.carpark.data.CarPark"%>
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
	User editUser = (User) request.getSession()
			.getAttribute("editUser");
	if (editUser == null) {
		editUser = new User();
	}

	@SuppressWarnings("unchecked")
	List<CarPark> carParkList = (List<CarPark>) request.getSession()
			.getAttribute("carparklist");

	String method = request.getParameter("method");
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
	<%
		if (theUser.isAdmin() == 1) {
	%>
	<table>
		<tr>
			<td>
				<form method="post" action="<%=contextPath%>/Main/listUsers">
					<button class="fsSubmitButton fsButtonSmall" type="submit">Users</button>
				</form>
			</td>
			<td>
				<form method="post" action="<%=contextPath%>/Main/listCarParks">
					<button class="fsSubmitButton fsButtonSmall" type="submit">Car Parks</button>
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
	<%
		}
	%>
	<p>${param.method == "edit" ? 'Edit' : 'Add New'}User</p>

	<form action="<%=contextPath%>/Main/submitUser" method="post">
		<input type="hidden" name="method" value="<%=method%>">

		<p>Email Address:</p>
		<p>
			<input type="text" name="userid" value="<%=editUser.getUserId()%>"
				${param.method == "edit" ? 'readonly' : ''}>
		</p>
		<p>Name:</p>
		<p>
			<input type="text" name="name" value="<%=editUser.getUserName()%>">
		</p>
		<p>Password:</p>
		<p>
			<input type="password" name="password"
				value="<%=editUser.getPassword()%>">
		</p>
		<p>Phone Number:</p>
		<p>
			<input type="text" name="telephone"
				value="<%=editUser.getTelephone()%>">
		</p>
		<p>Default Car Park:</p>
		<p>
			<select name="defaultCarParkId">
				<%
					Iterator<CarPark> it = carParkList.iterator();
					while (it.hasNext()) {
						CarPark thisCarPark = (CarPark) it.next();
				%>
				<option value="<%=thisCarPark.getCarparkid()%>"
					<%=(thisCarPark.getCarparkid() == editUser
						.getDefaultcarparkid()) ? "SELECTED" : ""%>><%=thisCarPark.getCarparkname()%></option>
				<%
					}
				%>
			</select>
		</p>
		<p>Administrator?:</p>
		<p>
			<select name="isAdmin">
				<option value="0" <%=(editUser.isAdmin() == 0) ? "SELECTED" : ""%>>No</option>
				<option value="1" <%=(editUser.isAdmin() == 1) ? "SELECTED" : ""%>>Yes</option>
			</select>
		</p>

		<button class="fsSubmitButton fsSubmitButtonBlue" type="submit">Submit</button>
	</form>

</body>
</html>
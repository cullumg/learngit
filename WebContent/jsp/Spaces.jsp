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

	int carparkid = theUser.getDefaultcarparkid();
	if (request.getParameter("carparkid") != null) {
		carparkid = Integer.parseInt(request.getParameter("carparkid"));
	} 
	
	@SuppressWarnings("unchecked")
	List<Space> spaceList = (List<Space>) request.getSession()
			.getAttribute("spacelist");
	if (spaceList == null)
		spaceList = new Vector<Space>();

	@SuppressWarnings("unchecked")
	List<CarPark> carParkList = (List<CarPark>) request.getSession()
			.getAttribute("carparklist");
	if (carParkList == null)
		carParkList = new Vector<CarPark>();
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
					<button disabled class="fsSubmitButton fsButtonSmall" type="submit">Spaces</button>
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
	<p>Space List
	<p>
	<form action="<%=contextPath%>/Main/listSpaces" method="POST">
		<select name="carparkid" onchange="this.form.submit();">
			<%
				Iterator<CarPark> it = carParkList.iterator();
				while (it.hasNext()) {
					CarPark thisCarPark = (CarPark) it.next();
			%>
			<option value="<%=thisCarPark.getCarparkid()%>" <%=(thisCarPark.getCarparkid()==carparkid)?"SELECTED":""%>><%=thisCarPark.getCarparkname()%></option>
			<%
				}
			%>
		</select>
	</form>

	<table>

		<%
			Iterator<Space> it2 = spaceList.iterator();
			while (it2.hasNext()) {
				Space thisSpace = (Space) it2.next();
		%>

		<tr>
			<td><p>
					<%=thisSpace.getSpaceName()%></p></td>
			<td>
				<form method="post" action="<%=contextPath%>/Main/showSpaceForm">
					<input type="hidden" name="method" value="edit" /> <input
						type="hidden" name="spaceid" value="<%=thisSpace.getSpaceId()%>" />
					<button class="fsSubmitButton fsSubmitButtonGreen" type="submit">Edit</button>
				</form>
			</td>
			<td>
				<form method="post" action="<%=contextPath%>/Main/deleteSpace">
					<input type="hidden" name="spaceid"
						value="<%=thisSpace.getSpaceId()%>" />
					<button class="fsSubmitButton fsSubmitButtonRed" type="submit">Delete</button>
				</form>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<form action="<%=contextPath%>/Main/showSpaceForm" method="post">
		<input type="hidden" name="method" value="add">
		<button class="fsSubmitButton fsSubmitButtonBlue" type="submit">Add
			New Space</button>
	</form>


</body>
</html>
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
	Space editSpace = (Space) request.getSession().getAttribute(
			"editSpace");
	if (editSpace == null) {
		editSpace = new Space(0, "", "");
	}

	@SuppressWarnings("unchecked")
	List<User> userlist = (List<User>) request.getSession()
			.getAttribute("userlist");

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
<body>
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
	<p>${param.method == "edit" ? 'Edit' : 'Add New'}Space</p>

	<form action="<%=contextPath%>/Main/submitSpace" method="post">
		<input type="hidden" name="method" value="<%=method%>" /> <input
			type="hidden" name="carparkid" value=<%=editSpace.getCarparkid()%> />
		<input type="hidden" name="spaceid" value=<%=editSpace.getSpaceId()%> />
		<p>SpaceName:</p>
		<p>
			<input type="text" name="spacename"
				value="<%=editSpace.getSpaceName()%>" />
		</p>
		<p>Description:</p>
		<p>
			<input type="text" name="spacedescription"
				value="<%=editSpace.getDescription()%>">
		</p>

		<p>Default Owner:</p>
		<p>
			<select name="defaultownerid">
				<option value=""
					<%=editSpace.getDefaultownerid().equals("") ? "SELECTED"
					: ""%>>--None--</option>
				<%
					Iterator<User> it = userlist.iterator();
					while (it.hasNext()) {
						User thisUser = (User) it.next();
				%>
				<option value="<%=thisUser.getUserId()%>"
					<%=(editSpace.getDefaultownerid().equals(thisUser
						.getUserId())) ? "SELECTED" : ""%>><%=thisUser.getUserName()%></option>
				<%
					}
				%>
			</select>
		</p>

		<button class="fsSubmitButton fsSubmitButtonBlue" type="submit">Submit</button>
	</form>

</body>
</html>
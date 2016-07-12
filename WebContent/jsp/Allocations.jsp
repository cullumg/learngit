<%@page import="java.util.Calendar"%>
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
	String selectedDate = (String) request.getSession().getAttribute("selecteddate");
	if (selectedDate == null) {
		selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	@SuppressWarnings("unchecked")
	List<Allocation> theAllocations = (List<Allocation>) request
	.getSession().getAttribute("allocations");

	@SuppressWarnings("unchecked")
	List<Request> myRequests = (List<Request>) request.getSession()
	.getAttribute("requests");

	@SuppressWarnings("unchecked")
	HashMap<Integer, Request> requestsFromMe = (HashMap<Integer, Request>) request
	.getSession().getAttribute("requestsFromMe");
	if (requestsFromMe == null)
		requestsFromMe = new HashMap<Integer, Request>();
	
	Date now = new Date();
	Calendar cal = Calendar.getInstance();
	cal.setTime(now);
	cal.add(Calendar.DATE, 14); 
	String today = new SimpleDateFormat("yyyy-MM-dd").format(now);
	String twoweeks = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

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
<body onblur="setBackground();" onFocus="gotFocus();">
	<%
		if (theUser.isAdmin()==1) {
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
					<button disabled class="fsSubmitButton fsButtonSmall" type="submit">Allocations</button>
				</form>
			</td>
		</tr>
	</table>
	<%
		}
	%>
	<table>
		<tr>
			<td><p>
					Hi
					<%=theUser.getUserName()%></p></td>
			<td>	<a onclick="RefreshPage();"><button
			class="fsSubmitButton fsSubmitButtonGreen" type="submit">Refresh</button></a>
			</td>
		</tr>
	</table>

	<form action="<%=contextPath%>/Main/changeDate" method="POST">
		<input type="date" name="selecteddate" onclick="disableRefresh();"
			onchange="this.form.submit();" value="<%=selectedDate%>" min="<%=today%>"
			max="<%=twoweeks%>" />
	</form>


	<div class="alert">
		<table>
			<%
				if (myRequests != null) {
									Iterator<Request> it2 = myRequests.iterator();
									while (it2.hasNext()) {
										Request thisRequest = (Request) it2.next();
			%>
			<tr>
				<td colspan="2"><p><%=UserManager.getInstance().getUserByEmailAddress(thisRequest.getRequestorUserId()).getUserName()%>
						has requested your space on
						<%=thisRequest.getRequestedDate()%></p></td>
			</tr>
			<tr>
				<td>
					<form method="post" action="<%=contextPath%>/Main/allocateSpace">
						<input type="hidden" name="requestid"
							value="<%=thisRequest.getRequestid()%>" />
						<button class="fsSubmitButton fsSubmitButtonGreen" type="submit">
							Allocate</button>

					</form>
				</td>
				<td>
					<form method="post" action="<%=contextPath%>/Main/denySpace">
						<input type="hidden" name="requestid"
							value="<%=thisRequest.getRequestid()%>" />
						<button class="fsSubmitButton fsSubmitButtonRed" type="submit">
							Deny</button>
					</form>
				</td>

			</tr>
			<%
				}
									}
			%>
		</table>
	</div>
	<table>

		<%
			if (theAllocations != null) {
				Iterator<Allocation> it = theAllocations.iterator();
				while (it.hasNext()) {
					Allocation thisAlloc = (Allocation) it.next();
		%>

		<tr>
			<td><p>
					<%=thisAlloc.getSpaceName()%></p></td>
			<td>
				<%
					if (thisAlloc.getUserId() == null) {
																				//This space is unallocated.
																				if (thisAlloc.getDefaultOwnerId() == null) {
																					//and has no default owner
				%>
				<form method="post" action="<%=contextPath%>/Main/claimSpace">
					<input type="hidden" name="spaceid"
						value="<%=thisAlloc.getSpaceId()%>" />
					<button class="fsSubmitButton" type="submit">Claim Space</button>
				</form> <%
 	} else if (thisAlloc.getDefaultOwnerId().equals(
                    					theUser.getUserId())) {
                    				//the default owner is me!!
 %>
				<button class="fsSubmitButton fsSubmitButtonBlue" type="submit">Allocated
					to Me</button> <%
 	} else if (requestsFromMe.get(thisAlloc.getSpaceId()) != null) {
                    				//I've requested this space
 %>
				<button class="fsSubmitButton fsSubmitButtonGreen" type="submit">Requested</button>
				<%
					} else {
																																																			// it has a default owner who is not me
				%>
				<form method="post" action="<%=contextPath%>/Main/requestSpace">
					<input type="hidden" name="spaceid"
						value="<%=thisAlloc.getSpaceId()%>" /> <input type="hidden"
						name="ownerid" value="<%=thisAlloc.getDefaultOwnerId()%>" />
					<button class="fsSubmitButton fsSubmitButtonAmber" type="submit">
						Request from
						<%=thisAlloc.getDefaultOwner()%></button>
				</form> <%
 	}
              			} else if (thisAlloc.getUserId().equals(theUser.getUserId())) {
                      			//This space is allocated to me.
 %>
				<form method="post" action="<%=contextPath%>/Main/relinquishSpace">
					<input type="hidden" name="spaceid"
						value="<%=thisAlloc.getSpaceId()%>" />
					<button class="fsSubmitButton fsSubmitButtonGreen" type="submit">Relinquish
						Space</button>
				</form> <%
 	} else {
                      			//This space is allocated to someone.
 %>
				<button class="fsSubmitButton fsSubmitButtonRed" type="submit"><%=thisAlloc.getUserName()%></button>
				<%
					}
				%>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>


	<form method="post" action="<%=contextPath%>/Main/doLogin">
		<button class="fsSubmitButton fsSubmitButtonRed" type="submit">Logout</button>
	</form>

	<script>
		var refreshing = new Boolean();
		var waitingForFocus = false;

		function gotFocus() {
			if (waitingForFocus) {
				RefreshPage();
			}
		}
		
		function setBackground() {
			waitingForFocus=true;
		}
		
		function disableRefresh() {
			refreshing = false;
		}

		function enableRefresh() {
			setTimeout("RefreshPage()", 5000);
			refreshing = true;
		}
		function toggleRefresh() {
			if (refreshing) {
				disableRefresh();
			} else {
				enableRefresh();
			}
		}
		
		function RefreshPage() {
			document.location.href = document.location.href;
		}
		
<%-- 		if (!!window.EventSource) {
			source = new EventSource('/ParkMe/NotificationService?clientId=<%=theUser.getUserId()%>&lastUpdateObserved=<%=(new Date()).getTime()%>');
		} else {
			source.open();
		};
 
		source.addEventListener('request', function(e) {
			console.log(e.data);
			RefreshPage();
		}, false);

		source.addEventListener('open', function(e) {
			//connection opened
		}, false);

		source.addEventListener('error', function(e) {
			if (e.readyState == EventSource.CLOSED) {
			}
		}, false);
--%>
</script>

</body>
</html>
package com.cullumg.carpark.bin;

import com.cullumg.carpark.data.CarPark;
import com.cullumg.carpark.data.Space;
import com.cullumg.carpark.data.User;
import com.cullumg.carpark.dataaccess.AllocationManager;
import com.cullumg.carpark.dataaccess.CarParkManager;
import com.cullumg.carpark.dataaccess.RequestManager;
import com.cullumg.carpark.dataaccess.SpaceManager;
import com.cullumg.carpark.dataaccess.UserManager;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainRequestHandler {
	public String handleRequest(String method, HttpServletRequest request, HttpServletResponse response) {
		Class<? extends MainRequestHandler> cls = this.getClass();
		@SuppressWarnings("rawtypes")
		Class[] methodParams = new Class[] { HttpServletRequest.class, HttpServletResponse.class };

		try {
			Method arg11 = cls.getDeclaredMethod(method, methodParams);
			String e = (String) arg11.invoke(this, new Object[] { request, response });
			return e;
		} catch (SecurityException arg7) {
			arg7.printStackTrace();
		} catch (NoSuchMethodException arg8) {
			arg8.printStackTrace();
		} catch (IllegalArgumentException arg9) {
			arg9.printStackTrace();
		} catch (IllegalAccessException arg10) {
			arg10.printStackTrace();
		} catch (InvocationTargetException arg111) {
			arg111.printStackTrace();
		}

		return null;
	}

	public String doLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().invalidate();
			String arg5 = request.getParameter("username");
			String password = request.getParameter("password");
			String persistentSessionId = UUID.randomUUID().toString();
			User theUser = UserManager.getInstance().login(arg5, password);
			if (theUser == null) {
				response.setHeader("message", "Login Failed");
				return "/Main/showLoginForm";
			} else {
				request.getSession().setAttribute("user", theUser);
				UserManager.getInstance().setPersistentSessionId(arg5, persistentSessionId);
				Cookie c = new Cookie("sessionId", persistentSessionId);
				c.setMaxAge(2592000);
				response.addCookie(c);
				return "redirect:/Main/showAllocations";
			}
		} catch (Exception arg7) {
			arg7.printStackTrace();
			return null;
		}
	}

	public String showLoginForm(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/Login.jsp";
	}

	public String showAllocations(HttpServletRequest request, HttpServletResponse response) {
		User theUser = null;

		try {
			theUser = (User) request.getSession().getAttribute("user");
			if (theUser == null) {
				Cookie[] arg9 = request.getCookies();

				for (int selectedDate = 0; selectedDate < arg9.length; ++selectedDate) {
					if (arg9[selectedDate].getName().equals("sessionId")) {
						String myRequests = arg9[selectedDate].getValue();
						System.out.println("Found persistent session cookie.");
						System.out.println("Value is: " + myRequests);
						theUser = UserManager.getInstance().getUserBySessionId(myRequests);
						request.getSession().setAttribute("user", theUser);
					}
				}
			}

			String arg10 = (String) request.getSession().getAttribute("selecteddate");
			if (arg10 == null || arg10.equals("")) {
				arg10 = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
			}

			java.sql.Date arg11 = new java.sql.Date((new SimpleDateFormat("yyyy-MM-dd")).parse(arg10).getTime());
			Vector<?> arg12 = RequestManager.getInstance().getRequestsForOwner(theUser.getUserId());
			List<?> allocations = AllocationManager.getInstance().getAllocations(arg11);
			request.getSession().setAttribute("allocations", allocations);
			request.getSession().setAttribute("requests", arg12);
			HashMap<?, ?> requestsFromMe = RequestManager.getInstance().getRequestsFromUser(theUser.getUserId(), arg11);
			request.getSession().setAttribute("requestsFromMe", requestsFromMe);
			return "/jsp/Allocations.jsp";
		} catch (ParseException arg8) {
			arg8.printStackTrace();
			return null;
		} catch (NullPointerException arg91) {
			return "/Main/doLogin";
		}
	}

	public String showUserForm(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getParameter("method");
		List<CarPark> carParklist = CarParkManager.getInstance().getCarParks();
		request.getSession().setAttribute("carparklist", carParklist);
		if (method.equals("add")) {
			request.getSession().setAttribute("editUser", new User("", "", "", 1, "", 0));
		} else {
			if (!method.equals("edit")) {
				return "redirect:/Main/listUsers";
			}

			String userid = request.getParameter("userid");
			User editUser = UserManager.getInstance().getUserByEmailAddress(userid);
			request.getSession().setAttribute("editUser", editUser);
		}

		return "/jsp/UserForm.jsp";
	}

	public String claimSpace(HttpServletRequest request, HttpServletResponse response) {
		int theSpaceId = Integer.parseInt(request.getParameter("spaceid"));

		try {
			String arg6 = (String) request.getSession().getAttribute("selecteddate");
			if (arg6 == null) {
				arg6 = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
			}

			java.sql.Date selectedDate = null;
			selectedDate = new java.sql.Date((new SimpleDateFormat("yyyy-MM-dd")).parse(arg6).getTime());
			User theUser = (User) request.getSession().getAttribute("user");
			AllocationManager.getInstance().claimSpace(theSpaceId, theUser.getUserId(), selectedDate);
			return "redirect:/Main/showAllocations";
		} catch (ParseException arg61) {
			arg61.printStackTrace();
			return null;
		}
	}

	public String relinquishSpace(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String arg6 = (String) request.getSession().getAttribute("selecteddate");
			if (arg6 == null) {
				arg6 = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
			}

			java.sql.Date selectedDate = new java.sql.Date((new SimpleDateFormat("yyyy-MM-dd")).parse(arg6).getTime());
			int theSpaceId = Integer.parseInt(request.getParameter("spaceid"));
			User theUser = (User) request.getSession().getAttribute("user");
			AllocationManager.getInstance().relinquishSpace(theSpaceId, theUser.getUserId(), selectedDate);
			return "redirect:/Main/showAllocations";
		} catch (Exception arg61) {
			arg61.printStackTrace();
			return null;
		}
	}

	public String allocateSpace(HttpServletRequest request, HttpServletResponse response) {
		try {
			int arg3 = Integer.parseInt(request.getParameter("requestid"));
			RequestManager.getInstance().acceptRequest(arg3);
			return "redirect:/Main/showAllocations";
		} catch (Exception arg31) {
			arg31.printStackTrace();
			return null;
		}
	}

	public String changeDate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String arg3 = request.getParameter("selecteddate");
			request.getSession().setAttribute("selecteddate", arg3);
			return "redirect:/Main/showAllocations";
		} catch (Exception arg31) {
			arg31.printStackTrace();
			return null;
		}
	}

	public String listUsers(HttpServletRequest request, HttpServletResponse response) {
		User theUser = null;

		try {
			theUser = (User) request.getSession().getAttribute("user");
			if (theUser.isAdmin() == 1) {
				List<User> arg4 = UserManager.getInstance().getUserList();
				request.getSession().setAttribute("userlist", arg4);
				return "/jsp/Users.jsp";
			} else {
				return "/Main/showAllocations";
			}
		} catch (NullPointerException arg41) {
			arg41.printStackTrace();
			return "/Main/showAllocations";
		}
	}

	public String submitUser(HttpServletRequest request, HttpServletResponse response) {
		UserManager um = UserManager.getInstance();
		String method = request.getParameter("method");
		String userid = request.getParameter("userid");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String telephone = request.getParameter("telephone");
		int defaultCarParkid = Integer.parseInt(request.getParameter("defaultCarParkId"));
		int isAdmin = Integer.parseInt(request.getParameter("isAdmin"));
		if (method.equals("add")) {
			um.registerUser(userid, name, telephone, defaultCarParkid, password, isAdmin);
		} else {
			if (!method.equals("edit")) {
				return "redirect:/Main/listUsers";
			}

			um.modifyUser(userid, name, telephone, defaultCarParkid, password, isAdmin);
		}

		return "redirect:/Main/listUsers";
	}

	public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String userid = request.getParameter("userid");

		try {
			UserManager.getInstance().deleteUser(userid);
			return "redirect:/Main/listUsers";
		} catch (Exception arg4) {
			arg4.printStackTrace();
			return null;
		}
	}

	public String denySpace(HttpServletRequest request, HttpServletResponse response) {
		try {
			int arg3 = Integer.parseInt(request.getParameter("requestid"));
			RequestManager.getInstance().denyRequest(arg3);
			return "redirect:/Main/showAllocations";
		} catch (Exception arg31) {
			arg31.printStackTrace();
			return null;
		}
	}

	public String requestSpace(HttpServletRequest request, HttpServletResponse response) {
		try {
			String arg7 = (String) request.getSession().getAttribute("selecteddate");
			if (arg7 == null) {
				arg7 = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
			}

			java.sql.Date selectedDate = null;
			selectedDate = new java.sql.Date((new SimpleDateFormat("yyyy-MM-dd")).parse(arg7).getTime());
			int spaceid = Integer.parseInt(request.getParameter("spaceid"));
			String ownerid = request.getParameter("ownerid");
			User theUser = (User) request.getSession().getAttribute("user");
			RequestManager.getInstance().makeRequest(spaceid, theUser.getUserId(), ownerid, selectedDate);
			return "redirect:/Main/showAllocations";
		} catch (Exception arg71) {
			arg71.printStackTrace();
			return null;
		}
	}

	public String listSpaces(HttpServletRequest request, HttpServletResponse response) {
		User theUser = null;

		try {
			theUser = (User) request.getSession().getAttribute("user");
			int arg6 = theUser.getDefaultcarparkid();
			if (request.getParameter("carparkid") != null) {
				arg6 = Integer.parseInt(request.getParameter("carparkid"));
			}

			if (theUser.isAdmin() == 1) {
				List<CarPark> carParklist = CarParkManager.getInstance().getCarParks();
				request.getSession().setAttribute("carparklist", carParklist);
				request.getSession().setAttribute("selectedcarparkid", Integer.valueOf(arg6));
				List<Space> spaceList = SpaceManager.getInstance().listSpacesInCarPark(arg6);
				request.getSession().setAttribute("spacelist", spaceList);
				return "/jsp/Spaces.jsp";
			} else {
				return "/Main/showAllocations";
			}
		} catch (NullPointerException arg61) {
			arg61.printStackTrace();
			return "/Main/showAllocations";
		}
	}

	public String showSpaceForm(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getParameter("method");
		int selectedcarparkid = ((Integer) request.getSession().getAttribute("selectedcarparkid")).intValue();
		List<User> userList = UserManager.getInstance().getUserList();
		request.getSession().setAttribute("userlist", userList);
		if (method.equals("add")) {
			request.getSession().setAttribute("editSpace", new Space(0, "", "", selectedcarparkid, ""));
		} else {
			if (!method.equals("edit")) {
				return "redirect:/Main/listSpaces";
			}

			int spaceid = Integer.parseInt(request.getParameter("spaceid"));
			Space editSpace = SpaceManager.getInstance().getSpaceById(spaceid);
			request.getSession().setAttribute("editSpace", editSpace);
		}

		return "/jsp/SpaceForm.jsp";
	}

	public String submitSpace(HttpServletRequest request, HttpServletResponse response) {
		SpaceManager sm = SpaceManager.getInstance();
		String method = request.getParameter("method");
		int spaceid = Integer.parseInt(request.getParameter("spaceid"));
		int carparkid = Integer.parseInt(request.getParameter("carparkid"));
		String spacename = request.getParameter("spacename");
		String spacedescription = request.getParameter("spacedescription");
		String defaultownerid = request.getParameter("defaultownerid");
		if (defaultownerid.equals("")) {
			defaultownerid = null;
		}

		Space theSpace = new Space(spaceid, spacename, spacedescription, carparkid, defaultownerid);
		if (method.equals("add")) {
			sm.createSpace(theSpace);
		} else {
			if (!method.equals("edit")) {
				return "redirect:/Main/listSpaces";
			}

			sm.modifySpace(theSpace);
		}

		return "redirect:/Main/listSpaces";
	}

	public String deleteSpace(HttpServletRequest request, HttpServletResponse response) {
		int spaceid = Integer.parseInt(request.getParameter("spaceid"));

		try {
			SpaceManager.getInstance().deleteSpace(spaceid);
			return "redirect:/Main/listSpaces";
		} catch (Exception arg4) {
			arg4.printStackTrace();
			return null;
		}
	}

	public String listCarParks(HttpServletRequest request, HttpServletResponse response) {
		User theUser = null;

		try {
			theUser = (User) request.getSession().getAttribute("user");
			if (theUser.isAdmin() == 1) {
				List<CarPark> arg4 = CarParkManager.getInstance().getCarParks();
				request.getSession().setAttribute("carparklist", arg4);
				return "/jsp/CarParks.jsp";
			} else {
				return "/Main/showAllocations";
			}
		} catch (Exception arg41) {
			arg41.printStackTrace();
			return null;
		}
	}

	public String showCarParkForm(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getParameter("method");
		if (method.equals("add")) {
			request.getSession().setAttribute("editcarpark", new CarPark(0, "", ""));
		} else {
			if (!method.equals("edit")) {
				return "redirect:/Main/listCarParks";
			}

			int carparkid = Integer.parseInt(request.getParameter("carparkid"));
			CarPark editCarPark = CarParkManager.getInstance().getCarParkById(carparkid);
			request.getSession().setAttribute("editcarpark", editCarPark);
		}

		return "/jsp/CarParkForm.jsp";
	}

	public String submitCarPark(HttpServletRequest request, HttpServletResponse response) {
		CarParkManager cm = CarParkManager.getInstance();
		String method = request.getParameter("method");
		int carparkid = Integer.parseInt(request.getParameter("carparkid"));
		String carparkname = request.getParameter("carparkname");
		String carparkaddress = request.getParameter("carparkaddress");
		CarPark theCarPark = new CarPark(carparkid, carparkname, carparkaddress);
		if (method.equals("add")) {
			cm.createCarPark(theCarPark);
		} else {
			if (!method.equals("edit")) {
				return "redirect:/Main/listCarParks";
			}

			cm.modifyCarPark(theCarPark);
		}

		return "redirect:/Main/listCarParks";
	}

	public String deleteCarPark(HttpServletRequest request, HttpServletResponse response) {
		int carparkid = Integer.parseInt(request.getParameter("carparkid"));

		try {
			CarParkManager.getInstance().deleteCarPark(carparkid);
			return "redirect:/Main/listCarParks";
		} catch (Exception arg4) {
			arg4.printStackTrace();
			return null;
		}
	}
}
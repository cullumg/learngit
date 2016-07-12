package com.cullumg.carpark.bin;

import com.cullumg.carpark.bin.MainRequestHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/Main" })
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String contextPath = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.contextPath = this.getServletContext().getContextPath();
		String method = null;

		try {
			method = request.getRequestURI().split("/")[3];
		} catch (ArrayIndexOutOfBoundsException arg5) {
			method = null;
		}

		if (method != null) {
			MainRequestHandler mrq = new MainRequestHandler();
			String location = mrq.handleRequest(method, request, response);
			if (location == null) {
				request.getRequestDispatcher("/jsp/errorPage.jsp").forward(request, response);
			} else if (location.startsWith("redirect:")) {
				response.sendRedirect(this.contextPath + location.split(":")[1]);
			} else {
				request.getRequestDispatcher(location).forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/Main/showAllocations").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
package com.cqut.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.permission.domain.User;
import com.cqut.service.PrivilegeService;
import com.cqut.service.impl.PrivilegeServiceImpl;



public class PassportServlet extends HttpServlet {
	private PrivilegeService s = new PrivilegeServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("login".equals(op)){
			login(request,response);
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = s.login(username, password);
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/manage/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

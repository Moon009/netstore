package com.cqut.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqut.permission.domain.Function;
import com.cqut.permission.domain.Role;
import com.cqut.permission.domain.User;
import com.cqut.service.PrivilegeService;
import com.cqut.service.impl.PrivilegeServiceImpl;

public class PrivilegeFilter implements Filter {
	private PrivilegeService s = new PrivilegeServiceImpl();
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request;
		HttpServletResponse response;
		try{
			request = (HttpServletRequest)req;
			response = (HttpServletResponse)resp;
		}catch(Exception e){
			throw new RuntimeException("no-http request or response");
		}
		//检查用户是否登录
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		//转向登录界面
		if(user==null){
			request.getRequestDispatcher("/passport/login.jsp").forward(request, response);
			return;
		}
		//登录：判断权限，根据有没有权限，放行
		//用户可以访问的资源有哪些：Set
		Set<Function> allowAccessFunctions = new HashSet<Function>();
		
		List<Role> roles = s.findRoles(user);
		for(Role r:roles){
			List<Function> fs = s.findFunctions(r);
			allowAccessFunctions.addAll(fs);
		}
		
		//得到当前用户访问的资源的URI
		String uri = request.getRequestURI();//    /day23_00_new/manage/index.jsp
		uri = uri.replace(request.getContextPath(), "");// /manage/index.jsp
		
		String queryString = request.getQueryString();
		if(queryString!=null){
			uri = uri+"?"+queryString;
		}
		
		boolean hasPermission = false;//有没有权限
		for(Function f:allowAccessFunctions){
			if(uri.equals(f.getUri())){
				hasPermission = true;
				break;
			}
		}
		
		if(hasPermission){
			chain.doFilter(request, response);
		}else{
			response.getWriter().write("对不起！您没有权限");
		}
		
	}

	public void destroy() {

	}

}

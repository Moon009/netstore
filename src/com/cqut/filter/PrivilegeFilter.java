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
		//����û��Ƿ��¼
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		//ת���¼����
		if(user==null){
			request.getRequestDispatcher("/passport/login.jsp").forward(request, response);
			return;
		}
		//��¼���ж�Ȩ�ޣ�������û��Ȩ�ޣ�����
		//�û����Է��ʵ���Դ����Щ��Set
		Set<Function> allowAccessFunctions = new HashSet<Function>();
		
		List<Role> roles = s.findRoles(user);
		for(Role r:roles){
			List<Function> fs = s.findFunctions(r);
			allowAccessFunctions.addAll(fs);
		}
		
		//�õ���ǰ�û����ʵ���Դ��URI
		String uri = request.getRequestURI();//    /day23_00_new/manage/index.jsp
		uri = uri.replace(request.getContextPath(), "");// /manage/index.jsp
		
		String queryString = request.getQueryString();
		if(queryString!=null){
			uri = uri+"?"+queryString;
		}
		
		boolean hasPermission = false;//��û��Ȩ��
		for(Function f:allowAccessFunctions){
			if(uri.equals(f.getUri())){
				hasPermission = true;
				break;
			}
		}
		
		if(hasPermission){
			chain.doFilter(request, response);
		}else{
			response.getWriter().write("�Բ�����û��Ȩ��");
		}
		
	}

	public void destroy() {

	}

}

package com.cqut.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqut.commons.Page;
import com.cqut.domain.Book;
import com.cqut.domain.Category;
import com.cqut.domain.Customer;
import com.cqut.domain.Order;
import com.cqut.domain.OrderItem;
import com.cqut.service.BusinessService;
import com.cqut.service.impl.BusinessServiceImpl;
import com.cqut.util.FillBeanUtil;
import com.cqut.util.IdGenertor;
import com.cqut.util.PaymentUtil;
import com.cqut.util.SendMail;
import com.cqut.web.beans.Cart;
import com.cqut.web.beans.CartItem;




public class ClientServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("showIndex".equals(op)){
			showIndex(request,response);
		}else if("showCategoryBooks".equals(op)){
			showCategoryBooks(request,response);
		}else if("showBookDetails".equals(op)){
			showBookDetails(request,response);
		}else if("buyBook".equals(op)){
			buyBook(request,response);
		}else if("changeNum".equals(op)){
			changeNum(request,response);
		}else if("delOneItem".equals(op)){
			delOneItem(request,response);
		}else if("registCustomer".equals(op)){
			registCustomer(request,response);
		}else if("active".equals(op)){
			active(request,response);
		}else if("login".equals(op)){
			login(request,response);
		}else if("logout".equals(op)){
			logout(request,response);
		}else if("genOrders".equals(op)){
			genOrders(request,response);
		}else if("showCustomerOrders".equals(op)){
			showCustomerOrders(request,response);
		}else if("pay".equals(op)){
			pay(request,response);
		}
	}
	//按照第三方支付要求，组织数据
	private void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String orderNum = request.getParameter("orderNum");//订单号
		String money = request.getParameter("money");//支付金额
		String pd_FrpId = request.getParameter("pd_FrpId");//银行
		
		String p0_Cmd = "Buy";
		String p1_MerId = "10001126856";
		String p2_Order = orderNum;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "unknown";
		String p6_Pcat = "unknown";
		String p7_Pdesc = "unknown";
		String p8_Url = "http://localhost:8080/day23_00_netstore/servlet/PaymentResponse";
		String p9_SAF = "1";
		String pa_MP = "no";
		String pr_NeedResponse = "1";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		
		request.setAttribute("p0_Cmd",p0_Cmd );
		request.setAttribute("p1_MerId",p1_MerId );
		request.setAttribute("p2_Order",p2_Order );
		request.setAttribute("p3_Amt",p3_Amt );
		request.setAttribute("p4_Cur",p4_Cur );
		request.setAttribute("p5_Pid",p5_Pid );
		request.setAttribute("p6_Pcat",p6_Pcat );
		request.setAttribute("p7_Pdesc",p7_Pdesc );
		request.setAttribute("p8_Url",p8_Url );
		request.setAttribute("p9_SAF",p9_SAF );
		request.setAttribute("pa_MP",pa_MP );
		request.setAttribute("pr_NeedResponse",pr_NeedResponse );
		request.setAttribute("pd_FrpId",pd_FrpId );
		request.setAttribute("hmac",hmac );
		
		request.getRequestDispatcher("/sure.jsp").forward(request, response);
	}
	//查询登录用户的订单
	private void showCustomerOrders(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		//判断用户有没有登录
		Customer c = (Customer) session.getAttribute("customer");
		if(c==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		List<Order> orders = s.findOrdersByCustomer(c);
		request.setAttribute("os", orders);
		request.getRequestDispatcher("/listOrders.jsp").forward(request, response);
	}
	//生成订单
	private void genOrders(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		//判断用户有没有登录
		Customer c = (Customer) session.getAttribute("customer");
		if(c==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//把购物车---》订单中
		Cart cart = (Cart)session.getAttribute("cart");
		
		Order order = new Order();
		String orderNum = IdGenertor.genOrdersNum();
		order.setOrderNum(orderNum);
		order.setQuantity(cart.getTotalQuantity());
		order.setAmount(cart.getAmount());
		order.setCustomer(c);
		
		//购物项---->订单项中
		Map<String, CartItem> items = cart.getItems();
		for(Map.Entry<String, CartItem> me:items.entrySet()){
			OrderItem oi = new OrderItem();
			oi.setId(UUID.randomUUID().toString());
			oi.setQuantity(me.getValue().getQuantity());
			oi.setPrice(me.getValue().getTotalPrice());
			oi.setBook(me.getValue().getBook());
			order.getItems().add(oi);
		}
		//建立关系
		
		//保存
		s.genOrder(order);//生成订单
		//转向支付页面
		request.getRequestDispatcher("/pay.jsp?orderNum="+orderNum+"&amount="+order.getAmount()).forward(request, response);
	}
	//注销
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().removeAttribute("customer");
		response.sendRedirect(request.getContextPath());
	}
	//登录
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Customer c = s.customerLogin(username, password);
		if(c!=null){
			request.getSession().setAttribute("customer", c);
			response.sendRedirect(request.getContextPath());
		}else{
			request.setAttribute("msg", "您的用户名、密码不正确，或者没有激活账户");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
	//激活账户
	private void active(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		String code = request.getParameter("code");
		if(code!=null&&!"".equals(code)){
			s.activeCustomer(code);
			response.getWriter().write("激活成功！2秒后自动转向主页");
			response.setHeader("Refresh", "2;URL="+request.getContextPath());
		}else{
			request.setAttribute("msg", "激活码有误");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
	//新用户注册：还要发送激活邮件
	private void registCustomer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		Customer c = FillBeanUtil.fillBean(request, Customer.class);
		//产生一个激活码：唯一
		String code = UUID.randomUUID().toString();
		c.setCode(code);
		//发送邮件：需要时间
		SendMail sm = new SendMail(c);
		sm.start();
		//保存信息
		s.regitsCustomer(c);
		request.setAttribute("msg", "注册成功！我们已经发送了一封激活邮件到您的"+c.getEmail()+"邮箱中，请及时激活您的账户");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	private void delOneItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String bookId = request.getParameter("bookId");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.getItems().remove(bookId);
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
		
	}
	//修改购物项的数量
	private void changeNum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String bookId = request.getParameter("bookId");
		String newNum = request.getParameter("num");//修改的新的数量
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		CartItem item = cart.getItems().get(bookId);
		item.setQuantity(Integer.parseInt(newNum));
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//购买书籍
	private void buyBook(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		//放入购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		//用户可定有一个购物车
		cart.addBook(book);
		
		request.setAttribute("msg", "书籍已经放入您的购物车，<a href='"+request.getContextPath()+"'>继续购物</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	//根据书籍的id查询书籍的详细内容
	private void showBookDetails(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/showDetails.jsp").forward(request, response);
	}
	//按照分类进行分页查询
	private void showCategoryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String num = request.getParameter("num");
		String categoryId = request.getParameter("categoryId");
		//查询所有的分类
		List<Category> cs = s.findAllCategories();
		//查询所有的分页书籍
		Page page = s.findAllBookPageRecords(num, categoryId);
		page.setUrl("/servlet/ClientServlet?op=showCategoryBooks&categoryId="+categoryId);
		
		request.setAttribute("cs",cs);
		request.setAttribute("page", page);
		
		request.getRequestDispatcher("/listAllBooks.jsp").forward(request, response);
		
	}
	//展现主页
	private void showIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String num = request.getParameter("num");
		//查询所有的分类
		List<Category> cs = s.findAllCategories();
		//查询所有的分页书籍
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/servlet/ClientServlet?op=showIndex");
		
		request.setAttribute("cs",cs);
		request.setAttribute("page", page);
		
		request.getRequestDispatcher("/listAllBooks.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

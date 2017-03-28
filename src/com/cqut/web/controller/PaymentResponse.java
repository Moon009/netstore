package com.cqut.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqut.domain.Order;
import com.cqut.service.BusinessService;
import com.cqut.service.impl.BusinessServiceImpl;
import com.cqut.util.PaymentUtil;
//处理支付结果
public class PaymentResponse extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取第三方传递过来的数据
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd= request.getParameter("r0_Cmd");
		String r1_Code= request.getParameter("r1_Code");//1代表成功
		String r2_TrxId= request.getParameter("r2_TrxId");
		String r3_Amt= request.getParameter("r3_Amt");
		String r4_Cur= request.getParameter("r4_Cur");
		String r5_Pid= request.getParameter("r5_Pid");
		String r6_Order= request.getParameter("r6_Order");//订单号
		String r7_Uid= request.getParameter("r7_Uid");
		String r8_MP= request.getParameter("r8_MP");
		String r9_BType= request.getParameter("r9_BType");//为“1”: 浏览器重定向;为“2”: 服务器点对点通讯.
		String hmac= request.getParameter("hmac");
		
		boolean b = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		if(b){
			//数据没有被篡改
			if("1".equals(r1_Code)){
				//成功
				if("2".equals(r9_BType)){
					response.getWriter().write("success");
				}
				//更改订单状态
				Order order = s.findOrderByOrderNum(r6_Order);
				if(order==null){
					response.getWriter().write("订单不存在");
					return;
				}
				order.setStatus(1);
				s.updateOrder(order);
				response.getWriter().write("交易成功！2秒返回主页");
				response.setHeader("Refresh", "2;URL="+request.getContextPath());
			}else{
				response.getWriter().write("交易失败");
			}
		}else{
			response.getWriter().write("支付有可能成功！但返回的信息可能被篡改。");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

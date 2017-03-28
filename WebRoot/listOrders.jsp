<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
    <br/>
    <c:if test="${empty os}">
    	<h2>对不起！您还没有下过任何单</h2>
    </c:if>
    <c:if test="${!empty os}">
    	<table border="1" width="438">
    		<tr>
    			<th>选择</th>
    			<th>订单号</th>
    			<th>订单金额</th>
    			<th>状态</th>
    			<th>操作</th>
    		</tr>
    		<c:forEach items="${os}" var="o" varStatus="vs">
    			<tr class="${vs.index%2==0?'odd':'even' }">
	    			<td>
	    				<input type="checkbox" name="orderIds" value="${o.id}"/>
	    			</td>
	    			<td>${o.orderNum}</td>
	    			<td>${o.amount}</td>
	    			<td>
	    				<c:choose>
	    					<c:when test="${o.status==0}">
	    						未付款
	    						<a href="${pageContext.request.contextPath}/pay.jsp?orderNum=${o.orderNum}&amount=${o.amount}">去付款</a>
	    					</c:when>
	    					<c:when test="${o.status==1}">
	    						已付款
	    					</c:when>
	    					<c:when test="${o.status==2}">
	    						已发货
	    					</c:when>
	    				</c:choose>
	    			</td>
	    			<td>
	    				<a href="#">删除</a>
	    				<a href="#">详细</a>
	    			</td>
	    		</tr>
    		</c:forEach>
    	</table>
    	
    </c:if>
  </body>
</html>
 
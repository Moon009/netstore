<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
    <br/>
    <a href="${pageContext.request.contextPath}">所有分类</a>:
    <c:forEach items="${cs}" var="c">
    	<a href="${pageContext.request.contextPath}/servlet/ClientServlet?op=showCategoryBooks&categoryId=${c.id}">${c.name}</a>
    </c:forEach>
    <hr/>
    <table>
    	<tr>
    		<c:forEach items="${page.records}" var="b">
	    		<td>
	    			<img src="${pageContext.request.contextPath}/images${b.path}/${b.photoFileName}"/><br/>
	    			书名：${b.name}<br/>
	    			作者：${b.author}<br/>
	    			单价：${b.price}<br/>
	    			<a href="${pageContext.request.contextPath}/servlet/ClientServlet?op=showBookDetails&bookId=${b.id}">查看详细</a>
	    		</td>
    		</c:forEach>
    	</tr>
    </table>
    <%@ include file="/commons/page.jsp"%>
  </body>
</html>

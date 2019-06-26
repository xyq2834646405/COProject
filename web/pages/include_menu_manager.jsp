<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
	<div class="navbar-header">
		<a class="navbar-brand" href="pages/jsp/manager/manager_index.jsp"><strong>协同办公管理系统（项目经理）</strong></a>
	</div>
	<ul class="nav navbar-nav">
		<li><a href="pages/jsp/manager/manager_index.jsp">首页</a></li>
		<c:if test="${manager!=null}">
			<c:forEach items="${manager.role.groups}" var="gup">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">${gup.title}<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<c:forEach items="${gup.actions}" var="act">
							<li><a href="<%=basePath%>${act.url}">${act.title}</a></li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<ul class="nav navbar-nav navbar-right">
		<li class="dropdown"><a href="javascript:;"
			class="dropdown-toggle" data-toggle="dropdown"> <i
				class="glyphicon glyphicon-user"></i>&nbsp;${manager.name}&nbsp;<span
				class="glyphicon glyphicon-chevron-down"></span></a>
			<ul class="dropdown-menu main-list">
				<li><a href="pages/jsp/manager/manager/manager_password_edit.jsp"><i class="glyphicon glyphicon-edit"></i>&nbsp;修改密码</a></li>
				<li><a href="pages/jsp/manager/manager/ManagerUpdateAction!updatePre.action"><i class="glyphicon glyphicon-info-sign"></i>&nbsp;个人资料</a></li>
				<li class="divider"></li>
				<li><a href="UserLogout!logout.action"><i class="glyphicon glyphicon-off"></i>&nbsp;登录注销</a></li>
			</ul>
		</li>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
	</ul>
</nav>

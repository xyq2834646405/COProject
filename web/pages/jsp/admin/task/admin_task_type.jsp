<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>协同办公管理系统</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<jsp:include page="/pages/include_javascript.jsp" />
<script type="text/javascript" src="js/admin/admin_task_type.js"></script>
</head>
<body class="back">
	<div class="container contentback">
		<div id="headDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<jsp:include page="/pages/include_menu_admin.jsp" />
			</div>
		</div>
		<div id="contentDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-success">
					<div class="panel-heading">
						<strong>任务类型维护</strong>
					</div>
					<div class="panel-body">
						<div><button class="btn btn-primary btn-lg" id="insertBtn"><span class="glyphicon glyphicon-file"></span>&nbsp;增加新类型</button></div>
						<div>
							<table class="table table-bordered table-hover table-condensed" id="ttTab">
								<thead>
									<tr>
										<td style="width:5%;" class="text-center"><strong>编号</strong></td> 
										<td style="width:50%;" class="text-center"><strong>类型名称</strong></td>
										<td style="width:10%;" class="text-center"><strong>操作</strong></td>
									</tr>
								</thead>
								<tbody>
									<c:if test="${allTasktypes!=null}">
										<c:forEach items="${allTasktypes}" var="tasktype">
											<tr>
												<td class="text-center">${tasktype.ttid}</td>
												<td class="text-center">
													<input type="text" id="title-${tasktype.ttid}" name="title-${tasktype.ttid}" class="form-control input-sm" value="${tasktype.title}">
												</td>
												<td class="text-center">
													<button class="btn btn-primary" id="updateBtn-${tasktype.ttid}"><span class="glyphicon glyphicon-edit"></span>&nbsp;修改</button>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
					<div class="panel-footer">
						<div class="alert alert-success" id="alertDiv" style="display: none;">
	                        <button type="button" class="close" data-dismiss="alert">&times;</button>
	                        <span id="alertText"></span>
	                    </div>
					</div>
				</div>
			</div>
		</div>
		<div id="footDiv" class="row navbar-fixed-bottom">
			<jsp:include page="/pages/include_foot.jsp" />
		</div>
	</div>
	<div class="modal fade" id="taskTypeInfo"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true">
		<div class="modal-dialog" style="width: 1000px">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3 class="modal-title">
						<span class="glyphicon glyphicon-file"></span>
						<strong>增加新的任务类型</strong></h3>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="" id="myform" method="post">
						<fieldset>
							<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
							<div class="form-group" id="tasktype.titleDiv">
								<!-- 定义表单提示文字 -->
								<label class="col-md-3 control-label" for="tasktype.title">类型名称：</label>
								<div class="col-md-5">
									<!-- 定义表单输入组件 -->
									<input type="text" id="tasktype.title" name="tasktype.title" class="form-control"
										placeholder="请输入任务类型名称">
								</div>
								<!-- 定义表单错误提示显示元素 -->
								<div class="col-md-4" id="tasktype.titleMsg"></div>
							</div>
							<div class="form-group">
								<div class="col-md-5 col-md-offset-3">
									<button type="submit" class="btn btn-primary">增加</button>
									<button type="reset" class="btn btn-warning">重置</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

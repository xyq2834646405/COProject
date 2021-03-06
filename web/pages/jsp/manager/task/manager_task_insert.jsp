<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String insertUrl = basePath + "pages/jsp/manager/task/TaskActionManager!insert.action" ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>协同办公管理系统</title>
<meta name="viewport" content="width=device-width,initial-scale=1"> 
<jsp:include page="/pages/include_javascript.jsp" /> 
<script type="text/javascript" src="js/manager/manager_task_insert.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap-datetimepicker.min.css" /> 
</head>
<body class="manback">
	<div class="container contentback">
		<div id="headDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<jsp:include page="/pages/include_menu_manager.jsp" />
			</div>
		</div>
		<div id="contentDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-success">
					<div class="panel-heading">
						<strong>添加项目任务</strong>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="<%=insertUrl%>" id="myform" method="post">
							<fieldset>
								<!-- 定义表单提示框 -->
								<legend>
									<label><span class="glyphicon glyphicon-tasks"></span>&nbsp;增加“协同办公系统”项目任务</label>
								</legend>
								<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
								<div class="form-group" id="task.titleDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="task.title">任务名称：</label>
									<div class="col-md-5">
										<!-- 定义表单输入组件 -->
										<input type="text" id="task.title" name="task.title" class="form-control"
											placeholder="请输入要创建的任务名称">
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="task.titleMsg"></div>
								</div>
								<div class="form-group" id="receiver.useridDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="receiver.userid">任务实施者：</label>
									<div class="col-md-5">
										<select id="receiver.userid" name="receiver.userid" class="form-control">
											<option value="">===== 请选择任务完成人员 =====</option>
											<c:forEach items="${allUsers}" var="ruser">
												<option value="${ruser.userid}">${ruser.name}</option>
											</c:forEach>
										</select>
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="receiver.useridMsg"></div>
								</div>
								<div class="form-group" id="tasktype.ttidDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="tasktype.ttid">任务类型：</label>
									<div class="col-md-5">
										<select id="tasktype.ttid" name="tasktype.ttid" class="form-control">
											<option value="">===== 请选择任务类型 =====</option>
											<c:forEach items="${allTasktypes}" var="type">
												<option value="${type.ttid}">${type.title}</option>
											</c:forEach>
										</select>
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="tasktype.ttidMsg"></div>
								</div>
								<div class="form-group" id="task.priorityDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="receiver.userid">任务优先级：</label>
									<div class="col-md-5">
										<select id="task.priority" name="task.priority" class="form-control">
											<option value="">===== 请选择任务的优先级别 =====</option>
											<option value="0">紧急【★★★】</option>
											<option value="1">普通【★★☆】</option>
											<option value="2">延后【★☆☆】</option>
										</select>
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="task.priorityMsg"></div>
								</div>
								<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
								<div class="form-group" id="task.estimateDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="task.estimate">预计工时（小时）：</label>
									<div class="col-md-5">
										<!-- 定义表单输入组件 -->
										<input type="text" id="task.estimate" name="task.estimate" class="form-control"
											placeholder="请输入任务完成预计使用的工时">
									</div>
									<!-- 定义表单错误提示显示元素 --> 
									<div class="col-md-4" id="task.estimateMsg"></div>
								</div>
								<div class="form-group" id="task.expiredateDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="task.expiredate">任务截止日期：</label>
									<div class="col-md-5">
										<input type="text" id="task.expiredate" name="task.expiredate"
											class="form-control" placeholder="请选择任务完成日期" readonly>
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="task.expiredateMsg"></div>
								</div>
								<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
								<div class="form-group" id="task.noteDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="task.note">任务详细说明：</label>
									<div class="col-md-5">
										<textarea rows="10" id="task.note" name="task.note"
											class="form-control" placeholder="请输入任务的详细内容"></textarea>
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="task.noteMsg"></div>
								</div>
								<div class="form-group">
									<div class="col-md-5 col-md-offset-3">
										<input type="hidden" id="project.proid" name="project.proid" value="${param.proid}">
										<button type="submit" class="btn btn-primary">创建任务</button>
										<button type="reset" class="btn btn-warning">重置</button>
									</div>
								</div>
							</fieldset>
						</form>
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
</body>
</html>

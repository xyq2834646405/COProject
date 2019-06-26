$(function(){
	$("a[id*='passwordBtn-']").each(function(){	// 取得修改按钮
		var adminid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			$("#password\\.userid").val(adminid);
			$("#upTitle").text($("#userid-"+adminid).text());
			$("#adminPassword").modal("toggle") ;
		}) ;
	}) ;
	$("a[id*='adminBtn-']").each(function(){	// 取得修改按钮
		var adminid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			$.post("pages/jsp/admin/admin/AdminActionAdmin!updatePre.action",{"user.userid":adminid},function (data) {
				$("#useridTitle").text(data.userid);
				$("#userid").val(data.userid);
				$("#name").val(data.name);
				$("#phone").val(data.phone);
				$("#email").val(data.email);
				$("#photo").attr("src","upload/user/"+data.photo)
				$("#rid option").remove();
				for (var x = 0 ; x < data.roles.length ; x++){
					$("#rid").append($("<option value='"+data.roles[x].rid+"' "+ (data.roles[x].rid==data.rid?"selected":"") +">"+data.roles[x].title+"</option>"))
				}
				$("#adminInfo").modal("toggle");
			},"json");
		}) ;
	}) ;
	$("#deleteBtn").on("click",function(){	// 绑定用户锁定操作
		operateChecked("确定要删除这些管理员吗？","user.userid","pages/jsp/admin/admin/AdminActionAdmin!delete.action","pages/jsp/admin/admin/AdminActionAdmin!list.action") ;
	}) ;
	$("#myform").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			var id = $("#password\\.userid").val();
			var password = $("#password").val();
			$.post("pages/jsp/admin/admin/AdminActionAdmin!updatePassword.action",{"user.userid":id,"user.password":password},function (data) {
				$("#adminPassword").modal("hide") ;
				operateAlert(data=="true","密码修改成功！","密码修改失败！") ;
				$("#password").val("");
			},"text");
		},
		errorPlacement : function(error, element) {
			$("#" + $(element).attr("id").replace(".", "\\.") + "Msg").append(error);
		},
		highlight : function(element, errorClass) {
			$(element).fadeOut(1,function() {
				$(element).fadeIn(1, function() {
					$("#" + $(element).attr("id").replace(".","\\.") + "Div").attr("class","form-group has-error");
				});

			})
		},
		unhighlight : function(element, errorClass) {
			$(element).fadeOut(1,function() {
				$(element).fadeIn(1,function() {
						$("#" + $(element).attr("id").replace(".","\\.") + "Div").attr("class","form-group has-success");
				});
			})
		},
		errorClass : "text-danger",
		rules : {
			"password" : {
				required : true
			}
		}
	});
	$("#adminForm").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			var userid = $("#userid").val();
			var name = $("#name").val();
			var phone = $("#phone").val();
			var email = $("#email").val();
			var rid = $("#rid").val();
			$.post("pages/jsp/admin/admin/AdminActionAdmin!updateInfo.action",
				{
					"user.userid":userid,
					"user.name":name,
					"user.phone":phone,
					"user.email":email,
					"role.rid":rid,
				},function (data) {
					$("#adminInfo").modal("hide") ;
					operateAlert(data=="true","管理员信息修改成功！","管理员信息修改失败！") ;
					if(data=="true"){
						$("#name-"+userid).text(name);
						$("#phone-"+userid).text(phone);
						$("#email-"+userid).text(email);
						$("#role\\.rid-"+userid+" option:selected").each(function(ind){
							console.log($(this).val() + "，" + $(this).text() + "，" + $(this).prop("selected")) ;
							$(this).prop("selected",false) ;
						}) ;
						$("#role\\.rid-"+userid+" option").each(function(ind){
							if ($(this).val() == rid) {
								$(this).prop("selected",true) ;
							}
						})
					}
				},"text");
		},
		errorPlacement : function(error, element) {
			$("#" + $(element).attr("id").replace(".", "\\.") + "Msg").append(error);
		},
		highlight : function(element, errorClass) {
			$(element).fadeOut(1,function() {
				$(element).fadeIn(1, function() {
					$("#" + $(element).attr("id").replace(".","\\.") + "Div").attr("class","form-group has-error");
				});

			})
		},
		unhighlight : function(element, errorClass) {
			$(element).fadeOut(1,function() {
				$(element).fadeIn(1,function() {
						$("#" + $(element).attr("id").replace(".","\\.") + "Div").attr("class","form-group has-success");
				});
			})
		},
		errorClass : "text-danger",
		rules : {
			"userid" : {
				required : true
			},
			"name" : {
				required : true
			},
			"password" : {
				required : true
			},
			"phone" : {
				required : true
			},
			"email" : {
				required : true,
				email : true
			},
			"rid" : {
				required : true
			}
		} 
	});
	// 处理管理员列表页面按钮
	$("button[id*='updateBtn-']").each(function(){	// 取得修改按钮
		var adminid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			var rid = $("#role\\.rid-" + adminid).val() ;
			console.log("adminid = " + adminid + "，rid = " + rid) ;
			// 编写Ajax异步数据调用
			$.post("pages/jsp/admin/admin/AdminActionAdmin!updateRole.action",{"user.userid":adminid,"role.rid":rid},function (data) {
				operateAlert(data=="true","管理员角色信息修改成功！","管理员角色修改失败！") ;
			},"text");
		}) ;
	}) ;
})


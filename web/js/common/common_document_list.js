$(function() {
	$("button[id*='showBtn-']").each(function(){	// 取得修改按钮
		var did = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			console.log("did = " + did) ;
			// 异步Ajax读取公告信息
			loadDocument(did) ;
		}) ;
	}) ;

	$("a[id*='showUserBtn-']").each(function () {
		var userid = this.id.split("-")[1];
		$(this).on("click",function () {
			loadUser(userid);
		})
	});
}) ; 

function loadDocument(did) {
	$.post("pages/jsp/common/document/DocumentActionCommon!show.action",{"document.did":did},function (data) {
		$("#titleHeadSpan").text(data.title);
		$("#titleSpan").text(data.title);
		$("#docTypeSpan").text(data.doctype);
		$("#useridSpan").html("<a class='btn btn-info' id='DocShowUserBtn' value='"+data.userid+"'>"+data.userid+"</a>");
		$("#pubdateSpan").text(data.pubdate);
		$("#contentSpan").text(data.content);
		$("#fileSpan").html("<a class='btn btn-info'href='upload/document/"+data.file+"'>"+data.file+"</a>");
		$("#DocShowUserBtn").on("click",function () {
			$("#documentInfo").modal("hide") ;
			loadUser($(this).attr("value"));
		})
	},"json");
	$("#documentInfo").modal("toggle") ;
}

function loadUser(userid) {
	$.post("pages/jsp/common/user/UserActionCommon!show.action",{"user.userid":userid},function (data) {
		$("#useridHeadSpan").text(data.userid);
		$("#userPhoto").attr("src","upload/user/"+data.photo);
		$("#userUserid").text(data.userid);
		$("#userName").text(data.name);
		$("#userPhone").text(data.phone);
		$("#userEmail").text(data.email);
		$("#userLockflag").text(data.lockflag==0?"激活":"锁定");
		$("#userLastLogin").text(data.lastlogin);
		if(data.level==0||data.level==1){
			$("#userLevel").text("管理员");
		}else if(data.level==2){
			$("#userLevel").text("项目经理");
		}else{
			$("#userLevel").text("员工");
		}
		$("#userInfo").modal("toggle") ;
	},"json");
}
$(function () {
    $("a[id*='showProjectBtn-']").each(function(){	// 取得修改按钮
        var proid = this.id.split("-")[1];	// 分离出id信息
        $(this).on("click",function(){
            console.log("proid = " + proid) ;
            // 异步Ajax读取公告信息
            loadProject(proid);
        }) ;
    }) ;

    $("a[id*='showUserBtn-']").each(function () {
        var userid = this.id.split("-")[1];
        $(this).on("click",function () {
            loadUser(userid);
        })
    });
})

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
function loadProject(proid) {
    $.post("pages/jsp/manager/project/ProjectActionManager!show.action",{"project.proid":proid},function (data) {
        $("#titleHeadSpan").text(data.title);
        $("#titleSpan").text(data.title);
        $("#creidSpan").html("<a class='btn btn-info' id='ProjectShowUserBtn1' value='"+data.creid+"'>"+data.creid+"</a>");
        $("#mgrSpan").html("<a class='btn btn-info' id='ProjectShowUserBtn2' value='"+data.mgr+"'>"+data.name+"</a>");
        $("#pubdateSpan").text(data.pubdate);
        $("#noteSpan").text(data.note);
        $("#ProjectShowUserBtn1").on("click",function () {
            $("#projectInfo").modal("hide");
            loadUser($(this).attr("value"))
        })
        $("#ProjectShowUserBtn2").on("click",function () {
            $("#projectInfo").modal("hide");
            loadUser($(this).attr("value"))
        })
        $("#projectInfo").modal("toggle");
    },"json");
}
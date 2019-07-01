function loadTask(tid) {
    $.post("pages/jsp/common/task/TaskActionCommon!show.action",{"task.tid":tid},function (data) {
        $("#titleHeadSpan").text(data.title);
        $("#projectSpan").text(data.project);
        $("#titleSpan").text(data.title);
        $("#typeSpan").text(data.type);
        $("#noteSpan").text(data.note);
        $("#creatorSpan").html("<a class='btn btn-info' id='CNoticeShowUserBtn' value='"+data.creator+"'>"+data.creator+"</a>");
        $("#receiverSpan").html("<a class='btn btn-info' id='RNoticeShowUserBtn' value='"+data.receiver+"'>"+data.receiver+"</a>");
        $("#rnoteSpan").text(data.rnote);
        $("#cancelerSpan").html("<a class='btn btn-info' id='CENoticeShowUserBtn' value='"+data.cancel+"'>"+data.cancel+"</a>");
        $("#cnoteSpan").text(data.cnote);
        if(data.pri==0){
            $("#prioritySpan").text("★★★");
        }else if(data.pri==1){
            $("#prioritySpan").text("★★☆");
        }else{
            $("#prioritySpan").text("★☆☆");
        }
        $("#createdateSpan").text(data.createdate);
        $("#expiredateSpan").text(data.expiredate);
        $("#lastupdatedateSpan").text(data.lastupdatedate);
        $("#estimateSpan").text(data.es);
        $("#expendSpan").text(data.ep);
        $("#CNoticeShowUserBtn").on("click",function () {
            $("#taskInfo").modal("hide");
            loadUser($(this).attr("value"));
        });
        $("#RNoticeShowUserBtn").on("click",function () {
            $("#taskInfo").modal("hide");
            loadUser($(this).attr("value"));
        });
        $("#CENoticeShowUserBtn").on("click",function () {
            $("#taskInfo").modal("hide");
            loadUser($(this).attr("value"));
        });
    },"json")
    $("#taskInfo").modal("toggle") ;
}
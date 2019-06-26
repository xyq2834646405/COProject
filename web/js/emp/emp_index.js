//页面加载的时候执行
$(function () {
    $.post("IndexActionEmp!allCount.action",{},function (data) {
        $("#noticeCount").text(data.noticeCount);
    },"json")
})
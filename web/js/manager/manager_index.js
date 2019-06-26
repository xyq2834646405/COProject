//页面加载的时候执行
$(function () {
    $.post("pages/jsp/manager/notice/NoticeActionManager!uncount.action",{},function (data) {
        $("#noticeCount").text(data);
    },"text")
})
package com.xyq.action.manager;

import com.xyq.entity.Notice;
import com.xyq.entity.User;
import com.xyq.service.manager.INoticeServiceManager;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("managerStack")
@ParentPackage("root")
@Namespace("/pages/jsp/manager/notice")
@Results({
    @Result(name = "notice.list",location = "/pages/jsp/manager/notice/manager_notice_list.jsp")
})
@Action("NoticeActionManager")
public class NoticeActionManager extends AbstractAction {
    private Notice notice = new Notice();

    @Autowired
    private INoticeServiceManager noticeServiceManager;

    public void uncount(){
        User manager = (User) getSession().getAttribute("manager");
        try {
            print(noticeServiceManager.unreadCount(manager.getUserid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(){
        User manager = (User) getSession().getAttribute("manager");
        try {
            Notice vo = noticeServiceManager.show(notice.getSnid(), manager.getUserid());
            JSONObject obj = new JSONObject();
            obj.put("title",vo.getTitle());
            obj.put("userid",vo.getUser().getUserid());
            obj.put("pubdate",formatDate(vo.getPubdate()));
            obj.put("level",vo.getLevel());
            obj.put("rnum",vo.getRnum());
            obj.put("content",vo.getContent());
            print(obj );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String list(){
        User manager = (User) getSession().getAttribute("manager");
        try {
            Map<String, Object> map = noticeServiceManager.list(manager.getUserid(), getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("noticeCount"),"manager.notice.split.url",null,null);
            getRequest().setAttribute("allNotices",map.get("allNotices"));
            getRequest().setAttribute("unread",map.get("unread"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "notice.list";
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "公告标题:title|公告内容:content";
    }

    public String getTypeName() {
        return "公告信息";
    }

    public Notice getNotice() {
        return notice;
    }
}

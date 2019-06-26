package com.xyq.action.emp;

import com.xyq.entity.Notice;
import com.xyq.entity.User;
import com.xyq.service.emp.INoticeServiceEmp;
import com.xyq.service.manager.INoticeServiceManager;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("empStack")
@ParentPackage("root")
@Namespace("/pages/jsp/emp/notice")
@Results({
    @Result(name = "notice.list",location = "/pages/jsp/emp/notice/emp_notice_list.jsp")
})
@Action("NoticeActionEmp")
public class NoticeActionEmp extends AbstractAction {
    private Notice notice = new Notice();

    @Autowired
    private INoticeServiceEmp noticeServiceEmp;

//    public void uncount(){
//        User emp = (User) getSession().getAttribute("emp");
//        try {
//            print(noticeServiceEmp.unreadCount(emp.getUserid()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void show(){
        User emp = (User) getSession().getAttribute("emp");
        try {
            Notice vo = noticeServiceEmp.show(notice.getSnid(), emp.getUserid());
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
        User emp = (User) getSession().getAttribute("emp");
        try {
            Map<String, Object> map = noticeServiceEmp.list(emp.getUserid(), getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("noticeCount"),"emp.notice.split.url",null,null);
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

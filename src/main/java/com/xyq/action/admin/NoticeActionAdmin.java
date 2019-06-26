package com.xyq.action.admin;

import com.xyq.entity.Notice;
import com.xyq.entity.User;
import com.xyq.service.admin.INoticeServiceAdmin;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results({
    @Result(name = "notice.insert",location = "/pages/jsp/admin/notice/admin_notice_insert.jsp"),
    @Result(name = "notice.update",location = "/pages/jsp/admin/notice/admin_notice_update.jsp"),
    @Result(name = "notice.list",location = "/pages/jsp/admin/notice/admin_notice_list.jsp"),
    @Result(name = "insertVF",location = "/pages/jsp/admin/notice/admin_notice_insert.jsp"),
    @Result(name = "updateVF",location = "/pages/jsp/admin/notice/NoticeActionAdmin!list.action",type = "redirectAction")
})
@Namespace("/pages/jsp/admin/notice")
@Action("NoticeActionAdmin")
public class NoticeActionAdmin extends AbstractAction {
    private static final String insertRule = "notice.title:string|notice.content:string";
    private static final String updateRule = "notice.snid:int|notice.title:string|notice.content:string";
    private Notice notice = new Notice();
    private String ids;
    @Autowired
    private INoticeServiceAdmin noticeServiceAdmin;

    public String list(){
        try {
            Map<String, Object> map = noticeServiceAdmin.list(getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("noticeCount"),"admin.notice.split.url",null,null);
            getRequest().setAttribute("allNotices",map.get("allNotices"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "notice.list";
    }


    public String insertPre(){
        return "notice.insert";
    }

    public String insert(){
        User admin = (User)getSession().getAttribute("admin");
        notice.setUser(admin);
        try {
            if (noticeServiceAdmin.insert(notice)){
                setMsgAndUrl("insert.success.msg","admin.notice.insert.action");
            }else{
                setMsgAndUrl("insert.failure.msg","admin.notice.insert.action");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String updatePre(){
        try {
            getRequest().setAttribute("notice",noticeServiceAdmin.updatePre(notice.getSnid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "notice.update";
    }

    public String update(){
        User admin = (User)getSession().getAttribute("admin");
        notice.setUser(admin);
        try {
            if (noticeServiceAdmin.update(notice)){
                setMsgAndUrl("update.success.msg","admin.notice.list.action");
            }else{
                setMsgAndUrl("update.failure.msg","admin.notice.list.action");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public void updateLevel(){
        try {
            print(noticeServiceAdmin.updateLevel(notice.getSnid(),notice.getLevel()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        Set<Integer> set = new HashSet<Integer>();
        String[] result = ids.split("\\|");
        for (int i = 0; i <result.length ; i++) {
            set.add(Integer.parseInt(result[i]));
        }
        try {
            noticeServiceAdmin.delete(set);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "公告标题:title|公告内容:content|发布管理员:user.userid";
    }

    public String getTypeName() {
        return "系统公告";
    }

    public Notice getNotice() {
        return notice;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}

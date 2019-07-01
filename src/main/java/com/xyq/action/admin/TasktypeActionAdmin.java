package com.xyq.action.admin;

import com.xyq.entity.Tasktype;
import com.xyq.service.admin.ITasktypeServiceAdmin;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results({
    @Result(name = "tasktype.list", location = "/pages/jsp/admin/task/admin_task_type.jsp"),
    @Result(name = "insertVF", location = "/pages/jsp/admin/task/TasktypeActionAdmin!list.action",type = "redirectAction"),
    @Result(name = "updateVF", location = "/pages/jsp/admin/task/TasktypeActionAdmin!list.action",type = "redirectAction")
})
@Namespace("/pages/jsp/admin/task")
@Action("TasktypeActionAdmin")
public class TasktypeActionAdmin extends AbstractAction {
    private static String insertRule ="tasktype.title:string";
    private static String updateRule ="tasktype.ttid:int|tasktype.title:string";
    private Tasktype tasktype = new Tasktype();

    @Autowired
    private ITasktypeServiceAdmin tasktypeServiceAdmin;

    public void insert(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("flag",tasktypeServiceAdmin.insert(tasktype));
        } catch (Exception e) {
            e.printStackTrace();
        }
        obj.put("ttid",tasktype.getTtid());
        print(obj);
    }

    public void update(){
        try {
            print(tasktypeServiceAdmin.update(tasktype));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String list(){
        try {
            getRequest().setAttribute("allTasktypes",tasktypeServiceAdmin.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "tasktype.list";
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "类型标题:title";
    }

    public String getTypeName() {
        return "任务类型";
    }

    public Tasktype getTasktype() {
        return tasktype;
    }
}

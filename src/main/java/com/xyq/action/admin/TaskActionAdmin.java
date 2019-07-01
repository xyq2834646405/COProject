package com.xyq.action.admin;

import com.xyq.entity.Project;
import com.xyq.entity.Task;
import com.xyq.entity.User;
import com.xyq.service.admin.ITaskServiceAdmin;
import com.xyq.service.emp.ITaskServiceEmp;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results({
    @Result(name = "task.admin", location = "/pages/jsp/admin/task/admin_task_list.jsp")
})
@Namespace("/pages/jsp/admin/task")
@Action("TaskActionAdmin")
public class TaskActionAdmin extends AbstractAction {
    private Project project = new Project();//任务的所属项目

    @Autowired
    private ITaskServiceAdmin taskServiceAdmin;

    public String list(){
        try {
            Map<String, Object> map = taskServiceAdmin.listByProject(project.getProid(), getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("taskCount"),"admin.task.split.url","project.proid",String.valueOf(project.getProid()));
            getRequest().setAttribute("allTasks",map.get("allTasks"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "task.admin";
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "任务标题:title";
    }

    public String getTypeName() {
        return "项目任务";
    }

    public Project getProject() {
        return project;
    }
}

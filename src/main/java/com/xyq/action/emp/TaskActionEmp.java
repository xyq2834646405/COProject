package com.xyq.action.emp;

import com.xyq.entity.Project;
import com.xyq.entity.Task;
import com.xyq.entity.User;
import com.xyq.service.emp.ITaskServiceEmp;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("empStack")
@ParentPackage("root")
@Results({
        @Result(name = "task.admin", location = "/pages/jsp/emp/task/emp_task_admin.jsp"),
        @Result(name = "task.list", location = "/pages/jsp/emp/task/emp_task_list.jsp"),
})
@Namespace("/pages/jsp/emp/task")
@Action("TaskActionEmp")
public class TaskActionEmp extends AbstractAction {
    private Project project = new Project();//任务的所属项目
    private Task task = new Task();

    @Autowired
    private ITaskServiceEmp taskServiceEmp;

    public void updateFinish(){
        User emp = (User)getSession().getAttribute("emp");
        task.setUserByReceiver(emp);//当前的用户信息
        try {
            print(taskServiceEmp.updateFinish(task));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String list(){
        try {
            Map<String, Object> map = taskServiceEmp.listByProject(project.getProid(), getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("taskCount"),"emp.task.split.url","project.proid",String.valueOf(project.getProid()));
            getRequest().setAttribute("allTasks",map.get("allTasks"));
            getRequest().setAttribute("project",map.get("project"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "task.list";
    }

    public String admin(){
        try {
            User emp = (User)getSession().getAttribute("emp");
            Map<String, Object> map = taskServiceEmp.listByReceiver(emp.getUserid(), getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("taskCount"),"emp.task.admin.split.action","project.proid",String.valueOf(project.getProid()));
            getRequest().setAttribute("allTasks",map.get("allTasks"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "task.admin";
    }

    public void show(){
        try {
            User emp = (User)getSession().getAttribute("emp");
            Task show = taskServiceEmp.show(task.getTid(),emp.getUserid());
            JSONObject obj = new JSONObject();
            obj.put("tid",show.getTid());
            obj.put("title",show.getTitle());
            if(show.getUserByCreator()!=null)
                obj.put("creator",show.getUserByCreator().getUserid());
            if(show.getUserByReceiver()!=null)
                obj.put("receiver",show.getUserByReceiver().getUserid());
            if(show.getUserByCanceler()!=null)
                obj.put("cancel",show.getUserByCanceler().getUserid());
            if(show.getTasktype()!=null)
                obj.put("type",show.getTasktype().getTitle());
            if (show.getProject()!=null)
                obj.put("project",show.getProject().getTitle());
            obj.put("note",show.getNote());
            obj.put("rnote",show.getRnote());
            obj.put("cnote",show.getCnote());
            obj.put("pri",show.getPriority());
            obj.put("createdate",formatDate(show.getCreatedate()));
            obj.put("expiredate",formatDate(show.getExpiredate()));
            obj.put("lastupdatedate",formatDate(show.getLastupdatedate()));
            obj.put("es",show.getEstimate());
            obj.put("ep",show.getExpend());
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Task getTask() {
        return task;
    }
}

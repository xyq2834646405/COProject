package com.xyq.action.manager;

import com.xyq.entity.Project;
import com.xyq.entity.Task;
import com.xyq.entity.Tasktype;
import com.xyq.entity.User;
import com.xyq.service.manager.ITaskServiceManager;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("managerStack")
@ParentPackage("root")
@Results({
    @Result(name = "task.insert", location = "/pages/jsp/manager/task/manager_task_insert.jsp"),
    @Result(name = "task.update", location = "/pages/jsp/manager/task/manager_task_update.jsp"),
    @Result(name = "task.admin", location = "/pages/jsp/manager/task/manager_task_admin.jsp"),
    @Result(name = "task.list", location = "/pages/jsp/manager/task/manager_task_list.jsp"),
    @Result(name = "insertVF", location = "/pages/jsp/manager/task/TaskActionManager!list.action",type = "redirectAction"),
    @Result(name = "updateVF", location = "/pages/jsp/manager/task/TaskActionManager!insertPre.action",type = "redirectAction")
})
@Namespace("/pages/jsp/manager/task")
@Action("TaskActionManager")
public class TaskActionManager extends AbstractAction {
    private static String insertRule = "task.title:string|task.estimate:int|task.expiredate:data|task.note:string";
    private static String updateRule = "task.tid:int|task.title:string|task.estimate:int|task.expiredate:data|task.note:string";
    private Task task = new Task();
    private User receiver = new User();//任务的实施者
    private Tasktype tasktype = new Tasktype();//项目类型
    private Project project = new Project();//任务的所属项目

    @Autowired
    private ITaskServiceManager taskServiceManager;

    public String insertPre(){
        try {
            Map<String, Object> map = taskServiceManager.insertPre();
            getRequest().setAttribute("allUsers",map.get("allUsers"));
            getRequest().setAttribute("allTasktypes",map.get("allTasktypes"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "task.insert";
    }

    public String insert(){
        User manager = (User) getSession().getAttribute("manager");
        task.setUserByCreator(manager);//设置任务的发布者
        task.setTasktype(tasktype);//设置任务的类型
        task.setUserByReceiver(receiver);//任务的接收者
        task.setProject(project);
        try {
            if(taskServiceManager.insert(task)){
                setMsgAndUrl("insert.success.msg","manager.task.insert.action");
            }else{
                setMsgAndUrl("insert.failure.msg","manager.task.insert.action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String updatePre(){
        try {
            Map<String, Object> map = taskServiceManager.updatePre(task.getTid());
            getRequest().setAttribute("allUsers",map.get("allUsers"));
            getRequest().setAttribute("allTasktypes",map.get("allTasktypes"));
            getRequest().setAttribute("task",map.get("task"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "task.update";
    }

    public String update(){
        User manager = (User) getSession().getAttribute("manager");
        task.setUserByCreator(manager);//设置任务的发布者
        task.setTasktype(tasktype);//设置任务的类型
        task.setUserByReceiver(receiver);//任务的接收者
        task.setProject(project);
        try {
            if(taskServiceManager.update(task)){
                setMsgAndUrl("update.success.msg","manager.task.list.action");
            }else{
                setMsgAndUrl("update.failure.msg","manager.task.list.action");
            }
            getRequest().setAttribute("url",getUrl("manager.task.list.action")+"?project.proid="+project.getProid());//手动控制url路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String list(){
        try {
            Map<String, Object> map = taskServiceManager.listByProject(project.getProid(), getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("taskCount"),"manager.task.split.url","project.proid",String.valueOf(project.getProid()));
            getRequest().setAttribute("allTasks",map.get("allTasks"));
            getRequest().setAttribute("project",map.get("project"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "task.list";
    }

    public String admin(){
        try {
            User manager = (User)getSession().getAttribute("manager");
            Map<String, Object> map = taskServiceManager.listByCreator(manager.getUserid(), getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("taskCount"),"manager.task.admin.split.action","project.proid",String.valueOf(project.getProid()));
            getRequest().setAttribute("allTasks",map.get("allTasks"));
            getRequest().setAttribute("project",map.get("project"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "task.admin";
    }

    public void cancel(){
        User manager = (User) getSession().getAttribute("manager");
        task.setUserByCreator(manager);//设置任务的取消者
        task.setProject(project);//设置所属的项目
        try {
            print(taskServiceManager.updateCancel(task));
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

    public Task getTask() {
        return task;
    }

    public User getReceiver() {
        return receiver;
    }

    public Project getProject() {
        return project;
    }

    public Tasktype getTasktype() {
        return tasktype;
    }
}

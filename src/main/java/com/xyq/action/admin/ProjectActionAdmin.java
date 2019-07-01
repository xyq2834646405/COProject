package com.xyq.action.admin;

import com.xyq.entity.Project;
import com.xyq.entity.User;
import com.xyq.service.admin.IProjectServiceAdmin;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results({
    @Result(name = "project.insert",location = "/pages/jsp/admin/project/admin_project_insert.jsp"),
    @Result(name = "project.update",location = "/pages/jsp/admin/project/admin_project_update.jsp"),
    @Result(name = "project.list",location = "/pages/jsp/admin/project/admin_project_list.jsp"),
    @Result(name = "insertVF",location = "/pages/jsp/admin/project/ProjectActionAdmin!insertPre.action",type = "redirectAction"),
    @Result(name = "updateVF",location = "/pages/jsp/admin/project/ProjectActionAdmin!list.action",type = "redirectAction")
})
@Namespace("/pages/jsp/admin/project")
@Action("ProjectActionAdmin")
public class ProjectActionAdmin extends AbstractAction {
    private static String insertRule ="project.title:string|project.note:string";
    private static String updateRule ="project.proid:int|project.title:string|project.note:string";
    private Project project = new Project();
    private User mgr = new User();//负责保存项目经理的信息

    @Autowired
    private IProjectServiceAdmin projectServiceAdmin;

    public String insertPre(){
        try {
            Map<String, Object> map = projectServiceAdmin.insertPre();
            getRequest().setAttribute("allManagers",map.get("allManagers"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "project.insert";
    }

    public String insert(){
        try {
            User admin = (User) getSession().getAttribute("admin");
            project.setUserByMgr(mgr);//保存项目经理的信息
            project.setUserByCreid(admin);//保存项目创建者的信息
            if(projectServiceAdmin.insert(project)){
                setMsgAndUrl("insert.success.msg","admin.project.insert.action");
            }else{
                setMsgAndUrl("insert.failure.msg","admin.project.insert.action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String updatePre(){
        try {
            Map<String, Object> map = projectServiceAdmin.updatePre(project.getProid());
            getRequest().setAttribute("allManagers",map.get("allManagers"));
            getRequest().setAttribute("project",map.get("project"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "project.update";
    }

    public String update(){
        try {
            User admin = (User) getSession().getAttribute("admin");
            project.setUserByMgr(mgr);//保存项目经理的信息
            project.setUserByCreid(admin);//保存项目创建者的信息
            if(projectServiceAdmin.update(project)){
                setMsgAndUrl("update.success.msg","admin.project.list.action");
            }else{
                setMsgAndUrl("update.failure.msg","admin.project.list.action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String list(){
        try {
            Map<String, Object> map = projectServiceAdmin.list(getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("projectCount"),"admin.project.split.url",null,null);
            getRequest().setAttribute("allProjects",map.get("allProjects"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "project.list";
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "项目标题:title|项目经理ID:userByMgr.userid|负责人:name";
    }

    public String getTypeName() {
        return "项目";
    }

    public Project getProject() {
        return project;
    }

    public User getMgr() {
        return mgr;
    }
}

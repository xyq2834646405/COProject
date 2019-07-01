package com.xyq.action.manager;

import com.xyq.entity.Project;
import com.xyq.entity.User;
import com.xyq.service.admin.IProjectServiceAdmin;
import com.xyq.service.manager.IProjectServiceManager;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("managerStack")
@ParentPackage("root")
@Results({
    @Result(name = "project.list",location = "/pages/jsp/manager/project/manager_project_list.jsp")
})
@Namespace("/pages/jsp/manager/project")
@Action("ProjectActionManager")
public class ProjectActionManager extends AbstractAction {
    private Project project = new Project();
    private User mgr = new User();//负责保存项目经理的信息

    @Autowired
    private IProjectServiceManager projectServiceManager;

    public String list(){
        try {
            Map<String, Object> map = projectServiceManager.list(getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("projectCount"),"manager.project.split.url",null,null);
            getRequest().setAttribute("allProjects",map.get("allProjects"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "project.list";
    }

    public void show(){
        try {
            Project show = projectServiceManager.show(project.getProid());
            JSONObject obj = new JSONObject();
            obj.put("proid",show.getProid());
            obj.put("title",show.getTitle());
            obj.put("creid",show.getUserByCreid().getUserid());
            obj.put("mgr",show.getUserByMgr().getUserid());
            obj.put("name",show.getName());
            obj.put("note",show.getNote());
            obj.put("pubdate",formatDate(show.getPubdate()));
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

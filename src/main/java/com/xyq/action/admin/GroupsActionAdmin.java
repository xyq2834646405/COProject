package com.xyq.action.admin;

import com.xyq.entity.Groups;
import com.xyq.service.admin.IActionServiceAdmin;
import com.xyq.service.admin.IGroupsServiceAdmin;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Map;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Namespace("/pages/jsp/admin/role")
@Results(value = {
    @Result(name = "groups.list",location = "/pages/jsp/admin/role/admin_groups_list.jsp")
})
@Action("GroupsActionAdmin")
public class GroupsActionAdmin extends AbstractAction {
    private static String updateRule = "groups.gid:int|groups.title:string";
    private static String showRule = "groups.gid:int";

    @Autowired
    private IGroupsServiceAdmin groupsServiceAdmin;

    private Groups groups = new Groups();

    public String list(){
        try {
            Map<String,Object> map = groupsServiceAdmin.list(getCp(),getLs(),getCol(),getKw());
            handleSplit(map.get("groupsCount"),"admin.groups.split.url",null,null);
            getRequest().setAttribute("allGroups",map.get("allGroups"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "groups.list";
    }

    public void update(){
        try {
            print(groupsServiceAdmin.update(groups));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(){
        try {
            Groups gup = groupsServiceAdmin.show(groups.getGid());
            JSONObject obj = new JSONObject();
            obj.put("gid",gup.getGid());
            obj.put("title",gup.getTitle());
            obj.put("note",gup.getNote());
            JSONArray array = new JSONArray();
            Iterator<com.xyq.entity.Action> iter = gup.getActions().iterator();
            while(iter.hasNext()){
                com.xyq.entity.Action act = iter.next();
                JSONObject temp = new JSONObject();
                temp.put("actid",act.getActid());
                temp.put("title",act.getTitle());
                temp.put("url",act.getUrl());
                array.add(temp);
            }
            obj.put("actions",array);
            print(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Groups getGroups() {
        return groups;
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "权限组标题:title";
    }

    public String getTypeName() {
        return "权限组";
    }
}

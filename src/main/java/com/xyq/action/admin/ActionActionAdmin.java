package com.xyq.action.admin;

import com.xyq.service.admin.IActionServiceAdmin;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Namespace("/pages/jsp/admin/role")
@Results(value = {
    @Result(name = "action.list",location = "/pages/jsp/admin/role/admin_action_list.jsp")
})
@Action("ActionActionAdmin")
public class ActionActionAdmin extends AbstractAction {
    private static String updateRule = "action.title:string|action.url:string";
    private com.xyq.entity.Action action = new com.xyq.entity.Action();

    @Autowired
    private IActionServiceAdmin actionServiceAdmin;

    public com.xyq.entity.Action getAction() {
        return action;
    }

    public String list(){
        try {
            Map<String,Object> map = actionServiceAdmin.list(getCp(),getLs(),getCol(),getKw());
            handleSplit(map.get("actionCount"),"admin.action.split.url",null,null);
            getRequest().setAttribute("allActions",map.get("allActions"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "action.list";
    }

    public void update(){
        try {
            print(actionServiceAdmin.update(action));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "权限标题:title|权限路径:url";
    }

    public String getTypeName() {
        return "权限";
    }
}

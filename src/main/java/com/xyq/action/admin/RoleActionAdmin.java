package com.xyq.action.admin;

import com.opensymphony.xwork2.ActionContext;
import com.xyq.entity.Groups;
import com.xyq.entity.Role;
import com.xyq.entity.User;
import com.xyq.service.admin.IGroupsServiceAdmin;
import com.xyq.service.admin.IRoleServiceAdmin;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results({
    @Result(name = "role.list",location = "/pages/jsp/admin/role/admin_role_list.jsp"),
    @Result(name = "role.insert.page",location = "/pages/jsp/admin/role/admin_role_insert.jsp")
})
@Namespace("/pages/jsp/admin/role")
@Action("RoleActionAdmin")
public class RoleActionAdmin extends AbstractAction {
    private static String insertRule = "role.title:string";
    private static String updateRule = "role.rid:int|role.title:string";
    private Role role = new Role();
    private Integer[] gids ;//处理权限组编号,在增加的时候使用
    private String ugid;//更新是使用的gid数据

    @Autowired
    private IRoleServiceAdmin roleServiceAdmin;

    @Autowired
    private IGroupsServiceAdmin groupsServiceAdmin;

    public void checkTitleInsert(){
        try {
            print(roleServiceAdmin.checkTitle(role.getTitle()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkTitleUpdate(){
        try {
            print(roleServiceAdmin.checkTitle(role.getTitle(),role.getRid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String insert(){
        Set<Groups> set = new HashSet<Groups>();
        for (int i = 0; i < gids.length; i++) {
            Groups groups = new Groups();
            groups.setGid(gids[i]);
            set.add(groups);
        }
        role.setGroups(set);
        try {
            if(roleServiceAdmin.insert(role)){
                setMsgAndUrl("insert.success.msg","admin.role.insert.action");
            }else{
                setMsgAndUrl("insert.failure.msg","admin.role.insert.action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String insertPre(){
        try {
            Map<String, Object> map = roleServiceAdmin.insertPre();
            getRequest().setAttribute("allGroups",map.get("allGroups"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "role.insert.page";
    }

    public void updatePre(){
        try {
            Map<String, Object> map = roleServiceAdmin.updatePre(role.getRid());
            Role role = (Role)map.get("role");
            Map<Integer,Boolean> rgids = (Map<Integer, Boolean>) map.get("gids");
            List<Groups> groups = (List<Groups>) map.get("allGroups");
            //准备json数据进行数据的返回
            JSONObject obj = new JSONObject();
            obj.put("rid",role.getRid());
            obj.put("title",role.getTitle());
            obj.put("note",role.getNote());
            Iterator<Groups> iter = groups.iterator();
            JSONArray array = new JSONArray();
            while(iter.hasNext()){
                JSONObject temp = new JSONObject();
                Groups g = iter.next();
                temp.put("gid",g.getGid());
                temp.put("title",g.getTitle());
                if (rgids.get(g.getGid())!=null){
                    temp.put("ckd","checked");
                }else{
                    temp.put("ckd","");
                }
                array.add(temp);
            }
            obj.put("groups",array);
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(){
        Set<Groups> set = new HashSet<Groups>();
        if (ugid != null){
            String result[] = ugid.split("\\|");
            for (int i = 0; i < result.length; i++) {
                Groups g = new Groups();
                g.setGid(Integer.parseInt(result[i]));
                set.add(g);
            }
        }
        role.setGroups(set);
        try {
            print(roleServiceAdmin.update(role));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String list(){
        try {
            Map<String,Object> map = roleServiceAdmin.list(getCp(),getLs(),getCol(),getKw());
            handleSplit(map.get("roleCount"),"admin.role.split.url",null,null);
            getRequest().setAttribute("allRoles",map.get("allRoles"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "role.list";
    }

    public String getDefaultColumn() {
        return "title";
    }

    public String getColumnData() {
        return "角色名称:title";
    }

    public String getTypeName() {
        return "角色";
    }

    public Role getRole() {
        return role;
    }

    public void setGids(Integer[] gids) {
        this.gids = gids;
    }

    public void setUgid(String ugid) {
        this.ugid = ugid;
    }
}

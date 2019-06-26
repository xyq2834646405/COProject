package com.xyq.action.admin;

import com.xyq.entity.Role;
import com.xyq.entity.User;
import com.xyq.service.admin.IAdminServiceAdmin;
import com.xyq.util.MD5Code;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Namespace("/pages/jsp/admin/admin")
@Action("AdminActionAdmin")
@Results(value = {
    @Result(name = "admin.insert",location = "/pages/jsp/admin/admin/admin_admin_insert.jsp"),
    @Result(name = "admin.list",location = "/pages/jsp/admin/admin/admin_admin_list.jsp"),
    @Result(name = "admin.show",location = "/pages/jsp/admin/user/admin_user_show.jsp"),
    @Result(name = "insertVF",location = "/pages/jsp/admin/admin/AdminActionAdmin!insertPre.action",type = "redirectAction"),
    @Result(name = "updateVF",location = "/pages/jsp/admin/admin/AdminActionAdmin!list.action",type = "redirectAction")
})
public class AdminActionAdmin extends AbstractAction {
    private static String insertRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int";
    private static String updateRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int";

    @Autowired
    private IAdminServiceAdmin adminServiceAdmin;
    private User user = new User();
    private Role role = new Role();
    private String ids;

    public String insertPre(){
        try {
            Map<String,Object> map = adminServiceAdmin.insertPre();
            getRequest().setAttribute("allRoles",map.get("allRoles"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin.insert";
    }

    public String insert(){
        user.setRole(role);//设置用户的角色信息
        User admin = (User)getSession().getAttribute("admin");
        user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
        try {
            if(adminServiceAdmin.insert(user,admin.getUserid())){
                setMsgAndUrl("insert.success.msg","admin.admin.insert.action");
            }else{
                setMsgAndUrl("insert.failure.msg","admin.admin.insert.action");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public void checkUserid(){
        try {
            print(adminServiceAdmin.checkUser(user.getUserid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String list(){
        try {
            Map<String, Object> map = adminServiceAdmin.list(getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("userCount"),"admin.admin.split.url",null,null);
            getRequest().setAttribute("allUsers",map.get("allUsers"));
            getRequest().setAttribute("allRoles",map.get("allRoles"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin.list";
    }

    public void updateRole(){
        try {
            user.setRole(role);
            print(adminServiceAdmin.updateRole(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(){
        try {
            user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
            print(adminServiceAdmin.updatePassword(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInfo(){
        try {
            user.setRole(role);
            print(adminServiceAdmin.update(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePre(){
        try {
            Map<String, Object> map = adminServiceAdmin.updatePre(user.getUserid());
            User u = (User) map.get("user");
            List<Role> roles = (List<Role>) map.get("allRoles");
            JSONObject obj = new JSONObject();
            obj.put("userid", u.getUserid());
            obj.put("name",u.getName());
            obj.put("phone",u.getPhone());
            obj.put("email",u.getEmail());
            obj.put("rid",u.getRole().getRid());
            obj.put("photo",u.getPhoto());
            JSONArray array = new JSONArray();
            Iterator<Role> iter = roles.iterator();
            while(iter.hasNext()){
                Role r = iter.next();
                JSONObject temp = new JSONObject();
                temp.put("rid",r.getRid());
                temp.put("title",r.getTitle());
                array.add(temp);
            }
            obj.put("roles",array);
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        Set<String> set = new HashSet<String>();
        String[] result = ids.split("\\|");
        for (int i = 0; i < result.length; i++) {
           set.add(result[i]);
        }
        try {
            adminServiceAdmin.delete(set);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String show(){
        try {
            getRequest().setAttribute("user",adminServiceAdmin.show(user.getUserid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin.show";
    }

    public String getDefaultColumn() {
        return "userid";
    }

    public String getColumnData() {
        return "用户ID:userid|真实姓名:name|电话号码:phone|EMAIL:email";
    }

    public String getTypeName() {
        return "管理员";
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}

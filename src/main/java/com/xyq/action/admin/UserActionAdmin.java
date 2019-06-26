package com.xyq.action.admin;

import com.xyq.entity.Role;
import com.xyq.entity.User;
import com.xyq.service.admin.IUserServiceAdmin;
import com.xyq.util.MD5Code;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@InterceptorRef("adminStack")
@ParentPackage("root")
@Results(value = {
    @Result(name = "user.insert",location = "/pages/jsp/admin/user/admin_user_insert.jsp"),
    @Result(name = "user.show",location = "/pages/jsp/admin/user/admin_user_show.jsp"),
    @Result(name = "user.list.active",location = "/pages/jsp/admin/user/admin_user_active_list.jsp"),
    @Result(name = "user.list.lock",location = "/pages/jsp/admin/user/admin_user_lock_list.jsp"),
    @Result(name = "insertVF",location = "/pages/jsp/admin/user/admin_user_lock_list.jsp"),
    @Result(name = "updateVF",location = "/pages/jsp/admin/user/UserActionAdmin!list.action",type = "redirectAction")
})
@Namespace("/pages/jsp/admin/user")
@Action("UserActionAdmin")
public class UserActionAdmin extends AbstractAction {
    private static String insertRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int|user.level:int";
    private static String updateRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int|user.level:int";
    private String ids;
    private User user = new User();
    private Role role = new Role();

    @Autowired
    private IUserServiceAdmin userServiceAdmin;

    public String insertPre(){
        return "user.insert";
    }

    public String insert(){
        user.setRole(role);//设置用户的角色信息
        User admin = (User)getSession().getAttribute("admin");
        user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
        try {
            if(userServiceAdmin.insert(user,admin.getUserid())){
                setMsgAndUrl("insert.success.msg","admin.user.insert.page");
            }else{
                setMsgAndUrl("insert.failure.msg","admin.user.insert.page");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public void checkUserid(){
        try {
            print(userServiceAdmin.checkUser(user.getUserid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String listActive(){
        try {
            Map<String, Object> map = userServiceAdmin.list(0, getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("userCount"),"admin.admin.split.url",null,null);
            getRequest().setAttribute("allUsers",map.get("allUsers"));
            getRequest().setAttribute("allRoles",map.get("allRoles"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user.list.active";
    }

    public String listLock(){
        try {
            Map<String, Object> map = userServiceAdmin.list(1, getCp(), getLs(), getCol(), getKw());
            handleSplit(map.get("userCount"),"admin.admin.split.url",null,null);
            getRequest().setAttribute("allUsers",map.get("allUsers"));
            getRequest().setAttribute("allRoles",map.get("allRoles"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user.list.lock";
    }

    public void updateRole(){
        try {
            user.setRole(role);
            print(userServiceAdmin.updateRole(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(){
        try {
            user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
            print(userServiceAdmin.updatePassword(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInfo(){
        try {
            user.setRole(role);
            print(userServiceAdmin.update(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePre(){
        try {
            Map<String, Object> map = userServiceAdmin.updatePre(user.getUserid());
            User u = (User) map.get("user");
            JSONObject obj = new JSONObject();
            obj.put("userid", u.getUserid());
            obj.put("name",u.getName());
            obj.put("phone",u.getPhone());
            obj.put("email",u.getEmail());
            obj.put("level",u.getLevel());
            obj.put("photo",u.getPhoto());
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateActive(){
        Set<String> set = new HashSet<String>();
        String[] result = ids.split("\\|");
        for (int i = 0; i < result.length; i++) {
            set.add(result[i]);
        }
        try {
            userServiceAdmin.updateLock(set,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLock(){
        Set<String> set = new HashSet<String>();
        String[] result = ids.split("\\|");
        for (int i = 0; i < result.length; i++) {
            set.add(result[i]);
        }
        try {
            userServiceAdmin.updateLock(set,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String show(){
        try {
            getRequest().setAttribute("user",userServiceAdmin.show(user.getUserid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user.show";
    }

    public String getDefaultColumn() {
        return "userid";
    }

    public String getColumnData() {
        return "用户ID:userid|真实姓名:name|电话号码:phone|EMAIL:email";
    }

    public String getTypeName() {
        return "用户";
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

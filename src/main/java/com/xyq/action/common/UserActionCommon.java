package com.xyq.action.common;

import com.xyq.entity.User;
import com.xyq.service.common.IUserServiceCommon;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

@Repository
@ParentPackage("root")
@Namespace("/pages/jsp/common/user")
@Action("UserActionCommon")
@InterceptorRef("commonStack")
public class UserActionCommon extends AbstractAction {
    private User user = new User();
    @Autowired
    private IUserServiceCommon userServiceCommon;

    public void show(){
        try {
            User user = userServiceCommon.show(this.user.getUserid());
            JSONObject obj = new JSONObject();
            obj.put("userid",user.getUserid());
            obj.put("photo",user.getPhoto());
            obj.put("name",user.getName());
            obj.put("phone",user.getPhone());
            obj.put("email",user.getEmail());
            obj.put("lastlogin",formatDate(user.getLastlogin()));
            obj.put("lockflag",user.getLockflag());
            obj.put("level",user.getLevel());
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDefaultColumn() {
        return null;
    }

    public String getColumnData() {
        return null;
    }

    public String getTypeName() {
        return null;
    }

    public User getUser() {
        return user;
    }
}

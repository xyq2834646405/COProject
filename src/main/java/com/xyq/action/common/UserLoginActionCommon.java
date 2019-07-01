package com.xyq.action.common;

import com.xyq.entity.User;
import com.xyq.service.common.IUserServiceCommon;
import com.xyq.util.MD5Code;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@ParentPackage("root")
@Namespace("/")
@InterceptorRef("loginStack")
@Results({
    @Result(name = "loginVF",location = "/login.jsp")
})
@Action("UserLogin")
public class UserLoginActionCommon extends AbstractAction {

    private String loginRule = "user.userid:string|user.password:string";

    @Autowired
    private IUserServiceCommon userServiceCommon;
    private User user = new User();

    public String login(){
        try {
            getSession().removeAttribute("admin");
            getSession().removeAttribute("manager");
            getSession().removeAttribute("emp");
            User resultUser = userServiceCommon.login(user.getUserid(), new MD5Code().getMD5ofStr(user.getPassword()));
            if (resultUser!=null){
                if (resultUser.getLevel()==0 || resultUser.getLevel()==1){
                    getSession().setAttribute("admin",resultUser);
                    setMsgAndUrl("user.login.success","admin.index.page");
                }else if (resultUser.getLevel()==2){
                    getSession().setAttribute("manager",resultUser);
                    setMsgAndUrl("user.login.success","manager.index.page");
                }else if (resultUser.getLevel()==3){
                    getSession().setAttribute("emp",resultUser);
                    setMsgAndUrl("user.login.success","emp.index.page");
                }
            }else {
                setMsgAndUrl("user.login.failure","login.page");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
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

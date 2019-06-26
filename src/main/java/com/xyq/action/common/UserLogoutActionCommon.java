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
@Action("UserLogout")
public class UserLogoutActionCommon extends AbstractAction {

    public String logout(){
        getSession().invalidate();
        setMsgAndUrl("user.logout.msg","login.page");
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
}

package com.xyq.action.manager;

import com.xyq.entity.User;
import com.xyq.service.common.IUserServiceCommon;
import com.xyq.util.MD5Code;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
@InterceptorRef("managerStack")
@ParentPackage("root")
@Namespace("/pages/jsp/manager/manager")
@Results(value = {
    @Result(name = "updatePasswordVF",location = "/pages/jsp/manager/manager/manager_password_edit.jsp"),
    @Result(name = "manager.updatepre",location = "/pages/jsp/manager/manager/manager_manager_update.jsp")
})
@Action("ManagerUpdateActionManager")
public class ManagerUpdateActionManager extends AbstractAction {
    private static final String updatePasswordRule= "newpassword:string|oldpassword:string";
    @Autowired
    private IUserServiceCommon userServiceCommon;
    private String newpassword;
    private String oldpassword;
    private File photo;//得到上传的文件
    private String photoContentType;//得到上传文件的类型
    private User user = new User();

    public String updatePre(){
        User user = (User) getSession().getAttribute("manager");
        try {
            getRequest().setAttribute("user",userServiceCommon.updatePre(user.getUserid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "manager.updatepre";
    }

    public String updatePassword(){
        User user = (User) getSession().getAttribute("manager");
        MD5Code md5Code = new MD5Code();
        try {
            if(userServiceCommon.updatePassword(user.getUserid(),md5Code.getMD5ofStr(this.oldpassword),md5Code.getMD5ofStr(this.newpassword))){
                setMsgAndUrl("user.password.update.success","login.page");
            }else{
                setMsgAndUrl("user.password.update.failure","login.page");
            }
            getSession().invalidate();//session失效
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String update(){
        User user = (User) getSession().getAttribute("manager");
        this.user.setUserid(user.getUserid());
        if(photo.length()>0){
            if ("nophoto.jpg".equals(this.user.getPhoto())){
                this.user.setPhoto(createSingleFileName(photoContentType));
            }
        }
        try {
            if (userServiceCommon.update(this.user)){
                if(photo.length()>0){
                    String filePath = getApplication().getRealPath("/upload/user/")+this.user.getPhoto();
                    if(saveSingleFile(filePath,photo)){
                        user.setPhoto(this.user.getPhoto());
                        getSession().setAttribute("manager",user);
                    }
                }
                setMsgAndUrl("update.success.msg","manager.user.update.action");
            }else {
                setMsgAndUrl("update.failure.msg","manager.user.update.action");
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
        return "项目经理";
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public User getUser() {
        return user;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }
}

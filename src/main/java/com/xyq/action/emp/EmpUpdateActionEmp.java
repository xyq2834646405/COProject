package com.xyq.action.emp;

import com.xyq.entity.User;
import com.xyq.service.common.IUserServiceCommon;
import com.xyq.util.MD5Code;
import com.xyq.util.action.AbstractAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
@InterceptorRef("empStack")
@ParentPackage("root")
@Namespace("/pages/jsp/emp/emp")
@Results(value = {
    @Result(name = "updatePasswordVF",location = "/pages/jsp/emp/emp/emp_password_edit.jsp"),
    @Result(name = "emp.updatepre",location = "/pages/jsp/emp/emp/emp_emp_update.jsp")
})
@Action("EmpUpdateActionEmp")
public class EmpUpdateActionEmp extends AbstractAction {
    private static final String updatePasswordRule= "newpassword:string|oldpassword:string";
    @Autowired
    private IUserServiceCommon userServiceCommon;
    private String newpassword;
    private String oldpassword;
    private File photo;//得到上传的文件
    private String photoContentType;//得到上传文件的类型
    private User user = new User();

    public String updatePre(){
        User user = (User) getSession().getAttribute("emp");
        try {
            getRequest().setAttribute("user",userServiceCommon.updatePre(user.getUserid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "emp.updatepre";
    }

    public String updatePassword(){
        User user = (User) getSession().getAttribute("emp");
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
        User user = (User) getSession().getAttribute("emp");
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
                        getSession().setAttribute("emp",user);
                    }
                }
                setMsgAndUrl("update.success.msg","emp.user.update.action");
            }else {
                setMsgAndUrl("update.failure.msg","emp.user.update.action");
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
        return "雇员";
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



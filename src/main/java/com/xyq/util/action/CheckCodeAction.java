package com.xyq.util.action;

import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage("root")
@Namespace("/")
@Action("CheckCode")
public class CheckCodeAction extends AbstractAction {
    private String code;

    public void check(){
        String rand = (String)getSession().getAttribute("rand");
        print(rand.equalsIgnoreCase(code));
    }

    public void setCode(String code) {
        this.code = code;
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

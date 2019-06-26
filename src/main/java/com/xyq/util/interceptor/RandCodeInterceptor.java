package com.xyq.util.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

public class RandCodeInterceptor extends AbstractInterceptor {
    public String intercept(ActionInvocation invocation) throws Exception {
        String rand = (String)invocation.getInvocationContext().getSession().get("rand");
        String code = ServletActionContext.getRequest().getParameter("code");
        if(code!=null){
            if(code.equalsIgnoreCase(rand)){
                return invocation.invoke();
            }else{
                ServletActionContext.getRequest().setAttribute("msg","验证码错误请重新输入验证码");
                ServletActionContext.getRequest().setAttribute("url","/login.jsp");
                return "forward.page";
            }
        }else{
            ServletActionContext.getRequest().setAttribute("msg","请输入验证码");
            ServletActionContext.getRequest().setAttribute("url","/login.jsp");
            return "forward.page";
        }
    }
}

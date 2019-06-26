package com.xyq.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import java.util.Map;

public class AdminInterceptor extends AbstractInterceptor {
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String,Object> map = invocation.getInvocationContext().getSession();
        ServletActionContext.getRequest().setAttribute("msg","你不具备操作资格,请重新登陆!");
        ServletActionContext.getRequest().setAttribute("url","/login.jsp");
        if (map.get("admin")==null){
            return "forward.page";
        }else
            return invocation.invoke();
    }
}

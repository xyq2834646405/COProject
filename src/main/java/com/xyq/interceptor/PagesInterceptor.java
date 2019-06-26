package com.xyq.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import java.util.Map;

public class PagesInterceptor extends AbstractInterceptor {
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String,Object> map = invocation.getInvocationContext().getSession();
        ServletActionContext.getRequest().setAttribute("msg","你还未登陆,请先登陆");
        ServletActionContext.getRequest().setAttribute("url","/login.jsp");
        if (map.get("admin")==null){
            if(map.get("manager")==null){
                if (map.get("emp")==null){
                    return "forward.page";//重新登陆
                }else
                    return invocation.invoke();
            }else
                return invocation.invoke();
        }else
            return invocation.invoke();
    }
}

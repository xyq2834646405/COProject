package com.xyq.util.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

public class EncodingInterceptor extends AbstractInterceptor {
    public String intercept(ActionInvocation invocation) throws Exception {
        ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
        ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
        return invocation.invoke();
    }
}

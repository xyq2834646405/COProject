package com.xyq.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/pages/*")
public class PagesFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession();
        if(session.getAttribute("admin")==null){
            if(session.getAttribute("manager")==null){
                if (session.getAttribute("emp")==null){//没有登陆
                    request.setAttribute("msg","您还未登陆,请先登陆");
                    request.setAttribute("url","/login.jsp");
                    request.getRequestDispatcher("/forward.jsp").forward(servletRequest,servletResponse);
                }else{
                    filterChain.doFilter(servletRequest,servletResponse);
                }
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    public void destroy() {

    }
}

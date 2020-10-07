package com.wm.shirodemo.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义身份验证过滤器
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,Object mappedValue){
        if (request instanceof HttpServletRequest){
            if (((HttpServletRequest)request).getMethod().toUpperCase().equals("OPTIONS")){
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * onAccessDenied
     * 该方法可指定未登录状态下访问受限资源返回指定内容
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin",((HttpServletRequest)request).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        //      身份未认证时，需要返回的数据，可以为json数据
        String result = "未登录，请先登录";
        httpServletResponse.getWriter().write(result);
        return false;
    }

}

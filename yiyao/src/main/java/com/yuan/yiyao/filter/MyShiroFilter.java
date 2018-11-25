package com.yuan.yiyao.filter;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.stereotype.Component;

import  org.apache.shiro.subject.Subject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;

/**
 * 自定义shirofilter
 */

public class MyShiroFilter extends PermissionsAuthorizationFilter  {

    /**
     * 重写PermissionsAuthorizationFilter中的isAccessAllowed方法来实现权限的控制
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws IOException
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
//        return super.isAccessAllowed(request, response, mappedValue);
        Subject subject = getSubject(request,response);
        String[] perms = (String[]) mappedValue;

        boolean b = false;
        if (perms != null){
            for (String p:perms
                    ) {
                System.out.println("自定义授权拦截器...................."+p);
                //进行判断该用户是否有该权限
                if (subject.isPermitted(p)){
                    b = true;
                }
            }
        }
        return b;
    }
}

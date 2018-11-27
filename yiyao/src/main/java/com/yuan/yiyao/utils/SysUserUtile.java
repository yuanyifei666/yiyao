package com.yuan.yiyao.utils;

import com.yuan.yiyao.user.vo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

/**
 * 得到用户的信息
 */
public class SysUserUtile {

    public static SysUser getSysUser(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();

        return user;
    }
}

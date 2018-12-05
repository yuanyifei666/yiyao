package com.yuan.yiyao.utils;

import com.yuan.yiyao.user.vo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 得到用户的信息
 */
public class SysUserUtile {

    public static SysUser getSysUser(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();

        return user;
    }

    /**
     * 生成采购单编号
     */
    public static String createBm(String sysid){
        String prefix = sysid.substring(0,8);
        String suffix = UUID.randomUUID().toString();
        return prefix+suffix.substring(0,suffix.indexOf("-"));
    }
}

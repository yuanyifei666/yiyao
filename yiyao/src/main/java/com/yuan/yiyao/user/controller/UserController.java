package com.yuan.yiyao.user.controller;

import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.user.service.UserService;
import com.yuan.yiyao.user.vo.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * 用户管理模块controller
 */
@Controller
@Api(tags = "用户管理模块的UserController")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户登录验证、
     */
    @ApiOperation(value="实现用户的认证",notes = "用户的登录验证")
    @ApiImplicitParam(name = "user",value = "用户接收请求参数",required = false,dataType = "SysUser")
    @PostMapping("/login")
    @ResponseBody
    public String login(SysUser user, HttpServletRequest request)throws Exception{
        //使用shiro进行用户的认证
        HttpSession session = request.getSession();
        //得到Subject（用户）
        Subject subject = SecurityUtils.getSubject();
        //创建UsernamePasswordToken对象
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(user.getUsername().trim());
        token.setPassword(DigestUtils.md5DigestAsHex(user.getPwd().trim().getBytes()).toCharArray());
        try{
            //进行认证
            subject.login(token);

            SysUser result = (SysUser) subject.getPrincipal();

            session.setAttribute("user",result);
            System.out.println("result----->"+result);
            //跳转到系统首页
            return "1";
        }catch (AuthenticationException ex){
            session.setAttribute("msg","用户名或者密码错误!");
            ex.printStackTrace();
        }

       /**
        try{
            //进行用户的登录验证
            SysUser result =  userService.login(user);
            //登录成功
            request.getSession().setAttribute("user",result);
            //跳转到系统首页
            return "1";
        }catch (Exception e){
            if (e instanceof MyException){
                MyException exception = (MyException) e;
                request.setAttribute("msg",exception.getMessager());
            }else{
                request.setAttribute("msg","系统繁忙，请稍后重试......");
            }
            e.printStackTrace();
        }
        */
        //登录失败，重新登录
        return "2";
    }

    /**
     * 退出登录
     */
    @ApiOperation(value = "用户退出登录",notes = "用户退出系统登录")
    @GetMapping("/userLogout")
    public String userLogout(HttpServletRequest request)throws Exception{
        //注销session
//        request.getSession().invalidate();
        Subject subject = SecurityUtils.getSubject();

        subject.logout();
        //跳转到登录页面
        return "index";
    }

    /**
     * 更新用户密码
     */
    @ApiOperation(value="修改密码",notes = "修改用户的密码")
    @ApiImplicitParam(name = "pwd",value = "要要修改后的新密码",required = true,dataType = "String")
    @PostMapping("/updatePass")
    @ResponseBody
    public String updatePass(@RequestParam(name = "pwd")String pwd , HttpServletRequest request)throws Exception{
        //得到当前登录的用户信息
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        logger.trace("updatePass--->user:"+user);
        user.setPwd(DigestUtils.md5DigestAsHex(pwd.getBytes()));
        userService.updatePwd(user);
        return "密码修改成功!";

    }

}

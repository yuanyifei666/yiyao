package com.yuan.yiyao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springmvc的扩展配置类
 */
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加默认的访问地址映射路径
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/main").setViewName("pages/main");
        //跳转到权限不足页面
        registry.addViewController("/unauthorized").setViewName("unauthorized");
        //跳转到医院管理列表页面
        registry.addViewController("/sys/userYYPage").setViewName("pages/sys/userYYList");
        //跳转到系统用户管理界面
        registry.addViewController("/sys/sysUserPage").setViewName("pages/sys/sysUserList");
      //跳转到系统权限管理界面
        registry.addViewController("/sys/operationsPage").setViewName("pages/sys/operationList");
      //跳转到角色管理界面
        registry.addViewController("/sys/sysRolePage").setViewName("pages/sys/sysRoleList");
        //跳转到供应商管理界面
        registry.addViewController("/sys/userGYSPage").setViewName("pages/sys/userGYSList");
        //跳转到供应商管理界面
        registry.addViewController("/sys/userJDPage").setViewName("pages/sys/userJDList");
     //跳转到供应商管理界面
        registry.addViewController("/sys/codePage").setViewName("pages/sys/codeList");
       //跳转到供应商管理界面
        registry.addViewController("/ypxx/ypxxPage").setViewName("pages/ypxx/ypxxList");
        //跳转到企业药品目录维护页面
        registry.addViewController("/ypxx/gysYpMLPage").setViewName("pages/ypxx/gysYpMLList");
        //跳转到企业药品目录维护页面
        registry.addViewController("/ypxx/jdYpMLPage").setViewName("pages/ypxx/jdYpMLList");
        //跳转到采购目录维护页面
        registry.addViewController("/ypxx/yyYpmlPage").setViewName("pages/ypxx/yyYpmlList");
        //跳转到采购目录维护页面
        registry.addViewController("/cgd/cgdListPage").setViewName("pages/cgd/cgdList");

        //跳转到采购查询页面
        registry.addViewController("/cgd/cgdSearch").setViewName("pages/cgd/cgdSearchList");
        //跳转到采购审核页面
        registry.addViewController("/cgd/cgdcontrolPage").setViewName("pages/cgd/cgdControlList");
         //跳转到采购受理页面
        registry.addViewController("/cgd/gysCgdPage").setViewName("pages/cgd/cgdAcceptList");
        //跳转到采购受理页面
        registry.addViewController("/cgd/yprkPage").setViewName("pages/cgd/yprkList");


    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///d:/");
    }
}

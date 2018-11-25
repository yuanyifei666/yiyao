package com.yuan.yiyao.sys.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.service.SysUserService;
import com.yuan.yiyao.sys.service.UserGYSService;
import com.yuan.yiyao.sys.service.UserJDService;
import com.yuan.yiyao.sys.service.UserYYService;
import com.yuan.yiyao.sys.vo.SysUserVo;
import com.yuan.yiyao.sys.vo.UserJD;
import com.yuan.yiyao.user.vo.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统管理controller、
 *
 */
@Api(tags = "系统用户管理controller")
@Controller
@RequestMapping("/sys")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserGYSService userGYSService;
    @Autowired
    private UserJDService userJDService;
    @Autowired
    private UserYYService userYYService;

    /**
     *使用datagrid加载用户列表信息
     */
    @ApiOperation(value = "加载系统用户的列表信息",notes = "使用datagrid分页加载系统用户的列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser",value = "用于封装sysUser查询条件的实体类",required = false,dataTypeClass = SysUser.class),
            @ApiImplicitParam(name="page",value = "当前要显示的页码",required = false ,dataTypeClass = Integer.class),
            @ApiImplicitParam(name="rows",value = "当前要显示的记录数",required = false ,dataTypeClass = Integer.class)
    })
    @GetMapping("/sysUsers")
    @ResponseBody
    public DataGridResultDTO sysUserList(SysUserVo sysUserVo , Integer page, Integer rows)throws Exception{

        System.out.println("sysUserVo:"+sysUserVo);

        //根据条件查询系统用户信息
        DataGridResultDTO resultDTO = sysUserService.findSysUserByPage(sysUserVo,new DataGridResultDTO(page,rows,null,null));

        return resultDTO;
    }
    /**
     * 判断该用户名是否存在
     */
    @ApiOperation(value="根据用户名判断该用户名是否可用",notes = "根据用户名判断该用户名是否可用")
    @ApiImplicitParam(name = "username",value = "要查询判断的用户名",required = false,dataType = "String")
    @GetMapping("/sysuser/{username}")
    @ResponseBody
    public String matchSysUserName(@PathVariable(name = "username")String username)throws Exception{
        //根据用户名 查询用户信息
        boolean f = sysUserService.findSysUserByUserName(username);
        //判断该用户名是否可用:返回1表示可用,0表示已经存在
        if (f){
            return "1";
        }else{
            return "0";
        }
    }

    /**
     * 加载单位列表信息
     */
    @ApiOperation(value="查询返回单位列表信息",notes = "查询返回单位列表信息")
    @ApiImplicitParam(name = "code",value = "要查询哪类型的单位信息",required = false,dataType = "String")
    @PostMapping("/sysid/{code}")
    @ResponseBody
    public List findBySysid(@PathVariable(name = "code")String code)throws Exception{
        System.out.println("code--->"+code);
        if ("s0100".equals(code)) {
           //系统管理员
            return null;
        }else if ("s0101".equals(code)){
            //卫生局2
           return userJDService.findAll();
        } else if ("s0104".equals(code)){
             //医院
            return userYYService.findAll();
        }else if ( "s0103".equals(code)){
            //供货商
            return userGYSService.findAll();
        }
        return null;
    }


    /**
     * 添加系统用户信息
     *
     */
    @ApiOperation(value="添加新系统用户信息",notes = "添加系统用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysUser",value = "用于封装用户的信息",dataType = "SysUser"),
            @ApiImplicitParam(name="pro_area",value = "用于封装省、市、区的信息",dataType = "String")
    })
    @PostMapping("/sysuser_add")
    @ResponseBody
    public String addSysUser(SysUser sysUser,String pro_area)throws Exception{

        System.out.println("sysUser:"+sysUser);
        //把数据保存到数据库
        sysUserService.saveSysUser(sysUser,pro_area);

        return "系统用户添加成功！";
    }


    /**
     * 根据username批量删除系统用户信息
     */
    @ApiOperation(value = "根据username批量删除用户信息",notes = "根据username批量删除用户信息")
    @ApiImplicitParam(name = "ids",value = "要删除的系统用户名",dataType = "String")
    @DeleteMapping("/sysusers_delete")
    @ResponseBody
    public String deleteSysUsers( String usernames)throws Exception{

        //批量删除系统用户信息
        String msg = sysUserService.deleteSysUsers(usernames);
        return msg;
    }

    /**
     * 恢复删除的用户信息
     */
    @ApiOperation(value = "根据username批量恢复用户信息",notes = "根据username批量恢复用户信息")
    @ApiImplicitParam(name = "ids",value = "要恢复的系统用户名",dataType = "String")
    @PutMapping("/sysusers_update")
    @ResponseBody
    public String updateUserState( String usernames)throws Exception{

        sysUserService.updateSysUserByState(usernames);
        return "系统用户恢复成功!";
    }

    /**
     * 关联角色
     */
    @ApiOperation(value = "进行用户和角色的关联",notes = "进行用户和角色的关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "usernames",value = "要关联的角色信息",dataType = "String"),
            @ApiImplicitParam(name = "roles",value = "要关联的角色id")
    })
    @PostMapping("/relationUserToRole")
    @ResponseBody
    public String relationUserToRole(String usernames
                                ,String roles[])throws Exception{

        //关联角色
        sysUserService.relationUserToRole(usernames,roles);

        return "用户管理角色成功!";
    }

}

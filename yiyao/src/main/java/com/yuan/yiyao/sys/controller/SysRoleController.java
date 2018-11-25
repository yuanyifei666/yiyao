package com.yuan.yiyao.sys.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.service.SysRoleService;
import com.yuan.yiyao.sys.vo.SysRole;
import com.yuan.yiyao.sys.vo.SysRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色管理controller
 */
@Api(tags = "系统角色管理controller")
@Controller
@RequestMapping("/sys")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     *
     */
    @ApiOperation(value="查询所有的系统角色信息",notes = "查询得到系统的所有角色列表信息")
    @GetMapping("/sysRoles")
    @ResponseBody
    public List<SysRoleVO> sysRoleList()throws Exception{

        //得到所有的系统角色列表信息
        List<SysRoleVO> list = sysRoleService.findSysRoleList();

        System.out.println("list----->"+list.size());
        return list;
    }

    /**
     * 添加角色信息
     */
    @ApiOperation(value="添加权限信息",notes = "添加权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole",value = "封装要添加的角色信息",dataTypeClass = SysRole.class),
            @ApiImplicitParam(name = "operations",value = "封装要添加的角色带有的操作权限",dataTypeClass = Integer.class)
    })
    @PostMapping("/sysRole_add")
    @ResponseBody
    public String addSysRole(SysRole sysRole, Integer operations[])throws Exception{

        System.out.println("sysRole:"+sysRole);

        //把角色的信息添加到数据库
        sysRoleService.save(sysRole,operations);

        return "权限添加成功!";
    }

    /**
     * 批量删除系统角色信息
     */
    @ApiOperation(value="批量删除权限信息",notes = "批量删除权限信息")
    @ApiImplicitParam(name = "id",value = "要删除的角色id",dataType = "String")
    @RequestMapping("/sysRole_delete")
    @ResponseBody
    public String deleteSysRoles(String id)throws Exception{

        System.out.println("delete------>ids:"+id);
        if (id == null||id==""){
            return null;
        }
        sysRoleService.deleteSysRoles(id);

        return "删除成功!";
    }

    /**
     * 更新角色的数据
     */
    @ApiOperation(value="修改权限信息",notes = "修改权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole",value = "封装要修改的角色信息",dataTypeClass = SysRole.class),
            @ApiImplicitParam(name = "operations",value = "封装要修改的角色带有的操作权限",dataTypeClass = Integer.class)
    })
    @PutMapping("/sysRole_update")
    @ResponseBody
    public String updateSysRole(SysRole sysRole, Integer operations[])throws  Exception{

        sysRoleService.updateSysRole(sysRole,operations);

        return "角色信息修改成功!";
    }

}

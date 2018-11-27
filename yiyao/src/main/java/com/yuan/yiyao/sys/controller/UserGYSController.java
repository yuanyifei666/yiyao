package com.yuan.yiyao.sys.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.service.UserGYSService;
import com.yuan.yiyao.sys.vo.UserGYS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商信息管理controller
 */
@Api(tags = "供应商信息管理controller")
@Controller
@RequestMapping("/sys")
public class UserGYSController {

    @Autowired
    private UserGYSService userGYSService;

    /**
     * 使用datagrid加载供应商列表
     */
    @ApiOperation(value = "使用datagrid加载供应商列表信息",notes = "使用datagrid加载供应商列表信息")
    @ApiImplicitParam(name = "userGYS",value = "用于接收查询条件",dataTypeClass = UserGYS.class)
    @GetMapping("/usergyss")
    @ResponseBody
    public DataGridResultDTO userGYSList(UserGYS userGYS,Integer page,Integer rows)throws Exception{

        //根据查询条件来分页查询
        DataGridResultDTO result = userGYSService.findUserGYSByPage(userGYS,page,rows);

        return result;
    }

    /**
     * 添加供应商信息
     */
    @ApiOperation(value="添加供应商信息",notes = "添加角色信息")
    @ApiImplicitParam(name = "userGYS",value = "用于接收添加供应商信息",dataTypeClass = UserGYS.class)
    @PostMapping("/usergys_add")
    @ResponseBody
    public String saveUserGYS(UserGYS userGYS)throws Exception{
        System.out.println("userGys:"+userGYS);
        //把数据添加到数据库
        userGYSService.save(userGYS);

        return "供应商信息添加成功！";
    }

    /**
     * 根据id删除供应商信息
     */
    @ApiOperation(value="删除供应商信息",notes = "删除供应商信息")
    @ApiImplicitParam(name = "id",value = "要删除的供应商id",dataTypeClass = String.class)
    @DeleteMapping("/usergys_delete")
    @ResponseBody
    public String deleteUserGYS(@RequestParam(name = "id") String id)throws Exception{
        userGYSService.deleteUserGYSById(id);
        return "供应商成功删除!";
    }

    /**
     * 更新供应商信息
     */
    @ApiOperation(value="修改供应商信息",notes = "修改供应商信息")
    @ApiImplicitParam(name = "userGYS",value = "要更新的供应商信息",dataTypeClass = UserGYS.class)
    @PutMapping("/usergys_update")
    @ResponseBody
    public String updateUserGYS(UserGYS userGYS)throws Exception{
        userGYSService.updateUserGYS(userGYS);
        return "供应商信息修改成功!";
    }

    /**
     * 查询所有的供应商单位名称
     */
    @GetMapping("/usergysall")
    @ResponseBody
    public List<UserGYS> findUsergysList()throws Exception{
        return userGYSService.findUserGysList();
    }

}

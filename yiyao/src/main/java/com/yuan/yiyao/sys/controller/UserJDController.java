package com.yuan.yiyao.sys.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.service.UserJDService;
import com.yuan.yiyao.sys.vo.UserJD;
import com.yuan.yiyao.sys.vo.UserJDVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商管理controller
 */
@Api(tags = "供应商管理controller")
@Controller
@RequestMapping("/sys")
public class UserJDController {

    @Autowired
    private UserJDService userJDService;

    /**
     * 使用datagrid加载监督单位列表信息
     */
    @ApiOperation(value="加载供应商信息列表",notes = "加载供应商列表信息")
    @GetMapping("/userjds")
    @ResponseBody
    public DataGridResultDTO userJGList(UserJD userJD,Integer page,Integer rows)throws Exception{

        //根据条件分页查询
        DataGridResultDTO resultDTO = userJDService.findByPage(userJD,new DataGridResultDTO(page,rows,null,null));

        return resultDTO;
    }

    /**
     * 添加监督单位信息
     */
    @ApiOperation(value="添加监督单位信息",notes = "添加监督单位信息")
    @ApiImplicitParam(name = "userJDVO",value = "要添加监督单位的信息",dataTypeClass = UserJD.class)
    @PostMapping("/userjd_add")
    @ResponseBody
    public String addUserJD(UserJD userJD)throws Exception{

        System.out.println("userJD---->"+userJD);
        //添加监督单位信息
        userJDService.save(userJD);
        return "监督机构信息添加成功!";
    }

    /**
     * 批量删除选择的行
     */
    @ApiOperation(value = "删除监督单位信息",notes = "根据id批量删除监督单位信息")
    @ApiImplicitParam(name = "id",value = "要删除的监督单位id",dataTypeClass = String.class)
    @DeleteMapping("/userjd_delete")
    @ResponseBody
    public String deleteUserJD(@RequestParam(name = "id")String id)throws Exception{
        //根据id批量删除监督单位信息
        userJDService.deleteById(id);
        return "删除成功!";
    }

    /**
     * 更新监督单位信息
     */
    @ApiOperation(value = "修改监督单位信息",notes = "根据修改监督单位信息")
    @ApiImplicitParam(name = "userJD",value = "要修改的监督单位信息",dataTypeClass = UserJD.class)
    @PutMapping("/userjd_update")
    @ResponseBody
    public String updateUserJD(UserJD userJD)throws Exception{

       userJDService.updateUserJD(userJD);
        return "信息修改成功!";
    }

}

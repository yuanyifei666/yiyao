package com.yuan.yiyao.sys.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.service.UserYYService;
import com.yuan.yiyao.sys.vo.UserYY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统医院信息的管理controller
 */
@Api(tags = "医院信息管理controller")
@Controller
@RequestMapping("/sys")
public class UserYYController {

    private Logger logger = LoggerFactory.getLogger(UserYYController.class);

    @Autowired
    private UserYYService userYYService;



    /**
     * 加载医院列表带有分页,带查询条件
     */
    @ApiOperation(value="加载医院列表信息",notes = "使用datagrid动态加载医院列表信息，带分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前显示的页码",required = false,dataType = "Integer"),
            @ApiImplicitParam(name="rows",value="当前页显示的记录数",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "userYY",value = "封装医院的查询条件",required = true,dataTypeClass = UserYY.class)
    })
    @GetMapping("/useryys")
    @ResponseBody
    public DataGridResultDTO userYYS(@RequestParam(name = "page",required = false)Integer page
                                ,@RequestParam(name = "rows",required = false) Integer rows,UserYY userYY)throws Exception{
        System.out.println("search:"+userYY);
        //得到医院信息列表
        DataGridResultDTO result = userYYService.findByPage(new DataGridResultDTO(page,rows,null,null),userYY);
        logger.trace("useryyList--->"+result.getTotal());
        return result;
    }

    /**
     * 添加新的医院信息
     */
    @ApiOperation(value="添加新医院信息",notes = "添加新的医院信息")
    @ApiImplicitParam(name = "userYY",value = "用于封装表单提交的医院信息",dataTypeClass = UserYY.class)
    @PostMapping("/userYY_add")
    @ResponseBody
    public String saveUserYY(UserYY userYY)throws Exception{
        logger.trace("userYY:"+userYY);
        userYYService.save(userYY);
        return "医院信息添加成功!";
    }

    /**
     * 根据医院的id批量删除医院信息
     */
    @ApiOperation(value = "根据id批量删除医院信息",notes = "根据医院信息批量删除医院信息")
    @ApiImplicitParam(name = "id",value="要批量删除的医院id",dataType = "String" ,required = false)
    @DeleteMapping("userYY_delete")
    @ResponseBody
    public String deleteUserYYs(String id)throws Exception{

        String ids[] = id.split(",");
        int fail = userYYService.deleteUserYYByIds(ids);
        String msg = "";
        if (fail == 0){
            return "成功删除"+ids.length+"条数据!";
        }
        return "成功删除"+(ids.length-fail)+"条数据,<span style='color : red;'>失败"+fail+"条数据!</span>";
    }

    /**
     * 更新医院的信息
     */
    @ApiOperation(value = "修改医院的信息",notes = "修改医院的信息")
    @ApiImplicitParam(name = "userYY",value = "更新后的医院信息对象",dataTypeClass = UserYY.class)
    @PutMapping("/userYY_update")
    @ResponseBody
    public String updateUserYY(UserYY userYY)throws Exception{

        logger.trace("userYY:"+userYY);
        userYYService.updateUserYY(userYY);

        return "成功修改医院信息!";
    }

}

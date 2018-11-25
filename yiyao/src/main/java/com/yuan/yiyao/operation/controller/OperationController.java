package com.yuan.yiyao.operation.controller;

import com.yuan.yiyao.operation.dto.OperationDTO;
import com.yuan.yiyao.operation.service.OperationService;
import com.yuan.yiyao.operation.vo.Operation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OperatorInstanceof;
import org.springframework.expression.spel.ast.OperatorNot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限 管理controller
 */
@Controller
@Api(tags = "系统权限管理")
public class OperationController {

    private Logger logger = LoggerFactory.getLogger(OperationController.class);

    @Autowired
    private OperationService operationService;

    /**
     *获取权限树
     */
    @ApiOperation(value = "加载权限树",notes = "通过父节点的id得到该节点下的子节点列表")
    @ApiImplicitParam(name = "id",value = "父节点的id",dataType = "Integer")
    @PostMapping("/operations")
    @ResponseBody
    public List<OperationDTO> operationList(@RequestParam(name = "id",required = false) Integer id,
                                                @RequestParam(name = "code")Integer code)throws Exception{
        logger.trace("operation--->id:"+id);
        List<OperationDTO> list = operationService.findByParentId(id,code);
        logger.trace("list----->"+list.size());
        return list;
    }
    /**
     * 添加权限
     */
    @ApiOperation(value="添加权限信息",notes = "添加新的权限")
    @ApiImplicitParam(name = "operation",value = "用于封装接收权限信息",dataTypeClass = Operation.class)
    @PostMapping("/operation_add")
    @ResponseBody
    public String addOperation(Operation operation)throws Exception{

        //打印日志
        logger.trace("operation:"+operation);
        operationService.save(operation);

        return "系统权限添加成功!";
    }

    /**
     * 修改权限的信息
     */
    @ApiOperation(value="修改权限信息",notes = "修改的权限")
    @ApiImplicitParam(name = "operation",value = "用于封装接收权限信息",dataTypeClass = Operation.class)
    @PutMapping("/operation_update")
    @ResponseBody
    public String updateOperation(Operation operation)throws Exception{

        logger.trace("operation:"+operation);
        operationService.updateOperationById(operation);

        return "权限修改成功!";
    }

    /**
     *根据权限的id批量删除权限信息
     */
    @ApiOperation(value="删除权限信息",notes = "删除的权限")
    @ApiImplicitParam(name = "ids",value = "用于封装接收权要删除权限的id",dataTypeClass = String.class)
    @DeleteMapping("/operation_delete")
    @ResponseBody
    public String deleteOperations(String ids)throws Exception{

        operationService.deleteOperations(ids);

        return "权限删除成功!";
    }
    /**
     * 根据code查询权限
     */
    @GetMapping("/operationMatchCode/{code}")
    @ResponseBody
    public String findOperationByCode(@PathVariable(name = "code")String code)throws Exception{
        Operation operation = operationService.findOperationByCode(code);
        if (operation !=null){
            return "0";
        }
        return "1";
    }

    /**
     * 得到所有的权限列表信息
     */
    @GetMapping("/operationsZtree")
    @ResponseBody
    public List<OperationDTO> operationsZtree()throws Exception{
        //查询所有的权限信息
        List<OperationDTO> result = operationService.findOperationByZtree();
        return result;
    }

}

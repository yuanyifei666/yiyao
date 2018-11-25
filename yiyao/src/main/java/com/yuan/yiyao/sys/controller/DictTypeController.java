package com.yuan.yiyao.sys.controller;

import com.yuan.yiyao.sys.service.DictTypeService;
import com.yuan.yiyao.sys.vo.DictInfo;
import com.yuan.yiyao.sys.vo.DictType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数字字典controller
 */
@Api(tags = "数字字典controller")
@Controller
@RequestMapping("/sys")
public class DictTypeController {

    private Logger logger = LoggerFactory.getLogger(DictTypeController.class);

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 根据数字字典表的id查询相应的数字字典明细
     */
    @ApiOperation(value="查询相应的数字明细",notes = "根据相应的code查询相应的数字字典信息")
    @ApiImplicitParam(name="code",value = "要查询数字字典明细的代码",dataType = "String")
    @RequestMapping("/dictTypes")
    @ResponseBody
    public List<DictInfo> dictTypes(@RequestParam(name = "code",required = false)String code)throws Exception{
        logger.trace("code:"+code);
        return dictTypeService.findByCode(code);
    }

    /**
     * 查询数字字典类型表
     */
    @ApiOperation(value="查询全部的数字字典类型信息列表",notes = "查询所有的字典类型信息")
    @GetMapping("/dicttypes")
    @ResponseBody
    public List<DictType> dictTypeList()throws Exception{

        return dictTypeService.findDictTypeList();
    }

    /**
     * 添加字典类型
     */
    @ApiOperation(value="添加字典类型",notes = "添加字典类型")
    @ApiImplicitParam(name = "dictType",value = "用于接收要添加的字典类型信息",dataTypeClass = DictType.class)
    @PostMapping("/dicttype_add")
    @ResponseBody
    public String addDictType(DictType dictType)throws Exception{
        //添加字典类型
        dictTypeService.insertDictType(dictType);
        return "添加成功!";
    }
    /**
     * 根据字典类型查询字典类型
     */
    @GetMapping("/dicttype/{typecode}")
    @ResponseBody
    public String findDictTypeByTypecode(@PathVariable(name = "typecode",required = false)String typecode)throws  Exception{

        if (typecode != null &&!"".equals(typecode.trim())){
            //根据字典类型查询数据
            DictType type = dictTypeService.findDictTypeByTypecode(typecode);
            if (type == null){
                //该字典代码可用
                return "0";
            }
            return "1";
        }
        return "2";
    }

    /**
     * 根据ID批量删除字典类型
     */
    @DeleteMapping("/dicttype_delete")
    @ResponseBody
    public String deleteDictType(@RequestParam(name = "id")String id)throws Exception{

        //根据id删除字典信息
        dictTypeService.deleteDictTypeById(id);
        return "删除成功!";
    }
    /**
     * 更新字典类型信息
     */
    @PutMapping("/dicttype_update")
    @ResponseBody
    public String updateDictType(DictType dictType)throws Exception{
        //修改数据库信息
        dictTypeService.updateDicitType(dictType);
        return "修改成功!";
    }

    /**
     * 添加字典信息
     */
    @PostMapping("/dictinfo_add")
    @ResponseBody
    public String addDictInfo(DictInfo dictInfo)throws Exception{
        //添加字典信息
        dictTypeService.saveDictInfo(dictInfo);
        return "添加成功!";
    }
    /**
     * 根据id删除数据
     */
    @DeleteMapping("/dictinfo_delete")
    @ResponseBody
    public String deleteDictInfo(@RequestParam(name = "id")String id)throws Exception{
        //根据id删除数据
        dictTypeService.deleteDictInfoById(id);
        return "删除成功!";
    }
    /**
     * 修改字典信息
     */
    @PutMapping("/dictinfo_update")
    @ResponseBody
    public String updateDictInfo(DictInfo dictInfo)throws Exception{
        dictTypeService.updateDictInfo(dictInfo);
        return "修改成功!";
    }
}

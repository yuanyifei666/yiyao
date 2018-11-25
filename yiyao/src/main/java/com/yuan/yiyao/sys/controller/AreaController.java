package com.yuan.yiyao.sys.controller;

import com.yuan.yiyao.sys.service.AreaService;
import com.yuan.yiyao.sys.vo.Areas;
import com.yuan.yiyao.sys.vo.Citys;
import com.yuan.yiyao.sys.vo.Provinces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 区域管理controller
 */
@Api(tags = "区域管理 controller")
@Controller
@RequestMapping("/sys")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 查询所有的省级列表
     */
    @ApiOperation(value="查询所有的省级列表信息",notes = "查询所有的省级列表信息")
    @RequestMapping("/provinces")
    @ResponseBody
    public List<Provinces> provinces()throws Exception{
        return areaService.findProvinces();
    }

    /**
     * 根据省级i查询该省下的所有城市列表
     */
    @ApiOperation(value="根据shengid查询该省下的所有城市列表",notes = "根据shengid查询该省下的所有城市列表")
    @ApiImplicitParam(name = "proId",value = "省级的id",dataType = "Integer" )
    @RequestMapping("/citys/{pro_id}")
    @ResponseBody
    public List<Citys> citys(@PathVariable(value = "pro_id")Integer proId )throws Exception{
        return areaService.findCitysByProId(proId);
    }

    /**
     * 根据城市的id查询城市下的所有区域列表
     */
    @ApiOperation(value="查询区域列表信息",notes = "根据指定的城市id查询该城市下的所有列表信息")
    @ApiImplicitParam(name = "city_id",value = "要查询城市的id",dataType = "Integer")
    @RequestMapping("/areas/{city_id}")
    @ResponseBody
    public List<Areas> areas(@PathVariable(name = "city_id")Integer city_id)throws Exception{
        return areaService.findAreasByCityId(city_id);
    }
}

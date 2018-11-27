package com.yuan.yiyao.ypxx.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import com.yuan.yiyao.ypxx.service.JdYpMLService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 药品目录的监控controller
 */
@Api(tags = "药品目录的监控controller")
@Controller
@RequestMapping("/ypxx")
public class JdYpMLController {

    @Autowired
    private JdYpMLService jdYpMLService;

    /**
     * 监督药品查询
     * @return
     * @throws Exception
     */
    @GetMapping("/jdypmls")
    @ResponseBody
    public DataGridResultDTO jdYpmlList(YpJdDTO ypJdDTO)throws Exception{

        return  jdYpMLService.findYpmlList(ypJdDTO);
    }
    /**
     * 更新药品信息状态
     */
    @PutMapping("/jdypml_update")
    @ResponseBody
    public String updateJdYpmlControl(YpJdDTO ypJdDTO)throws Exception{
        //TODO 继续完成药品监控功能
        jdYpMLService.updateJdYpmlControl(ypJdDTO);
        return "修改成功!";
    }
}

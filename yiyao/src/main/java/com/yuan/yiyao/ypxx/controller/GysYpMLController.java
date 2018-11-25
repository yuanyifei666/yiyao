package com.yuan.yiyao.ypxx.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.service.GysYpMLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 企业药品目录维护controller
 */
@Api(tags = "企业药品目录维护controller")
@Controller
@RequestMapping("/ypxx")
public class GysYpMLController {

    @Autowired
    private GysYpMLService gysYpMLService;

    /**
     *  加载当前供应企业的药品目录信息
     */
    @GetMapping("/gysYps")
    @ResponseBody
    public DataGridResultDTO findByGysYpML(HttpServletRequest request, YpxxDTO ypxxDTO)throws Exception{
        //得到当前登录的用户信息
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("user");
        if(sysUser !=null){
            ypxxDTO.setSysid(sysUser.getSysid());
            DataGridResultDTO resultDTO = gysYpMLService.findByGysYpML(ypxxDTO);
            return resultDTO;
        }
        return null;
    }

}

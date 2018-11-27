package com.yuan.yiyao.ypxx.controller;

import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.service.GysYpMLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            ypxxDTO.setSysid(getSysId(request));
            DataGridResultDTO resultDTO = gysYpMLService.findByGysYpML(ypxxDTO);
            return resultDTO;

    }

    /**
     * 加载供应药品添加页面药品信息
     */
    @GetMapping("/ypxxsbygys")
    @ResponseBody
    public DataGridResultDTO findYpxxByGYS(HttpServletRequest request,YpxxDTO ypxxDTO)throws Exception{
        ypxxDTO.setSysid(getSysId(request));
        return gysYpMLService.findYpxxByGYS(ypxxDTO);
    }

    /**
     * 供应药品信息的添加
     * @param ypxxids 要添加的药品信息
     * @return
     * @throws Exception
     */
    @PostMapping("/gysypxx_add")
    @ResponseBody
    public String addGysYpxx(HttpServletRequest request,String ypxxids[])throws Exception{
        try{
            //添加药品信息
            gysYpMLService.addGysYpxx(ypxxids,getSysId(request));
            return "供应药品信息添加成功!";
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof MyException){
                String msg =((MyException) e).getMessager();
                return "流水号为：<font style='color : red;'>"+msg+"</font>的药品状态为暂停，不能供应!";
            }
           return "系统繁忙，请稍后重试.......";
        }
    }

    /**
     * 得到当前用户的单位id
     */
    public String getSysId(HttpServletRequest request){
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if (user != null){
            return user.getSysid();
        }
        return "";
    }

    /**
     * 删除不供应的药品信息
     * @param ypxxids 药品id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/gysypxx_delete")
    @ResponseBody
    public String deleteGysYpxx(String ypxxids[])throws Exception{
        //删除不供应的药品信息
        gysYpMLService.deleteGysYpxx(ypxxids);
        return "成功移除不供应的药品！";
    }

    /**
     * 更新药品信息
     * @param ypxxDTO 药品信息
     * @return
     * @throws Exception
     */
    @PutMapping("/gysypxx_update")
    @ResponseBody
    public String updateGysYpxx(HttpServletRequest request,YpxxDTO ypxxDTO)throws Exception{
        //得到当前用户的单位名称
        ypxxDTO.setSysid(getSysId(request));
        gysYpMLService.updateGysYpxx(ypxxDTO);
        return "修改成功!";
    }
}

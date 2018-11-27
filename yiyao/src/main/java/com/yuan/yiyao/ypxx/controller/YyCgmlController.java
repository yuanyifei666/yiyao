package com.yuan.yiyao.ypxx.controller;

import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.utils.SysUserUtile;
import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import com.yuan.yiyao.ypxx.dto.YyCgYpmlDTO;
import com.yuan.yiyao.ypxx.service.JdYpMLService;
import com.yuan.yiyao.ypxx.service.YyCgmlService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 采购目录维护
 */
@Api(tags = "医院采购药品目录维护controller")
@Controller
@RequestMapping("/ypxx")
public class YyCgmlController {

    @Autowired
    private YyCgmlService yyCgmlService;


    /**
     * 查询医采购药品目录
     * @return
     * @throws Exception
     */
    @GetMapping("/yycgmls")
    @ResponseBody
    public DataGridResultDTO yyypcgmlList(HttpServletRequest request, YyCgYpmlDTO yyCgYpmlDTO)throws Exception{
        //查询当前登录的医院用户的采购药品目录
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            yyCgYpmlDTO.setSysid(user.getSysid());
        }

        return yyCgmlService.yyypcgmlList(yyCgYpmlDTO);
    }

    /**
     * 采购药品添加查询
     * @param ypJdDTO
     * @return
     * @throws Exception
     */
    @GetMapping("/cgypxxs")
    @ResponseBody
    public DataGridResultDTO cgypxxList(YpJdDTO ypJdDTO)throws Exception{
        return yyCgmlService.findYpmlListNotInCgml(ypJdDTO);
    }

    /**
     * 医院采购药品添加
     * @param cgxx 要添加的药品信息
     * @return
     * @throws Exception
     */
    @PostMapping("/yycgypml_add")
     @ResponseBody
    public String saveYyCgypxx(HttpServletRequest request,String cgxx[])throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            YyCgYpmlDTO dto = new YyCgYpmlDTO();
            dto.setSysid(user.getSysid());
            try{
                //添加采购药品信息
                yyCgmlService.saveCgypml(dto,cgxx);
                return "采购药品添加成功!";
            }catch (Exception e){
                e.printStackTrace();
                if (e instanceof MyException){
                    String msg = ((MyException)e).getMessager();
                    return "流水号为:<font style='color : red;'>"+msg+"</font>的药品暂停交易，不能添加!";
                }
            }
        }
        return "非法用户,采购药品添加失败!";
    }

    @DeleteMapping("/yycgypml_delete")
    @ResponseBody
    public String deleteYyCgypml(String ids[])throws Exception{
        if (ids == null || ids.length == 0){
            return "亲选择要删除的药品信息!";
        }
        yyCgmlService.deleteYyCgypml(ids);
        return "药品成功移除!";
    }
}

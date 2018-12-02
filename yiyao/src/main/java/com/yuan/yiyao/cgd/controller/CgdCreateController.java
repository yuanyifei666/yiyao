package com.yuan.yiyao.cgd.controller;

import com.yuan.yiyao.cgd.dto.CgdDTO;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.service.CgdCreateService;
import com.yuan.yiyao.cgd.vo.CgdVO;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.service.UserYYService;
import com.yuan.yiyao.sys.vo.UserYY;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.utils.SysUserUtile;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 采购单创建controller
 */
@Api(tags = "医院采购单 创建controller")
@Controller
@RequestMapping("/cgd")
public class CgdCreateController {

    @Autowired
    private UserYYService userYYService;
    @Autowired
    private CgdCreateService cgdCreateService;

    /**
     * 跳转到添加采购单页面
     */
    @RequestMapping("/cgdCreatePage")
    public String toCreateCgdPage(HttpServletRequest request)throws Exception{
        SysUser user = SysUserUtile.getSysUser(request);
        CgdDTO cgdDTO = new CgdDTO();
        if (user != null){
            //判断当前用户是否是医院用户
            if ("s0104".equals(user.getGroupid())){
                UserYY userYY = userYYService.findById(user.getSysid());
                //得到当前用户的单位信息
                if (userYY != null){
                    cgdDTO = cgdCreateService.createCgdMessage(userYY);
                }
            }
        }
        request.setAttribute("cgd",cgdDTO);
        return "pages/cgd/createCgd";
    }

    /**
     * 保存采购药品基本信息
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("/cgdxx_save")
    @ResponseBody
    public String saveCgdXX(CgdVO cgdVo)throws Exception{
        if (cgdVo != null){
            //根据id查询该采购单是否存在
            CgdVO cgdVO =  cgdCreateService.findCgdById(cgdVo.getId());
            if (cgdVO == null){
                //添加采购单信息
                cgdCreateService.saveCgdXX(cgdVo);
                return "采购药品基本信息添加成功!";
            }else{
                //修改采购单信息
                cgdCreateService.updateCgdXX(cgdVo);
                return "采购药品基本修改成功!";
            }
        }
       return "非法的用户信息";
    }

    /**
     * 添加采购药品明细
     * @param ypxxids 药品id和供应企业id
     * @param cgdid 采购单id
     * @return
     * @throws Exception
     */
    @PostMapping("/cgdmx_add")
    @ResponseBody
    public String addCgdMx(String ypxxids[],String cgdid)throws Exception{

        try{
            //添加采购药品明细
             cgdCreateService.addCgdMx(ypxxids,cgdid);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof MyException){
                String msg = ((MyException)e).getMessager();
                return msg;
            }
        }
        return "<font style='color: green;'> 成功添加采购药品!</font>";
    }

    /**
     * 采购单明细列表查询
     * @return
     * @throws Exception
     */
    @GetMapping("/cgdmxs")
    @ResponseBody
    public DataGridResultDTO cgdmxList(CgdmxDTO cgdmxDTO)throws Exception{

        DataGridResultDTO resultDTO = cgdCreateService.findCgdmxList(cgdmxDTO);
        return resultDTO;
    }

    /**
     * 根据id删除采购单明细
     * @param cgdmxids
     * @return
     * @throws Exception
     */
    @DeleteMapping("/cgdmx_delete")
    @ResponseBody
    public String deleteCgdmx(String cgdmxids[])throws Exception{
        cgdCreateService.deleteCgdmx(cgdmxids);
        return "删除成功!";
    }


}

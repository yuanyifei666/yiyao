package com.yuan.yiyao.cgd.controller;

import com.yuan.yiyao.cgd.dto.CgdDTO;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.dto.GysCgdDTO;
import com.yuan.yiyao.cgd.service.CgdCreateService;
import com.yuan.yiyao.cgd.service.YyCgdService;
import com.yuan.yiyao.cgd.vo.CgdMxVO;
import com.yuan.yiyao.cgd.vo.CgdVO;
import com.yuan.yiyao.cgd.vo.YpRkVO;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.utils.SysUserUtile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 医院采购单维护controller
 */
@Controller
@RequestMapping("/cgd")
public class YyCgdController {

    @Autowired
    private YyCgdService yyCgdService;
    @Autowired
    private CgdCreateService cgdCreateService;

    /**
     * 医院采购单查询
     * @return
     * @throws Exception
     */
    @GetMapping("/yycgds")
    @ResponseBody
    public DataGridResultDTO findCgdList(CgdDTO cgdDTO, HttpServletRequest request)throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            cgdDTO.setUseryyid(user.getSysid());
        }
        DataGridResultDTO resultDTO = yyCgdService.findCgdList(cgdDTO);
        return resultDTO;
    }

    @DeleteMapping("/yycgd_delete")
    @ResponseBody
    public String deleteCgdById(String cgdids[])throws Exception{
        yyCgdService.deleteCgdByIds(cgdids);
        return "采购单删除成功！";
    }


    /**
     * 采购单修改
     * @param request
     * @param cgdid 采购单id
     * @return
     * @throws Exception
     */
    @GetMapping("/cgdEditPage")
    public String toEditCgdPage(HttpServletRequest request,String cgdid)throws Exception{
        //准备该采购单基本信息
        CgdDTO cgdDTO = yyCgdService.findCgdById(cgdid);
        request.setAttribute("cgd",cgdDTO);
        return "pages/cgd/cgdEditPage";
    }

    /**
     * 更新采购量
     */
    @PutMapping("/cgl_update")
    @ResponseBody
    public String updateCgl(String id,Integer cgl)throws Exception{
        if (cgl != 0){
            yyCgdService.updateCgl(id,cgl);
        }
        return "需改成功";
    }

    /**
     * 提交采购单
     * @param cgdVO 采购单信息
      * @return
     * @throws Exception
     */
    @PostMapping("/yycgd_submit")
    @ResponseBody
    public String submitCgd(CgdVO cgdVO)throws Exception{
        yyCgdService.submitCgd(cgdVO);

        return "采购单提交成功!";
    }

    @GetMapping("/cgdsearch")
    @ResponseBody
    public DataGridResultDTO cgdSearchList(CgdDTO cgdDTO, HttpServletRequest request)throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            cgdDTO.setUseryyid(user.getSysid());
            cgdDTO.setGroupid(user.getGroupid());
        }
        DataGridResultDTO resultDTO = yyCgdService.findCgdList(cgdDTO);
        return resultDTO;
    }

    /**
     * 查看单个采购单页面
     * @param id 采购单id
     * @return
     * @throws Exception
     */
    @GetMapping("/yycgd")
    public String toCgdmxPage(HttpServletRequest request,String id)throws Exception{
        //根据采购单id查询采购单信息
        CgdDTO cgdDTO = yyCgdService.findCgdById(id);
        request.setAttribute("cgd",cgdDTO);

        return "pages/cgd/cgdmxPage";
    }

    /**
     * 监督单位采购单控制查询
     * @return
     * @throws Exception
     */
    @GetMapping("/jdcgdcontrols")
    @ResponseBody
    public DataGridResultDTO jdCgdControlList(CgdDTO cgdDTO, HttpServletRequest request)throws Exception{
        //得到当前登录的用户类型
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            String groupid = user.getGroupid();
            if ("s0101".equals(groupid)){
                System.out.println("监督单位采购单控制------------------------");
                cgdDTO.setGroupid(groupid);
                cgdDTO.setZt("s0011");
                return yyCgdService.findCgdList(cgdDTO);
            }
        }
        //查询采购单状态为
        return null;
    }

    @GetMapping("/yycgdcontrol")
    public String toCgdControlPage(HttpServletRequest request,String id)throws Exception{
        //根据id查询采购单详细详细
        CgdDTO cgdDTO = yyCgdService.findCgdById(id);
        request.setAttribute("cgd",cgdDTO);
        return "pages/cgd/cgdcontrol";
    }

    /**
     * 采购单审核:根据采购单id查询该采购单下的所有明细
     * @param cgdmxDTO
     * @return
     * @throws Exception
     */
    @GetMapping("/jdcontrolcgdmxs")
    @ResponseBody
    public DataGridResultDTO jdControlCgdmx(CgdmxDTO cgdmxDTO)throws Exception{
        return cgdCreateService.findCgdmxList(cgdmxDTO);
    }

    /**
     * 提交采购单审核状态
     * @param cgdVO
     * @return
     * @throws Exception
     */
    @PostMapping("/cgdcontrol_submit")
    @ResponseBody
    public String submitCgdControl(CgdVO cgdVO)throws Exception{
            yyCgdService.submitControlCgd(cgdVO);

        return "采购单审核成功!";
    }

    /**
     * 供应商采购但受理
     * @return
     * @throws Exception
     */
    @GetMapping("/gyscgdmxlist")
    @ResponseBody
    public DataGridResultDTO gysCgdList(HttpServletRequest request,GysCgdDTO dto)throws Exception{
        //得到当前用户的单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user!= null){
            dto.setUsergysid(user.getSysid());
            return yyCgdService.gysCgdList(dto);
        }
        return null;
    }

    /**
     * 供应商对采购单的受理
     * @param cgdmxids 要受理的采购单id
     * @return
     * @throws Exception
     */
    @PutMapping("/gyscgd_accept")
    @ResponseBody
    public String gysCgdAccept(String cgdmx[])throws Exception{
        yyCgdService.gysCgdAccept(cgdmx);
        return "<font style='color:green'>采购单受理成功!</font>";
    }

    /**
     * 医院药品入库查询
     * @return
     * @throws Exception
     */
    @GetMapping("/yprks")
    @ResponseBody
    public DataGridResultDTO ypRkList(CgdmxDTO cgdmxDTO,HttpServletRequest request)throws Exception{
        //得到当前登录的用户信息
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            cgdmxDTO.setUseryyid(user.getSysid());
        }
        return  yyCgdService.findRkList(cgdmxDTO);
    }

    /**
     * 药品入库信息保存
     * @return
     * @throws Exception
     */
    @PostMapping("/yprk_save")
    @ResponseBody
    public String saveYprk(YpRkVO ypRkVO)throws Exception{
        yyCgdService.saveYprk(ypRkVO);
        return "<font style='color:green'>入库信息成功保存!</font>";
    }

    /**
     * 药品入库提交
     * @return
     * @throws Exception
     */
    @PostMapping("/yprk_submit")
    @ResponseBody
    public String submitYpRk(YpRkVO ypRkVO,String rks[])throws Exception{
        try{
            //提交入库
            yyCgdService.yprkSubmit(ypRkVO,rks);
            return "<font style='color : green'>批量入库采购药品成功！</font>";
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof MyException){
                String msg = ((MyException)e).getMessager();
                return "采购单编号为：<font style='color : red'>"+msg+"</font>的采购药品不能入库，请填写入库信息!";
            }
        }
        return "<font style='color : green'>系统繁忙，请稍后重试......</font>";
    }

}

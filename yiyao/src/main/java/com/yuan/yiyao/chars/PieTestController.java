package com.yuan.yiyao.chars;

import com.yuan.yiyao.chars.dto.ChartsDTO;
import com.yuan.yiyao.chars.dto.ChartsResultDTO;
import com.yuan.yiyao.chars.service.ChartService;
import com.yuan.yiyao.sys.service.UserGYSService;
import com.yuan.yiyao.sys.service.UserYYService;
import com.yuan.yiyao.sys.vo.UserGYS;
import com.yuan.yiyao.sys.vo.UserYY;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.utils.SysUserUtile;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 统计分析controller
 */
@Controller
@RequestMapping("/charts")
public class PieTestController {

    @Autowired
    private ChartService chartService;
    @Autowired
    private UserYYService userYYService;
    @Autowired
    private UserGYSService userGYSService;

    /**
     * 根据医院采购的药品进行统计

     */
    @GetMapping("/userYYPie")
    @ResponseBody
    public List<List<Object>> userYYPie()throws Exception{

       return chartService.userYYPie();
    }

    /**
     * 根据不同的医院查询统计采购药品
     */
    @GetMapping("/userYYBar")
    @ResponseBody
    public ChartsResultDTO userYYBar(String id)throws Exception{

        return chartService.findUserYYBar(id);
    }

    /**
     * 统计所有供应商供应的所有药品
     */
    @GetMapping("/userGysPie")
    @ResponseBody
    public  List<List<Object>> userGysPie()throws Exception{

        return  chartService.findByUserGysPie();
    }

    /**
     * 根据不同的供应商统计该供应商供应的药品
     */
    @GetMapping("/userGysBar")
    @ResponseBody
    public ChartsResultDTO userGysBar(String id)throws Exception{
        return chartService.findByUserGysBar(id);
    }

    /**
     * 加载所有的医院列表
     */
    @GetMapping("/useryys")
    @ResponseBody
    public  List<UserYY> userYYList()throws Exception{
        return userYYService.findAll();
    }
    /**
     * 加载所有的供应商列表
     */
    @GetMapping("/usergyss")
    @ResponseBody
    public List<UserGYS> userGYSs()throws Exception{
        return userGYSService.findAll();
    }

    /**
     * 药品采购统计查询
     * @return
     * @throws Exception
     */
    @GetMapping("/ypxxBar")
    @ResponseBody
    public ChartsResultDTO ypxxBar(HttpServletRequest request, ChartsDTO dto)throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            if ("s0104".equals(user.getGroupid())){
                //医院用户
                dto.setUseryyid(user.getSysid());
            }
            if ("s0103".equals(user.getGroupid())){
                //供应商用户
                dto.setUsergysid(user.getSysid());
                dto.setGroupid("s0103");
            }
            return chartService.ypxxBar(dto);

        }
        return null;
    }

    /**
     * 获取药品信息列表
     */
    @GetMapping("/ypxxs")
    @ResponseBody
    public List<ChartsDTO> ypxxList(HttpServletRequest request,ChartsDTO dto)throws Exception{
        //得到用户的信息
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            if ("s0104".equals(user.getGroupid())){
                //医院用户
                dto.setUseryyid(user.getSysid());
            }
            if ("s0103".equals(user.getGroupid())){
                //供应商用户
                dto.setUsergysid(user.getSysid());
            }
            //查询采购的药品信息
            return chartService.findByYpxxList(dto);
        }

        return null;
    }


}

package com.yuan.yiyao.jsd.controller;

import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.jsd.dto.JsdDTO;
import com.yuan.yiyao.jsd.dto.JsdMxDTO;
import com.yuan.yiyao.jsd.service.YyJsdService;
import com.yuan.yiyao.jsd.vo.JsdMx;
import com.yuan.yiyao.jsd.vo.JsdVO;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.utils.PaymentUtil;
import com.yuan.yiyao.utils.SysUserUtile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.springframework.boot.logging.LoggingSystem.NONE;

/**
 * 医院结算单controller
 */
@Controller
@RequestMapping("/jsd")
public class YyJsdController {

    @Autowired
    private YyJsdService yyJsdService;

    /**
     * 结算单列表查询
     * @param jsdDTO
     * @return
     * @throws Exception
     */
    @GetMapping("/yyjsds")
    @ResponseBody
    public DataGridResultDTO findJsdList(JsdDTO jsdDTO,HttpServletRequest request)throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null ){
            jsdDTO.setUseryyid(user.getSysid());
            jsdDTO.setGroupid(user.getGroupid());
            //结算单维护功能模块
            jsdDTO.setState("1");
            return yyJsdService.findJsdList(jsdDTO);
        }
        return null;
    }

    /**
     * 跳转到创建结算单页面
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdCreatePage")
    public String toJsdCreatePage(HttpServletRequest request)throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        JsdDTO dto;
        if (user != null){
            //准备初始化数据
             dto = yyJsdService.jsdPageInit(user.getSysid());
        }else{
            dto = null;
        }
        request.setAttribute("jsd",dto);
        //转发到创建结算单页面
        return "pages/jsd/createjsd";
    }

    /**
     * 保存结算单基本信息
     * @param jsdVO
     * @return
     * @throws Exception
     */
    @PostMapping("/yyjsd_save")
    @ResponseBody
    public String saveYyJsd(JsdVO jsdVO)throws Exception{
        //保存结算单基本信息
        yyJsdService.saveYyJsd(jsdVO);

        return "结算单基本信息保存成功!";
    }

    /**
     * 添加结算单明细查询
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdmxadds")
    @ResponseBody
    public DataGridResultDTO addJsdMxFind(HttpServletRequest request,  CgdmxDTO cgdmxDTO)throws Exception{
        //得到当前登录的用户单位id
        SysUser user  = SysUserUtile.getSysUser(request);
        if (user != null){
            cgdmxDTO.setUseryyid(user.getSysid());
            return yyJsdService.findJsdMx(cgdmxDTO);
        }
        return  null;
    }

    /**
     * 根据结算单id查询该结算单下的所有结算明细
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdmxs")
    @ResponseBody
    public DataGridResultDTO loadJsdMx(JsdMxDTO jsdMxDTO)throws Exception{

        return yyJsdService.loadJsdMx(jsdMxDTO);
    }

    /**
     * 添加结算单
     * @return
     * @throws Exception
     */
    @PostMapping("/yyjsd_add")
    @ResponseBody
    public String saveJsdMx(String jsdmxs[])throws Exception{
        try{
            //添加结算单
            yyJsdService.saveJsdMx(jsdmxs);
            return "<font style='color : green;'>结算单添加成功!</font>";

        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof MyException){
                String msg = ((MyException)e).getMessager();
                return "采购单编号:<font style='color: red;'>"+msg +"</font>的药品重复添加！";
            }
        }
        return "系统繁忙，请稍后重试.......";
    }

    /**
     * 跳转到结算单修改页面+
     * @return
     * @throws Exception
     */
    @RequestMapping("/jsdEditPage")
    public String toEditJsdPage(HttpServletRequest request,String yyjsdid)throws Exception{
        JsdDTO jsdVO = yyJsdService.findJsdById(yyjsdid);
        //如果s受理不通过则查询受理意见
        if (jsdVO.getZt().equals("3")){
            jsdVO.setState("3");
        }else{
            jsdVO.setState("2");
        }
        request.setAttribute("jsd",jsdVO);
        return "pages/jsd/editjsd";
    }

    /**
     * 批量删除结算单明细
     * @param jsdmxids 结算单明细id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/jsdmx_delete")
    @ResponseBody
    public String deleteJsdmx(String jsdmxids[])throws Exception{
        yyJsdService.deleteJsdmx(jsdmxids);

        return "移除成功!";
    }

    /**
     * 更新结算量
     * @param jsdMx
     * @return
     * @throws Exception
     */
    @PutMapping("/jsdmxjsl_update")
    @ResponseBody
    public String updateJsdmxByjsl(JsdMx jsdMx)throws Exception{
        yyJsdService.updateJsdmxByjsl(jsdMx);
        return "更新成功!";
    }

    /**
     * 提交结算单
     * @param jsdVO
     * @return
     * @throws Exception
     */
    @PutMapping("/yyjsd_submit")
    @ResponseBody
    public String yyJsdSubmit(JsdVO jsdVO)throws Exception{
        yyJsdService.jsdSubmit(jsdVO);
        return "结算单提交成功!";
    }

    /**
     * 批量删除结算单
     * @param jsdids
     * @return
     * @throws Exception
     */
    @DeleteMapping("/yyjsd_delete")
    @ResponseBody
    public String deleteYyJsd(String jsdids[])throws Exception{
        yyJsdService.deleteJsd(jsdids);
        return "删除成功!";
    }

    /**
     * 结算单查询功能查询
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdsearchlist")
    @ResponseBody
    public DataGridResultDTO jsdSearchList(JsdDTO jsdDTO, HttpServletRequest request)throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            jsdDTO.setState("2");
            jsdDTO.setGroupid(user.getGroupid());
            if ("s0104".equals(user.getGroupid().trim())){
                //医院单位
                jsdDTO.setUseryyid(user.getSysid());
            }
            if ("s0103".equals(user.getGroupid().trim())){
                jsdDTO.setUsergysid(user.getSysid());
            }
            return yyJsdService.findJsdList(jsdDTO);
        }

        return null;
    }

    /**
     * 跳转到查看结算单详情页面
     * @return
     */
    @GetMapping("/jsdSearchxqPage")
    public String jsdSearchxqPage(HttpServletRequest request,String yyjsdid){
        JsdDTO jsdVO = yyJsdService.findJsdById(yyjsdid);
        jsdVO.setState("2");
        request.setAttribute("jsd",jsdVO);
        return "pages/jsd/searchjsd";
    }

    /**
     * 结算单受理查询
     */
    @GetMapping("/jsdacceptlist")
    @ResponseBody
    public DataGridResultDTO jsdAcceptSearch(JsdDTO jsdDTO,HttpServletRequest request)throws Exception{
        //得到单位的id
        SysUser user = SysUserUtile.getSysUser(request);
         if (user != null){
             if ("s0103".equals(user.getGroupid().trim())){
                 jsdDTO.setState("3");
                 jsdDTO.setUsergysid(user.getSysid());
                 return yyJsdService.findJsdList(jsdDTO);
             }
         }

        return null;
    }

    /**
     * 结算单受理页面
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdAcceptPage")
    public  String toJsdAccept(String yyjsdid ,HttpServletRequest request)throws Exception{
        JsdDTO jsdVO = yyJsdService.findJsdById(yyjsdid);
        jsdVO.setState("3");
        request.setAttribute("jsd",jsdVO);

        return "pages/jsd/searchjsd";
    }

    /**
     * 结算单的受理提交
     * @param jsdVO
     * @return
     * @throws Exception
     */
    @PutMapping("/yyjsdaccept_submit")
    @ResponseBody
    public  String jsdAcceptSubmit(JsdVO jsdVO)throws Exception{
        yyJsdService.jsdAcceptSubmit(jsdVO);
        return "结算单受理提交成功!";
    }

    /**
     * 结算单支付
     * @param jsdDTO
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdzflist")
    @ResponseBody
    public DataGridResultDTO yyjsdZfList(JsdDTO jsdDTO,HttpServletRequest request)throws Exception{
        //得到当前登录的用户单位id
        SysUser user = SysUserUtile.getSysUser(request);
        if (user != null){
            if ("s0104".equals(user.getGroupid())){
                jsdDTO.setState("4");
                jsdDTO.setUseryyid(user.getSysid());
                return yyJsdService.findJsdList(jsdDTO);
            }
        }
        return null;
    }

    /**
     * 结算单支付
     * @param yyjsdid
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdzfPage")
    public String toJsdZfPage(String yyjsdid ,HttpServletRequest request)throws  Exception{
        JsdDTO jsdVO = yyJsdService.findJsdById(yyjsdid);
        request.setAttribute("jsd",jsdVO);

        return "pages/jsd/jsdPay";
    }

    /**
     * 结算单进行支付

     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/jsdpay")
    public void toJsdPay(@RequestParam(name = "pd_FrpId") String pd_Id,String id,HttpServletRequest request,HttpServletResponse response)throws Exception{
        System.out.println("pd_FrpId---->"+id);
        //封装信息转发到第三方支付公司
        String p0_Cmd = "Buy";
        String p1_MerId = "10001126856";// 真实
        String p2_Order = id;
        String p3_Amt = "0.01";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        String p8_Url = "http://localhost/jsd/callback";
        String p9_SAF = "";
        String pa_MP = "";
        String pd_FrpId = pd_Id;
        String pr_NeedResponse = "1";

        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur,
                p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId,
                pr_NeedResponse, keyValue);

        //封装好重定向到第三方支付公司的url
        StringBuffer buffer = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node");
        buffer.append("?pd_FrpId=").append(pd_FrpId);
        buffer.append("&p0_Cmd=").append(p0_Cmd);
        buffer.append("&p1_MerId=").append(p1_MerId);
        buffer.append("&p2_Order=").append(p2_Order);
        buffer.append("&p3_Amt=").append(p3_Amt);
        buffer.append("&p4_Cur=").append(p4_Cur);
        buffer.append("&p5_Pid=").append(p5_Pid);
        buffer.append("&p6_Pcat=").append(p6_Pcat);
        buffer.append("&p7_Pdesc=").append(p7_Pdesc);
        buffer.append("&p8_Url=").append(p8_Url);
        buffer.append("&p9_SAF=").append(p9_SAF);
        buffer.append("&pa_MP=").append(pa_MP);
        buffer.append("&pr_NeedResponse=").append(pr_NeedResponse);
        buffer.append("&hmac=").append(hmac);

        //重定向到易宝支付平台
        String redirect_url = buffer.toString();
        System.out.println("redirect_url--->"+redirect_url);
        response.sendRedirect(redirect_url);
//        return "pages/jsd/yyjsdzfList";
    }

    /**
     * 结算但支付回调
     */
    @RequestMapping("/callback")
    public  String callback(HttpServletResponse response,HttpServletRequest request)throws Exception{
        System.out.println("callback =================");
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");

        // hmac
        String hmac = request.getParameter("hmac");
        // 利用本地密钥和加密算法 加密数据
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if (isValid) {
            // 有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向
//                response.setContentType("text/html;charset=utf-8");
//                response.getWriter().println(
//                        "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
                System.out.println("支付成功!-->"+r6_Order);
                //更新结算单的状态
                yyJsdService.updateJsdZt(r6_Order);

                return "forward:/jsd/yyjsdzfPage?pay=1";

            } else if (r9_BType.equals("2")) {

                // 服务器点对点，来自于易宝的通知
                System.out.println("收到易宝通知，修改订单状态！");//
                // 回复给易宝success，如果不回复，易宝会一直通知
                response.getWriter().print("success");
            }

        } else {
            throw new RuntimeException("数据被篡改！");
        }
        return "pages/jsd/yyjsdzfList";
    }

}

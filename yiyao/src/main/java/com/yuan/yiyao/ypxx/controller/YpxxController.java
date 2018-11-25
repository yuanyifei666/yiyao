package com.yuan.yiyao.ypxx.controller;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.utils.ExcelExportSXXSSF;
import com.yuan.yiyao.utils.HxlsOptRowsInterface;
import com.yuan.yiyao.utils.HxlsRead;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.service.YpxxService;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 药瓶信息维护controller
 */
@Api(tags = "药品信息维护controller")
@Controller
@RequestMapping("/ypxx")
public class YpxxController {

    @Autowired
    private YpxxService  ypxxService;
    @Autowired
    private HxlsOptRowsInterface hxlsOptRowsInterface;

    /**
     * 加载药品信息列表信息
     */
    @GetMapping("/ypxxs")
    @ResponseBody
    public DataGridResultDTO findYpxxList(YpxxDTO ypxxDTO)throws Exception{
        DataGridResultDTO result =  ypxxService.findYpxxListByPage(ypxxDTO);
        return result;
    }
    /**
     * 添加药品信息
     */
    @PostMapping("/ypxx_add")
    @ResponseBody
    public String saveYpxx(Ypxx ypxx)throws Exception{
        //添加药品信息
        ypxxService.saveYpxx(ypxx);
        return "药品添加成功!";
    }

    /**
     *
     * @param ypxx 要修改的药品信息
     * @return
     * @throws Exception
     */
    @PutMapping("/ypxx_update")
    @ResponseBody
    public String updateYpxx(Ypxx ypxx)throws Exception{
        //修改药品信息
        ypxxService.updateYpxx(ypxx);
        return "药品信息修改成功!";
    }

    /**
     * 批量删除药品信息
     * @param id 要删除的药品信息
     * @return
     * @throws Exception
     */
    @DeleteMapping("/ypxx_delete")
    @ResponseBody
    public String deleteYpxx(String id)throws Exception{
        //批量删除数据
        ypxxService.deleteYpxxs(id);
        return "成功删除药品信息!";
    }

    /**
     * 实现药品信息的导出
     */
    @GetMapping("/exportYpxx")
    @ResponseBody
    public String doExportYpxx(YpxxDTO ypxxDTO)throws  Exception{
        //使用工具类导出药品信息ExcelExporySXXSSF
        //定义一个导出文件实际存放的路劲
        String filePath = "d:/";
        //定义一个虚拟路径
        String fileWebPath ="/upload/";
        //定义导出文件名的前缀
        String filePrefix = "yiyao";
        //定义导出文件的标题
        List<String > fieldNames = new ArrayList<String>();
        fieldNames.add("流水号");
        fieldNames.add("通用名");
        fieldNames.add("剂型");
        fieldNames.add("规格");
        fieldNames.add("药品类别");
        fieldNames.add("生成企业");
        fieldNames.add("商品名称");
        fieldNames.add("中标价格");
        fieldNames.add("交易状态");
        fieldNames.add("单位");

        //定义标题对应的属性名
        List<String > fieldCodes = new ArrayList<String>();
        fieldCodes.add("bm");
        fieldCodes.add("mc");
        fieldCodes.add("jx");
        fieldCodes.add("gg");
        fieldCodes.add("blmc");
        fieldCodes.add("scqymc");
        fieldCodes.add("spmc");
        fieldCodes.add("zbjg");
        fieldCodes.add("jyztmc");
        fieldCodes.add("dw");

        //自定义刷新的药品数目
        int flushRows = 100;

        ExcelExportSXXSSF sxxssf = ExcelExportSXXSSF.start(filePath, fileWebPath, filePrefix, fieldNames, fieldCodes, flushRows);
        //得到符合查询条件的药品集合
        List<YpxxDTO> ypxxDTOS = ypxxService.findYpxxListByExport(ypxxDTO);
        //使用ExcelExportSXXSSF导出药品目录
        sxxssf.writeDatasByObject(ypxxDTOS);
        //返回文件的HTTP地址
        String webpath = sxxssf.exportFile();

        System.out.println("访问路径："+webpath);
        String message = "药品信息成功导出"+ypxxDTOS.size()+",<a href=\""+webpath+"\" target=\"_blank\"><font style='color: orangered;'>点击下载</font></a>";

        return message;
    }

    /**
     * 实现药品信息的导入
     * @param ypxxFile 到导入药品信息的文件
     * @return
     * @throws Exception
     */
        @PostMapping("/importYpxx")
    @ResponseBody
    public String importYpxx(MultipartFile ypxxFile)throws Exception{

        System.out.println("ypxxFile----->"+ypxxFile);
        //定义导入信息提示
        String resultMsg = "";
        if (ypxxFile != null){
            //把上传的文件写入到本地的临时文件中
            //定义临时文件的路径
            String filePath = "d:/";
            //文件名
            String filename = UUID.randomUUID().toString()+ypxxFile.getOriginalFilename().substring(ypxxFile.getOriginalFilename().indexOf("."));
            System.out.println("filePath---->"+filename);
            //创建临时文件
            File file = new File(filePath+filename);
            ypxxFile.transferTo(file);
            //读取药品信息数据
            HxlsRead hxlsRead = new HxlsRead(file.getAbsolutePath(),1,hxlsOptRowsInterface);
            //导入数据
            hxlsRead.process();
            //得到导入成功或者失败的数据
            //得到失败的数据量
            long fail_total = hxlsRead.getOptRows_failure();
            //得到成功的数量
            long success_total = hxlsRead.getOptRows_success();
           if (fail_total == 0){
               //全部导入成功
               resultMsg += "<font style='color:green;'>药品信息全部成功导入，功导入"+success_total+"条药品数据!</font>";
           }else{
               //重新导入失败的记录
               //返回导入失败的行数据
               //得到导入错误的每一行数据
               List<List<String> > rowList = hxlsRead.getFailrows();
               //得到导人模板的标题
               List<String> rowsTitle = hxlsRead.getRowtitle();
               rowsTitle.add("错误信息");
               //得到导入失败的信息
               List<String> messList = hxlsRead.getFailmsgs();
               for (int i = 0; i < messList.size(); i++) {
                   List<String> list = rowList.get(i);
                   list.add(messList.get(i));
               }
               //把导入失败的行重新导出到一个错误的excel文件
               ExcelExportSXXSSF exportSXXSSF = ExcelExportSXXSSF.start("d:/", "/upload/", "importFail", rowsTitle, null, 100);
               for (List<String> string : rowList) {
                   exportSXXSSF.writeDatasByString(string);
               }
               //导出导入失败的数据
               String filePaht = exportSXXSSF.exportFile();
               //定义下载失败数据的链接
               String uploadFial = "<a style='color:orangered;' href=\""+filePaht+"\">点击下载</a>"+"导入失败的药品数据";
               //定义放回的信息
                resultMsg += "成功导入<font style='color:green;'>"+success_total+"条</font>数据，失败<font style='color:red;'>"+fail_total+"条</font>数据,"+uploadFial;
           }
        }else{
            resultMsg = "没有数据可以导入！";
        }

        return resultMsg;
    }
}

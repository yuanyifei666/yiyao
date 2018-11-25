package com.yuan.yiyao.ypxx.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuan.yiyao.utils.HxlsOptRowsInterface;
import com.yuan.yiyao.ypxx.repository.YpxxRepository;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 实现药品信息导入功能
 */
@Service
public class YpxxImportServiceImpl implements HxlsOptRowsInterface {

    @Autowired
    private YpxxRepository repository;


    /**
     *实现药品信息的导入功能
     * @param sheetIndex 为sheet的序号
     * @param curRow	为行号
     * @param rowlist   行数据
     * @return
     * @throws Exception
     */
    public String optRows(int sheetIndex, int curRow, List<String> rowlist) throws Exception {
        //rowlist 为解析得到的每一行药品数据
        String mc = rowlist.get(0);//通用名
        String jx = rowlist.get(1);//剂型
        String gg = rowlist.get(2);//规格
        String zbjg = rowlist.get(3);//中标价
        String scqymc = rowlist.get(4);//生产企业名称
        String spmc = rowlist.get(5);//商品名
        String jyzt = rowlist.get(6);//药品交易状态
        String dw = rowlist.get(7);//单位
        //验证解析得到的数据是否合法
        String fileMsg = "";
        if (mc == null || "".equals(mc.trim())){
            fileMsg += "通用名不能为空，";
        }
        if (zbjg == null || "".equals(zbjg.trim())){
            fileMsg += "中标价格不能为空,";
        }
        if (scqymc == null || "".equals(scqymc.trim())){
            fileMsg += "生产企业不能为空,";
        }
        if (spmc == null || "".equals(spmc.trim())){
            fileMsg += "商品名称不能为空,";
        }
        if (dw == null || "".equals(dw.trim())){
            fileMsg += "单位不能为空,";
        }
        Float zb =null;
        //校验中标价格是否合法
        if (zbjg !=null &&!"".equals(zbjg.trim())){
            try{
                zb = Float.parseFloat(zbjg);
            }catch (Exception e){
                e.printStackTrace();
                fileMsg += "输入的中标价格不合法,";
            }
        }
        System.out.println("fileMsg----->"+fileMsg);
        if (fileMsg == ""){
            //校验是否有重复的药品记录
            EntityWrapper<Ypxx> entityWrapper = new EntityWrapper();
            entityWrapper.eq("mc",mc.trim());
            entityWrapper.eq("scqymc",scqymc.trim());
            entityWrapper.eq("spmc",spmc.trim());
            entityWrapper.eq("zbjg",zbjg.trim());
            int total = repository.selectCount(entityWrapper);
            if (total == 0){
                //补全药品信息
                Ypxx ypxx = new Ypxx();
                //设置流水号
                ypxx.setBm(getRandomBm());
                ypxx.setScqymc(scqymc);
                ypxx.setSpmc(spmc);
                ypxx.setZbjg(zb);
                ypxx.setPzwh(UUID.randomUUID().toString());
                if (jyzt == null || "".equals(jyzt.trim())){
                    jyzt = "1";
                }
                ypxx.setJyzt(jyzt);
                ypxx.setDw(dw);
                ypxx.setMc(mc);
                ypxx.setJx(jx);
                ypxx.setGg(gg);
                //向数据中添加药品数据
                try{
                    repository.insert(ypxx);
                    return null;
                }catch (Exception e){
                    e.printStackTrace();
                    return "导入失败!";
                }
            }else{
                fileMsg += "添加的药品信息重复";
            }
        }
       return  fileMsg;
    }

    /**
     * 生成流水号
     */
    public String getRandomBm(){
        String pzwh = UUID.randomUUID().toString();
        String ps[] = pzwh.split("-");
        for (String p:ps
                ) {
            pzwh += p;
        }
        return pzwh;
    }

}

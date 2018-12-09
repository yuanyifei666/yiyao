package com.yuan.yiyao.chars.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuan.yiyao.cgd.repository.CgdMxRepository;
import com.yuan.yiyao.cgd.repository.CgdRepository;
import com.yuan.yiyao.chars.dto.ChartsDTO;
import com.yuan.yiyao.chars.dto.ChartsResultDTO;
import com.yuan.yiyao.chars.repository.ChartsRepository;
import com.yuan.yiyao.chars.service.ChartService;
import com.yuan.yiyao.sys.repository.UserYYRepositoryPlus;
import com.yuan.yiyao.sys.vo.UserYY;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.css.CSSCharsetRule;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析业务层
 */
@Service
@Transactional
public class ChartServiceImpl implements ChartService {

    @Autowired
    private CgdRepository cgdRepository;
    @Autowired
    private CgdMxRepository cgdMxRepository;
    @Autowired
    private UserYYRepositoryPlus userYYRepository;

    @Autowired
    private ChartsRepository repository;
    //格式化小数
    DecimalFormat format  = new DecimalFormat("##.00");

    /**
     * 根据医院统计每个医院下的所有采购药品
     * @return
     */
    public List<List<Object>> userYYPie() {
        //得到医院单位名称
        List<List<Object>> result = new ArrayList<>();
        List<ChartsDTO> list = repository.findByUserYYPie();
        if (list != null){
            int total = 0;
            for (ChartsDTO dto: list
                 ) {
                total += dto.getCgltotal();
            }
            List<Object> objects = null;

            for (ChartsDTO dto:list
                 ) {
                objects = new ArrayList<>();
                objects.add(dto.getYymc());
                double d = Double.parseDouble(format.format(((double)dto.getCgltotal()/total)*100));
                objects.add(d );
                result.add(objects);
            }

        }
        return result;
    }

    /**
     * 根据不同的医院查询该医院采购的所有药品
     * @param id 医院id
     * @return
     */
    public ChartsResultDTO findUserYYBar(String id) {

        if (id == null || "".equals(id)){
            id = "d03ad3e2-067e-11e3-8a3c-0019d2ce5116";
        }

        List<ChartsDTO> dtos = repository.findByUserYYBar(id);
        //药品名称
        List<String> ypmcs = new ArrayList<>();
        //采购量
        List<Integer> cgds = new ArrayList<>();
        //入库量
        List<Integer> rkls = new ArrayList<>();

        if (dtos != null){
            for (ChartsDTO dto :dtos) {
                ypmcs.add(dto.getYpmc());//药品名称
                //药品采购量
                if (dto.getYpcgl() == null){
                    cgds.add(0);
                }else{
                    cgds.add(dto.getYpcgl());
                }
                //药品入库量
                if (dto.getYprkl() == null){
                    rkls.add(0);
                }else{
                    rkls.add(dto.getYprkl());
                }
            }
        }
        ChartsResultDTO resultDTO = new ChartsResultDTO();
        resultDTO.setYpmcs(ypmcs);//药品名称集合
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> mapcgl = new HashMap<>();
        mapcgl.put("name","药品采购量");
        mapcgl.put("data",cgds);
        list.add(mapcgl);
        //入库量
        Map<String,Object> maprkl = new HashMap<>();
        maprkl.put("name","药品入库量");
        maprkl.put("data",rkls);
        list.add(maprkl);

        resultDTO.setTotal(list);

        return resultDTO;
    }

    /**
     * 统计所有的供应商供应的所有药品
     * @return
     */
    public List<List<Object>> findByUserGysPie() {
        List<ChartsDTO> dtos = repository.findByUserGysPie();
        List<List<Object>> result = new ArrayList<>();
        if (dtos != null){
            List<Object> list = null;
            int total = 0;
            for (ChartsDTO dto:dtos
                 ) {
                if (dto.getGyscgl() != null){
                    total += dto.getGyscgl()+10;
                }else{
                    total += 10;
                }

            }

            for (ChartsDTO dto:dtos
                 ) {
                list = new ArrayList<>();
                list.add(dto.getUsergysmc());
                if (dto.getGyscgl() != null){
                    double d = Double.parseDouble(format.format((((double)dto.getGyscgl()+10)/total)*100));
                    list.add(d);
                }else{
                    double d = Double.parseDouble(format.format(((double)10/total)*100));
                    list.add(d);
                }

                result.add(list);
            }
        }
        return result;
    }

    /**
     * 统计单个供应商的药品供应情况
     * @param id 供应商id
     * @return
     */
    public ChartsResultDTO findByUserGysBar(String id) {
        if (id == null || "".equals(id)) {
            id = "0dc94edc-08cf-11e3-8a4f-60a44cea4388";
        }
        //继续完成根据单个供应商来统计
        List<ChartsDTO> dtos = repository.findByUserGysBar(id);
        List<Integer> cgls = new ArrayList<>();
        List<String > ypmcs = new ArrayList<>();
        if (dtos != null){
            for (ChartsDTO dto:dtos
                 ) {
                ypmcs.add(dto.getYpmc());
                if (dto.getGyscgl() != null){
                    cgls.add(dto.getGyscgl());
                }else{
                    cgls.add(0);
                }
            }
        }
        List<Map<String ,Object>> list = new ArrayList<>();
        //设置数据
        Map<String,Object> map = new HashMap<>();
        map.put("name","药品供应量");
        map.put("data",cgls);
        list.add(map);

        ChartsResultDTO resultDTO = new ChartsResultDTO();
        resultDTO.setYpmcs(ypmcs);
        resultDTO.setTotal(list);
        return resultDTO;
    }

    /**
     * 药品统计
     * @return
     */
    public ChartsResultDTO ypxxBar(ChartsDTO dto) {
        //查询药品名称列表
        List<ChartsDTO> ypmcs = repository.findByYpmc(dto);

        //得到药品的采购量
        List<ChartsDTO> cgls = repository.findByYpCgl(dto);
        //得到药品的入库量
        List<ChartsDTO> rkls = repository.findByYpRkl(dto);
        //得到药品的结算量
        List<ChartsDTO> jsls = repository.findByYpJsl(dto);

        //定义数量数组
        List<Integer> cglResult = new ArrayList<>();
        List<Integer> rklResult = new ArrayList<>();
        List<Integer> jslResult = new ArrayList<>();
        //定义药品名称列表
        List<String> ypmcResult = new ArrayList<>();

        //定义标记
        boolean cglbl = false;
        boolean rklbl = false;
        boolean jslbl = false;

        if (ypmcs != null){

            for (ChartsDTO ypmc:ypmcs) {
                String ypxxid = ypmc.getYpxxid();
                ypmcResult.add(ypmc.getYpmc());

                //得到采购量
                if (cgls== null||cgls.size() ==0){
                    cglResult.add(0);
                }else{
                    for (ChartsDTO cglDto:cgls) {
                        if (ypxxid.equals(cglDto.getYpxxid())){
                            cglResult.add(cglDto.getYpcgl());
                            cglbl = true;
                        }
                    }
                    //判断该药品是否有采购量
                    if (!cglbl){
                        cglResult.add(0);
                    }
                    cglbl = false;
                }
                //得到入库量
                if (rkls== null||rkls.size() ==0){
                    rklResult.add(0);
                } else{
                    for (ChartsDTO rklDto:rkls) {
                        if (ypxxid.equals(rklDto.getYpxxid())){
                            rklResult.add(rklDto.getYprkl());
                            rklbl = true;
                        }
                    }
                    //判断该药品是否有入库量
                    if (!rklbl){
                        rklResult.add(0);
                    }
                    rklbl = false;
                }
                //得到结算量
                if (jsls== null||jsls.size() ==0){
                    jslResult.add(0);
                }else{
                    for (ChartsDTO jslDto:jsls) {
                        if (ypxxid.equals(jslDto.getYpxxid())){
                            jslResult.add(jslDto.getYpjsl());
                            jslbl = true;
                        }
                    }
                    //判断该药品是否有结算量
                    if (!jslbl){
                        jslResult.add(0);
                    }
                    jslbl = false;
                }
            }
        }
        //封装返回的页面信息
        List<Map<String ,Object>> resultData = new ArrayList<>();
        //添加采购量
        Map<String ,Object> cglMap = new HashMap<>();
        cglMap.put("name","采购量");
        cglMap.put("data",cglResult);
        resultData.add(cglMap);
        if (!"s0103".equals(dto.getGroupid())){
            //供应商不统计入库量
            //添加入库量
            Map<String,Object> rklMap = new HashMap<>();
            rklMap.put("name","入库量");
            rklMap.put("data",rklResult);
            resultData.add(rklMap);
        }

        //添加结算量
        Map<String,Object> jslMap = new HashMap<>();
        jslMap.put("name","结算量");
        jslMap.put("data",jslResult);
        resultData.add(jslMap);

        //返回页面的数据
        ChartsResultDTO resultDTO = new ChartsResultDTO();
        resultDTO.setYpmcs(ypmcResult);
        resultDTO.setTotal(resultData);

        return resultDTO;
    }

    /**
     * 查询要单位下采购 的药品信息
     * @param dto
     * @return
     */
    public List<ChartsDTO> findByYpxxList(ChartsDTO dto) {
        List<ChartsDTO> ypmcs = repository.findByYpmc(dto);
        ChartsDTO d = new ChartsDTO();
        d.setYpmc("全部药品");
        d.setYpxxid("");
        ypmcs.add(d);
        return ypmcs;
    }
}

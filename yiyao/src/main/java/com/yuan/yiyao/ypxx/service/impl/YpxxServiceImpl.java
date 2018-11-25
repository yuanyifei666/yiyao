package com.yuan.yiyao.ypxx.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.repository.YpxxRepository;
import com.yuan.yiyao.ypxx.service.YpxxService;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 药品信息维护页面业务层
 */
@Service
@Transactional
public class YpxxServiceImpl implements YpxxService {

    @Autowired
    private YpxxRepository repository;

    /**
     * 根据药品信息分页查询数据
     * @param ypxxDTO 药品信息查询条件
     * @return
     */
    public DataGridResultDTO findYpxxListByPage(YpxxDTO ypxxDTO) {
        //查询药品信息
        EntityWrapper<Ypxx> wrapper =  addWrapper(ypxxDTO);
        if (ypxxDTO != null){
            //添加查询条件
            ypxxDTO.setBegin((ypxxDTO.getPage()-1)*ypxxDTO.getRows());
        }
        System.out.println("ypxxDTO--->"+ypxxDTO);
        List<YpxxDTO> dto  = repository.findYpxxListByPage(ypxxDTO);
        //查询总记录数
        int total = repository.selectCount(wrapper);

        if (dto !=null){
            return new DataGridResultDTO(null,null,total,dto);
        }
        return new DataGridResultDTO(null,null,0,new ArrayList());
    }

    /**
     * 添加 药品信息
     * @param ypxx 药品信息
     */
    public void saveYpxx(Ypxx ypxx) {
        //生成药品的流水号
        String bm = getRandomBm();
        System.out.println("bm----------->"+bm);
        ypxx.setBm(bm);
        toTrim(ypxx);
        //保存药品信息
        repository.insert(ypxx);
    }
    /**
     * 添加查询条件
     */
    public EntityWrapper<Ypxx> addWrapper(YpxxDTO ypxxDTO){
        EntityWrapper<Ypxx> wrapper = new EntityWrapper<>();
        if (ypxxDTO !=null){
            //添加查询条件
            if (ypxxDTO.getBm()!=null&&!"".equals(ypxxDTO.getBm().trim())){
                wrapper.like("bm",ypxxDTO.getBm().trim());
            }
            if (ypxxDTO.getSpmc() !=null&&!"".equals(ypxxDTO.getSpmc().trim())){
                wrapper.like("spmc",ypxxDTO.getSpmc().trim());
            }
            if (ypxxDTO.getScqymc() !=null&&!"".equals(ypxxDTO.getScqymc().trim())){
                wrapper.like("scqymc",ypxxDTO.getScqymc().trim());
            }
            if (ypxxDTO.getJyzt() !=null&&!"".equals(ypxxDTO.getJyzt())){
                wrapper.eq("jyzt",ypxxDTO.getJyzt());
                System.out.println("jyzt------>"+ypxxDTO.getJyzt());
            }
            if (ypxxDTO.getDw()!=null && !"".equals(ypxxDTO.getDw().trim())){
                wrapper.eq("dw",ypxxDTO.getDw());
            }
            if (ypxxDTO.getJx()!=null && !"".equals(ypxxDTO.getJx().trim())){
                wrapper.eq("jx",ypxxDTO.getJx());
            }
            if (ypxxDTO.getLb()!=null && !"".equals(ypxxDTO.getLb().trim())){
                wrapper.eq("lb",ypxxDTO.getLb());
            }
            if (ypxxDTO.getStartZbjg()!=null){
                wrapper.gt("zbjg",ypxxDTO.getStartZbjg());
            }
            if (ypxxDTO.getEndZbjg()!=null){
                wrapper.lt("zbjg",ypxxDTO.getEndZbjg());
            }
        }
        return wrapper;
    }

    /**
     * 去掉空格
     */
    public void toTrim(Ypxx ypxx){
        if (ypxx !=null){
            if (ypxx.getCpsm() !=null){
                ypxx.setCpsm(ypxx.getCpsm().trim());
            }
            if (ypxx.getMc()!=null){
                ypxx.setMc(ypxx.getMc().trim());
            }
            if (ypxx.getScqymc() !=null){
                ypxx.setScqymc(ypxx.getScqymc().trim());
            }
            if (ypxx.getSpmc()!=null){
                ypxx.setSpmc(ypxx.getSpmc().trim());
            }
        }

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

    /**
     * 更新药品信息
     * @param ypxx 药品信息
     */
    public void updateYpxx(Ypxx ypxx) {
        repository.updateById(ypxx);
    }

    /**
     * 批量删除药品信息
     * @param id 要删除的药品信息
      */
    public void deleteYpxxs(String id) {
        if (id!=null){
            String ids[] = id.split(",");
            if (ids != null){
                for (String i:ids
                     ) {
                    repository.deleteById(i);
                }
            }
        }
    }
    /**
     * 导出符合查询条件的所有药品信息
     */
    @Override
    public List<YpxxDTO> findYpxxListByExport(YpxxDTO ypxxDTO) {
        //查询药品信息
        EntityWrapper<Ypxx> wrapper =  addWrapper(ypxxDTO);
        List<YpxxDTO> dto  = repository.findYpxxListByExport(ypxxDTO);
        System.out.println("ypxxDTO--->"+dto.size());
        return dto;
    }
}

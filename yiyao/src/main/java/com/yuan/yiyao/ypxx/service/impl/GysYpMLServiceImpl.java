package com.yuan.yiyao.ypxx.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.user.vo.SysUser;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.repository.GysYpMLRepository;
import com.yuan.yiyao.ypxx.repository.JdYpMLRepository;
import com.yuan.yiyao.ypxx.repository.YpxxRepository;
import com.yuan.yiyao.ypxx.service.GysYpMLService;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 企业药品目录维护业务层
 */
@Service
@Transactional
public class GysYpMLServiceImpl implements GysYpMLService {

    @Autowired
    private GysYpMLRepository repository;
    @Autowired
    private YpxxRepository ypxxRepository;
    @Autowired
    private JdYpMLRepository jdYpMLRepository;

    /**
     * 根据查询条件查询当前供货企业下的所有供应药品
     * @param
     * @return
     */
    public DataGridResultDTO findByGysYpML( YpxxDTO ypxxDTO) {
        if (ypxxDTO.getSysid() != null &&!"".equals(ypxxDTO.getSysid())){
            //设置查询条件
            ypxxDTO.setBegin((ypxxDTO.getPage()-1)*ypxxDTO.getRows());
            DataGridResultDTO resultDTO = new DataGridResultDTO();
            //给用户的单位id查询该供货企业的供货药品目录
            List<YpxxDTO> ypxxDTOS = repository.findByGysYpml(ypxxDTO);

            //查询总记录数
            int total = repository.findByGysYpmlCount(ypxxDTO);
            System.out.println("gysYpml---total:"+total);
            //封装查询信息
            resultDTO.setTotal(total);
            resultDTO.setRows(ypxxDTOS);
            return resultDTO;
        }
        return null;
    }

    /**
     * 添加供应药品信息查询
     * @param ypxxDTO
     * @return
     */
    public DataGridResultDTO findYpxxByGYS(YpxxDTO ypxxDTO) {
        if (ypxxDTO != null){
            //添加查询条件
            ypxxDTO.setBegin((ypxxDTO.getPage()-1)*ypxxDTO.getRows());
        }
        System.out.println("ypxxDTO--->"+ypxxDTO);
        List<YpxxDTO> dto  = repository.findYpxxListByPage(ypxxDTO);
        //查询总记录数
        int total = repository.findYpxxByCount(ypxxDTO);

        if (dto !=null){
            return new DataGridResultDTO(null,null,total,dto);
        }
        return new DataGridResultDTO(null,null,0,new ArrayList());
    }

    /**
     * 添加供应的药品信息
     * @param ypxxids 要供应的药品id
     */
    public void addGysYpxx(String[] ypxxids,String sysid)throws Exception {
        String msgError = "";
        for (String ypxxid:ypxxids
             ) {
            //状态为暂停的无法添加1为正常，2为暂停
            Ypxx ypxx = ypxxRepository.selectById(ypxxid);
            if ("2".equals(ypxx.getJyzt())){
                msgError += ypxx.getBm()+"、";
            }else{
                //添加供应药品信息
                //生成id
                String id = UUID.randomUUID().toString();
                repository.addGysYpxx(id,ypxxid,sysid,ypxx.getZbjg(),ypxx.getSpmc());
                //同步药品监控目录表
                jdYpMLRepository.saveJdYpxx(id,ypxxid,sysid,ypxx.getJyzt());
            }
        }
        if (msgError != ""){
            throw  new MyException(msgError);
        }
    }

    /**
     * 删除不供应的药品信息
     * @param ypxxids
     */
    public void deleteGysYpxx(String[] ypxxids) {
        for (String ypxxid:ypxxids
             ) {
            repository.deleteGysYpxx(ypxxid);
            Subject subject = SecurityUtils.getSubject();
            SysUser user = (SysUser) subject.getPrincipal();
            if (user != null){
                //得到当前用户的单位id
                String sysid = user.getSysid();
                System.out.println("-------------------sysid:"+sysid);
                //同步删除监控药品表的药品信息
                jdYpMLRepository.deleteJdYpxx(ypxxid,sysid);
            }

        }
    }

    /**
     * 更新药品信息
     * @param ypxxDTO 药品信息
     */
    public void updateGysYpxx(YpxxDTO ypxxDTO) {
        //更新药品信息
        repository.upateGysYpxx(ypxxDTO);
    }
}

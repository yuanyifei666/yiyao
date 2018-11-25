package com.yuan.yiyao.ypxx.service.impl;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.repository.GysYpMLRepository;
import com.yuan.yiyao.ypxx.service.GysYpMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 企业药品目录维护业务层
 */
@Service
@Transactional
public class GysYpMLServiceImpl implements GysYpMLService {

    @Autowired
    private GysYpMLRepository repository;

    /**
     * 查询当前供货企业下的所有供应药品
     * @param
     * @return
     */
    public DataGridResultDTO findByGysYpML( YpxxDTO ypxxDTO) {
        if (ypxxDTO.getSysid() != null &&!"".equals(ypxxDTO.getSysid())){
            //设置查询条件
            //TODO 根据查询条件分页查询供货企业目录
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
}

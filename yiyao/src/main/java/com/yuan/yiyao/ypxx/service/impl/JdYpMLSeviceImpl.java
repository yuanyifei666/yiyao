package com.yuan.yiyao.ypxx.service.impl;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import com.yuan.yiyao.ypxx.repository.JdYpMLRepository;
import com.yuan.yiyao.ypxx.service.JdYpMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.DatagramChannel;
import java.util.List;

/**
 * 药品监控业务层
 */
@Service
@Transactional
public class JdYpMLSeviceImpl implements JdYpMLService {

    @Autowired
    private JdYpMLRepository repository;

    /**
     * 监督药品查询
     * @param ypJdDTO
     * @return
     */
    public DataGridResultDTO findYpmlList(YpJdDTO ypJdDTO) {
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        //查询监控药品目录
        ypJdDTO.setBegin((ypJdDTO.getPage()-1)*ypJdDTO.getRows());
        List<YpJdDTO> rows = repository.findYpmlJdList(ypJdDTO);

        //查找总记录数
        int total = repository.findYpmlJdCount(ypJdDTO);
        resultDTO.setRows(rows);
        resultDTO.setTotal(total);
        return resultDTO;
    }

    /**
     * 更新供应药品的状态
     * @param ypJdDTO
     */
    public void updateJdYpmlControl(YpJdDTO ypJdDTO) {
        if (ypJdDTO != null) {
            if (ypJdDTO.getControl()==null||"".equals(ypJdDTO.getControl().trim())){
                ypJdDTO.setControl("1");//默认为正常
            }
            repository.updateJdYpmlControl(ypJdDTO);
        }
    }
}

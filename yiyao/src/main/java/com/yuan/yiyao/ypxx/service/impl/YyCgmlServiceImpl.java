package com.yuan.yiyao.ypxx.service.impl;

import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import com.yuan.yiyao.ypxx.dto.YyCgYpmlDTO;
import com.yuan.yiyao.ypxx.repository.JdYpMLRepository;
import com.yuan.yiyao.ypxx.repository.YpxxRepository;
import com.yuan.yiyao.ypxx.repository.YyCgmlRepository;
import com.yuan.yiyao.ypxx.service.YyCgmlService;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 医院药品采购目录维护业务层
 */
@Service
@Transactional
public class YyCgmlServiceImpl implements YyCgmlService {

    @Autowired
    private YyCgmlRepository repository;
    @Autowired
    private JdYpMLRepository jdYpMLRepository;
    @Autowired
    private YpxxRepository ypxxRepository;

    /**
     * 医院药品采购目录查询
     * @param yyCgYpmlDTO
     * @return
     */
    public DataGridResultDTO yyypcgmlList(YyCgYpmlDTO yyCgYpmlDTO) {

        //设置分页查询的开始位置
        if (yyCgYpmlDTO != null ){
            yyCgYpmlDTO.setBegin((yyCgYpmlDTO.getPage()-1)*yyCgYpmlDTO.getRows());
            List<YyCgYpmlDTO> rows = repository.findYpmlList(yyCgYpmlDTO);
            //查询总记录数
            int total = repository.findYpmlCount(yyCgYpmlDTO);

            DataGridResultDTO resultDTO = new DataGridResultDTO();
            resultDTO.setTotal(total);
            resultDTO.setRows(rows);

            return resultDTO;
        }else{
            return null;
        }

    }

    /**
     * 添加采购药品
     * @param dto 药品信息
     */
    public void saveCgypml(YyCgYpmlDTO dto,String cgxx[])throws Exception{
        String msg = "";
        //解析cgxx
        if (cgxx != null){
            for (String c:cgxx) {
                if (c != null){
                    String ps[] = c.split(",");
                    if (ps.length == 2){
                        dto.setYpxxid(ps[0]);
                        dto.setUsergysid(ps[1]);
                        //判断该药品信息是否可以添加
                        YpJdDTO yp = jdYpMLRepository.findYpmlJdByYpxxidAndUsergysid(dto.getYpxxid(),dto.getUsergysid());
                        if (yp != null){
                            if ("1".equals(yp.getControl())){
                                //判断该商品是否已经存在了
                                List<YyCgYpmlDTO> result = repository.findYycgypmlByYpxxidAndGysUserIdAndUsergysid(dto.getYpxxid(),dto.getUsergysid(),dto.getSysid());
                                if (result != null){
                                   System.out.println("result---------------->"+result.size());
                                }
                                //不添加重复的药品信息
                                if (result == null || result.size() == 0){
                                    //药品状态为正常
                                    //生成id
                                    String id = UUID.randomUUID().toString();
                                    dto.setId(id);
                                    //添加数据
                                    repository.saveGgypml(dto);
                                }
                            }else{
                                //不能添加
                                Ypxx ypxx = ypxxRepository.selectById(dto.getYpxxid());
                                msg += ypxx.getBm()+"、";
                            }
                        }

                    }
                }
            }
        }
        /////////////返回添加信息
        if (msg != ""){
            throw new MyException(msg);
        }
    }

    /**
     * 添加采购药品目录查询
     * @param ypJdDTO
     * @return
     */
    public DataGridResultDTO findYpmlListNotInCgml(YpJdDTO ypJdDTO) {
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        //查询监控药品目录
        ypJdDTO.setBegin((ypJdDTO.getPage()-1)*ypJdDTO.getRows());
        List<YpJdDTO> rows = jdYpMLRepository.findYpmlJdList(ypJdDTO);

        //查找总记录数
        int total = jdYpMLRepository.findYpmlJdCount(ypJdDTO);
        resultDTO.setRows(rows);
        resultDTO.setTotal(total);
        return resultDTO;
    }

    /**
     *从医院采购药品目录中删除药品信息
     * @param ids
     */
    public void deleteYyCgypml(String[] ids) {
        for (String id:ids
             ) {
            //根据id删除采购药品信息
            repository.deleteYyCgypmlById(id);
        }
    }
}

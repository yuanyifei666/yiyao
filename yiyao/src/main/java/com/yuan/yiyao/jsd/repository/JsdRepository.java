package com.yuan.yiyao.jsd.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.jsd.dto.JsdDTO;
import com.yuan.yiyao.jsd.vo.JsdVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 医院结算单数据方位层
 */
@Mapper
public interface JsdRepository  extends BaseMapper<JsdVO> {

    /**
     * 根据结算单id查询
     * @return
     */
    @Select("SELECT *FROM YYJSD WHERE ID = #{param1}")
    public JsdDTO findJsdById(String yyjsdid);


}

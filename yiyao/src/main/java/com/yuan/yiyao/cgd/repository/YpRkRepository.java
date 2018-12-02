package com.yuan.yiyao.cgd.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.cgd.vo.YpRkVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 药品入库数据访问层
 */
@Mapper
public interface YpRkRepository extends BaseMapper<YpRkVO> {


}

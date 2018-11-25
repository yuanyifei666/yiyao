package com.yuan.yiyao.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.sys.vo.UserYY;
import org.apache.ibatis.annotations.Mapper;

/**
 * 使用mybatis的插件进行操作UserYY表
 */
@Mapper
public interface UserYYRepositoryPlus extends BaseMapper<UserYY> {


}

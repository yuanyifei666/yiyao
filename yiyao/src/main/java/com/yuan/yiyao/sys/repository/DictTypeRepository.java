package com.yuan.yiyao.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.sys.vo.DictInfo;
import com.yuan.yiyao.sys.vo.DictType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 数字字典表的数据访问层
 */
@Mapper
public interface DictTypeRepository extends BaseMapper<DictType> {


    /**
     * 根据code查询相应的数字字典明细
     * @param code
     * @return
     */
    @Select("SELECT * FROM DICTINFO WHERE TYPECODE = #{param1};")
    List<DictInfo> findByCode(String code);

    /**
     * 根据字典类型代码查询字典列表
     * @param typecode
     * @return
     */
    @Select("SELECT ID,DICTCODE ,TYPECODE,INFO ,REMARK  FROM DICTINFO WHERE TYPECODE = #{param1};")
    List<DictInfo> findDictInfoList(String typecode);

    /**
     * 更新字典信息
     * @param info
     */
    @Update("UPDATE DICTINFO SET TYPECODE = #{param1} WHERE TYPECODE = #{param2};")
    void updateDictInfoByTypeCode(String afterTypeCode,String beforTypeCode);

    /**
     * 根据字典类型代码删除字典信息
     * @param typecode
     */
    @Delete("DELETE FROM DICTINFO WHERE TYPECODE = #{param1};")
    void deleteDictInfoByTypeCode(String typecode);
}

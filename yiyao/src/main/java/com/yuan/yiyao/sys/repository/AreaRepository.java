package com.yuan.yiyao.sys.repository;

import com.yuan.yiyao.sys.vo.Areas;
import com.yuan.yiyao.sys.vo.Citys;
import com.yuan.yiyao.sys.vo.Provinces;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 区域管理数据访问层
 */
@Mapper
public interface AreaRepository {

    /**
     * 查询所有的省级信息列表
     * @return
     */
    @Select("SELECT * FROM provinces;")
    List<Provinces> findProvinces();


    /**
     * 根据省的id查询该省份下单号所城市列表
     * @param proId
     * @return
     */
    @Select("SELECT *FROM citys WHERE PRO_ID = #{param1};")
    List<Citys> findCitysByProId(Integer proId);

    /**
     * 根据该城市id查询该城市下的所有区域列表信息
     * @param city_id
     * @return
     */
    @Select("SELECT *FROM areas WHERE CITY_ID = #{param1};")
    List<Areas> findAreaByCityId(Integer city_id);

    /**
     * 根据省的id查询该省的信息
     * @param pro_id
     * @return
     */
    @Select("SELECT * FROM provinces WHERE PRO_ID = #{param1};")
    Provinces findProvincesById(Integer pro_id);

    /**
     * 根据城市的id查询该城市的信息
     * @param city_id
     * @return
     */
    @Select("SELECT * FROM citys WHERE CITY_ID = #{param1};")
    Citys findCityById(Integer city_id);

    /**
     * 根据区域的id查询该区域的信息
     * @param area_id
     * @return
     */
    @Select("SELECT * FROM areas WHERE AREA_ID = #{param1};")
    Areas findAreaById(Integer area_id);
}

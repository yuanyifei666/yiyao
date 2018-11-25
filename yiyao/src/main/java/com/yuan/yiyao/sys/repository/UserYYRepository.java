package com.yuan.yiyao.sys.repository;

import com.yuan.yiyao.sys.vo.UserYY;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 操作医院信息列表的数据访问层
 */
@Mapper
public interface UserYYRepository {

    /**
     * 分页查询医院列表信息,带条件查询
     *  @Select("SELECT ID,MC,DZ,YZBM,DQ,JB,CWS,FYLJG,DH,YJKDH,LB,YPSR,YWSR,CZ,PRO_ID,CITY_ID,AREA_ID FROM USERYY LIMIT #{param1} ,#{param2};")
     * @return
     */
    @Select("SELECT ID,MC,DZ,YZBM,DQ,JB,CWS,FYLJG,DH,YJKDH,LB,YPSR,YWSR,CZ,PRO_ID,CITY_ID,AREA_ID FROM USERYY LIMIT #{param1} ,#{param2};")
    List<UserYY> findByPage(Integer page ,Integer pageSize,UserYY userYY);

    /**
     * 查询总记录数
     * @return
     */
    @Select("SELECT  COUNT(MC) FROM USERYY;")
    int findByCount();

    /**
     * 添加新医院信息
     * @param userYY
     */
    @Insert("INSERT INTO USERYY (ID,MC,DZ,YZBM,FYLJG,DH,LB,PRO_ID,CITY_ID,AREA_ID) VALUES(#{id},#{mc},#{dz},#{yzbm},#{fyljg},#{dh},#{lb},#{proId},#{cityId},#{areaId});")
    void save(UserYY userYY);

    /**
     * 根据医院的id删除该医院
     * @param id
     */
    @Delete("DELETE FROM USERYY WHERE ID = #{param1};")
    void deleteById(String id);

    /**
     * 修改医院的信息
     * @param result
     */
    @Update("UPDATE USERYY SET MC=#{mc},DZ=#{dz},YZBM=#{yzbm},FYLJG=#{fyljg},DH=#{dh},LB=#{lb},PRO_ID=#{proId},CITY_ID=#{cityId},AREA_ID=#{areaId} WHERE ID = #{id}")
    void updateUserYY(UserYY result);
}

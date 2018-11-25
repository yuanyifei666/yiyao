package com.yuan.yiyao.ypxx.repository;

import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 企业药品目录维护数据访问层
 */
@Mapper
public interface GysYpMLRepository {


    /**
     * 查询该供货企业下对应的所有供货药品信息
     * @param sysid 单位id
     * @return
     */
    @Select(" SELECT YPML.* FROM (  " +
            " SELECT " +
            " YP.ID,YP.BM,YP.SCQYMC,YP.ZBJG,YP.ZLCC,YP.CPSM,YP.JYZT,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICT.INFO JYZTMC,DICTLB.INFO BLMC " +
            " FROM YPXX YP " +
            " LEFT JOIN DICTINFO DICT ON YP.JYZT = DICT.DICTCODE " +
            " LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID  " +
            " WHERE DICT.TYPECODE='003' " +
            ") YPML ,GYSYPML GYS WHERE  YPML.ID = GYS. YPXXID AND GYS.USERGYSID = #{sysid} LIMIT #{begin},#{rows};")
    List<YpxxDTO> findByGysYpml(YpxxDTO ypxxDTO);

    /**
     * 查询符合条件的总记录数
     * @param ypxxDTO
     * @return
     */
    @Select(" SELECT count(*) total FROM (  " +
            " SELECT " +
            " YP.ID,YP.BM,YP.SCQYMC,YP.ZBJG,YP.ZLCC,YP.CPSM,YP.JYZT,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICT.INFO JYZTMC,DICTLB.INFO BLMC " +
            " FROM YPXX YP " +
            " LEFT JOIN DICTINFO DICT ON YP.JYZT = DICT.DICTCODE " +
            " LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID  " +
            " WHERE DICT.TYPECODE='003' " +
            ") YPML ,GYSYPML GYS WHERE  YPML.ID = GYS. YPXXID AND GYS.USERGYSID = #{sysid} ;")
    int findByGysYpmlCount(YpxxDTO ypxxDTO);
}

package com.yuan.yiyao.ypxx.repository;

import com.yuan.yiyao.ypxx.dto.GysYpDTO;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import org.apache.ibatis.annotations.*;

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
    @Select("<script> "+
            " SELECT P.* ,DICT.INFO CONTROLMC FROM ( " +
            " SELECT " +
            " YPML.* ,GYS.MONY,GYS.YPMC ,JD.ADVICE ,JD.CONTROL,JD.YPXXID,JD.USERGYSID ,USERGYS.MC GYSMC  " +
            " FROM (  " +
            " SELECT " +
            " YP.ID,YP.BM,YP.SPMC,YP.SCQYMC,YP.ZBJG,YP.ZLCC,YP.CPSM,YP.JYZT,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICTLB.INFO BLMC  " +
            "  FROM YPXX YP " +
            " LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID  " +
            "    ) YPML ,GYSYPML GYS ,GYSYPML_CONTROL JD ,USERGYS USERGYS " +
            " WHERE YPML.ID = GYS.YPXXID AND GYS.USERGYSID = JD.usergysid AND JD.YPXXID = YPML.ID  AND JD.USERGYSID = USERGYS.ID AND GYS.USERGYSID = #{sysid} " +
            " ) P LEFT JOIN DICTINFO DICT ON P.CONTROL = DICT.DICTCODE WHERE DICT.TYPECODE='003' " +
            " <when test=' bm != null and bm != \"\"'>"+
            " AND P.BM like CONCAT('%',#{bm},'%') "+
            " </when>"+
            " <when test='ypmc !=null and ypmc != \"\"'>"+
            " AND P.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
            " </when>"+
            " <when test='scqymc !=null and scqymc != \"\"'>"+
            " AND P.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
            " </when>"+
            " <when test='dw !=null and dw != \"\"'>"+
            " AND P.DW = #{dw} "+
            " </when>"+
            " <when test='jx !=null and jx != \"\"'>"+
            " AND P.JX = #{jx} "+
            " </when>"+
            " <when test='lb !=null and lb != \"\"'>"+
            " AND P.LB = #{lb} "+
            " </when>"+
            " <when test='startZbjg !=null'>"+
            " AND P.ZBJG &gt; #{startZbjg} "+
            " </when>"+
            " <when test='endZbjg !=null '>"+
            " AND P.ZBJG &lt; #{endZbjg} "+
            " </when>"+
            " <when test='startMong !=null '>"+
            " AND P.MONY &gt; #{startMong} "+
            " </when>"+
            " <when test='endMong !=null '>"+
            " AND P.MONY &lt; #{endMong} "+
            " </when>"+
            " LIMIT #{begin},#{rows};"+
            " </script> ")
    List<YpxxDTO> findByGysYpml(YpxxDTO ypxxDTO);

    /**
     * 查询符合条件的总记录数
     * @param ypxxDTO
     * @return
     */
    @Select("<script> "+
            " SELECT count(*) total FROM (  " +
            " SELECT " +
            " YP.ID,YP.BM,YP.SPMC,YP.SCQYMC,YP.ZBJG,YP.ZLCC,YP.CPSM,YP.JYZT,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICT.INFO JYZTMC,DICTLB.INFO BLMC " +
            " FROM YPXX YP " +
            " LEFT JOIN DICTINFO DICT ON YP.JYZT = DICT.DICTCODE " +
            " LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID  " +
            " WHERE DICT.TYPECODE='003' " +
            ") YPML ,GYSYPML GYS WHERE  YPML.ID = GYS. YPXXID AND GYS.USERGYSID = #{sysid} "+
            " <when test=' bm != null and bm != \"\"'>"+
            " AND YPML.BM like CONCAT('%',#{bm},'%') "+
            " </when>"+
            " <when test='ypmc !=null and ypmc != \"\"'>"+
            " AND GYS.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
            " </when>"+
            " <when test='scqymc !=null and scqymc != \"\"'>"+
            " AND YPML.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
            " </when>"+
            " <when test='dw !=null and dw != \"\"'>"+
            " AND YPML.DW = #{dw} "+
            " </when>"+
            " <when test='jx !=null and jx != \"\"'>"+
            " AND YPML.JX = #{jx} "+
            " </when>"+
            " <when test='lb !=null and lb != \"\"'>"+
            " AND YPML.LB = #{lb} "+
            " </when>"+
            " <when test='startZbjg !=null'>"+
            " AND YPML.ZBJG &gt; #{startZbjg} "+
            " </when>"+
            " <when test='endZbjg !=null '>"+
            " AND YPML.ZBJG &lt; #{endZbjg} "+
            " </when>"+
            " <when test='startMong !=null '>"+
            " AND GYS.MONY &gt; #{startMong} "+
            " </when>"+
            " <when test='endMong !=null '>"+
            " AND GYS.MONY &lt; #{endMong} "+
            " </when>"+
            " LIMIT #{begin},#{rows};"+
            " </script>")
    int findByGysYpmlCount(YpxxDTO ypxxDTO);

    /**
     * 供应药品添加查询
     * @param ypxxDTO
     * @return
     */
    @Select("<script>"+
            "SELECT YP.ID,YP.BM,YP.SPMC,YP.SCQYMC,YP.ZBJG,YP.ZLCC,YP.CPSM,YP.JYZT,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICT.INFO JYZTMC,DICTLB.INFO BLMC FROM YPXX YP " +
            " LEFT JOIN DICTINFO DICT ON YP.JYZT = DICT.DICTCODE LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID " +
            " WHERE DICT.TYPECODE='003' " +
            " <when test=' bm != null and bm != \"\"'>"+
            " AND YP.BM like CONCAT('%',#{bm},'%') "+
            " </when>"+
            " <when test='spmc !=null and spmc != \"\"'>"+
            " AND YP.SPMC LIKE CONCAT('%',#{spmc},'%') "+
            " </when>"+
            " <when test='scqymc !=null and scqymc != \"\"'>"+
            " AND YP.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
            " </when>"+
            " <when test='jyzt !=null and jyzt != \"\"'>"+
            " AND YP.JYZT = #{jyzt} "+
            " </when>"+
            " <when test='dw !=null and dw != \"\"'>"+
            " AND YP.DW = #{dw} "+
            " </when>"+
            " <when test='jx !=null and jx != \"\"'>"+
            " AND YP.JX = #{jx} "+
            " </when>"+
            " <when test='lb !=null and lb != \"\"'>"+
            " AND YP.LB = #{lb} "+
            " </when>"+
            " <when test='startZbjg !=null '>"+
            " AND YP.ZBJG &gt; #{startZbjg} "+
            " </when>"+
            " <when test='endZbjg !=null '>"+
            " AND YP.ZBJG &lt; #{endZbjg} "+
            " </when>"+
            " AND YP.ID NOT IN (SELECT YPXXID FROM GYSYPML WHERE USERGYSID = #{sysid}) "+
            " LIMIT #{begin},#{rows} "+
            "</script>")
    List<YpxxDTO> findYpxxListByPage(YpxxDTO ypxxDTO);

    /**
     * 供应药品信息添加查询总记录数
     * @param ypxxDTO
     * @return
     */
    @Select("<script>"+
            "SELECT  COUNT(YP.ID) TOTAL FROM YPXX YP " +
            " LEFT JOIN DICTINFO DICT ON YP.JYZT = DICT.DICTCODE LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID " +
            " WHERE DICT.TYPECODE='003' " +
            " <when test=' bm != null and bm != \"\"'>"+
            " AND YP.BM like CONCAT('%',#{bm},'%') "+
            " </when>"+
            " <when test='spmc !=null and spmc != \"\"'>"+
            " AND YP.SPMC LIKE CONCAT('%',#{spmc},'%') "+
            " </when>"+
            " <when test='scqymc !=null and scqymc != \"\"'>"+
            " AND YP.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
            " </when>"+
            " <when test='jyzt !=null and jyzt != \"\"'>"+
            " AND YP.JYZT = #{jyzt} "+
            " </when>"+
            " <when test='dw !=null and dw != \"\"'>"+
            " AND YP.DW = #{dw} "+
            " </when>"+
            " <when test='jx !=null and jx != \"\"'>"+
            " AND YP.JX = #{jx} "+
            " </when>"+
            " <when test='lb !=null and lb != \"\"'>"+
            " AND YP.LB = #{lb} "+
            " </when>"+
            " <when test='startZbjg !=null '>"+
            " AND YP.ZBJG &gt; #{startZbjg} "+
            " </when>"+
            " <when test='endZbjg !=null '>"+
            " AND YP.ZBJG &lt; #{endZbjg} "+
            " </when>"+
            " AND YP.ID NOT IN (SELECT YPXXID FROM GYSYPML WHERE USERGYSID = #{sysid}) "+
            "</script>")
    int findYpxxByCount(YpxxDTO ypxxDTO);

    /**
     * 添加供应药品
     * @param id
     * @param ypxxid
     * @param sysid
     */
    @Insert("INSERT INTO GYSYPML (ID,YPXXID,USERGYSID,MONY,YPMC) VALUES(#{param1},#{param2},#{param3},#{param4},#{param5});")
    void addGysYpxx(String id,String ypxxid, String sysid,Float mony,String ypmc);

    /**
     * 删除不供应的药品信息
     * @param ypxxid
     */
    @Delete("DELETE FROM GYSYPML WHERE YPXXID = #{param1};")
    void deleteGysYpxx(String ypxxid);

    /**
     * 更新药品信息
     * @param ypxxDTO
     */
    @Update("UPDATE GYSYPML SET MONY = #{mony} , YPMC = #{ypmc} WHERE YPXXID = #{id} AND USERGYSID= #{sysid};")
    void upateGysYpxx(YpxxDTO ypxxDTO);

    /**
     * 根据药品id和供应企业id查询
     * @param ypxxid
     * @param usergysid
     * @return
     */
    @Select("SELECT *  FROM GYSYPML WHERE YPXXID = #{param1} AND USERGYSID = #{param2}")
    GysYpDTO findGysYpmlByYpxxidAndUsergysid(String ypxxid, String usergysid);
}

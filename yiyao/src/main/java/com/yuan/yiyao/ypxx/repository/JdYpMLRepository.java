package com.yuan.yiyao.ypxx.repository;

import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 药品监控目录 数据访问层
 */
@Mapper
public interface JdYpMLRepository {


    /**
     * 添加监控药品信息
     * @param id
     * @param ypxxid
     * @param sysid
     */
    @Insert("INSERT INTO GYSYPML_CONTROL (ID,YPXXID,USERGYSID,CONTROL) VALUES(#{param1},#{param2},#{param3},#{param4});")
    void saveJdYpxx(String id, String ypxxid, String sysid,String control);

    /**
     * 删除监控药品信息
     */
    @Delete(" DELETE FROM GYSYPML_CONTROL WHERE YPXXID = #{param1} AND USERGYSID =#{param2};")
    void deleteJdYpxx(String ypxxid, String sysid);

    /**
     * 监控药品目录查询
     * @param ypJdDTO
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
            " WHERE YPML.ID = GYS.YPXXID AND GYS.USERGYSID = JD.usergysid AND JD.YPXXID = YPML.ID  AND JD.USERGYSID = USERGYS.ID" +
            " ) P LEFT JOIN DICTINFO DICT ON P.CONTROL = DICT.DICTCODE WHERE DICT.TYPECODE='003' " +
            " <when test=' gysmc != null and gysmc != \"\"'>"+
            " AND P.GYSMC like CONCAT('%',#{gysmc},'%') "+
            " </when>"+
            " <when test='ypmc !=null and ypmc != \"\"'>"+
            " AND P.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
            " </when>"+
            " <when test='scqymc !=null and scqymc != \"\"'>"+
            " AND P.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
            " </when>"+
            " <when test='control !=null and control != \"\"'>"+
            " AND P.CONTROL = #{control} "+
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
            " LIMIT #{begin},#{rows}"+
            " </script> ")
    List<YpJdDTO> findYpmlJdList(YpJdDTO ypJdDTO);

    /**
     * 查询总记录数
     * @param ypJdDTO
     * @return
     */
    @Select("<script> "+
            " SELECT COUNT(P.ID) TOTAL FROM ( " +
            " SELECT " +
            " YPML.* ,GYS.MONY,GYS.YPMC ,JD.ADVICE ,JD.CONTROL ,USERGYS.MC GYSMC  " +
            " FROM (  " +
            " SELECT " +
            " YP.ID,YP.BM,YP.SPMC,YP.SCQYMC,YP.ZBJG,YP.ZLCC,YP.CPSM,YP.JYZT,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICTLB.INFO BLMC  " +
            "  FROM YPXX YP " +
            " LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID  " +
            "    ) YPML ,GYSYPML GYS ,GYSYPML_CONTROL JD ,USERGYS USERGYS " +
            " WHERE YPML.ID = GYS.YPXXID AND GYS.USERGYSID = JD.usergysid AND JD.YPXXID = YPML.ID  AND JD.USERGYSID = USERGYS.ID" +
            " ) P LEFT JOIN DICTINFO DICT ON P.CONTROL = DICT.DICTCODE WHERE DICT.TYPECODE='003' " +
            " <when test=' gysmc != null and gysmc != \"\"'>"+
            " AND P.GYSMC like CONCAT('%',#{gysmc},'%') "+
            " </when>"+
            " <when test='ypmc !=null and ypmc != \"\"'>"+
            " AND P.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
            " </when>"+
            " <when test='scqymc !=null and scqymc != \"\"'>"+
            " AND P.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
            " </when>"+
            " <when test='control !=null and control != \"\"'>"+
            " AND P.CONTROL = #{control} "+
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
            " </script> ")
    int findYpmlJdCount(YpJdDTO ypJdDTO);

    /**
     * 更新供应药品监控信息
     * @param ypJdDTO
     */
    @Update("UPDATE GYSYPML_CONTROL SET  CONTROL = #{control} ,ADVICE =  #{advice} WHERE YPXXID = #{ypxxid} AND USERGYSID = #{usergysid};")
    void updateJdYpmlControl(YpJdDTO ypJdDTO);

    /**
     * 根据药品信息id和供应商id查询信息
     * @param ypxxid
     * @param usergysid
     * @return
     */
    @Select("SELECT * FROM GYSYPML_CONTROL WHERE YPXXID = #{param1} AND USERGYSID = #{param2};")
    YpJdDTO findYpmlJdByYpxxidAndUsergysid(String ypxxid, String usergysid);


}

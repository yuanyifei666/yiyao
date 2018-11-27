package com.yuan.yiyao.ypxx.repository;

import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import com.yuan.yiyao.ypxx.dto.YyCgYpmlDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 医院采购目录药品维护数据访问层
 */
@Mapper
public interface YyCgmlRepository {

    /**
     * 药品采购目录查询
     * @param yyCgYpmlDTO
     * @return
     */
    @Select( "<script>"+
            " SELECT YPML.* ,DICT.INFO CONTROLMC,GYSYPML.MONY ,GYSYPML.YPMC FROM ( " +
            " SELECT P.* ,GYS.MC GYSMC,JD.CONTROL ,YY.ID,YY.USERYYID SYSID,YY.USERGYSID FROM ( " +
            " SELECT  " +
            "   YP.ID YPXXID,YP.BM,YP.SPMC,YP.SCQYMC,YP.ZBJG,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICTLB.INFO BLMC  " +
            "          FROM YPXX YP LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID  " +
            "  )P ,GYSYPML_CONTROL JD ,USERGYS GYS ,YYYPML YY WHERE P.YPXXID = JD.YPXXID AND P.YPXXID = YY.YPXXID  AND JD.YPXXID = YY.YPXXID AND JD.USERGYSID = GYS.ID AND YY.USERGYSID = GYS.ID " +
            " )YPML LEFT JOIN DICTINFO DICT ON YPML.CONTROL = DICT.DICTCODE " +
            " LEFT JOIN GYSYPML GYSYPML ON YPML.YPXXID = GYSYPML.YPXXID AND YPML.USERGYSID = GYSYPML.USERGYSID " +
             " WHERE DICT.TYPECODE='003' AND YPML.SYSID = #{sysid} " +
            " <when test=' gysmc != null and gysmc != \"\"'>"+
            " AND YPML.GYSMC like CONCAT('%',#{gysmc},'%') "+
            " </when>"+
            " <when test='ypmc !=null and ypmc != \"\"'>"+
            " AND GYSYPML.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
            " </when>"+
            " <when test='scqymc !=null and scqymc != \"\"'>"+
            " AND YPML.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
            " </when>"+
            " <when test='control !=null and control != \"\"'>"+
            " AND YPML.CONTROL = #{control} "+
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
            " AND GYSYPML.MONY &gt; #{startMong} "+
            " </when>"+
            " <when test='endMong !=null '>"+
            " AND GYSYPML.MONY &lt; #{endMong} "+
            " </when>"+
            " limit #{begin},#{rows}"+
            "</script>")
    List<YyCgYpmlDTO> findYpmlList(YyCgYpmlDTO yyCgYpmlDTO);

    /**
     * 添加采购药品
     * @param dto 药品信息
     */
    @Insert("INSERT INTO YYYPML(ID,USERYYID,YPXXID,USERGYSID) VALUES(#{id},#{sysid},#{ypxxid},#{usergysid});")
    void saveGgypml(YyCgYpmlDTO dto);


    /**
     * 根据药品信息id和供应商id和用户单位id查询
     * @param ypxxid
     * @param usergysid
     * @param sysid
     * @return
     */
    @Select("SELECT * FROM YYYPML WHERE YPXXID = #{param1} AND USERGYSID = #{param2} AND USERYYID = #{param3};")
    List<YyCgYpmlDTO> findYycgypmlByYpxxidAndGysUserIdAndUsergysid(String ypxxid, String usergysid, String sysid);

    /**
     * 查询医药采购药品记录数
     * @param yyCgYpmlDTO
     * @return
     */
    @Select("<script>"+
            " SELECT COUNT(C.ID) TOTAL FROM ( "+

                " SELECT YPML.* ,DICT.INFO CONTROLMC,GYSYPML.MONY ,GYSYPML.YPMC FROM ( " +
                " SELECT P.* ,GYS.MC GYSMC,JD.CONTROL ,YY.ID,YY.USERYYID SYSID,YY.USERGYSID FROM ( " +
                " SELECT  " +
                "   YP.ID YPXXID,YP.BM,YP.SPMC,YP.SCQYMC,YP.ZBJG,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICTLB.INFO BLMC  " +
                "          FROM YPXX YP LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID  " +
                "  )P ,GYSYPML_CONTROL JD ,USERGYS GYS ,YYYPML YY WHERE P.YPXXID = JD.YPXXID AND P.YPXXID = YY.YPXXID  AND JD.YPXXID = YY.YPXXID AND JD.USERGYSID = GYS.ID AND YY.USERGYSID = GYS.ID " +
                " )YPML LEFT JOIN DICTINFO DICT ON YPML.CONTROL = DICT.DICTCODE " +
                " LEFT JOIN GYSYPML GYSYPML ON YPML.YPXXID = GYSYPML.YPXXID AND YPML.USERGYSID = GYSYPML.USERGYSID " +
                " WHERE DICT.TYPECODE='003' AND YPML.SYSID = #{sysid} " +
            " )C WHERE 1=1 "+
                    " <when test=' gysmc != null and gysmc != \"\"'>"+
                    " AND C.GYSMC like CONCAT('%',#{gysmc},'%') "+
                    " </when>"+
                    " <when test='ypmc !=null and ypmc != \"\"'>"+
                    " AND C.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
                    " </when>"+
                    " <when test='scqymc !=null and scqymc != \"\"'>"+
                    " AND C.SCQYMC LIKE CONCAT('%',#{scqymc},'%') "+
                    " </when>"+
                    " <when test='control !=null and control != \"\"'>"+
                    " AND C.CONTROL = #{control} "+
                    " </when>"+
                    " <when test='jx !=null and jx != \"\"'>"+
                    " AND C.JX = #{jx} "+
                    " </when>"+
                    " <when test='lb !=null and lb != \"\"'>"+
                    " AND C.LB = #{lb} "+
                    " </when>"+
                    " <when test='startZbjg !=null'>"+
                    " AND C.ZBJG &gt; #{startZbjg} "+
                    " </when>"+
                    " <when test='endZbjg !=null '>"+
                    " AND C.ZBJG &lt; #{endZbjg} "+
                    " </when>"+
                    " <when test='startMong !=null '>"+
                    " AND C.MONY &gt; #{startMong} "+
                    " </when>"+
                    " <when test='endMong !=null '>"+
                    " AND C.MONY &lt; #{endMong} "+
                    " </when>"+
            "</script>"
            )
    int findYpmlCount(YyCgYpmlDTO yyCgYpmlDTO);

    /**
     * 从采购目录中 根据id删除药品信息
     * @param id
     */
    @Delete("DELETE FROM YYYPML WHERE ID = #{param1}")
    void deleteYyCgypmlById(String id);

}

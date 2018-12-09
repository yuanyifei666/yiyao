package com.yuan.yiyao.chars.repository;

import com.yuan.yiyao.chars.dto.ChartsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 统计分析数据访问层
 */
@Mapper
public interface ChartsRepository {

    /**
     * 根据医院采购药品生产饼图
     */
    @Select("SELECT MX.CGL,YY.MC YYMC,SUM(MX.CGL)CGLTOTAL  FROM YYCGDMX MX ,YYCGD CGD,USERYY YY WHERE MX.YYCGDID = CGD.ID AND CGD.USERYYID = YY.ID GROUP BY YY.MC; ")
    public List<ChartsDTO> findByUserYYPie();

    /**
     * 根据不同的医院id查询不同的医院下的所有采购药品
     */
    @Select(" SELECT" +
            " MX1.*,SUM(MX1.CGL) YPCGL ,SUM(RK.RKL) YPRKL " +
            " FROM ( " +
            " SELECT MX.CGL ,MX.YPXXID,MX.YYCGDID ,YP.MC YPMC FROM YYCGDMX MX ,YPXX YP,YYCGD CGD  " +
            "             WHERE MX.YPXXID = YP.ID AND CGD.ID = MX.YYCGDID AND CGD.USERYYID = #{param1}  " +
            " )MX1 LEFT JOIN YYCGDRK RK ON RK.YYCGDID = MX1.YYCGDID AND RK.YPXXID = MX1.YPXXID GROUP BY MX1.MX1.YPXXID;")
    public  List<ChartsDTO> findByUserYYBar(String useryyid);


    /**
     * 统计所有供应商供应的所有药品
     */
    @Select("SELECT GYS.MC USERGYSMC,MX.CGL ,SUM(MX.CGL)GYSCGL FROM USERGYS GYS LEFT JOIN YYCGDMX MX ON GYS.ID = MX.USERGYSID GROUP BY GYS.ID;")
    public List<ChartsDTO> findByUserGysPie();

    /**
     * 统计单个医院下供应药品的情况
     */
    @Select(" SELECT YP.MC YPMC,SUM(MX.CGL) GYSCGL FROM YYCGDMX MX ,YPXX YP " +
            "   WHERE MX.YPXXID = YP.ID AND MX.USERGYSID = #{param1} GROUP BY MX.YPXXID;")
    public List<ChartsDTO> findByUserGysBar(String usergysid);

    /**
     * 查询采购药品名称列表
     */
    @Select("<script>"+
            " SELECT DISTINCT MX.YPXXID,YP.MC YPMC FROM YYCGDMX MX,YPXX YP,YYCGD CGD " +
            " WHERE MX.YPXXID = YP.ID AND MX.YYCGDID = CGD.ID " +

            " <when test='useryyid !=null and useryyid != \"\"'>"+
            " AND CGD.USERYYID = #{useryyid} "+
            " </when>"+
            " <when test='usergysid !=null and usergysid != \"\"'>"+
            " AND  MX.USERGYSID  = #{usergysid} "+
            " </when>"+

            "</script>")
     List<ChartsDTO> findByYpmc(ChartsDTO dto);


    /**
     * 查询药品的采购量
     */
    @Select("<script>"+
            "SELECT  MX.YPXXID,YP.MC YPMC ,SUM(MX.CGL) YPCGL FROM YYCGDMX MX,YPXX YP,YYCGD CGD " +
            "    WHERE MX.YPXXID = YP.ID AND MX.YYCGDID = CGD.ID " +

            " <when test='useryyid !=null and useryyid != \"\"'>"+
            " AND CGD.USERYYID = #{useryyid} "+
            " </when>"+
            " <when test='usergysid !=null and usergysid != \"\"'>"+
            " AND  MX.USERGYSID  = #{usergysid} "+
            " </when>"+
            " <when test='ypxxid !=null and ypxxid != \"\"'>"+
            " AND MX.YPXXID = #{ypxxid}" +
            " </when>"+

            " GROUP BY MX.YPXXID;"+
            "</script>")
    List<ChartsDTO> findByYpCgl(ChartsDTO dto);

    /**
     * 查询药品入库量
     * @param dto
     * @return
     */
    @Select("<script>"+
            "SELECT RK.YPXXID ,SUM(RK.RKL) YPRKL FROM YYCGDRK RK,YYCGD CGD " +
            "  WHERE RK.YYCGDID = CGD.ID  " +
            " <when test=' useryyid !=null and useryyid != \"\"'> "+
            " AND CGD.USERYYID = #{useryyid} "+
            " </when>"+
            " <when test='ypxxid !=null and ypxxid != \"\"'>"+
            " AND RK.YPXXID = #{ypxxid}" +
            " </when>"+
            " GROUP BY RK.YPXXID "+
            "</script>")
    List<ChartsDTO> findByYpRkl(ChartsDTO dto);


    /**
     * 查询药品的结算量
     * @param dto
     * @return
     */
    @Select("<script>"+
            "SELECT JSDMX.YPXXID ,SUM(JSDMX.JSL) YPJSL FROM YYJSDMX JSDMX, YYJSD JSD" +
            "  WHERE JSDMX.YYJSDID = JSD.ID " +

            " <when test='useryyid !=null and useryyid != \"\"'>"+
            " AND JSD.USERYYID = #{useryyid} "+
            " </when>"+
            " <when test='usergysid !=null and usergysid != \"\"'>"+
            " AND  JSD.USERGYSID  = #{usergysid} "+
            " </when>"+
            " <when test='ypxxid !=null and ypxxid != \"\"'>"+
            " AND JSDMX.YPXXID = #{ypxxid}" +
            " </when>"+
            " GROUP BY JSDMX.YPXXID"+
            "</script>")
    List<ChartsDTO> findByYpJsl(ChartsDTO dto);
}

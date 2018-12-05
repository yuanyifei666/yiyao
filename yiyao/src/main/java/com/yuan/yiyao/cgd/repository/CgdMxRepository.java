package com.yuan.yiyao.cgd.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.vo.CgdMxVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 采购明细数据访问层
 */
@Mapper
public interface CgdMxRepository extends BaseMapper<CgdMxVO> {


    /**
     * 查询某个采购单下的所有药品明细
     * @param cgdmxDTO
     * @return
     */
    @Select("<script>"+
            " SELECT P.* ,DICTLB.INFO LBMC ,INFO.INFO CGZTMC  ,RK.RKL ,RK.YPYXQ  FROM ( " +
            "   SELECT YP.BM,YP.SPMC,YP.SCQYMC,YP.MC,YP.LB,GYS.MC GYSMC ,CGDMX.* ,CGD.BM cgdbm,CGD.MC CGDMC ,CGD.USERYYID " +
            "         FROM YPXX YP,USERGYS GYS,YYCGDMX CGDMX ,YYCGD CGD WHERE YP.ID = CGDMX.YPXXID AND CGDMX.USERGYSID = GYS.ID AND CGDMX.YYCGDID = CGD.ID   " +
            " )P LEFT JOIN DICTINFO DICTLB ON P.LB = DICTLB.ID LEFT JOIN DICTINFO INFO ON P.CGZT = INFO.DICTCODE  LEFT JOIN YYCGDRK RK ON  P.YYCGDID = RK.YYCGDID AND P.YPXXID = RK.YPXXID  " +
            " WHERE INFO.TYPECODE = '011'  " +

            " <when test='mc != null and mc !=\"\"' >" +
            " AND P.MC LIKE CONCAT ('%',#{mc},'%') "+
            " </when>"+
            " <when test='cgdbm != null and cgdbm !=\"\"' >" +
            " AND P.cgdbm LIKE CONCAT ('%',#{cgdbm},'%')  "+
            " </when>"+
            " <when test='scqymc != null and scqymc !=\"\"' >" +
            " AND P.SCQYMC LIKE CONCAT ('%',#{scqymc},'%')  "+
            " </when>"+
            " <when test='cgdmc != null and cgdmc !=\"\"' >" +
            " AND P.CGDMC LIKE CONCAT ('%',#{cgdmc},'%') "+
            " </when>"+
            " <when test='startMong != null' >" +
            " AND P.JYJG &gt; #{startMong}  "+
            " </when>"+
            " <when test='endMong != null' >" +
            " AND P.JYJG &lt; #{endMong}  "+
            " </when>"+
            " <when test='startZbjg != null' >" +
            " AND P.ZBJG &gt; #{startZbjg}  "+
            " </when>"+
            " <when test='endZbjg != null' >" +
            " AND P.ZBJG &lt; #{endZbjg}  "+
            " </when>"+

            " <when test='cgzt != null and cgzt !=\"\"' >" +
            " AND P.CGZT = #{cgzt} "+
            " </when>"+
            " <when test='useryyid != null and useryyid !=\"\"' >" +
            " AND P.USERYYID = #{useryyid} "+
            " </when>"+
            " <when test='usergysid != null and usergysid !=\"\"' >" +
            " AND P.USERGYSID = #{usergysid} "+
            " </when>"+
            " <when test='yycgdid != null and yycgdid !=\"\"' >" +
            " AND P.YYCGDID = #{yycgdid} "+
            " </when>"+

            " limit #{begin},#{rows}"+
            "</script>")
    List<CgdmxDTO> findCgdmxList(CgdmxDTO cgdmxDTO);

    /**
     * 查询符合条件的采购明细总记录数
     * @param cgdmxDTO
     * @return
     */
    @Select("<script>"+
            " SELECT COUNT(P.ID) TOTAL FROM ( " +
            "   SELECT YP.BM,YP.SPMC,YP.SCQYMC,YP.MC,YP.LB,GYS.MC GYSMC ,CGDMX.* ,CGD.BM CGDBM,CGD.MC CGDMC ,CGD.USERYYID " +
            "         FROM YPXX YP,USERGYS GYS,YYCGDMX CGDMX ,YYCGD CGD WHERE YP.ID = CGDMX.YPXXID AND CGDMX.USERGYSID = GYS.ID AND CGDMX.YYCGDID = CGD.ID   " +
            " )P LEFT JOIN DICTINFO DICTLB ON P.LB = DICTLB.ID LEFT JOIN DICTINFO INFO ON P.CGZT = INFO.DICTCODE  " +
            " WHERE INFO.TYPECODE = '011'  " +

            " <when test='mc != null and mc !=\"\"' >" +
            " AND P.MC LIKE CONCAT ('%',#{mc},'%') "+
            " </when>"+
            " <when test='cgdbm != null and cgdbm !=\"\"' >" +
            " AND P.cgdbm LIKE CONCAT ('%',#{cgdbm},'%')  "+
            " </when>"+
            " <when test='scqymc != null and scqymc !=\"\"' >" +
            " AND P.SCQYMC LIKE CONCAT ('%',#{scqymc},'%')  "+
            " </when>"+
            " <when test='cgdmc != null and cgdmc !=\"\"' >" +
            " AND P.CGDMC LIKE CONCAT ('%',#{cgdmc},'%') "+
            " </when>"+
            " <when test='startMong != null' >" +
            " AND P.JYJG &gt; #{startMong}  "+
            " </when>"+
            " <when test='endMong != null' >" +
            " AND P.JYJG &lt; #{endMong}  "+
            " </when>"+
            " <when test='startZbjg != null' >" +
            " AND P.ZBJG &gt; #{startZbjg}  "+
            " </when>"+
            " <when test='endZbjg != null' >" +
            " AND P.ZBJG &lt; #{endZbjg}  "+
            " </when>"+

            " <when test='cgzt != null and cgzt !=\"\"' >" +
            " AND P.CGZT = #{cgzt} "+
            " </when>"+
            " <when test='useryyid != null and useryyid !=\"\"' >" +
            " AND P.USERYYID = #{useryyid} "+
            " </when>"+
            " <when test='usergysid != null and usergysid !=\"\"' >" +
            " AND P.USERGYSID = #{usergysid} "+
            " </when>"+

            "</script>")
    int findCgdmxCount(CgdmxDTO cgdmxDTO);

    @Select(" SELECT P.* ,DICTLB.INFO LBMC ,INFO.INFO CGZTMC FROM ( " +
            "   SELECT YP.BM,YP.SPMC,YP.SCQYMC,YP.MC,YP.LB,GYS.MC GYSMC ,CGDMX.* " +
            "         FROM YPXX YP,USERGYS GYS,YYCGDMX CGDMX WHERE YP.ID = CGDMX.YPXXID AND CGDMX.USERGYSID = GYS.ID  " +
            " )P LEFT JOIN DICTINFO DICTLB ON P.LB = DICTLB.ID LEFT JOIN DICTINFO INFO ON P.CGZT = INFO.DICTCODE  " +
            " WHERE INFO.TYPECODE = '011' AND P.YYCGDID = #{yycgdid} ")
    List<CgdmxDTO> findCgdmxZonji(CgdmxDTO cgdmxDTO);


}

package com.yuan.yiyao.cgd.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.cgd.dto.CgdDTO;
import com.yuan.yiyao.cgd.dto.GysCgdDTO;
import com.yuan.yiyao.cgd.vo.CgdVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * c采购单数据访问层
 */
@Mapper
public interface CgdRepository extends BaseMapper<CgdVO> {


    /**
     * 根据采购单id查询：把信息封装到dto对象中
     * @param cgdid
     * @return
     */
    @Select("SELECT " +
                " CGD.ID ,CGD.BM,CGD.MC,CGD.USERYYID,CGD.LXR,CGD.LXDH,CGD.CJR,CGD.CJTIME,CGD.TJTIME,CGD.BZ,CGD.ZT," +
                " CGD.SHYJ,CGD.SHTIME,CGD.XGTIME,YY.MC YYMC,DICT.INFO ZTMC FROM YYCGD CGD ,USERYY YY,DICTINFO DICT " +
            " WHERE CGD.USERYYID = YY.ID AND DICT.DICTCODE = CGD.ZT AND DICT.TYPECODE = '010' AND CGD.ID = #{param1};")
    CgdDTO findCgdByIdToCgdDTO(String cgdid);


    /**
     * 供应商采购单受理查询
     * @param dto
     * @return
     *  bm : cgd.bm,
     *                 yymc : cgd.yymc,
     *                 ypmc : cgd.ypmc,
     *                 lxr : cgd.lxr,
     *                 startJyjg : cgd.startJyjg,
     *                 endJyjg : cgd.endJyjg,
     *                 yymc : cgd.yymc,
     *                 lxdh : cgd.lxdh
     */
    @Select("<script>"+
            " SELECT CGDMX.*,YY.MC YYMC ,YY.DZ FROM ( " +
            "   SELECT CGD.BM ,CGD.ZT,YPXX.MC YPMC ,MX.* ,CGD.USERYYID , CGD.LXR ,CGD.LXDH,CGD.BZ FROM YYCGDMX MX LEFT JOIN  YPXX YPXX ON MX.YPXXID = YPXX.ID LEFT JOIN YYCGD CGD ON MX.YYCGDID = CGD.ID  " +
            " )CGDMX LEFT JOIN USERYY YY ON CGDMX.USERYYID = YY.ID WHERE CGDMX.ZT = '3'AND CGDMX.CGZT = '1' AND CGDMX.USERGYSID = #{usergysid} "+

            " <when test='bm !=null and bm != \"\"'>"+
                " AND CGDMX.BM LIKE CONCAT('%',#{bm},'%') "+
            " </when>"+
            " <when test='yymc !=null and yymc != \"\"'>"+
            " AND YY.MC LIKE CONCAT('%',#{yymc},'%') "+
            " </when>"+
            " <when test='ypmc !=null and ypmc != \"\"'>"+
            " AND CGDMX.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
            " </when>"+
            " <when test='lxr !=null and lxr != \"\"'>"+
            " AND CGDMX.LXR LIKE CONCAT('%',#{lxr},'%') "+
            " </when>"+
            " <when test='lxdh !=null and lxdh != \"\"'>"+
            " AND CGDMX.LXDH LIKE CONCAT('%',#{lxdh},'%') "+
            " </when>"+
            " <when test='startJyjg !=null'>"+
            " AND CGDMX.JYJG  &gt; #{startJyjg} "+
            " </when>"+
            " <when test='endJyjg !=null'>"+
            " AND CGDMX.JYJG   &lt; #{endJyjg} "+
            " </when>"+

            " limit #{begin},#{rows}"+
            "</script>")
    List<GysCgdDTO> gysCgdList(GysCgdDTO dto);

    /**
     * 供应商采购单受理查询总记录数
     * @param dto
     * @return
     */
    @Select("<script>"+
            " SELECT COUNT(CGDMX.ID) TOTAL  FROM ( " +
            "   SELECT CGD.BM ,CGD.ZT,YPXX.MC YPMC ,MX.* ,CGD.USERYYID , CGD.LXR ,CGD.LXDH,CGD.BZ FROM YYCGDMX MX LEFT JOIN  YPXX YPXX ON MX.YPXXID = YPXX.ID LEFT JOIN YYCGD CGD ON MX.YYCGDID = CGD.ID  " +
            " )CGDMX LEFT JOIN USERYY YY ON CGDMX.USERYYID = YY.ID WHERE CGDMX.ZT = '3' AND CGDMX.CGZT = '1' AND CGDMX.USERGYSID = #{usergysid} " +

            " <when test=' bm != null and bm != \"\"'>"+
            " AND CGDMX.BM LIKE CONCAT('%',#{bm},'%') "+
            " </when>"+
            " <when test='yymc !=null and yymc != \"\"'>"+
            " AND YY.MC LIKE CONCAT('%',#{yymc},'%') "+
            " </when>"+
            " <when test='ypmc !=null and ypmc != \"\"'>"+
            " AND CGDMX.YPMC LIKE CONCAT('%',#{ypmc},'%') "+
            " </when>"+
            " <when test='lxr !=null and lxr != \"\"'>"+
            " AND CGDMX.LXR LIKE CONCAT('%',#{lxr},'%') "+
            " </when>"+
            " <when test='lxdh !=null and lxdh != \"\"'>"+
            " AND CGDMX.LXDH LIKE CONCAT('%',#{lxdh},'%') "+
            " </when>"+
            " <when test='startJyjg !=null'>"+
            " AND CGDMX.JYJG  &gt; #{startJyjg} "+
            " </when>"+
            " <when test='endJyjg !=null'>"+
            " AND CGDMX.JYJG  &lt; #{endJyjg} "+
            " </when>"+
            " </script>")
    int gysCgdCount(GysCgdDTO dto);
}

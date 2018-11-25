package com.yuan.yiyao.ypxx.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 药品信息维护数据访问层
 *  SELECT YP.ID,YP.BM,YP.SCQYMC,YP.ZBJG,YP.ZLCC,YP.CPSM,YP.JYZT,YP.DW,YP.MC,YP.JX,YP.GG,YP.ZHXS,YP.LB,DICT.INFO JYZTMC,DICTLB.INFO BLMC FROM YPXX YP LEFT JOIN DICTINFO DICT ON YP.JYZT = DICT.DICTCODE LEFT JOIN DICTINFO DICTLB ON YP.LB = DICTLB.ID WHERE DICT.TYPECODE='003'  LIMIT 0,40 ;
 */
@Mapper
public interface YpxxRepository extends BaseMapper<Ypxx> {


    /**
     * 根据查询条件分页查询药品信息
     * @param ypxxDTO
     * @return bm :
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
            " LIMIT #{begin},#{rows} "+
            "</script>")
    List<YpxxDTO> findYpxxListByPage(YpxxDTO ypxxDTO);

    /**
     * 导出符合条件的数据
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
            "</script>")
    List<YpxxDTO> findYpxxListByExport(YpxxDTO ypxxDTO);
}

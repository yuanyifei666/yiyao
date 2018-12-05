package com.yuan.yiyao.jsd.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.jsd.dto.JsdMxDTO;
import com.yuan.yiyao.jsd.vo.JsdMx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JsdMxRepository extends BaseMapper<JsdMx> {

    /**
     * 根据结算单id查询该结算单下单的所有明细
     * @return
     */
    @Select(" SELECT MX.*,YP.BM ,YP.MC,YP.ZBJG,YP.SCQYMC ,DICT.INFO JSZTMC FROM ( " +
            " SELECT CGD.BM CGDBM,CGD.MC CGDMC ,JSDMX.*,RK.RKL ,RK.RKJE FROM YYJSDMX JSDMX ,YYCGDRK RK,YYCGD CGD " +
            " WHERE JSDMX.YPXXID=RK.YPXXID AND JSDMX.YYCGDID = CGD.ID AND JSDMX.YYCGDID = RK.YYCGDID  " +
            "  )MX LEFT JOIN DICTINFO DICT ON DICT.TYPECODE = '015' LEFT JOIN YPXX YP ON YP.ID = MX.YPXXID " +
            "   WHERE DICT.DICTCODE = MX.JSZT AND MX.YYJSDID = #{yyjsdid} limit #{begin},#{rows};")
    public List<JsdMxDTO> findJsdMxList(JsdMxDTO jsdMxDTO);


    /**
     * 查询符合条件的结算单名称总记录数
     * @param jsdMxDTO
     * @return
     */
    @Select(" SELECT COUNT(MX.ID) TOTAL FROM ( " +
            " SELECT CGD.BM CGBM,CGD.MC CGDMC ,JSDMX.*,RK.RKL ,RK.RKJE FROM YYJSDMX JSDMX ,YYCGDRK RK,YYCGD CGD " +
            " WHERE JSDMX.YPXXID=RK.YPXXID AND JSDMX.YYCGDID = CGD.ID AND JSDMX.YYCGDID = RK.YYCGDID  " +
            "  )MX LEFT JOIN YPXX YP ON YP.ID = MX.YPXXID WHERE MX.YYJSDID = #{yyjsdid} ;")
    public int findJsdMxCOUNT(JsdMxDTO jsdMxDTO);

}

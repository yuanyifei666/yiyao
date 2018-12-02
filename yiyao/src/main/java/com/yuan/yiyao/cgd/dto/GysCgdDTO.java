package com.yuan.yiyao.cgd.dto;

import com.yuan.yiyao.cgd.vo.CgdVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *供应商受理传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GysCgdDTO extends CgdVO implements Serializable {

    private String ypmc ;//药品名称
    private String yycgdid;//采购单id
    private String ypxxid; //药品id
    private String usergysid;//供应商id
    private Float zbjg;//中标价格
    private Float jyjg;//交易价格
    private Integer cgl;//采购量
    private Float cgje;//采购总金额
    private String yymc ;//医院名称
    private String dz; //联系地址

    private Float startJyjg;
    private Float endJyjg;
    private Integer page;
    private Integer begin;
    private Integer rows;

}

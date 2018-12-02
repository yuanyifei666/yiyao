package com.yuan.yiyao.cgd.dto;

import com.yuan.yiyao.cgd.vo.CgdMxVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 采购单明细传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CgdmxDTO extends CgdMxVO {

    private String bm ;//药品流水号
    private String scqymc;//生产企业名称
    private Float zbjg;//中标价
    private String mc;//通用名
    private String lb;//药品类别
    private String gysmc;//供货企业名称
    private String lbmc;//类别名称
    private Integer page;
    private Integer rows;
    private Integer begin;
    private String useryyid;//医院的id
    private String cgdbm;
    private String cgdmc;
    private Integer rkl;
    private Date ypyxq;

}

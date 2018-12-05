package com.yuan.yiyao.jsd.dto;

import com.yuan.yiyao.jsd.vo.JsdMx;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结算单名称传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsdMxDTO extends JsdMx {

    private String cgdbm;
    private String cgdmc;
    private String bm;
    private String mc;
    private Float zbjg;
    private String scqymc;
    private String jsztmc;
    private Float rkl;
    private Float rkje;

    private Integer page;
    private Integer rows;
    private Integer begin;
}

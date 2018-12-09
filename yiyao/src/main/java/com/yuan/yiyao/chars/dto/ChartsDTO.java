package com.yuan.yiyao.chars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计查询封装实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartsDTO {

    private String yymc;
    private int cgltotal;

    private String ypmc;

    private Integer ypcgl;
    private Integer yprkl;
    private Integer ypjsl;

    private String usergysmc;
    private Integer gyscgl;

    //封装查询条件
    private String useryyid;
    private String usergysid;
    private String ypxxid;

    private String groupid;

}

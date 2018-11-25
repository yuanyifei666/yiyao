package com.yuan.yiyao.sys.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 供应商用户表对应的实体类
 */
@TableName("USERGYS")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class UserGYS {

    private String id;
    private String mc; //企业名称
    private String lxr;//企业联系人
    private String dh ; //企业联系电话
    private String lxdz ; //企业联系地址街道
    private String addres;//企业的详细地址
    private String yzbm ;//企业邮政编码
    private String frmc;//法人名称
    private String frsfz;//法人身份证
    private String zczj;//企业注册资金
    private String jj;//公司简介
    private Integer proId;
    private Integer cityId;
    private Integer areaId;

}

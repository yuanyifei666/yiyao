package com.yuan.yiyao.sys.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 医院信息的实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("USERYY")
public class UserYY {

    private String id;
    private String mc;
    private String dz;
    private  String yzbm;
    private String dq;
    private String jb;
    private String cws;
    private String fyljg;
    private String dh;
    private String yjkdh;
    private String lb;
    private String ypsr;
    private String ywsr;
    private String cz;
    private Integer proId;
    private Integer cityId;
    private Integer areaId;

}

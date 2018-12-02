package com.yuan.yiyao.cgd.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 医院药品入库实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("YYCGDRK")
public class YpRkVO {

    @TableId(type = IdType.UUID)
    private String id; //入库id
    private String yycgdid;//采购单id
    private String ypxxid;//药品信息id
    private Integer rkl;//入库量
    private Float rkje;//入库金额
    private String rkdh;//发票号或入库单号
    private String ypph;//药品批号
    private Date ypyxq;//药品有效期
    private Date rktime;//入库时间（年月日时分秒）
    private String cgzt;//采购状态,已入库

}

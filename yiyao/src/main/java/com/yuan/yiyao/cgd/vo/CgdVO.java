package com.yuan.yiyao.cgd.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 采购单实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("YYCGD")
public class CgdVO {

    @TableId(type = IdType.UUID)
    private String id; //采购单id
    private String bm;//采购单编号
    private String mc;//采购单名称
    private String useryyid;//医院id
    private String lxr;//联系人
    private String lxdh;//联系电话
    private String cjr;//建单人
    private Date cjtime;//建单时间
    private Date tjtime;//提交时间
    private String bz;//备注
    private String zt;//采购单状态(存储数据字典：1：未提交、2：已提交未审核、3：审核通过、4：审核不通过)
    private String shyj;//审核意见
    private Date shtime;//审核时间
    private Date xgtime;//最近修改时间

}

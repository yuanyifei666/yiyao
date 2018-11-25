package com.yuan.yiyao.ypxx.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 药品信息表对应的实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("YPXX")
public class Ypxx implements Serializable {

    @TableId(type = IdType.UUID)
    private String id; //药品id
    private String bm ;//药品流水号
    private String scqymc;//生产企业名称
    private String spmc;//商品名
    private Float zbjg;//中标价
    private String pzwh;//批准文号
    private String jky;//是否进口药
    private String zlcc;//质量层次
    private String cpsm;//产品说明
    private String jyzt;//药品交易状态
    private String dw;//单位
    private String mc;//通用名
    private String jx;//剂型
    private String gg;//规格
    private String zhxs;//转换系数
    private String lb;//药品类别
}

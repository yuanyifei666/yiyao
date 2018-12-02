package com.yuan.yiyao.cgd.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 采购单明细实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("YYCGDMX")
public class CgdMxVO {

    @TableId(type = IdType.UUID)
    private String id;//采购明细id
    private String yycgdid;//采购单id
    private String ypxxid;//药品信息id
    private String usergysid;//供货企业id
    private Float zbjg;//中标价
    private Float jyjg;//交易价
    private Integer cgl;//采购量
    private Float cgje;//采购金额
    private String cgzt;//存储数据字典：1、未确认送货  2、已发货、3、已入库、4无法供货

}

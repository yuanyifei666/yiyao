package com.yuan.yiyao.sys.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数字字典表的实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
@TableName("DICTTYPE")
public class DictType {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String typecode;
    private String typename;
    private  String remark;
}

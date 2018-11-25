package com.yuan.yiyao.operation.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限表对应的实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("OPERATION")
@ApiModel(value = "权限表对应的实体类")
public class Operation {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String url ;
    private String code;
    private String state ;
    private Integer parentId;


}

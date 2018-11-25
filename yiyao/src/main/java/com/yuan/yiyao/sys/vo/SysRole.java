package com.yuan.yiyao.sys.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统角色表对应的实体类
 */
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("SYSROLE")
public class SysRole {

    @TableId(type = IdType.UUID)
    private String id; //角色的id
    private String rolename; //角色的名称
    private String groupid; //角色所属的用户类型 0:系统管理员,1：卫生局2：医院 3:供货商
    private String discription; //角色描述


}

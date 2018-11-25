package com.yuan.yiyao.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色实体类的扩展类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysRoleVO extends SysRole {

    private String dictcode;
    private String info;
    List<String> operations = new ArrayList<>();

}

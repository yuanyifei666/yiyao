package com.yuan.yiyao.sys.service;

import com.yuan.yiyao.sys.vo.SysRole;
import com.yuan.yiyao.sys.vo.SysRoleVO;

import java.util.List;

public interface SysRoleService {
    List<SysRoleVO> findSysRoleList();

    void save(SysRole sysRole, Integer[] operations);

    void deleteSysRoles(String id);

    void updateSysRole(SysRole sysRole, Integer[] operations);
}

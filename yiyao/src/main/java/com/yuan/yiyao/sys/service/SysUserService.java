package com.yuan.yiyao.sys.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.vo.SysUserVo;
import com.yuan.yiyao.user.vo.SysUser;

public interface SysUserService {

    DataGridResultDTO findSysUserByPage(SysUserVo sysUser, DataGridResultDTO dto);

    boolean findSysUserByUserName(String username);

    void saveSysUser(SysUser sysUser, String pro_area);

    String deleteSysUsers(String ids);

    void updateSysUserByState(String ids);

    void relationUserToRole(String usernames, String[] roles);
}

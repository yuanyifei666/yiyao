package com.yuan.yiyao.user.service;

import com.yuan.yiyao.user.vo.SysUser;

public interface UserService {

    SysUser login(SysUser user)throws Exception;

    void updatePwd(SysUser user);
}

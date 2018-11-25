package com.yuan.yiyao.sys.vo;

import com.yuan.yiyao.user.vo.SysUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统用户类的扩展类
 */

@Data
public class SysUserVo extends SysUser {

    private String info;
    private String mc;
    private Date startTime;
    private Date endTime;
    private Integer begin ;
    private Integer rows;
    private List<SysRole> roles = new ArrayList<>();


}

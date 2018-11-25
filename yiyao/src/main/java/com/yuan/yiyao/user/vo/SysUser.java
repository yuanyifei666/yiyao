package com.yuan.yiyao.user.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("SYSUSER")
public class SysUser implements Serializable {

    protected String userid;
    protected String username;
    protected String groupid;
    protected String pwd;
    protected String contact;
    protected String addr;
    protected String email;
    protected String userstate;
    protected String remark;
    protected Date createtime;
    protected String sex;
    protected String phone;
    protected String movephone;
    protected String fax;
    protected String lastupdate;
    protected String sysid;


}

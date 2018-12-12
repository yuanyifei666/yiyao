package com.yuan.yiyao.user.repository;

import com.yuan.yiyao.user.vo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户的数据访问层
 */
@Mapper
public interface UserRepository {


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
//    @Select("select * from SYSUSER where  username = #{param1};")
    @Select(" SELECT SYS.* ," +
            "   CASE " +
            "   WHEN GROUPID = 's0101' THEN (SELECT MC FROM USERJD WHERE ID = SYS.SYSID) " +
            "   WHEN GROUPID = 's0103' THEN ( SELECT MC FROM USERGYS WHERE ID = SYS.SYSID) " +
            "  WHEN GROUPID = 's0104' THEN (SELECT MC FROM USERYY WHERE ID = SYS.SYSID) END SYSMC " +
            " FROM SYSUSER SYS where  SYS.username = #{param1};")
    SysUser findByUsername(String username);

    /**
     * 更新用户的密码
     * @param user
     */
    @Update("UPDATE SYSUSER SET PWD = #{pwd} WHERE USERNAME = #{username};")
    void updatePwd(SysUser user);
}

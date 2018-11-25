package com.yuan.yiyao.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yuan.yiyao.sys.vo.SysUserVo;
import com.yuan.yiyao.user.vo.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 系统用户的数据访问层
 */
@Mapper
public interface SysUserRepository extends BaseMapper<SysUser> {

    /**
     * 实现多表查询，分页
     * SELECT USER.* ,ROLE.ID ROLEID ,ROLE.ROLENAME FROM SYSUSER USER LEFT JOIN USERROLE UR ON USER.USERNAME = UR.USERID
     *                                                                          LEFT JOIN SYSROLE ROLE ON UR.ROLEID = ROLE.ID;
     */
    @Select("<script>"+
            "SELECT SU.*,DICT.INFO , " +
            "CASE " +
            "  WHEN SU.GROUPID = 's0101' THEN (SELECT JD.MC FROM USERJD JD WHERE SU.SYSID = JD.ID)" +
            "   WHEN SU.GROUPID = 's0103' THEN (SELECT GYS.MC FROM USERGYS GYS WHERE SU.SYSID = GYS.ID)" +
            "  WHEN SU.GROUPID = 's0104' THEN (SELECT YY.MC FROM USERYY YY WHERE SU.SYSID = YY.ID) END MC" +
            " FROM SYSUSER SU LEFT JOIN DICTINFO DICT ON SU.GROUPID = DICT.ID " +
            " WHERE 1=1 "+
            " <when test='username != null and username != \"\"'> "+
            "  AND SU.USERNAME LIKE CONCAT('%',#{username},'%') "+
            " </when>"+
            " <when test='groupid != null and groupid != \"\"'> "+
            "  AND SU.groupid = #{groupid} "+
            " </when>"+
            " <when test='userstate != null and userstate !=\"\"'> "+
            "  AND SU.userstate = #{userstate} "+
            " </when>"+
            " <when test='startTime != null '> "+
            "  AND SU.createtime &gt; #{startTime} "+
            " </when>"+
            " <when test='endTime != null '> "+
            "  AND SU.createtime &lt; #{endTime} "+
            " </when> "+
            " LIMIT #{begin} ,#{rows} "+
            "</script>")
    public List<SysUserVo> findSysUserList(SysUserVo sysUser);


    /**
     * 逻辑上删除用户信息
     *1正常，0暂停
     * @param username 要删除的用户信息
     */
    @Update("UPDATE SYSUSER SET USERSTATE = #{param1} WHERE USERNAME = #{param2};")
    void updateSysUserByUserstate( String userstate, String username);

    /**
     * 关联用户和角色信息
     * @param s
     * @param s1 用户的名称
     * @param role 角色的id
     */
    @Insert("INSERT INTO USERROLE (ID,USERID,ROLEID) VALUES(#{param1},#{param2},#{param3});")
    void relationUserToRole(String s, String s1, String role);

    /**
     * 查询该用户对应的角色
     * @param username
     * @return
     */
    @Select("SELECT ROLEID FROM USERROLE WHERE USERID = #{param1};")
    List<String> findUserRole(String username);

    /**
     * 根据用户的名称删除该用户关联的角色
     * @param s 用户的名称
     */
    @Delete("DELETE FROM USERROLE WHERE USERID = #{param1};")
    void deleteUserRoleByUsername(String s);
}

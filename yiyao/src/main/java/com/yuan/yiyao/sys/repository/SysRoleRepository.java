package com.yuan.yiyao.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.sys.vo.SysRole;
import com.yuan.yiyao.sys.vo.SysRoleVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色数据访问层
 */
@Mapper
public interface SysRoleRepository extends BaseMapper<SysRole> {


    /**
     * 查询所有的系统角色信息
     * @return
     */
    @Select("SELECT ROLE.ID,ROLE.ROLENAME,ROLE.GROUPID,ROLE.DISCRIPTION " +
            ",DICT.DICTCODE,DICT.INFO  FROM SYSROLE ROLE LEFT JOIN DICTINFO DICT ON ROLE.GROUPID = DICT.DICTCODE WHERE DICT.TYPECODE = 'S01';")
    List<SysRoleVO> findSysRoleList();

    /**
     * 给角色授权
     * @param id
     * @param operationsId
     */
    @Insert("INSERT INTO ROLE_OPERATION(ROLEID,OPERATION) VALUES(#{param1},#{param2});")
    void updateSysRoleAndOperation(String id, Integer operationsId);

    /**
     * 根据角色的id删除该角色对应的权限
     * @param roleId
     */
    @Delete("DELETE FROM ROLE_OPERATION WHERE ROLEID = #{param1};")
    void deleteByRoleAndOperation(String roleId);

    /**
     *根据角色的id查询该角色具有的权限
     * @param roleid
     * @return
     */
    @Select("SELECT OPERATION FROM ROLE_OPERATION WHERE ROLEID = #{param1};")
    List<String> findOperationIdsByRoleId(String roleid);


}

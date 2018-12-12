package com.yuan.yiyao.operation.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuan.yiyao.operation.vo.Operation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限管理操作数据库的接口
 */
@Mapper
public interface OperationRepository extends BaseMapper<Operation> {


    /**
     * 查询所有的根节点列表
     * @return
     */
    @Select("SELECT * FROM OPERATION WHERE PARENT_ID IS NULL;")
    List<Operation> findByRoot();


    /**
     * 查询该节点下的子节点列表
     * @param parent_id
     * @return
     */

    @Select("<script>"+
            "SELECT DISTINCT OP.* FROM USERROLE UR ,SYSROLE ROLE ,ROLE_OPERATION RO,OPERATION OP " +
            " WHERE UR.ROLEID = ROLE.ID AND UR.ROLEID=RO.ROLEID AND ROLE.ID = RO.ROLEID AND RO.OPERATION = OP.ID " +
            " AND OP.PARENT_ID = #{param1} " +
            " <when test='param2 != null'>" +
            " AND UR.USERID=#{param2} "+
            " </when>"+
            " ORDER BY OP.ORD "+
            "</script>")
    List<Operation> findByParentId(Integer parent_id,String username);

    /**
     * 角色授权权限树根据上级id查询子权限
     */
    @Select("SELECT * FROM OPERATION WHERE PARENT_ID = #{param1}")
    List<Operation> findOperationZtreeByParentId(Integer parent_id,String username);

    /**
     * 根据id查询
     */
    @Select ("SELECT * FROM OPERATION WHERE id = #{param1};")
    public Operation findById(Integer id);

    /**
     * 根据用户名得到该用户具有的
     * @param yuan01
     * @return
     */
    @Select("SELECT distinct OP.CODE FROM OPERATION OP,ROLE_OPERATION RO WHERE OP.ID=RO.OPERATION AND RO.ROLEID IN (SELECT ROLEID FROM USERROLE WHERE USERID=#{param1}) AND CODE IS NOT NULL;")
    List<String> findOperationCodeByUsername(String yuan01);


    /**
     * 得到系统权限所有的访问url
     * @return
     */
    @Select("SELECT DISTINCT URL ,CODE FROM OPERATION WHERE URL IS NOT NULL;")
    List<Operation> findOperations();

    /**
     * 根据父id查询所有的权限
     * @param parent_id
     * @return
     */
    @Select("SELECT * FROM OPERATION WHERE  PARENT_ID= #{param1};")
    List<Operation> findByCode(Integer parent_id);
}

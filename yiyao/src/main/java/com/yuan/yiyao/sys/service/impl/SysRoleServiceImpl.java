package com.yuan.yiyao.sys.service.impl;

import com.yuan.yiyao.sys.repository.SysRoleRepository;
import com.yuan.yiyao.sys.service.SysRoleService;
import com.yuan.yiyao.sys.vo.SysRole;
import com.yuan.yiyao.sys.vo.SysRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 系统角色管理业务层
 */
@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleRepository repository;

    /**
     * 得到所有的系统角色信息
     * @return
     */
    public List<SysRoleVO> findSysRoleList() {
        //得到所有的角色
        List<SysRoleVO> result = repository.findSysRoleList();
        for (SysRoleVO vo:result) {
            //得到每个角色对应的权限
            List<String> operations = getOperationIds(vo.getId());
            if (operations == null || operations.size() == 0){
                operations = new ArrayList<>();
            }
            vo.setOperations(operations);

        }

        return result;
    }

    /**
     * 得到每个角色对应的权限
     */
    public List<String> getOperationIds(String roleid){
        return repository.findOperationIdsByRoleId(roleid);
    }

    /**
     * 添加角色信息
     * @param sysRole
     * @param operations
     */
    public void save(SysRole sysRole, Integer[] operations) {
        //生成角色的id
        String id = UUID.randomUUID().toString();
        sysRole.setId(id);
        //把角色信息添加到数据库
        repository.insert(sysRole);

        //更新该角色的具有的权限
        if (operations != null ){
            for (Integer operationsId:operations) {
                repository.updateSysRoleAndOperation(id,operationsId);
            }
        }
    }

    /**
     * 根据角色的id批量删除
     */
    public void deleteSysRoles(String id) {
        //解析id;
        String ids[] = id.split(",");
        for (String roleId: ids) {
            //根据id删除角色信息
            repository.deleteById(roleId);
            //同步删除该角色对应的权限
            repository.deleteByRoleAndOperation(roleId);

        }
    }

    /**
     * 更新角色
     */
    public void updateSysRole(SysRole sysRole, Integer[] operations) {
        //更新角色的基本信息
        repository.updateById(sysRole);
        //删除角色本来的权限
        repository.deleteByRoleAndOperation(sysRole.getId());
        if (operations != null){
            //更新角色对应的权限
            for (Integer operationId:operations) {
                repository.updateSysRoleAndOperation(sysRole.getId(),operationId);
            }
        }

    }
}

package com.yuan.yiyao.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.repository.SysRoleRepository;
import com.yuan.yiyao.sys.repository.SysUserRepository;
import com.yuan.yiyao.sys.service.SysUserService;
import com.yuan.yiyao.sys.vo.SysRole;
import com.yuan.yiyao.sys.vo.SysUserVo;
import com.yuan.yiyao.user.vo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 系统用户管理的业务层
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository repository ;

    @Autowired
    private SysRoleRepository roleRepository;
    /**
     * 根据查询条件分页查询系统用户信息列表
     * @param sysUser
     * @param dto
     * @return
     */
    public DataGridResultDTO findSysUserByPage(SysUserVo sysUser, DataGridResultDTO dto) {
        //根据条件分页查询系统用户数据
        EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
        //动态添加查询条件
        if (sysUser != null){
            if (sysUser.getUsername() != null && !"".equals(sysUser.getUsername())){
                //添加用户名称条件
                wrapper.like("username",sysUser.getUsername());
            }

            if (sysUser.getGroupid() != null && !"".equals(sysUser.getGroupid())){
                //添加用户类型查询条件
                wrapper.eq("groupid" ,sysUser.getGroupid());
            }
            if (sysUser.getUserstate() != null && !"".equals(sysUser.getUserstate())){
                //根据用户的状态查询
                wrapper.eq("userstate",sysUser.getUserstate());
            }
            if (sysUser.getStartTime() != null){
                //根据创建时间来查询
                wrapper.gt("createtime",sysUser.getStartTime());
            }
            if (sysUser.getEndTime() != null){
                //根据创建时间来查询
                wrapper.lt("createtime",sysUser.getEndTime());
            }

        }

        System.out.println("sysUser:"+sysUser);
        //得到总记录数
        int total = repository.selectCount(wrapper);
        //设置分页信息
        sysUser.setBegin((dto.getPage()-1)*dto.getPageSize());
//        List<SysUser> rows = repository.selectPage(new Page<SysUser>(dto.getPage(),dto.getPageSize()),wrapper);
        //得到
        List<SysUserVo> rows = repository.findSysUserList(sysUser);
        //查询该用户下关联的角色
       if (rows != null){
           for (SysUserVo userVo: rows) {
                findRoleByUsername(userVo);
           }

       }

        return new DataGridResultDTO(null,null,total,rows);
    }

    /**
     * 根据用户的名查询该关联的角色信息
     */
    public void findRoleByUsername(SysUserVo sysUserVo){
        //得到该用户关联的角色id
        List<String> roleids = repository.findUserRole(sysUserVo.getUsername());
        if (roleids != null){
            System.out.println("roleids ----->"+roleids.size());
            for (String roleid:roleids) {
                SysRole role = roleRepository.selectById(roleid);
                sysUserVo.getRoles().add(role);
            }
        }

    }

    /**\
     * 根据用户名判断该用户名是否可用
     * @param username 要查询的用户名
     * @return 返回比较后的结果
     */
    public boolean findSysUserByUserName(String username) {

        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        if (username != null && username != ""){
            wrapper.eq("username",username);
        }
        List<SysUser> list = repository.selectList(wrapper);
        if (list != null && list.size() != 0){
            //该用户名已经存在
            return true;
        }
        //该用户名可用
        return false;
    }

    /**
     * 添加系统用户信息
     * @param sysUser
     * @param pro_area
     */
    public void saveSysUser(SysUser sysUser, String pro_area) {
        //完善要添加的系统用户信息

        sysUser.setPwd(DigestUtils.md5DigestAsHex("123456".getBytes()));
        sysUser.setAddr(pro_area+sysUser.getAddr());
        sysUser.setCreatetime(new Date());
        sysUser.setLastupdate(new Date().toString());
        System.out.println("sysUser:"+sysUser);
        //保存到数据库
        repository.insert(sysUser);
    }

    /**
     * 0批量删除系统用户的信息
     */
    @Override
    public String deleteSysUsers(String ids) {
        //解析得到具体的用户名
        String usernames[] = ids.split(",");
        String fail ="";
        int success = 0;
        String msg="";
        for (int i = 0;i<usernames.length;i++){
            EntityWrapper wrapper = new EntityWrapper();
            //根据用户名删除系统用户
            try{
                wrapper.eq("username",usernames[i]);
                //改为逻辑删除用户状态：1正常，0暂停

                repository.updateSysUserByUserstate("0",usernames[i]);
                success ++;
            }catch (Exception e){
                fail += usernames[i]+"、";
                e.printStackTrace();
            }
            msg = "成功删除"+success+"条数据";
            if (fail != ""){
                msg += ","+fail+"删除失败！";
            }
        }

        return msg;
    }

    /**
     * 恢复已经删除的用户信息
     * @param ids
     */
    public void updateSysUserByState(String ids) {
        String  usernames[] = ids.split(",");
        for (String  username:usernames ) {
            //把已经暂停的用户信修改为1 用户状态：1正常，0暂停
            try{
                repository.updateSysUserByUserstate("1",username);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 进行用户和角色的关联
     * @param usernames 用户的名称
     * @param roles 角色的id
     */
    public void relationUserToRole(String usernames, String[] roles) {

        if (usernames == null){
            return;
        }
        //解析得到用户名称
        String usernameArray[] = usernames.split(",");
       for (int i = 0;i<usernameArray.length;i++){
           //删除该用户本来关联的角色信息
           repository.deleteUserRoleByUsername(usernameArray[i]);
           if (roles == null||roles.length == 0){
               continue;
           }
           for (int j = 0;j<roles.length;j++){
               //关联角色
               repository.relationUserToRole(UUID.randomUUID().toString(),usernameArray[i],roles[j]);
           }
       }

    }
}

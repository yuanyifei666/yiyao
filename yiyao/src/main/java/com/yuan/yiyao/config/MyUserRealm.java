package com.yuan.yiyao.config;


import com.yuan.yiyao.operation.repository.OperationRepository;
import com.yuan.yiyao.operation.vo.Operation;
import com.yuan.yiyao.sys.vo.SysRole;
import com.yuan.yiyao.user.repository.UserRepository;
import com.yuan.yiyao.user.service.UserService;
import com.yuan.yiyao.user.vo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 进行用户的认证和授权
 */
public class MyUserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    /**
     * 进行授权
     * @param principalCollection
     * @return
     * SELECT OP.CODE FROM OPERATION OP,ROLE_OPERATION RO WHERE OP.ID=RO.OPERATION AND RO.ROLEID IN (SELECT ROLEID FROM USERROLE WHERE USERID='yuan01');
     *
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //得到当前操作的用户
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        //进行授权
        //如果是 admin用户这拥有全部的权限
        if("admin".equals(user.getUsername().trim())){
            //得到该用户关联的角色
            List<Operation> operations = operationRepository.findOperations();
            System.out.println("进行授权。。。。。。。。。。"+operations.size());

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for (Operation operation:operations
                 ) {
                if (operation.getCode() !=null){
                    info.addStringPermission(operation.getCode());
                }
            }
            return info;
        }
        //得到该用户关联的角色
        List<String> permis = operationRepository.findOperationCodeByUsername(user.getUsername());
        System.out.println("进行授权。。。。。。。。。。"+permis.size());
        //根据得到当前用户拥有的权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permis);

        return info;
    }

    /**
     * 进行认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进行用户身份的验证-----------------");
        //得到用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //根据用户名查询数据库
        SysUser user = userRepository.findByUsername(token.getUsername());
        System.out.println("user--state--->"+user.getUserstate());
        if (user == null){
            //用户名不存在
            return null;
        }
        if (user.getUserstate().equals("0")){
            return null;
        }
        //判断密码是否正确
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");

    }
}

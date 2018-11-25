package com.yuan.yiyao.user.service.impl;

import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.user.repository.UserRepository;
import com.yuan.yiyao.user.service.UserService;
import com.yuan.yiyao.user.vo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * 用户的业务层
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    /**
     * 进行用户的登录验证
     * @param user
     */
    public SysUser login(SysUser user)throws Exception {
        //根据用户名查询用户信息
        SysUser result = repository.findByUsername(user.getUsername());
        //判断用户是否存在
        if (result == null){
            //登录失败
            throw new MyException("用户名或密码错误!");
        }
        //判断密码是否正确(密码是进行加密的)
        String password = DigestUtils.md5DigestAsHex(user.getPwd().getBytes());
        System.out.println("password:"+password);
        if (!result.getPwd().equals(password)){
            throw new MyException("用户名或密码错误!");
        }
        return result;
    }


    /**
     * 修改用户的密码
     * @param user
     */
    public void updatePwd(SysUser user) {
        repository.updatePwd(user);
    }
}


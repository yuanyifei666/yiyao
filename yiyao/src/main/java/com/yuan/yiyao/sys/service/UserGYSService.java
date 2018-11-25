package com.yuan.yiyao.sys.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.vo.UserGYS;

import java.util.List;

public interface UserGYSService {
    List findAll();

    void save(UserGYS userGYS);

    void deleteUserGYSById(String id);

    void updateUserGYS(UserGYS userGYS);

    DataGridResultDTO findUserGYSByPage(UserGYS userGYS,Integer page,Integer rows);
}

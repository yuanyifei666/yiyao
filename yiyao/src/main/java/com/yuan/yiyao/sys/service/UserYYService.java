package com.yuan.yiyao.sys.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.vo.UserYY;

import java.util.List;

public interface UserYYService {
    DataGridResultDTO findByPage(DataGridResultDTO dto,UserYY userYY)throws Exception;

    void save(UserYY userYY);

    int deleteUserYYByIds(String[] ids);

    void updateUserYY(UserYY userYY);

    List<UserYY> findAll();
}

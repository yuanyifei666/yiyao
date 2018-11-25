package com.yuan.yiyao.sys.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.vo.UserJD;
import com.yuan.yiyao.sys.vo.UserJDVO;

import java.util.List;

public interface UserJDService {
    List<UserJD> findAll();

    void save(UserJD userJD);

    void deleteById(String id);

    void updateUserJD(UserJD userJD);

    DataGridResultDTO findByPage(UserJD userJD,  DataGridResultDTO dto);
}

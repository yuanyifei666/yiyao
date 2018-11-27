package com.yuan.yiyao.ypxx.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpJdDTO;

public interface JdYpMLService {

    DataGridResultDTO findYpmlList(YpJdDTO ypJdDTO);


    void updateJdYpmlControl(YpJdDTO ypJdDTO);
}

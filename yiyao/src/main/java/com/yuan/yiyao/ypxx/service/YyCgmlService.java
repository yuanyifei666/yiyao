package com.yuan.yiyao.ypxx.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import com.yuan.yiyao.ypxx.dto.YyCgYpmlDTO;

public interface YyCgmlService {
    DataGridResultDTO yyypcgmlList(YyCgYpmlDTO yyCgYpmlDTO);

    void saveCgypml(YyCgYpmlDTO dto,String cgxx[])throws Exception;

    DataGridResultDTO findYpmlListNotInCgml(YpJdDTO ypJdDTO);

    void deleteYyCgypml(String[] ids);
}

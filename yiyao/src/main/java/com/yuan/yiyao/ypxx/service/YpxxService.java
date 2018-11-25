package com.yuan.yiyao.ypxx.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;
import com.yuan.yiyao.ypxx.vo.Ypxx;

import java.util.List;

public interface YpxxService {
    DataGridResultDTO findYpxxListByPage(YpxxDTO ypxxDTO);

    void saveYpxx(Ypxx ypxx);

    void updateYpxx(Ypxx ypxx);

    void deleteYpxxs(String id);

    List<YpxxDTO> findYpxxListByExport(YpxxDTO ypxxDTO);
}

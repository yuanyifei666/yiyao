package com.yuan.yiyao.ypxx.service;

import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.ypxx.dto.YpxxDTO;

public interface GysYpMLService {

    DataGridResultDTO findByGysYpML(YpxxDTO ypxxDTO);

    DataGridResultDTO findYpxxByGYS(YpxxDTO ypxxDTO);

    void addGysYpxx(String[] ypxxids,String sysid)throws Exception;

    void deleteGysYpxx(String[] ypxxids);

    void updateGysYpxx(YpxxDTO ypxxDTO);
}

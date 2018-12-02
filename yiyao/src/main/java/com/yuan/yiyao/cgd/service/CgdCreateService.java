package com.yuan.yiyao.cgd.service;

import com.yuan.yiyao.cgd.dto.CgdDTO;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.vo.CgdVO;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.vo.UserYY;

public interface CgdCreateService {
    CgdDTO createCgdMessage(UserYY userYY);

    void saveCgdXX(CgdVO cgdDTO);

    CgdVO findCgdById(String id);

    void updateCgdXX(CgdVO cgdDTO);

    void addCgdMx(String[] ypxxids, String cgdid)throws Exception;

    DataGridResultDTO findCgdmxList(CgdmxDTO cgdmxDTO);

    void deleteCgdmx(String[] cgdmxids);
}

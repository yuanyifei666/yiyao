package com.yuan.yiyao.cgd.service;

import com.yuan.yiyao.cgd.dto.CgdDTO;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.dto.GysCgdDTO;
import com.yuan.yiyao.cgd.vo.CgdVO;
import com.yuan.yiyao.cgd.vo.YpRkVO;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;

public interface YyCgdService {
    DataGridResultDTO findCgdList(CgdDTO cgdDTO);

    void deleteCgdByIds(String[] cgdids);

    CgdDTO findCgdById(String cgdid);

    void updateCgl(String id,Integer cgl);

    void submitCgd(CgdVO cgdVO);

    void submitControlCgd(CgdVO cgdVO);

    DataGridResultDTO gysCgdList(GysCgdDTO dto);

    void gysCgdAccept(String[] cgdmxids);

    DataGridResultDTO findRkList(CgdmxDTO cgdmxDTO);

    void saveYprk(YpRkVO ypRkVO);

    void yprkSubmit(YpRkVO ypRkVO,String rks[])throws Exception;

}

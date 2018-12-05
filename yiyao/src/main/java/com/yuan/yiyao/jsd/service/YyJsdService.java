package com.yuan.yiyao.jsd.service;

import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.jsd.dto.JsdDTO;
import com.yuan.yiyao.jsd.dto.JsdMxDTO;
import com.yuan.yiyao.jsd.vo.JsdMx;
import com.yuan.yiyao.jsd.vo.JsdVO;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;

public interface YyJsdService {
    JsdDTO jsdPageInit(String sysid);

    void saveYyJsd(JsdVO jsdVO);

    DataGridResultDTO findJsdMx(CgdmxDTO cgdmxDTO);

    void saveJsdMx(String[] jsdmxs)throws Exception;

    DataGridResultDTO loadJsdMx(JsdMxDTO jsdMxDTO);

    DataGridResultDTO findJsdList(JsdDTO jsdDTO);

    JsdDTO findJsdById(String yyjsdid);

    void deleteJsdmx(String[] jsdmxids);

    void updateJsdmxByjsl(JsdMx jsdMx);

    void jsdSubmit(JsdVO jsdVO);

    void deleteJsd(String[] jsdids);

    void jsdAcceptSubmit(JsdVO jsdVO);

    void updateJsdZt(String r6_order);
}

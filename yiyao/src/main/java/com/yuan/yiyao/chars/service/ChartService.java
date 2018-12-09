package com.yuan.yiyao.chars.service;

import com.yuan.yiyao.chars.dto.ChartsDTO;
import com.yuan.yiyao.chars.dto.ChartsResultDTO;
import com.yuan.yiyao.ypxx.vo.Ypxx;

import java.util.List;

public interface ChartService {
    List<List<Object>> userYYPie();


    ChartsResultDTO findUserYYBar(String id);

    List<List<Object>> findByUserGysPie();

    ChartsResultDTO findByUserGysBar(String id);

    ChartsResultDTO ypxxBar(ChartsDTO dto);

    List<ChartsDTO> findByYpxxList(ChartsDTO dto);
}

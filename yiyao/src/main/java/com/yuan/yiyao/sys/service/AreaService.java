package com.yuan.yiyao.sys.service;

import com.yuan.yiyao.sys.vo.Areas;
import com.yuan.yiyao.sys.vo.Citys;
import com.yuan.yiyao.sys.vo.Provinces;

import java.util.List;

public interface AreaService {
    List<Provinces> findProvinces();


    List<Citys> findCitysByProId(Integer proId);

    List<Areas> findAreasByCityId(Integer city_id);
}

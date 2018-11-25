package com.yuan.yiyao.sys.service.impl;

import com.yuan.yiyao.sys.repository.AreaRepository;
import com.yuan.yiyao.sys.service.AreaService;
import com.yuan.yiyao.sys.vo.Areas;
import com.yuan.yiyao.sys.vo.Citys;
import com.yuan.yiyao.sys.vo.Provinces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域管理业务层
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository repository;

    /**
     * 查询所有的省级信息列表
     * @return
     */
    public List<Provinces> findProvinces() {
        return repository.findProvinces();
    }

    /**
     * 根据省的id查询该省下的所有城市
     * @param proId
     * @return
     */
    public List<Citys> findCitysByProId(Integer proId) {
        return repository.findCitysByProId(proId);
    }

    /**
     * 根据城市的id查询该城市下的所有区域列表信息
     * @param city_id
     * @return
     */
    public List<Areas> findAreasByCityId(Integer city_id) {
        return repository.findAreaByCityId(city_id);
    }
}

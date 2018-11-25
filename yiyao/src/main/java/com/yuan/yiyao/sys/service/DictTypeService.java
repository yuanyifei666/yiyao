package com.yuan.yiyao.sys.service;

import com.yuan.yiyao.sys.vo.DictInfo;
import com.yuan.yiyao.sys.vo.DictType;

import java.util.List;

public interface DictTypeService {
    List<DictInfo> findByCode(String code);

    List<DictType> findDictTypeList();


    void insertDictType(DictType dictType);

    DictType findDictTypeByTypecode(String typecode);

    void deleteDictTypeById(String id);

    void updateDicitType(DictType dictType);

    void saveDictInfo(DictInfo dictInfo);

    void deleteDictInfoById(String id);

    void updateDictInfo(DictInfo dictInfo);
}

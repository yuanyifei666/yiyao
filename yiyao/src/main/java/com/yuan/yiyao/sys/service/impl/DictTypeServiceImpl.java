package com.yuan.yiyao.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuan.yiyao.sys.repository.DictInfoRepository;
import com.yuan.yiyao.sys.repository.DictTypeRepository;
import com.yuan.yiyao.sys.service.DictTypeService;
import com.yuan.yiyao.sys.vo.DictInfo;
import com.yuan.yiyao.sys.vo.DictType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 数字字典业务层
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Autowired
    private DictTypeRepository repository;

    @Autowired
    private DictInfoRepository  dictInfoRepository;
    /**
     * 根据数字字典的代码查询相应的数字字典明细
     * @param code
     * @return
     */
    public List<DictInfo> findByCode(String code) {
        return repository.findByCode(code);
    }

    /**
     * 查询所有的字典类型信息列表
     */
    public List<DictType> findDictTypeList() {
        return repository.selectList(new EntityWrapper<DictType>());
    }

    /**
     * 添加字典类型
     */
    public void insertDictType(DictType dictType) {
        dictType.setTypecode(dictType.getTypecode().trim());
        repository.insert(dictType);
    }
    /**
     * 根据typecode查询字典信息
     */
    public DictType findDictTypeByTypecode(String typecode) {
        EntityWrapper<DictType> wrapper = new EntityWrapper<>();
        wrapper.eq("typecode",typecode.trim());
        List<DictType> list = repository.selectList(wrapper);
        if (list !=null&&list.size() != 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据id批量 删除字典数据
     */
    public void deleteDictTypeById(String id) {
        if (id==null||"".equals(id.trim())){
            return;
        }
        //解析得到具体的id
        String ids[] = id.split(",");
        for (String typeId:ids){
            //同步删除该字典类型下的字典
            DictType type = repository.selectById(typeId);
            repository.deleteDictInfoByTypeCode(type.getTypecode());
            //根据id删除字典类型数据
            repository.deleteById(Integer.parseInt(typeId));
        }
    }
    /**
     * 修改数据
     */
    public void updateDicitType(DictType dictType) {
//        同步更新该字典类型下的字典
        DictType beforeDictType = repository.selectById(dictType.getId());
        List<DictInfo> dictInfos = null;
        if (beforeDictType != null&&beforeDictType.getTypecode() != dictType.getTypecode().trim()){
            dictInfos = repository.findDictInfoList(beforeDictType.getTypecode());
        }
        if (dictInfos != null){
            //更新字典的字典类型代码
            repository.updateDictInfoByTypeCode(dictType.getTypecode().trim(),beforeDictType.getTypecode());
        }
        //修改字典类型的信息
        repository.updateById(dictType);
    }
    /**
     * 添加字典信息
     */
    public void saveDictInfo(DictInfo dictInfo) {
        //完善信息
        dictInfo.setId(UUID.randomUUID().toString());
        dictInfoRepository.insert(dictInfo);
    }
    /**
     * 根据字典id删除数据
     */
    public void deleteDictInfoById(String id) {
        //解析得到具体的字典id
        if (id == null){
            return;
        }
        String ids[] = id.split(",");
        if (ids != null){
            for (String infoId:ids
                 ) {
                //根据id删除数据
                dictInfoRepository.deleteById(infoId);
            }
        }
    }
    /**
     * 更新字典信息
     */
    public void updateDictInfo(DictInfo dictInfo) {
        //更新字典信息
        dictInfoRepository.updateById(dictInfo);
    }
}

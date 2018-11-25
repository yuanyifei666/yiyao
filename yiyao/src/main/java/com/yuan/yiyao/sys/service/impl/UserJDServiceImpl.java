package com.yuan.yiyao.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.repository.UserJDRepositoryPlus;
import com.yuan.yiyao.sys.service.UserJDService;
import com.yuan.yiyao.sys.vo.UserJD;
import com.yuan.yiyao.sys.vo.UserJDVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 监督单位的业务层
 */
@Service
@Transactional
public class UserJDServiceImpl implements UserJDService {

    @Autowired
    private UserJDRepositoryPlus repository;

    /**
     * 查询所有的监督单位的列表信息
     * @return
     */
    public List<UserJD> findAll() {
        return repository.selectList(new EntityWrapper<UserJD>());
    }

    /**
     * 添加监督单位信息
     */
    public void save(UserJD userJD) {
        //完善 监督单位的地址
       //设置id
        userJD.setId(UUID.randomUUID().toString());
        //保存到数据库
        repository.insert(userJD);
    }
    /**
     * g根据id批量删除监督单位信息
     */
    public void deleteById(String id) {
        //判断是否有数据
        if (id == null||"".equals(id.trim())){
            return;
        }
        //解析得到id
        String ids[] = id.split(",");
        for (String userJdId:ids
             ) {
            //根据id删除数据
            repository.deleteById(userJdId);
        }
    }

    /**
     * 修改监督单位的信息
     */
    public void updateUserJD(UserJD userJD) {
        //更新监督单位的信息
        repository.updateById(userJD);
    }

    /**
     * 根据条件分页查询
     */
    public DataGridResultDTO findByPage(UserJD userJD, DataGridResultDTO dto) {
        EntityWrapper<UserJD> wrapper = new EntityWrapper<>();
        if (userJD != null){
            //添加查询条件
            if (userJD.getMc()!=null&&!"".equals(userJD.getMc().trim())){
                //根据名称查询
                wrapper.like("mc",userJD.getMc().trim());
            }
            if (userJD.getYzbm()!=null&&!"".equals(userJD.getYzbm().trim())){
                //根据 邮政编码查询
                wrapper.eq("yzbm",userJD.getYzbm().trim());
            }
            if (userJD.getDh() !=null&&!"".equals(userJD.getDh().trim())){
                //根据电话查询
                wrapper.eq("dh",userJD.getDh().trim());
            }
            if (userJD.getProId()!=null){
                wrapper.eq("pro_id",userJD.getProId());
            }
            if (userJD.getCityId()!=null){
                wrapper.eq("city_id",userJD.getCityId());
            }
            if (userJD.getAreaId() != null){
                wrapper.eq("area_id",userJD.getAreaId());
            }
        }
        //查询符合条件的总记录数
        int count = repository.selectCount(wrapper);
        //根据条件分页查询
        List<UserJD> rows = repository.selectPage(new Page<UserJD>(dto.getPage(),dto.getPageSize()),wrapper);
        dto.setTotal(count);
        dto.setRows(rows);
        return dto;
    }
}

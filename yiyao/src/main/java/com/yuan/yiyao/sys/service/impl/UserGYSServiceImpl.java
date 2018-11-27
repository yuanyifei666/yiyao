package com.yuan.yiyao.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.repository.UserGYSRepository;
import com.yuan.yiyao.sys.service.UserGYSService;
import com.yuan.yiyao.sys.vo.UserGYS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 供应商的业务访问层
 */
@Service
@Transactional
public class UserGYSServiceImpl implements UserGYSService {

    @Autowired
    private UserGYSRepository repository;


    /**
     *查询所有的供应商信息列表
     * @return
     */
    public List findAll() {
        return repository.selectList(new EntityWrapper<UserGYS>());
    }

    /**
     * 把数据添加到数据库
     * @param userGYS 要添加的供应商信息
     */
    public void save(UserGYS userGYS) {
        //完善供应商信息
        userGYS.setId(UUID.randomUUID().toString());
        //设置供应商的地址
        userGYS.setAddres(userGYS.getAddres()+userGYS.getLxdz());

        //保存到数据库
        repository.insert(userGYS);
    }

    /**
     * 根据供应商得到id删除信息
     * @param id
     */
    public void deleteUserGYSById(String id) {
        //解析id
        if (id != null){
            String ids[] = id.split(",");
            if (ids != null){
                for (String userGYSid:ids
                     ) {
                    //根据供应商id删除相应的信息
                    repository.deleteById(userGYSid);
                }
            }
        }
    }
    /**
     * 更新供应商的信息
     */
    public void updateUserGYS(UserGYS userGYS) {
        //修改供应商的地址
        userGYS.setAddres(userGYS.getAddres()+userGYS.getLxdz());
        repository.updateById(userGYS);
    }

    /**
     *根据条件分页查询供应商信息
     */
    public DataGridResultDTO findUserGYSByPage(UserGYS userGYS,Integer page,Integer rows) {
        //定义分页查询条件
        EntityWrapper<UserGYS> wrapper = new EntityWrapper<>();
        if (userGYS != null){
            //添加查询条件
            if (userGYS.getMc() != null&&!"".equals(userGYS.getMc().trim())){
                //添加根据企业名称模糊查询
                wrapper.like("mc",userGYS.getMc().trim());
            }
            if (userGYS.getYzbm() !=  null&&!"".equals(userGYS.getYzbm().trim())){
                //添加根据邮政编码查询
                wrapper.eq("yzbm",userGYS.getYzbm().trim());
            }
            if (userGYS.getLxr() != null && !"".equals(userGYS.getLxr().trim())){
                //添加根据企业联系人来查询
                wrapper.like("lxr",userGYS.getLxr().trim());
            }
            if (userGYS.getDh() != null && !"".equals(userGYS.getDh().trim())){
                //添加联系电话查询条件
                wrapper.eq("dh",userGYS.getDh().trim());
            }
            if (userGYS.getFrmc() != null &&!"".equals(userGYS.getFrmc().trim())){
                //添加根据法人名称查询条件
                wrapper.like("frmc",userGYS.getFrmc().trim());
            }
            if (userGYS.getProId() != null){
                //根据省来查询
                wrapper.eq("pro_id",userGYS.getProId());
            }
            if (userGYS.getCityId() !=null){
                //根据城市来查询
                wrapper.eq("city_id",userGYS.getCityId());
            }
            if (userGYS.getAreaId() !=null){
                //根据区域lai查询
                wrapper.eq("area_id",userGYS.getAreaId());
            }
        }
        //查询符合条件的总记录数
        int total = repository.selectCount(wrapper);
        //根据条件分页查询供应商列表信息
        List<UserGYS> list = repository.selectPage(new Page<UserGYS>(page,rows),wrapper);
        System.out.println("userGYS---list------>"+list.size());

        return new DataGridResultDTO(null,null,total,list);
    }

    @Override
    public List<UserGYS> findUserGysList() {
        return repository.selectList(new EntityWrapper<UserGYS>());
    }
}

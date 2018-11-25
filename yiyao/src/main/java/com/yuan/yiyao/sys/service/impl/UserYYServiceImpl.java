package com.yuan.yiyao.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.repository.AreaRepository;
import com.yuan.yiyao.sys.repository.UserYYRepository;
import com.yuan.yiyao.sys.repository.UserYYRepositoryPlus;
import com.yuan.yiyao.sys.service.UserYYService;
import com.yuan.yiyao.sys.vo.Areas;
import com.yuan.yiyao.sys.vo.Citys;
import com.yuan.yiyao.sys.vo.Provinces;
import com.yuan.yiyao.sys.vo.UserYY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

/**
 * 系统医院信息的业务逻辑层
 */
@Service
@Transactional
public class UserYYServiceImpl  implements UserYYService {

    @Autowired
    private UserYYRepository repository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private UserYYRepositoryPlus userYYRepositoryPlus;

    /**
     * 分页查询医院列表信息
     * @return
     */
    public DataGridResultDTO findByPage(DataGridResultDTO dto,UserYY userYY)throws  Exception {
        //得到总记录数
//        int count = repository.findByCount();

        //根据条件分页查询医院的信息
        //定义分页条件
        Page<UserYY> page = new Page<UserYY>(dto.getPage(),dto.getPageSize());
        //添加查询条件
        EntityWrapper wrapper = new EntityWrapper();
        if (userYY != null){
            if (userYY.getMc()!=null && userYY.getMc() != ""){
                //添加根据医院名称模糊查询
                wrapper.like("MC",userYY.getMc());
            }
            if (userYY.getYzbm() != null && userYY.getYzbm() != ""){
                //根据邮政编码来查询
                wrapper.eq("YZBM",userYY.getYzbm());
            }
            if (userYY.getDh() != null && userYY.getDh() != ""){
                //根据电话查询
                wrapper.eq("DH",userYY.getDh());
            }
            if (userYY.getLb() != null && userYY.getLb() != ""){
                //根据医院类别来查询
                wrapper.eq("LB",userYY.getLb());
            }
            if (userYY.getProId() != null ){
                //根据省来查询
                wrapper.eq("PRO_ID",userYY.getProId());
            }
            if (userYY.getCityId() != null){
                //根据城市的id来查询
                wrapper.eq("CITY_ID",userYY.getCityId());
            }
            if (userYY.getAreaId() != null){
                //根据区来查询
                wrapper.eq("AREA_ID",userYY.getAreaId());
            }
            if (userYY.getFyljg() != null && userYY.getFyljg() != ""){
                //根据盈利条件来查询
                wrapper.eq("FYLJG",userYY.getFyljg());
            }
        }

        List<UserYY> list = userYYRepositoryPlus.selectPage(page,wrapper);
        //得到符合条件的总记录数
        int count = userYYRepositoryPlus.selectCount(wrapper);
        System.out.println("count:"+count);
//        List<UserYY> list = repository.findByPage((dto.getPage()-1)*dto.getPageSize(),dto.getPageSize(),userYY);
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        resultDTO.setTotal(count);
        resultDTO.setRows(list);
        return resultDTO;
    }

    /**
     * 添加新医院信息
      * @param userYY
     */
    public void save(UserYY userYY) {
        //生成医院的id
        String id  = UUID.randomUUID().toString();
        userYY.setId(id);//设置医院的id
        //设置医院的详细地址
        UserYY result = setDz(userYY);
        repository.save(result);
    }

    /**
     * 批量删除医院信息
     * @param ids
     */
    public int deleteUserYYByIds(String[] ids) {
        int fail = 0;
        for (String id:ids ) {
            try{
                repository.deleteById(id);
            }catch (Exception e){
                e.printStackTrace();
                fail+=1;
            }
        }
        return fail;
    }

    /**
     * 修改医院的信息
     * @param userYY
     */
    public void updateUserYY(UserYY userYY) {
        //修改医院的地址
        UserYY result = setDz(userYY);
        //更新医院的信息
        repository.updateUserYY(result);
    }

    /**
     * 设置医院的联系地址
     */
    public UserYY setDz(UserYY userYY){
        //设置联系地址
        Provinces province ;
        Citys city;
        Areas area ;
//        医院的地址
        String dz = "";
        if (userYY.getProId() != null){
            province = areaRepository.findProvincesById(userYY.getProId());
            dz += province.getProName();
        }
        if (userYY.getCityId() != null){
            city = areaRepository.findCityById(userYY.getCityId());
            dz += city.getCityName();
        }
        if (userYY.getAreaId() != null){
            area = areaRepository.findAreaById(userYY.getAreaId());
            dz += area.getAreaName();
        }
        dz += userYY.getMc();
        System.out.println("dz---->"+dz);
        userYY.setDz(dz);
        return userYY;
    }

    /**
     * 查询所有的医院列表信息
     * @return
     */
    public List<UserYY> findAll() {
        return userYYRepositoryPlus.selectList(new EntityWrapper<UserYY>());
    }
}

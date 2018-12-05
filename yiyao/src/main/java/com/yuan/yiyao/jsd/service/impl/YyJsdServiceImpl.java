package com.yuan.yiyao.jsd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.repository.CgdMxRepository;
import com.yuan.yiyao.cgd.repository.CgdRepository;
import com.yuan.yiyao.cgd.vo.CgdMxVO;
import com.yuan.yiyao.cgd.vo.CgdVO;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.jsd.dto.JsdDTO;
import com.yuan.yiyao.jsd.dto.JsdMxDTO;
import com.yuan.yiyao.jsd.repository.JsdMxRepository;
import com.yuan.yiyao.jsd.repository.JsdRepository;
import com.yuan.yiyao.jsd.service.YyJsdService;
import com.yuan.yiyao.jsd.vo.JsdMx;
import com.yuan.yiyao.jsd.vo.JsdVO;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.repository.UserGYSRepository;
import com.yuan.yiyao.sys.repository.UserYYRepositoryPlus;
import com.yuan.yiyao.sys.vo.UserGYS;
import com.yuan.yiyao.sys.vo.UserYY;
import com.yuan.yiyao.utils.SysUserUtile;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 医院结算单业务层
 */
@Service
@Transactional
public class YyJsdServiceImpl implements YyJsdService {

    @Autowired
    private JsdRepository repository;
    @Autowired
    private UserYYRepositoryPlus userYYRepository;
    @Autowired
    private CgdMxRepository cgdMxRepository;
    @Autowired
    private JsdMxRepository jsdMxRepository;
    @Autowired
    private CgdRepository cgdRepository;
    @Autowired
    private UserGYSRepository userGYSRepository;


    /**
     * 封装创建结算单页面初始化数据
     * @param sysid
     * @return
     */
    public JsdDTO jsdPageInit(String sysid) {
        //得到当前用户单位的信息
        UserYY userYY = userYYRepository.selectById(sysid);
        JsdDTO dto = new JsdDTO();
        //生成id
        dto.setId(UUID.randomUUID().toString());
        //生成结算单的编号
        dto.setBm(SysUserUtile.createBm(sysid));
        dto.setYymc(userYY.getMc());
        //生成结算单的名称
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String time = format.format(new Date());
        dto.setMc(userYY.getMc()+time+dto.getBm());
        dto.setUseryyid(sysid);

        return dto;
    }

    /**
     * 保存结算单基本信息
     * @param jsdVO
     */
    public void saveYyJsd(JsdVO jsdVO) {
        if (jsdVO == null){
            return;
        }
        JsdVO vo = repository.selectById(jsdVO.getId());
        if (vo == null){
            //新添加结算单基本信息1.未提交，2已提交至供货商
            jsdVO.setCjtime(new Date());
            jsdVO.setZt("1");
            jsdVO.setXgtime(new Date());
            repository.insert(jsdVO);
        }else{
            //更新结算单基本信息
            vo.setLxr(jsdVO.getLxr());
            vo.setLxdh(jsdVO.getLxdh());
            vo.setCjr(jsdVO.getCjr());
            vo.setBz(jsdVO.getBz());
            vo.setUsergysid(jsdVO.getUsergysid());
            vo.setXgtime(new Date());
            repository.updateById(vo);
        }
    }

    /**
     * 结算单明细添加查询
     * @param cgdmxDTO
     * @return
     */
    public DataGridResultDTO findJsdMx(CgdmxDTO cgdmxDTO) {
        //查询采购药品明细为已经入库的药品方可结算
        cgdmxDTO.setCgzt("3");
        cgdmxDTO.setBegin((cgdmxDTO.getPage()-1)*cgdmxDTO.getRows());

        List<CgdmxDTO> rows = cgdMxRepository.findCgdmxList(cgdmxDTO);
        int total = cgdMxRepository.findCgdmxCount(cgdmxDTO);

        DataGridResultDTO resultDTO = new DataGridResultDTO();
        resultDTO.setRows(rows);
        resultDTO.setTotal(total);

        return resultDTO;
    }

    /**
     * 保存添加的结算单明细
     * @param jsdmxs
     * @throws Exception
     */
    public void saveJsdMx(String[] jsdmxs) throws Exception {
        String errorMsg = "";
        if (jsdmxs != null &&jsdmxs.length !=0){
            //解析得到具体结算信息
            for (String jsdmx:jsdmxs
                 ) {
                String jsd[] = jsdmx.split("##");
                if (jsd.length == 3){
                    String yyjsdid = jsd[0];
                    String ypxxid = jsd[1];
                    String yycgdid = jsd[2];
                    //判断该信息是否存在
                    EntityWrapper<JsdMx> jsdmxWrapper = new EntityWrapper<>();
                    jsdmxWrapper.eq("yyjsdid",yyjsdid);
                    jsdmxWrapper.eq("ypxxid",ypxxid);
                    jsdmxWrapper.eq("yycgdid",yycgdid);
                    List<JsdMx> jsdMxes = jsdMxRepository.selectList(jsdmxWrapper);
                    if (jsdMxes == null || jsdMxes.size() ==0){

                        //得到交易价格
                        CgdMxVO cgdMxVO = findCgdmxByYpxxidAndYyCgdid(ypxxid,yycgdid);
                        //添加保存结算明细
                        JsdMx jsdMxVo = new JsdMx();
                        jsdMxVo.setId(UUID.randomUUID().toString());
                        jsdMxVo.setYyjsdid(yyjsdid);
                        jsdMxVo.setYpxxid(ypxxid);
                        jsdMxVo.setYycgdid(yycgdid);
                        jsdMxVo.setJsl(1);
                        jsdMxVo.setJsje(cgdMxVO.getJyjg()*jsdMxVo.getJsl());
                        jsdMxVo.setJszt("1");//结算状态1、未确认结算 2、已确认结算
                        jsdMxRepository.insert(jsdMxVo);
                    }else{
                        //重复添加的结算明细
                        CgdVO cgdVO = cgdRepository.selectById(yycgdid);
                        errorMsg += cgdVO.getBm()+"、";
                    }
                }
            }
        }
        //返回信息
        if (!"".equals(errorMsg)){
            throw new MyException(errorMsg);
        }
    }

    public CgdMxVO findCgdmxByYpxxidAndYyCgdid(String ypxxid,String yycgdid){
        EntityWrapper<CgdMxVO> wrapper = new EntityWrapper<>();
        wrapper.eq("ypxxid",ypxxid);
        wrapper.eq("yycgdid",yycgdid);
        //得到交易价格
        List<CgdMxVO> cgdMxVOS = cgdMxRepository.selectList(wrapper);
        if (cgdMxVOS != null){
            return cgdMxVOS.get(0);
        }
        return null;
    }
    /**
     * 根据结算单id加载该结算单下的所有结算明细
     * @param yyjsdid 结算单id
     * @return
     */
    public DataGridResultDTO loadJsdMx(JsdMxDTO jsdMxDTO) {

        //设置分页查询的开始页吗
        jsdMxDTO.setBegin((jsdMxDTO.getPage()-1)*jsdMxDTO.getRows());
        List<JsdMxDTO> rows = jsdMxRepository.findJsdMxList(jsdMxDTO);
        if (rows != null && rows.size() != 0){
            //计算总和
            JsdMxDTO totalDTO = zonji(jsdMxDTO.getYyjsdid());
            rows.add(totalDTO);
        }

        int total = jsdMxRepository.findJsdMxCOUNT(jsdMxDTO);
        //封装返回的数据
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        resultDTO.setTotal(total);
        resultDTO.setRows(rows);


        return resultDTO;
    }

    /**
     * 计算总结
     * @return
     */
    public JsdMxDTO zonji(String yyjsdid){
        EntityWrapper<JsdMx> wrapper = new EntityWrapper<>();
        wrapper.eq("yyjsdid",yyjsdid);
        List<JsdMx> list = jsdMxRepository.selectList(wrapper);
        int zon_jsl =0;
        Float zon_jsje = 0f ;
        for (JsdMx jsdmx:list
             ) {
            zon_jsl += jsdmx.getJsl();
            zon_jsje += jsdmx.getJsje();
        }
        JsdMxDTO dto = new JsdMxDTO();
        dto.setJsl(zon_jsl);
        dto.setJsje(zon_jsje);
        dto.setBm("总计");
        return dto;
    }

    /**
     * 查询该单位下的所有未提交的结算单列表
     * @param jsdDTO
     * @return
     */
    public DataGridResultDTO findJsdList(JsdDTO jsdDTO) {
        if ("".equals(jsdDTO.getGroupid())||"s0100".equals(jsdDTO.getGroupid())){
            return null;
        }
        //设置查询条件
        EntityWrapper<JsdVO> wrapper = new EntityWrapper<>();
        if (jsdDTO.getBm() != null&&!"".equals(jsdDTO.getBm().trim())){
            wrapper.like("bm",jsdDTO.getBm().trim());
        }
        if (jsdDTO.getLxr() != null && !"".equals(jsdDTO.getLxr().trim())){
            wrapper.like("lxr",jsdDTO.getLxr().trim());
        }
        if (jsdDTO.getCjr() != null && !"".equals(jsdDTO.getCjr().trim())){
            wrapper.like("cjr",jsdDTO.getCjr().trim());
        }
        if (jsdDTO.getStartCjtime() != null){
            wrapper.gt("cjtime",jsdDTO.getStartCjtime());
        }
        if (jsdDTO.getEndCjtime() != null){
            wrapper.lt("cjtime",jsdDTO.getEndCjtime());
        }
        if (jsdDTO.getZt() != null && !"".equals(jsdDTO.getZt().trim())){
            wrapper.eq("zt",jsdDTO.getZt());
        }
        //根据不同的功能模块查询执行不同的查询条件1.未提交，2受理中，3受理不通过，4.待结算，5，已结算

        //采购单维护功能模块
        if ("1".equals(jsdDTO.getState())){
            //结算单维护功能查询查询(查询结算单状态为1.未提交和 3受理不通过)
            wrapper.eq("useryyid",jsdDTO.getUseryyid());
            wrapper.in("zt",new String[]{"1","3"});
        }
        //结算单查询功能模块
        if ("2".equals(jsdDTO.getState())){
            //结算单查询功能模块1.未提交，2受理中，3受理不通过，4.待结算，
            if ("s0101".equals(jsdDTO.getGroupid().trim())){
                //如果是监督单位自可以查询除了未提交的所有的结算单
                wrapper.in("zt",new String[]{"2","3","4","5"});
            }
            if ("s0104".equals(jsdDTO.getGroupid().trim())){
                //医院则可以查询该医院下的状态为
                 wrapper.in("zt",new String[]{"2","5"});
                wrapper.eq("useryyid",jsdDTO.getUseryyid());
            }
            if ("s0103".equals(jsdDTO.getGroupid().trim())){
                //供应商可以查询该供应单位下的状态为
                wrapper.in("zt",new String[]{"3","4","5"});
                wrapper.eq("usergysid",jsdDTO.getUsergysid());
            }
        }

        //结算单受理功能模块
        if ("3".equals(jsdDTO.getState())){
            //查询某个供应商下的所有受理中的结算
                //供应商可以查询该供应单位下的状态为：受理中
                wrapper.eq("zt","2");
                wrapper.eq("usergysid",jsdDTO.getUsergysid());

        }
        //结算支付查询
        if ("4".equals(jsdDTO.getState())){
            //查询该医院下结算单状态为待结算的
            wrapper.eq("zt","4");
            wrapper.eq("useryyid",jsdDTO.getUseryyid());
        }

        List<JsdVO> rows = repository.selectPage(new Page<JsdVO>(jsdDTO.getPage(),jsdDTO.getRows()),wrapper);
        int total = repository.selectCount(wrapper);
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        resultDTO.setRows(rows);
        resultDTO.setTotal(total);
        return resultDTO;
    }

    /**
     * 根据条件查询结算单信息
     * @param yyjsdid 结算单id
     * @return
     */
    public JsdDTO findJsdById(String yyjsdid) {

        if (yyjsdid != null &&!"".equals(yyjsdid)){
            JsdDTO dto = repository.findJsdById(yyjsdid);
            if (dto!=null){
                UserYY userYY = userYYRepository.selectById(dto.getUseryyid());
                dto.setYymc(userYY.getMc());
                UserGYS userGYS = userGYSRepository.selectById(dto.getUsergysid());
                if(userGYS != null){
                    dto.setGysmc(userGYS.getMc());
                }
                return dto;
            }
        }
        return null;
    }

    /**
     * 批量删除结算单明细
     * @param jsdmxids
     */
    public void deleteJsdmx(String[] jsdmxids) {
        if (jsdmxids != null){
            for (String id :jsdmxids
                 ) {
                jsdMxRepository.deleteById(id);
            }
        }
    }

    /**
     * 更新结算明细结算量
     * @param jsdMx
     */
    public void updateJsdmxByjsl(JsdMx jsdMx) {
        if (jsdMx != null){
            JsdMx vo = jsdMxRepository.selectById(jsdMx.getId());
            EntityWrapper<CgdMxVO> wrapper = new EntityWrapper<>();
            wrapper.eq("ypxxid",vo.getYpxxid());
            wrapper.eq("yycgdid",vo.getYycgdid());
            List<CgdMxVO> cgdMxVOS = cgdMxRepository.selectList(wrapper);
            if (cgdMxVOS != null){
                //更新结算金额
                vo.setJsje(cgdMxVOS.get(0).getJyjg()*jsdMx.getJsl());
            }
            //更新结算量
            vo.setJsl(jsdMx.getJsl());
            jsdMxRepository.updateById(vo);
        }
    }

    /**
     * 提交结算单
     * @param jsdVO
     */
    public void jsdSubmit(JsdVO jsdVO) {
        JsdVO vo = repository.selectById(jsdVO.getId());
        //更新数据
        vo.setLxr(jsdVO.getLxr());
        vo.setLxdh(jsdVO.getLxdh());
        vo.setCjr(jsdVO.getCjr());
        vo.setBz(jsdVO.getBz());
        vo.setXgtime(new Date());
        vo.setTjtime(new Date());
        vo.setZt("2");//更新结算单状态
        repository.updateById(vo);
    }

    /**
     * 批量删除结算单
     * @param jsdids 结算单id
     */
    public void deleteJsd(String[] jsdids) {
        if (jsdids != null){
            for (String jsdid:jsdids
                 ) {
                repository.deleteById(jsdid);
                //同步 删除该结算单下的所有结算明
                EntityWrapper<JsdMx> wrapper = new EntityWrapper();
                wrapper.eq("yyjsdid",jsdid);
                jsdMxRepository.delete(wrapper);
            }
        }
    }

    /**
     * 结算D单受理提交
     * @param jsdVO
     */
    public void jsdAcceptSubmit(JsdVO jsdVO) {
        if (jsdVO != null){
            JsdVO vo = repository.selectById(jsdVO.getId());
            //更新结算信息
            vo.setSlyj(jsdVO.getSlyj());
            vo.setZt(jsdVO.getZt());
            repository.updateById(vo);
        }
    }

    /**
     * 支付成功后更新结算单的状态
     * @param r6_order
     */
    public void updateJsdZt(String r6_order) {
        JsdVO vo = repository.selectById(r6_order);
        if (vo != null){
            //更新结算单的状态为已结算1.未提交，2受理中，3受理不通过，4.待结算，5，已结算
            vo.setZt("5");
            repository.updateById(vo);
        }
    }
}

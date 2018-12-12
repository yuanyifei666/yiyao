package com.yuan.yiyao.cgd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yuan.yiyao.cgd.dto.CgdDTO;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.dto.GysCgdDTO;
import com.yuan.yiyao.cgd.repository.CgdMxRepository;
import com.yuan.yiyao.cgd.repository.CgdRepository;
import com.yuan.yiyao.cgd.repository.YpRkRepository;
import com.yuan.yiyao.cgd.service.YyCgdService;
import com.yuan.yiyao.cgd.vo.CgdMxVO;
import com.yuan.yiyao.cgd.vo.CgdVO;
import com.yuan.yiyao.cgd.vo.YpRkVO;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 医院采购单维护页面
 */
@Service
@Transactional
public class YyCgdServiceImpl implements YyCgdService {

    @Autowired
    private CgdRepository repository;
    @Autowired
    private CgdMxRepository cgdMxRepository;
    @Autowired
    private YpRkRepository ypRkRepository;

    /**
     * 采购单查询
     * @param cgdDTO
     * @return
     */
    public DataGridResultDTO findCgdList(CgdDTO cgdDTO) {
        //定义查询条件
        EntityWrapper<CgdVO> wrapper = new EntityWrapper();
        //添加查询条件
        if (cgdDTO.getBm() !=null&&!"".equals(cgdDTO.getBm().trim())){
            wrapper.like("bm",cgdDTO.getBm().trim());
        }
        if (cgdDTO.getLxr() != null && !"".equals(cgdDTO.getLxr().trim())){
            wrapper.like("lxr",cgdDTO.getLxr().trim());
        }
        if (cgdDTO.getCjr() != null && !"".equals(cgdDTO.getCjr().trim())){
            wrapper.like("cjr",cgdDTO.getCjr().trim());
        }
        if (cgdDTO.getStartCjtime() != null){
            wrapper.ge("cjtime",cgdDTO.getStartCjtime());
        }
        if (cgdDTO.getEndCjtime()!= null){
            wrapper.le("cjtime",cgdDTO.getEndCjtime());
        }
        if (cgdDTO.getZt() != null&& !"".equals(cgdDTO.getZt().trim())&&!"s0011".equals(cgdDTO.getZt())){
            wrapper.eq("zt",cgdDTO.getZt().trim());
        }

            if (cgdDTO.getGroupid()!=null&&!"".equals(cgdDTO.getGroupid())){
                if ("s0011".equals(cgdDTO.getZt())){
                    //监督单位采购单控制查询.查询采购单状态为审核中的2：已提交未审核
                    wrapper.eq("zt","2");
                }else{
                    //采购单查询页面查询
                    //只查询状态为为2：已提交未审核、3：审核通过的采购单信息 采购单状态(存储数据字典：1：未提交、2：已提交未审核、3：审核通过、4：审核不通过)

                    //判断当前用户是否是监督单位
                    if (!"s0101".equals(cgdDTO.getGroupid())){
                        //不是监督单位则查询当前用户的单位下的采购单
                        //查询该用户单位下的所有采购单
                        wrapper.in("zt",new String[]{"2","3"});
                        wrapper.eq("useryyid",cgdDTO.getUseryyid());
                    }else{
                        //监督单位查询采购单状态为审核通过和不通过的
                        wrapper.in("zt",new String[]{"3","4"});
                    }
                }

            }else{
                //采购单维护页面查询 只查询状态为为1：未提交、4：审核不通过的采购单
                wrapper.in("zt",new String[]{"1","4"});
                //查询该用户单位下的所有采购单
                wrapper.eq("useryyid",cgdDTO.getUseryyid());
            }

        //执行查询
        List<CgdVO> rows = repository.selectPage(new Page<CgdVO>(cgdDTO.getPage(),cgdDTO.getRows()),wrapper);
        int total = repository.selectCount(wrapper);
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        resultDTO.setTotal(total);
        resultDTO.setRows(rows);
        return resultDTO;
    }

    /**
     * 根据id删除采购单信息
     * @param cgdids 采购单id
     */
    public void deleteCgdByIds(String[] cgdids) {
        if (cgdids != null){
            for (String id:cgdids
                 ) {
                //同步删除该采购单下的所有采购明细
                repository.deleteById(id);
                EntityWrapper wrapper = new EntityWrapper();
                wrapper.eq("yycgdid",id);
                cgdMxRepository.delete(wrapper);
            }
        }
    }

    /**
     *根据id查询采购单信息
     * @param cgdid
     * @return
     */
    public CgdDTO findCgdById(String cgdid) {
        CgdDTO cgdDTO = repository.findCgdByIdToCgdDTO(cgdid);

        return cgdDTO;
    }

    /**
     * 需改采购量
     * @param cgl 采购量
     */
    public void updateCgl(String id,Integer cgl) {
        CgdMxVO vo = cgdMxRepository.selectById(id);
        System.out.println("vo------->"+vo);
        //修改采购量
        vo.setCgl(cgl);
        //需改采购金额
        vo.setCgje(vo.getJyjg()*cgl);
        cgdMxRepository.updateById(vo);
    }

    /**
     *提交采购单
     * @param cgdVO
     */
    public void submitCgd(CgdVO cgdVO) {
        CgdVO vo = repository.selectById(cgdVO.getId());
        //更新采购单信息
        vo.setLxr(cgdVO.getLxr());
        vo.setLxdh(cgdVO.getLxdh());
        vo.setCjr(cgdVO.getCjr());
        vo.setBz(cgdVO.getBz());
        //更新采购单状态1：未提交、2：审核中、3：审核通过、4：审核不通过)
        vo.setZt("2");
        vo.setTjtime(new Date());
        //提交采购单
        repository.updateById(vo);
    }

    /**
     * 提交采购单审核状态
     * @param cgdVO
     */
    public void submitControlCgd(CgdVO cgdVO) {
        CgdVO vo = repository.selectById(cgdVO.getId());
        //更新采购单信息
        vo.setZt(cgdVO.getZt().trim());
        vo.setShyj(cgdVO.getShyj());
        vo.setShtime(new Date());
        repository.updateById(vo);
    }

    /**
     * 供应商采购单受理查询
     * @param dto
     * @return
     */
    public DataGridResultDTO gysCgdList(GysCgdDTO dto) {
        if (dto!= null){
            dto.setBegin((dto.getPage()-1)*dto.getRows());
        }
        List<GysCgdDTO> rows = repository.gysCgdList(dto);
        int total = repository.gysCgdCount(dto);
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        resultDTO.setRows(rows);
        resultDTO.setTotal(total);
        return resultDTO;
    }

    /**
     * 采购单的受理
     * @param cgdmxids
     */
    public void gysCgdAccept(String[] cgdmxids) {

        //更新采购单明细的采购状态
        if (cgdmxids != null){
            for (String cgdmx:cgdmxids
                 ) {
                System.out.println("cgdmx-->"+cgdmx);
                //解析cgdnx
                String mxs[] = cgdmx.split("##");
                if (mxs.length ==2){
                    String cgdmxid = mxs[0];
                    String cgzt = mxs[1];
                    System.out.println("cgzt------->"+cgzt);
                    CgdMxVO vo = cgdMxRepository.selectById(cgdmxid);
                    //修改采购单明细的采购状态1、未确认送货  2、已发货、3、已入库、4无法供货
                    if (!"a".equals(cgzt.trim())){
                        vo.setCgzt(cgzt);
                        cgdMxRepository.updateById(vo);
                    }
                }
            }
        }
    }

    /**
     * 查询指定医院下的所有发货的才采购单明细
     * @param cgdmxDTO
     * @return
     */
    public DataGridResultDTO findRkList(CgdmxDTO cgdmxDTO) {
        //设置查询采购明细为发货的药品信息列表
        cgdmxDTO.setBegin((cgdmxDTO.getPage()-1)*cgdmxDTO.getRows());
        cgdmxDTO.setCgzt("2");
        List<CgdmxDTO> rows = cgdMxRepository.findCgdmxList(cgdmxDTO);

        //查询符合条件的总记录数
        int total = cgdMxRepository.findCgdmxCount(cgdmxDTO);
        //定义返回的数据
        DataGridResultDTO resultDTO = new DataGridResultDTO();
        resultDTO.setRows(rows);
        resultDTO.setTotal(total);
        return resultDTO;
    }

    /**
     * 采购单药品入库信息保存
     * @param ypRkVO
     */
    public void saveYprk(YpRkVO ypRkVO) {
        //入库一条药品信息
        EntityWrapper<YpRkVO> wrapper = new EntityWrapper<>();
        wrapper.eq("yycgdid",ypRkVO.getYycgdid());
        wrapper.eq("ypxxid",ypRkVO.getYpxxid());
        List<YpRkVO> list = ypRkRepository.selectList(wrapper);
        if (list == null||list.size() ==0){
            //新入库
            ypRkVO.setId(UUID.randomUUID().toString());
            ypRkVO.setRkje(ypRkVO.getRkje()*ypRkVO.getRkl());
            System.out.println("ypRkVO----->"+ypRkVO);
            ypRkRepository.insert(ypRkVO);
        }else{
            //更新入库信息
            YpRkVO result = list.get(0);
            System.out.println("result----->"+result);
            result.setRkl(ypRkVO.getRkl());
            result.setYpyxq(ypRkVO.getYpyxq());
            result.setRkje(ypRkVO.getRkl()*ypRkVO.getRkje());
            ypRkRepository.updateById(result);
        }
    }

    /**
     * 药品入库
     * @param ypRkVO
     */
    public void yprkSubmit(YpRkVO ypRkVO,String rks[])throws Exception {
        String msg = "";
        if (rks == null || rks.length == 0){
            //入库一条药品信息
            EntityWrapper<YpRkVO> wrapper = new EntityWrapper<>();
            wrapper.eq("yycgdid",ypRkVO.getYycgdid());
            wrapper.eq("ypxxid",ypRkVO.getYpxxid());
            List<YpRkVO> list = ypRkRepository.selectList(wrapper);
            if (list == null||list.size() ==0){
                //新入库
                ypRkVO.setId(UUID.randomUUID().toString());
                ypRkVO.setRkje(ypRkVO.getRkje()*ypRkVO.getRkl());
                //更新药品的状态为入库
                ypRkVO.setCgzt("3");
                //更新入库时间
                ypRkVO.setRktime(new Date());
                ypRkRepository.insert(ypRkVO);
                //同步更新采购单明细的采购状态
                EntityWrapper<CgdMxVO> wra = new EntityWrapper<>();
                wra.eq("yycgdid",ypRkVO.getYycgdid());
                wra.eq("ypxxid",ypRkVO.getYpxxid());
                List<CgdMxVO> cgdMxVOS  = cgdMxRepository.selectList(wra);
                if (cgdMxVOS != null){
                    CgdMxVO cgdMxVO = cgdMxVOS.get(0);
                    cgdMxVO.setCgzt("3");
                    cgdMxRepository.updateById(cgdMxVO);
                }
            }else{
                //更新入库信息
                YpRkVO result = list.get(0);
                result.setRkl(ypRkVO.getRkl());
                result.setYpyxq(ypRkVO.getYpyxq());
                result.setRkje(ypRkVO.getRkl()*ypRkVO.getRkje());
                //更新药品的状态为入库
                result.setCgzt("3");
                //更新入库时间
                result.setRktime(new Date());
                ypRkRepository.updateById(result);
                //同步更新采购单明细的采购状态
                EntityWrapper<CgdMxVO> wra = new EntityWrapper<>();
                wra.eq("yycgdid",ypRkVO.getYycgdid());
                wra.eq("ypxxid",ypRkVO.getYpxxid());
                List<CgdMxVO> cgdMxVOS  = cgdMxRepository.selectList(wra);
                if (cgdMxVOS != null){
                    CgdMxVO cgdMxVO = cgdMxVOS.get(0);
                    cgdMxVO.setCgzt("3");
                    cgdMxRepository.updateById(cgdMxVO);
                }
            }
        }else{
            //批量提交入库
            //解析rks
            for (String rk:rks
                 ) {
                String ids[] = rk.split("##");
                if (ids.length == 2){
                    EntityWrapper<YpRkVO> wrapper = new EntityWrapper<>();
                    wrapper.eq("yycgdid",ids[0]);
                    wrapper.eq("ypxxid",ids[1]);
                    List<YpRkVO> list = ypRkRepository.selectList(wrapper);
                    if (list != null && list.size() != 0){
                        YpRkVO vo = list.get(0);
                        if (vo.getRkl() != null && vo.getRkl() !=0){
                            //更新入库时间
                            vo.setRktime(new Date());
                            vo.setCgzt("3");
                            ypRkRepository.updateById(vo);
                            //同步更新采购单明细的采购状态
                            EntityWrapper<CgdMxVO> wra = new EntityWrapper<>();
                            wra.eq("yycgdid",ids[0]);
                            wra.eq("ypxxid",ids[1]);
                            List<CgdMxVO> cgdMxVOS  = cgdMxRepository.selectList(wra);
                            if (cgdMxVOS != null){
                                CgdMxVO cgdMxVO = cgdMxVOS.get(0);
                                cgdMxVO.setCgzt("3");
                                cgdMxRepository.updateById(cgdMxVO);
                            }
                        }else{
                            CgdVO cgdVO = repository.selectById(ids[0]);
                            msg += cgdVO.getBm()+"、";
                        }

                    }else{
                        CgdVO cgdVO = repository.selectById(ids[0]);
                        msg += cgdVO.getBm()+"、";
                    }
                }
            }
        }
        //判断是否有不能入库的信息
        if (!"".equals(msg)){
            throw new MyException(msg);
        }
    }
}

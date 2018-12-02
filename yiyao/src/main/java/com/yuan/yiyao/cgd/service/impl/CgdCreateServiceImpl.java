package com.yuan.yiyao.cgd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuan.yiyao.cgd.dto.CgdDTO;
import com.yuan.yiyao.cgd.dto.CgdmxDTO;
import com.yuan.yiyao.cgd.repository.CgdCreateRepository;
import com.yuan.yiyao.cgd.repository.CgdMxRepository;
import com.yuan.yiyao.cgd.repository.CgdRepository;
import com.yuan.yiyao.cgd.service.CgdCreateService;
import com.yuan.yiyao.cgd.vo.CgdMxVO;
import com.yuan.yiyao.cgd.vo.CgdVO;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.sys.dto.DataGridResultDTO;
import com.yuan.yiyao.sys.vo.UserYY;
import com.yuan.yiyao.ypxx.dto.GysYpDTO;
import com.yuan.yiyao.ypxx.dto.YpJdDTO;
import com.yuan.yiyao.ypxx.repository.GysYpMLRepository;
import com.yuan.yiyao.ypxx.repository.JdYpMLRepository;
import com.yuan.yiyao.ypxx.repository.YpxxRepository;
import com.yuan.yiyao.ypxx.vo.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

/**
 * 采购单创建业务层
 */
@Service
@Transactional
public class CgdCreateServiceImpl implements CgdCreateService {

    @Autowired
    private CgdRepository repository;
    @Autowired
    private JdYpMLRepository jdYpMLRepository;
    @Autowired
    private YpxxRepository ypxxRepository;
    @Autowired
    private CgdMxRepository cgdMxRepository;
    @Autowired
    private GysYpMLRepository gysYpMLRepository;


    /**
     * 创建采购单基本信息
     * @param userYY
     * @return
     */
    public CgdDTO createCgdMessage(UserYY userYY) {
        CgdDTO cgdDTO = new CgdDTO();
        //设置采购单id
        cgdDTO.setId(UUID.randomUUID().toString());
        //设置医院的id
        cgdDTO.setUseryyid(userYY.getId());
        //设置采购单编号
        String bm = createBm(userYY.getId());
        System.out.println("bm--------------->"+bm);
        cgdDTO.setBm(bm);
        //生成采购单名称
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  timeMc  = format.format(new Date());
        cgdDTO.setMc(userYY.getMc()+timeMc+bm.substring(5));
        //设置医院名称
        cgdDTO.setYymc(userYY.getMc());

        return cgdDTO;
    }
    /**
     * 生成采购单编号
     */
    public String createBm(String sysid){
         String prefix = sysid.substring(0,8);
         String suffix = UUID.randomUUID().toString();
         return prefix+suffix.substring(0,suffix.indexOf("-"));
    }

    /**
     * 保存采购单基本信息
     * @param cgdDTO
     */
    public void saveCgdXX(CgdVO cgdDTO) {
        if (cgdDTO != null){
            //完善采购单信息
            cgdDTO.setCjtime(new Date());
            cgdDTO.setXgtime(new Date());
            cgdDTO.setZt("1");//采购单状态(存储数据字典：1：未提交、2：审核中、3：审核通过、4：审核不通过)
            repository.insert(cgdDTO);
        }
    }

    /**
     * 根据id查询采购单信息
     * @param id 采购单id
     * @return
     */
    public CgdVO findCgdById(String id) {
        return repository.selectById(id);
    }

    /**
     * 修改 采购单信息
     * @param cgdDTO
     */
    public void updateCgdXX(CgdVO cgdDTO) {
        CgdVO cgdVO = repository.selectById(cgdDTO.getId());
        //修改信息
        cgdVO.setLxr(cgdDTO.getLxr());
        cgdVO.setLxdh(cgdDTO.getLxdh());
        cgdVO.setBz(cgdDTO.getBz());
        cgdVO.setCjr(cgdDTO.getCjr());
        cgdVO.setXgtime(new Date());

        repository.updateById(cgdVO);
    }

    /**
     * 添加采购单明细
     * @param ypxxids
     * @param cgdid
     */
    public void addCgdMx(String[] ypxxids, String cgdid)throws Exception {
        CgdMxVO vo = new CgdMxVO();
        String msg = "";
        String exitMsg = "";
        String oneP = "";
        int count = 0;
        if (ypxxids != null&& cgdid != null){
            //解析得到药品id和供货企业id
            for (String p:ypxxids) {
                System.out.println("ypxxids--------------------->"+p+"----->"+cgdid);
                if (p.indexOf(",")==-1){
                    //添加一条药品明细
                    oneP+=p+",";
                    count ++;
                    if (count == 0)
                    {
                        continue;
                    }
                }
                String str[] ;
                if (oneP != ""){
                   str = oneP.split(",");
                }else{
                    str = p.split(",");
                }
                if (str.length == 2){
                    String ypxxid = str[0];
                    String usergysid = str[1];
                    //查询药品信息
                    Ypxx ypxx = ypxxRepository.selectById(ypxxid);
                    //判断该药品是否重复添加
                    CgdMxVO cgdmxVo = findCgdmxByCgdidAndYpxxidAndUsergysid(cgdid,ypxxid,usergysid);
                    if (cgdmxVo != null){
                        //说明该采购明细已经存在
                        exitMsg += ypxx.getBm()+"、";
                        continue;
                    }
                    //该药品的交易状态为正常才能添加
                    YpJdDTO ypJdDTO = jdYpMLRepository.findYpmlJdByYpxxidAndUsergysid(ypxxid,usergysid);
                    if (ypJdDTO != null){
                        System.out.println("ypjdDto--->"+ypJdDTO.getControl());
                        //得到供货企业的交易价格
                        GysYpDTO gysYpDTO = gysYpMLRepository.findGysYpmlByYpxxidAndUsergysid(ypxxid,usergysid);
                        if (gysYpDTO != null){
                            if ("1".equals(ypJdDTO.getControl())){
                                //完善药品明细信息
                                vo.setId(UUID.randomUUID().toString());
                                vo.setYycgdid(cgdid);
                                vo.setYpxxid(ypxxid);
                                vo.setUsergysid(usergysid);
                                vo.setZbjg(ypxx.getZbjg());
                                vo.setJyjg(gysYpDTO.getMony());
                                vo.setCgl(1);
                                vo.setCgje(gysYpDTO.getMony());
                                vo.setCgzt("1");
                                cgdMxRepository.insert(vo);
                                //添加药品明细
                            }else{
                                //得到不能添加的药品信息
                                msg += ypxx.getBm()+"、";
                            }
                        }else{
                            //得到不能添加的药品信息
                            msg += ypxx.getBm()+"、";
                        }
                    }
                }
            }
        }
        String errormsg = "";
        //返回不能采购大药品信息
        if (!"".equals(msg)){
            errormsg += "流水号为：<font style='color: red;'>"+msg+"</font>的药品暂停交易,不能采购!";
        }
        if (!"".equals(exitMsg)){
            errormsg += "流水号为：<font style='color: red;'>"+exitMsg+"</font>的药品重复添加!";
        }
        if (!"".equals(errormsg)){
            throw new MyException(errormsg);
        }
    }

    /**
     * 根据查询采购单明细信息
     * @param cgdid 采购单id
     * @param ypxxid 药品信息id
     * @param usergysid 供货企业id
     * @return
     */
    public CgdMxVO findCgdmxByCgdidAndYpxxidAndUsergysid(String cgdid, String ypxxid, String usergysid) {
        EntityWrapper<CgdMxVO> wrapper = new EntityWrapper<>();
        wrapper.eq("yycgdid",cgdid);
        wrapper.eq("ypxxid",ypxxid);
        wrapper.eq("usergysid",usergysid);
        List<CgdMxVO> list = cgdMxRepository.selectList(wrapper);
        if(list != null&& list.size()!=0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据采购单id查询该采购单下的所有药品明细
     * @param cgdmxDTO
     * @return
     */
    public DataGridResultDTO findCgdmxList(CgdmxDTO cgdmxDTO) {

        DataGridResultDTO resultDTO = new DataGridResultDTO();
        if (cgdmxDTO != null){
            if (cgdmxDTO.getYycgdid() != null){
                cgdmxDTO.setBegin((cgdmxDTO.getPage()-1)*cgdmxDTO.getRows());
                List<CgdmxDTO> dto = cgdMxRepository.findCgdmxList(cgdmxDTO);
                // 进行药品金额总计findCgdmxZonji
                List<CgdmxDTO> z_dto = cgdMxRepository.findCgdmxZonji(cgdmxDTO);
                if (z_dto != null && z_dto.size() !=0){
                    //进行总计求和
                    dto.add(addZongji(z_dto));
                }
                resultDTO.setRows(dto);
                //得到总记录数
                EntityWrapper<CgdMxVO> wrapper = new EntityWrapper<>();
                wrapper.eq("yycgdid",cgdmxDTO.getYycgdid());
                int total = cgdMxRepository.selectCount(wrapper);
                resultDTO.setTotal(total);
            }
        }
        return resultDTO;
    }
    /**
     * 采购量总计
     */
    public CgdmxDTO addZongji(List<CgdmxDTO> dtos){
        // 进行药品金额总计
        CgdmxDTO zongji = new CgdmxDTO();
        zongji.setBm("总 计");

        int z_cgl = 0;  //总采购量
        Float z_cgje = 0f;
        for (CgdmxDTO dto:dtos
             ) {
            z_cgl += dto.getCgl();
            z_cgje += dto.getCgje();
        }
        zongji.setCgl(z_cgl);
        zongji.setCgje(z_cgje);
        return zongji;
    }

    /**
     * 根据id删除采购药品明细
     */
    public void deleteCgdmx(String[] cgdmxids) {
        if (cgdmxids != null){
            for (String id:cgdmxids
                 ) {
                cgdMxRepository.deleteById(id);
            }
        }
    }
}

package com.yuan.yiyao.ypxx.dto;

import com.yuan.yiyao.ypxx.vo.Ypxx;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class YyCgYpmlDTO extends Ypxx {

    private String ypxxid; //药品信息id
    private String usergysid ; //供货企业id
    private String sysid; //用户的单位id
    private String control; //监控状态
    private String advice ; //监控已将
    private String controlmc;
    private String blmc; //药品类别名称
    private Float mony;//零售价
    private String ypmc; //药品名称
    private String gysmc;//供货企业名称
    private Integer page;
    private Integer rows;
    private Integer begin;

    private Float startZbjg;
    private Float endZbjg;
    private Float startMong;
    private Float endMong;
}

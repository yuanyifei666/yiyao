package com.yuan.yiyao.cgd.dto;

import com.yuan.yiyao.cgd.vo.CgdVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 采购单数据传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CgdDTO extends CgdVO {

    private String yymc ;//医院名
    private String ztmc;//采购单状态名称
    private Integer page;
    private Integer rows;
    private Date startCjtime;
    private Date endCjtime;
    private String groupid ; //用户类型

}

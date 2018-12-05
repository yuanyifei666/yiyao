package com.yuan.yiyao.jsd.dto;

import com.yuan.yiyao.jsd.vo.JsdVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 医院结算单数据传输对象
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsdDTO extends JsdVO {

    private String yymc;
    private String gysmc;
    private Integer page;
    private Integer rows;

    private Date startCjtime;
    private Date endCjtime;

    private String state;//哪个功能模块的查询标志
    private String groupid;
}

package com.yuan.yiyao.jsd.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结算单明细
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("YYJSDMX")
public class JsdMx {

    private String id;
    private String yyjsdid;
    private String ypxxid;
    private String yycgdid;
    private Integer jsl;//结算量
    private Float jsje; //结算金额
    private String jszt;//结算状态


}

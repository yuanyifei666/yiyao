package com.yuan.yiyao.ypxx;

import com.yuan.yiyao.ypxx.vo.Ypxx;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 药品信息的传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class YpxxDTO extends Ypxx {

    private String jyztmc; //交易状态名称
    private String blmc; //药品类别名称
    private Integer page;
    private Integer rows;
    private Integer begin;//开始查询记录
    private Float startZbjg;
    private Float endZbjg;
}

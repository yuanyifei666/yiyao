package com.yuan.yiyao.sys.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数字字典明细实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("DICTINFO")
public class DictInfo {

    private String id;
    private String dictcode;
    private String typecode;
    private String info;
    private String remark;
}

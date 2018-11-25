package com.yuan.yiyao.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区/县
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Areas {

    private Integer areaId;
    private String areaName;
    private Integer cityId;

}

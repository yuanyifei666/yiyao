package com.yuan.yiyao.chars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 页面传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartsResultDTO {

    private List<String> ypmcs;
    private List<Map<String,Object>> total;

}

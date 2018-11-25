package com.yuan.yiyao.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义响应datagrid分页的shuj格式传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataGridResultDTO implements Serializable {

    private Integer page;
    private Integer pageSize;
    private Integer total;
    private List rows = new ArrayList();

}

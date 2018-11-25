package com.yuan.yiyao.operation.dto;

import com.yuan.yiyao.operation.vo.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 权限实体类的传输对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDTO extends Operation {

//    private Integer id;
    private String text;
//    private String state;
    private Map<String ,String> attributes;
    private List<OperationDTO> children;

}

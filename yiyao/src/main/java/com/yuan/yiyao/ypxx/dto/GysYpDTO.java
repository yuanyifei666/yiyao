package com.yuan.yiyao.ypxx.dto;

import com.yuan.yiyao.ypxx.vo.Ypxx;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业供应药品实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GysYpDTO extends YpxxDTO {

    private String gysid ;//企业的id
    private String gysmc ; //企业的名称
    private String ypxxid;
    private String usergysid;


}

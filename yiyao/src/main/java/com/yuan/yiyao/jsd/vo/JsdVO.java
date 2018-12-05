package com.yuan.yiyao.jsd.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 结算单表对应的实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("YYJSD")
public class JsdVO implements Serializable {

    @TableId(type = IdType.UUID)
    private String id;
    private String bm;
    private String mc;
    private String useryyid;
    private String lxr;
    private String lxdh;
    private String cjr;
    private Date cjtime;
    private Date xgtime;
    private Date tjtime;
    private String bz;
    private String zt;
    private String usergysid;
    private String slyj;//受理意见

}

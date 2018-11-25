package com.yuan.yiyao.sys.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 监督单位表对应的实体类
 */
@TableName("USERJD")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@Data
public class UserJD {

    private String id;
    private String mc; //监督单位名称
    private String dz; //联系地址
    private String yzbm ;//邮政编码
    private String xlr ; //联系人
    private String dh ;//联系电话
    private String cz;//传真
    private String dzyx;//电子邮箱
    private Integer proId;
    private Integer cityId;
    private Integer areaId;

}

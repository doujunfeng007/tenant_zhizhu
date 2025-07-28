package com.minigod.zero.bpmn.module.account.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: ClientInfoVo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/16
 * @Version 1.0
 */
@Data
@Builder
public class ClientInfoVo {
    private String clientId;

    /**
     * 资金帐号
     */
    @ExcelProperty(value = "资金帐号")
    @ApiModelProperty("资金帐号")
    private String fundAccount;

    /**
     * 客户中文名
     */
    @ExcelProperty(value = "客户中文名")
    @ApiModelProperty("客户中文名")
    private String clientName;

    /**
     * 英文名
     */
    @ExcelProperty(value = "英文名")
    @ApiModelProperty("英文名")
    private String clientNameSpell;

    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型")
    @ApiModelProperty("证件类型")
    private Integer idKind;

    /**
     * 账户类型[1=现金账户 2=融资账户]
     */
    @ExcelProperty(value = "账户类型[1=现金账户 2=融资账户]")
    @ApiModelProperty("账户类型[1=现金账户 2=融资账户]")
    private Integer fundAccountType;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    @ApiModelProperty("证件号码")
    private String idNo;

    /**
     * 性别[0=男 1=女 2=其它]
     */
    @ExcelProperty(value = "性别[0=男 1=女 2=其它]")
    @ApiModelProperty("性别[0=男 1=女 2=其它]")
    private String sex;

    /**
     * 生日日期
     */
    @ExcelProperty(value = "生日日期")
    @ApiModelProperty("生日日期")
    private String birthday;

    /**
     * 电子邮箱
     */
    @ExcelProperty(value = "电子邮箱")
    @ApiModelProperty("电子邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    @ApiModelProperty("手机号码")
    private String mobile;


    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String familyAddr;

    private Long custId;
}

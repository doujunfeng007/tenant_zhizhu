package com.minigod.zero.bpmn.module.deposit.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @ClassName: BankCardReviewBo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/13
 * @Version 1.0
 */
@Data
public class BankCardReviewBo  {
    /**
     * 交易帐号
     */
    @ApiModelProperty(value="交易帐号")
    @NotBlank(message = "交易帐号不能为空",groups = {SubmitBankCard.class})
    private String clientId;
    @NotNull(message = "用户ID",groups = {SubmitBankCard.class})
    @ApiModelProperty(value="用户ID不能为空")
    private Long userId;

    /**
     * 银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]
     */
    @ApiModelProperty(value="银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]")
    @NotNull(message = "请选择银行卡类型")
    private Integer bankType;

    /**
     * 银行名称
     */
    @ApiModelProperty(value="银行名称")
    @NotBlank(message = "请输入银行名称")
    @Size(max = 256,message = "银行名称最大长度要小于 256")
    private String bankName;

    /**
     * 银行账号
     */
    @ApiModelProperty(value="银行账号")
    @NotBlank(message = "请输入银行账号")
    @Size(max = 32,message = "银行账号最大长度要小于 32")
    private String bankNo;

    /**
     * 银行账户名
     */
    @ApiModelProperty(value="银行账户名")
    @NotBlank(message = "请输入银行账户名")
    @Size(max = 256,message = "银行账户名最大长度要小于 256")
    private String bankAccount;

    /**
     * 银行代码
     */
    @ApiModelProperty(value="银行代码")
    @NotBlank(message = "请输入银行代码")
    @Size(max = 64,message = "银行代码最大长度要小于 64")
    private String bankCode;

    /**
     * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
     */
    @ApiModelProperty(value="银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]")
    @NotNull(message = "请输入银行账户类别")
    private Integer bankAccountCategory;

	@ApiModelProperty(value="电汇代码")
	@NotNull(message = "请输入电汇代码")
	private String swiftCode;

    /**
     * 认证标识[0-未认证 1-待认证 2-已认证]
     */
    @ApiModelProperty(value="认证标识[0-未认证 1-待认证 2-已认证]")
    private Integer authSign;


    /**
     * 审核类型 1新增 2 删除 3 修改
     */
    @ApiModelProperty(value="审核类型 1新增 2 删除 3 修改")
    @NotNull(message = "请选择审核类型")
    private Integer applicationType;

    /**
     * 银行卡 ID
     */
    @ApiModelProperty(value="银行卡 ID")
    private Long bankCardId;


    @ApiModelProperty(value="来源" )
    private String source = "other";


	private String bankId;

	@ApiModelProperty("凭证图片Id集合")
	private List<String> imageIds;

    public interface SubmitBankCard {}

}

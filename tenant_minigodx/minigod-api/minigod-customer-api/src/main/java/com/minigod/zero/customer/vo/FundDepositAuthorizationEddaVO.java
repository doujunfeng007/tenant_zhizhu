package com.minigod.zero.customer.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.FundDepositEddaVO
 * @Description: 入金授权 入参
 * @Author: linggr
 * @CreateDate: 2024/5/9 18:03
 * @Version: 1.0
 */
@Data
public class FundDepositAuthorizationEddaVO {


	@ApiModelProperty(value="bankType银行 1大陆 0香港 2其他")
	private Integer bankType;

	@ApiModelProperty(value="bankName银行名称")
	private String bankName;

	@ApiModelProperty(value="depositAccountName存入账户名称")
	private String depositAccountName;

	@ApiModelProperty(value="depositAccountType存入账户类型:1 港币账户; 2 综合多币种账户")
	private Integer depositAccountType;

	@ApiModelProperty(value="depositAccount存入银行账户")
	private String depositAccount;

	/**
	 * 银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
	 */
	@ApiModelProperty(value="bankIdKind银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照")
	private Integer bankIdKind;

	/**
	 * 银行开户证件号码
	 */
	@ApiModelProperty(value="bankIdNo银行开户证件号码")
	private String bankIdNo;

	@ApiModelProperty(value = "身份证正面图片路径")
	private String imgFrontPath;

	@ApiModelProperty(value = "身份证反面图片路径")
	private String imgReversePath;


}

package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.tencentcloudapi.ecm.v20190719.models.Country;
import lombok.Data;

import javax.validation.constraints.Max;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.UpdateAccountReq
 * @Description: AFE账户资料设置接口全部 字段
 * @Author: wengzejie
 * @CreateDate: 2024年12月18日17:37:08
 * @Version: 1.0
 */
@Data
public class UpdateAccountReq extends ReqVO {

	@JSONField(name ="ACCOUNTID")
	private String accountId;

	@JSONField(name ="COUNTRYID")
	private String countryId;

	@JSONField(name ="ADDRESS1")
	private String address1;

	@JSONField(name ="ADDRESS2")
	private String address2;

	@JSONField(name ="ADDRESS3")
	private String address3;

	@JSONField(name ="ADDRESS4")
	private String address4;

	@JSONField(name ="EMAILADDRESS")
	private String emailAddress;

	@JSONField(name ="PHONENUMBER")
	private String phoneNumber;

	@JSONField(name ="PHONETYPE")
	private String phoneType;

	@JSONField(name ="SMSNUMBER")
	private String smsNumber;

	@JSONField(name ="HOMENUMBER")
	private String homeNumber;

	@JSONField(name ="OFFICENUMBER")
	private String officeNumber;

	@JSONField(name ="MOBILENUMBER")
	private String mobileNumber;

	@JSONField(name ="PROOFFILE")
	private String prooffile;

	@JSONField(name ="PROOFFILENAME")
	private String prooffileName;

}

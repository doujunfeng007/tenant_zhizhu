package com.minigod.zero.platform.dto;

import com.minigod.zero.platform.enums.InformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author minigod
 */
@Data
public class SendSmsDTO {

	private List<String> phones = new ArrayList<>();

	private String phone;

	private String areaCode;

	@ApiModelProperty("短信内容")
	private String message;

	@ApiModelProperty("模板编码")
	private int templateCode;

	@ApiModelProperty("模板参数")
	private List<String> params = new ArrayList<>();

	@ApiModelProperty("定时和即时")
	private Integer sendType = InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode();

	@ApiModelProperty("定时时间")
	private Date timing;

	/**
	 * 验证码有效期
	 */
	private Long expire;

	/**
	 * 发送的是否是验证码
	 */
	private Boolean verificationCode;
	private String captchaKey;
	private String captchaCode;


}

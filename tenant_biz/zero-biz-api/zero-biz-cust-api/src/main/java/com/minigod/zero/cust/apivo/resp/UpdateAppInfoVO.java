package com.minigod.zero.cust.apivo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.resp.UpdateAppInfoVO
 * @Date: 2023年02月16日 19:49
 * @Description:
 */
@Data
public class UpdateAppInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "0为已是最新版本无须升级1为有新版本，无须升级2为有新版本，建议升级3为有新版本，必须升级方可使用")
	private Integer checkCode;


	/**
	 * 0当已为最新版本，无须升级时，返回 客户端已是最新版本，无须升级
	 * 1当有新版本时，但无须升级时，返回 有新版本x.x.xxx,无须升级仍可正常使用
	 * 2当有新版本，建议升级时，返回 有新版本x.x.xxx， 建议升级
	 * 3当有新版本，必须升级时，返回 当前版本太久，必须升级方可继续使用
	 */
	@ApiModelProperty(value = "message")
	private String checkMsg;

	@ApiModelProperty(value = "版本升级更新的内容")
	private String note;

	@ApiModelProperty(value = "最新版本号")
	private String lastVer;

	@ApiModelProperty(value = "下载地址")
	private String url;

	@ApiModelProperty(value = "下软件包的大小")
	private String size;

	@ApiModelProperty(value = "md5")
	private String md5;
}

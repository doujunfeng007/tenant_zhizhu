package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息读取记录表
 *
 * @author 掌上智珠
 */
@Data
@TableName("platform_msg_read_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "短信内容信息表", description = "短信内容信息表")
public class PlatformMsgReadRecordEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "消息分类")
	private Integer msgCode;

	@ApiModelProperty(value = "消息子分类")
	private Integer msgChildCode = 0;

	@ApiModelProperty(value = "最后已读的时间戳或者id")
	private Long readVersion;

	@ApiModelProperty(value = "未读消息数量")
	private Integer unreadNum;

	@ApiModelProperty(value = "乐观锁版本号")
	private Integer lockVersion;

	@ApiModelProperty(value = "客户端类型(0-全部 1-安卓 2-IOS)")
	private Integer clientType;

}

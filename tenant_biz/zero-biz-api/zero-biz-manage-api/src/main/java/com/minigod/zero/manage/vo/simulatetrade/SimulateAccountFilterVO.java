package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模拟比赛参赛帐号过滤（白名单与黑名单）
 */
@Data
public class SimulateAccountFilterVO implements Serializable {
	private static final long serialVersionUID = -2260388129919493489L;
	private Long id;//主键
	private Long competitionId; //模拟比赛ID
	private String competitionName; //模拟比赛名称
	private Long userId;//用户id
	private Integer filterType;//类型，0:无限制，1:白名单，2:黑名单
	private String tradeAccount; //交易号
	private String clientName; //客户姓名
	private String nickName; //客户呢称
	private String clientNameSpell; //客户英文名
	private Date createTime;//创建时间
	private Date updateTime;//更新时间

}

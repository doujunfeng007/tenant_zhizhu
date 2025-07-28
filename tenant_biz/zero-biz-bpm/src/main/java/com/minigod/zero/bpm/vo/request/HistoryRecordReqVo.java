package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 潘辉
 * @version v 1.0
 * @Title:HistoryRecord.java
 * @Description: 这里描述类的用处
 * @Copyright: 2017
 * @Company:
 * @date 2017年4月26日 下午6:22:43
 */
@Data
public class HistoryRecordReqVo implements Serializable {
	private static final long serialVersionUID = 7116702471305876549L;
	private Integer currency;//币种
	private Integer state;//状态
	private Integer type;//1:投入 2:提取
}

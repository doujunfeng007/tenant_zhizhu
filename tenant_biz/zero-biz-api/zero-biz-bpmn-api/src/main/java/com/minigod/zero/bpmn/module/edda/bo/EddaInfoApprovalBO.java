package com.minigod.zero.bpmn.module.edda.bo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: com.minigod.zero.bpmn.module.edda.bo.eddaInfoApprovalBO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/17 16:51
 * @Version: 1.0
 */
@Data
public class EddaInfoApprovalBO {
	/**
	 * 预约号
	 */
	private String applicationId;
	/**
	 * 状态 1 通过  2 拒绝
	 */
	private Integer state;
	/**
	 * 拒绝原因
	 */
	private String rejDescription;


}

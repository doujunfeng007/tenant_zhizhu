package com.minigod.zero.bpmn.module.margincredit.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitApplicationEntity;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @ClassName IncreaseCreditLimit.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */

/**
 * 信用额度申请信息表
 */
@ApiModel(description = "信用额度申请信息表")
@Data
@TableName(value = "increase_credit_limit")
public class IncreaseCreditLimitVO extends IncreaseCreditLimitEntity {

	private static final long serialVersionUID = 1L;


	private List<FileUploadInfoEntity> fileList;

	private IncreaseCreditLimitApplicationEntity applicationEntity;





}

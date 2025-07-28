package com.minigod.zero.bpmn.module.paycategory.vo;

import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryImgEntity;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.vo.PayeeCategoryEntityVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 17:58
 * @Version: 1.0
 */
@Data
public class PayeeCategoryEntityVO extends PayeeCategoryEntity {
	/**
	 * 图片list
	 */
	private List<PayeeCategoryImgEntity> imageList;
}

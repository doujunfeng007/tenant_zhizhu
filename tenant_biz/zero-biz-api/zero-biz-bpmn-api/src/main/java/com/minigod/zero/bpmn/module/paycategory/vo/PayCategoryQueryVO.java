package com.minigod.zero.bpmn.module.paycategory.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIVoucherImageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.vo.PayCategoryQueryVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 11:40
 * @Version: 1.0
 */
@Data
public class PayCategoryQueryVO extends PayeeCategoryEntity{
	/**
	 * 客户英文名
	 */
	@JSONField(name = "eName")
	private String eName;

	/**
	 * 客户中文名
	 */
	@JSONField(name = "cName")
	private String cName;

	@ApiModelProperty(value = "支付文件")
	private List<String> imagesList;

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}
}

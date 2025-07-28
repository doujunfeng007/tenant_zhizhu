package com.minigod.zero.bpm.constant;

import com.minigod.zero.bpm.vo.response.OpenBackErrorVo;

import java.util.ArrayList;
import java.util.List;

public enum OpenBackErrorEnum {
	CODE_1("1","证照信息与填写内容不符"),
	CODE_2("2","资金来源"),
	CODE_3("3","银行帐户有误"),
	CODE_4("4","衍生品金融机构经验"),
	CODE_5("5","年龄"),
	CODE_6("6","衍生品与股票经验不一致"),
	CODE_7("7","就业信息"),
	CODE_8("8","年收入"),
	CODE_9("9","公司名称"),
	CODE_10("10","财产种类")
	;
	public String value;
	public String description;
	OpenBackErrorEnum(String value,String description) {
		this.value = value;
		this.description = description;
	}

	public static List<OpenBackErrorVo> allBackError() {
		List<OpenBackErrorVo> list =new ArrayList<>();
		for(OpenBackErrorEnum errorEnum : OpenBackErrorEnum.values()) {
			OpenBackErrorVo vo = new OpenBackErrorVo();
			vo.setCode(errorEnum.value);
			vo.setDesc(errorEnum.description);
			list.add(vo);
		}
	 	return list;
	}

	public static OpenBackErrorVo getBackError(String code) {
		OpenBackErrorVo vo = null;
		for(OpenBackErrorEnum errorEnum : OpenBackErrorEnum.values()) {
			if(errorEnum.value.equals(code)) {
				vo = new OpenBackErrorVo();
				vo.setCode(errorEnum.value);
				vo.setDesc(errorEnum.description);
			}
		}
		return vo;
	}
}

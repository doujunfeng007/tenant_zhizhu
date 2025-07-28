package com.minigod.zero.platform.engine;

import com.minigod.zero.platform.constants.Constants;

import java.util.Map;

public class VelocityTemplateEngine extends AbstractTemplateEngine{

private String DEFAULT_TEMPLATE = "VelocityExample.vm";

	public String getTemplatePath() {
		return "/template/";
	}

	public String run(Map map, String filePath) throws Exception {
		this.setTemplate(filePath);
		return super.run(map);
	}

	public void setTemplate(String filePath) {
		 this.DEFAULT_TEMPLATE = filePath;
	}
	public String getTemplate() {
		// TODO Auto-generated method stub
		return DEFAULT_TEMPLATE;
	}

	public String getEngineType() {
		// TODO Auto-generated method stub
		return Constants.ENGINE_TYPE_VELOCITY;
	}
}

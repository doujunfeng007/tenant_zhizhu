package com.minigod.zero.platform.engine;

import com.minigod.zero.platform.constants.Constants;

import java.util.Map;

public class FreemarkerTemplateEngine extends AbstractTemplateEngine{
	private String DEFAULT_TEMPLATE = "FreemarkerExample.ftl";

	public String getTemplatePath() {
		return "/template/";
	}

	public String run(Map root, String filePath) throws Exception {
		this.setTemplate(filePath);
		return super.run(root);

	}

	public void setTemplate(String filePath) {
		 this.DEFAULT_TEMPLATE = filePath;
	}

	public String getTemplate() {
		// TODO Auto-generated method stub
		return DEFAULT_TEMPLATE;
	}

	public String getEngineType() {
		return Constants.ENGINE_TYPE_FREEMARKER;
	}
}

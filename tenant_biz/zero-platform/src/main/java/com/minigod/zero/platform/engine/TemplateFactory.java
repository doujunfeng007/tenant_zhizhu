package com.minigod.zero.platform.engine;

import java.util.HashMap;
import java.util.Map;


public class TemplateFactory {
	private static TemplateFactory instance;
	private Map objectMap;

	static{
		instance = new TemplateFactory();
	}

	@SuppressWarnings("unchecked")
	public TemplateFactory() {
		super();
		this.objectMap = new HashMap();
		synchronized (this) {
//			objectMap.put("freemarker", new FreemarkerTemplateEngine(){
//				public String getTemplatePath() {
//					return "src/main/resources/template";
//				}
//			});
			objectMap.put("freemarker", new FreemarkerTemplateEngine());
			objectMap.put("velocity", new VelocityTemplateEngine());
		}
	}

	public static TemplateFactory getInstance(){
		return instance;
	}

	public Object getBean(String beanName){
		return objectMap.get(beanName);
	}

}

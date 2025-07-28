package com.minigod.zero.platform.engine;

import com.minigod.zero.platform.constants.Constants;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

public abstract class AbstractTemplateEngine implements TemplateEngine{

	public abstract String getTemplatePath();

	public abstract String getTemplate();

	public abstract String getEngineType();

	public String run(Map context)throws Exception {
		if (Constants.ENGINE_TYPE_FREEMARKER.equals(getEngineType())) {
			return executeFreemarker(context);
		} else {
			return executeVelocity(context);
		}
	}

	private String executeFreemarker(Map context)throws Exception {
		Configuration cfg = new Configuration();
//	    cfg.setDirectoryForTemplateLoading(new File(getTemplatePath()));
	    cfg.setClassForTemplateLoading(AbstractTemplateEngine.class, getTemplatePath());
	    cfg.setObjectWrapper(new DefaultObjectWrapper());

	    cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));

	    Template temp = cfg.getTemplate(getTemplate(),"utf-8");

//	    Writer out = new OutputStreamWriter(System.out);
	    StringWriter out=new StringWriter();
	    temp.process(context, out);
//	    out.flush();
	    return out.toString();
	}

	private String executeVelocity(Map root)throws Exception {

//		Velocity.init();
//		VelocityContext context = new VelocityContext(root);
//		org.apache.velocity.Template template = null;
//
//	    template = Velocity.getTemplate(getTemplatePath()+getTemplate());
//
//		StringWriter sw = new StringWriter();
//		template.merge( context, sw );
//		System.out.print(sw.toString());
//		return sw.toString();
		return null;

	}

}

package com.minigod.zero.platform.engine;

import java.util.Map;


public interface TemplateEngine {

	String run(Map context, String filePath)throws Exception;

}

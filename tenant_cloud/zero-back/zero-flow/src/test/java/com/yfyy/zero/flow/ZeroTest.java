package com.minigod.zero.flow;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.minigod.zero.core.test.ZeroBootTest;
import com.minigod.zero.core.test.ZeroSpringExtension;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.engine.entity.FlowModel;
import com.minigod.zero.flow.engine.service.FlowEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Zero单元测试
 *
 * @author Chill
 */
@ExtendWith(ZeroSpringExtension.class)
@SpringBootTest(classes = FlowApplication.class)
@ZeroBootTest(appName = "zero-flow",profile="sit", enableLoader = true)
public class ZeroTest {

	@Autowired
	private FlowEngineService service;
	@Autowired
	RuntimeService runtimeService;

	@Test
	public void test() {
		Map<String,Object> map = JSONObject.parseObject("{\n" +
				"    \"initiator\":\"1123598821738675201\",\n" +
				"    \"applicationId\":\"2024020200000003\",\n" +
				"    \"table\":\"customer_account_open_application\",\n" +
				"    \"flow\":\"customer_open_online_flow\"\n" +
				"}");
		ProcessInstance processInstance = runtimeService.startProcessInstanceById("Process_1658399890149:4:315d3d89-c1a6-11ee-a107-de117eb55d63",map);
		System.out.println("123");
	}

	@Test
	public void contextLoads() {
		System.out.println("=====数据迁移启动=====");

		// 获取 ACT_DE_MODEL 表需要转换的数据
		List<FlowModel> list = service.list();
		// 循环转换
		list.forEach(flowModel -> {
			if (StringUtil.isBlank(flowModel.getModelEditorXml())) {
				service.update(Wrappers.<FlowModel>lambdaUpdate()
					.set(FlowModel::getModelEditorXml, new String(service.getModelEditorXML(flowModel)))
					.ge(FlowModel::getId, flowModel.getId())
				);
			}
		});

		System.out.println("=====数据迁移完毕=====");
	}


}

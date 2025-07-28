package com.minigod.zero.bpm.openapi;

import cn.hutool.json.JSONUtil;
import com.minigod.zero.bpm.service.IEsopService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ESOP接口
 *
 * @author 掌上智珠
 * @since 2023-06-06
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/esop_api")
@Api(value = "ESOP接口", tags = "ESOP接口")
@Slf4j
public class EsopController {

	@Resource
	private IEsopService esopService;

	@Value("${esop.login_url:http://esop-member.reotest.com/login}")
	private String esopLoginUrl;
	@Value("${esop.expired_url:https://qa.ekeeper.com/error/activate-zh}")
	private String esopExpiredUrl;

	/**
	 * 点击邮件链接激活ekeeper
	 *
	 * @param params
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ekeeper_active/{params}")
	public void ekeeperActive(@PathVariable("params") String params, HttpServletResponse response) throws IOException {
		try {
			//激活动作改成ekeeper首次登录，这边只判断下激活链接的有效期，有效则重定向到ekeeper登录页
			R<String> rt = esopService.ekeeperActive(params);
			log.info("EsopService ResponseVO:{}", JSONUtil.toJsonStr(rt));

			if (rt.isSuccess()) {
				//激活成功，直接跳转登录页
				response.sendRedirect(esopLoginUrl);
			} else {
				//激活链接过期失效
				response.sendRedirect(esopExpiredUrl);
			}

		} catch (Exception e) {
			log.error("ekeeper_active error:{}", e.getMessage(), e);
			response.sendRedirect(esopExpiredUrl);
		}
	}

}

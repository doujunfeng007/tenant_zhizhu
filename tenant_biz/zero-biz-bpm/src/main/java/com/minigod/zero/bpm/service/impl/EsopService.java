package com.minigod.zero.bpm.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.minigod.zero.bpm.service.IEsopService;
import com.minigod.zero.biz.common.utils.AESUtil;
import com.minigod.zero.biz.common.utils.SecurityKey;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.Date;

@Service
@Slf4j
public class EsopService implements IEsopService {

	@Override
	public R<String> ekeeperActive(String params) {
		if (StringUtils.isBlank(params)) {
			log.info("ekeeperActive params isBlank");
			return R.fail();
		}
		params = params.replace("=", "%");
		params = URLDecoder.decode(params);
		params = AESUtil.decrypt(params, SecurityKey.AES_IV);
		if (!JSONUtil.isTypeJSON(params)) {
			log.info("ekeeperActive 参数解析错误");
			return R.fail();
		}
		log.info("ekeeperActive params => {}", params);
		JSONObject jsonObject = JSONUtil.parseObj(params);
		String actEmailTime = jsonObject.getStr("actEmailTime");
		Date actEmailDate = DateUtil.parse(actEmailTime, "yyyy-MM-dd HH:mm:ss");

		Date expiredDate = DateUtil.offsetDay(actEmailDate, 7);
		//链接过期失效
		if (new Date().compareTo(expiredDate) > 0) {
			return R.fail();
		} else {
			return R.success();
		}
	}

}

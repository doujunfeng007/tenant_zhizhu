package com.minigod.zero.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.core.tool.utils.ReflectUtils;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author:yanghu.luo
 * @create: 2023-04-24 10:20
 * @Description: 恒生公共处理
 */
@Slf4j
abstract class CommonService {
	/**
	 * 恒生请求参数处理
	 */
	@SneakyThrows
	public GrmRequestVO getHsRequest(String request,String funId) {
		Map<String,Object> params = (Map)JSON.parse(request);
		String lang = (String) params.get(Constants.Fields.LANG);
		GrmFunction grmFun = GrmFunctionHolder.getFunction(funId);
		if (null == grmFun) {
			log.error("获取不到功能号对应接口信息，功能号：" ,funId);
			return null;
		}
		Map oParam = (Map) params.get("params");

		Map<String, Class> mfield = grmFun.getReqFields();
		Set<String> sfield = mfield.keySet();
		GrmRequestVO reqVo = grmFun.getRequestVo().newInstance();
		String fname;
		Object fvalue;
		for (Iterator<String> it = sfield.iterator(); it.hasNext(); ) {
			fname = it.next();
			Method method = grmFun.getReqSetMethodByField(fname);
			fvalue = oParam.get(fname);
			if (null != fvalue && !String.valueOf(fvalue).isEmpty()) {
				Object oValue = ReflectUtils.getValue(fvalue, mfield.get(fname));
				if (null == oValue) {
					log.error("获取不到功能号对应接口信息，功能号1：" ,funId);
					return null;
				}
				method.invoke(reqVo, oValue);
			} else if (!HsConstants.NULLABLEPARAMS.contains(fname)
				&& !GrmFunctions.BROKER_GET_CACHE_SUM_INFO.equals(funId)
				&& !GrmFunctions.BROKER_FUND_TOTAL.equals(funId)
				&& !notCheckFunidValue(funId, fname)) {
				//校验必填参数，BROKER_GET_CACHE_SUM_INFO为兼容1.0版本接口，排除在外
				String errorMsg = ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.EMPTY_PARAMS, lang);
				log.error("ef.params.error.fname=" + fname);// 必要参数为空
				log.error("ef.params.error=" + errorMsg);// 必要参数为空
				log.error("获取不到功能号对应接口信息，功能号2：" ,funId);
				return null;
			}
		}
		return reqVo;
	}

	/**
	 * 跳过指定方法指定参数的校验
	 */
	private boolean notCheckFunidValue(String funId, String fname) {
		Set<String> fnameSet = HsConstants.getNotCheckFunidValue(funId);
		if (fnameSet != null) {
			return fnameSet.contains(fname);
		}
		return false;
	}
}

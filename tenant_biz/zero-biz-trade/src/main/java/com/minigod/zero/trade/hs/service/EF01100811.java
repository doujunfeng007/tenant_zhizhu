package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100811Request;
import com.minigod.zero.trade.hs.resp.EF01100811VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 811 服务_周边_密码有效期校验
 *
 * @author sunline
 *
 */
@Component
@GrmFunctionEntity(requestVo = EF01100811Request.class, responseVo = EF01100811VO.class)
public class EF01100811<T extends GrmRequestVO> extends T2sdkBiz<T> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
//    @Resource
//    protected UserOldPwdsDao userOldPwdsDao;

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if (request instanceof EF01100811Request) {
			EF01100811Request req = (EF01100811Request) request;
			Map<String, String> reqMap = new HashMap<>();
			reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
			reqMap.put(HsConstants.Fields.PASSWORD_TYPE, req.getPasswordType());
			reqMap.put(HsConstants.Fields.PASSWORD, req.getPassword());
			// 是否存量用户密码表能校验通过 户迁移临时过渡
//    		if (null != req.getUserId() && StringUtils.isNotBlank(req.getClientId()) && StringUtils.isNotBlank(req.getPassword())
//					&& userOldPwdsDao.checkOldTradePwd(req.getUserId(),req.getClientId(), req.getPassword())) {
//    			logger.info("存量用户校验交易密码通过，交易与资金账户: {}, {}", req.getClientId(), req.getFundAccount());
//    			logger.info("存量用户校验交易密码通过,将访问恒生的密码设置为默认值，ClientId: {},userid:{}", req.getClientId(),req.getUserId());
//    			req.setPassword("12345678");
//    			reqMap.put(HsConstants.Fields.PASSWORD, "12345678");
//    		}
			oParamMap.putAll(reqMap);
		}
		return oParamMap;
	}

	@Override
	protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam,
																   R request) {
		GrmResponseVO responseVO = GrmResponseVO.getInstance();

		if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			List<Map<String, String>> list = grmDataVO.getResult();
			Iterator<Map<String, String>> iterator = list.iterator();
			if (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				EF01100811VO vo = new EF01100811VO();
				vo.setErrorId(rowMap.get(HsConstants.Fields.ERROR_ID));
				vo.setErrorInfo(rowMap.get(HsConstants.Fields.ERROR_INFO));
				vo.setWarningDays(rowMap.get(HsConstants.Fields.WARNING_DAYS));
				vo.setExpiryDays(rowMap.get(HsConstants.Fields.EXPIRY_DAYS));
				vo.setExpiryDate(rowMap.get(HsConstants.Fields.EXPIRY_DATE));
				vo.setWarningFlag(rowMap.get(HsConstants.Fields.WARNING_FLAG));
				responseVO.addResultData(vo);
			}
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}

}

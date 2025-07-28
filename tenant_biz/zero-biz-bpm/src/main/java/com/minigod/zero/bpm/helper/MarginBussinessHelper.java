package com.minigod.zero.bpm.helper;

import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * 孖展业务校验工具类
 */
@Slf4j
public class MarginBussinessHelper {

    /**
     * 数据完整性校验
     *
     * @param dto 客户信用额度申请入参协议
     * @return Boolean
     */
    public static Boolean validateMarginCreditAdjustInfo(MarginCreditAdjustApplyDTO dto) {

        if (StringUtils.isBlank(dto.getTradeAccount())) {
            log.error("【数据完整性校验】：交易账号为空");
            return Boolean.FALSE;
        }

        if (StringUtils.isBlank(dto.getCapitalAccount())) {
			log.error("【数据完整性校验】：资金账号为空");
            return Boolean.FALSE;
        }

        if (null == dto.getLineCreditInfo() || dto.getLineCreditInfo().size() == 0) {
			log.error("【数据完整性校验】：申请额度信息为空");
			return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}

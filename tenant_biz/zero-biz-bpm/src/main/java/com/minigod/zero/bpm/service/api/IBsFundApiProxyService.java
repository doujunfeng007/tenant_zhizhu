package com.minigod.zero.bpm.service.api;

import com.minigod.zero.bpm.vo.request.BsCashDepositReqVo;
import com.minigod.zero.bpm.vo.request.BsStkTrdCaleReqVo;
import com.minigod.zero.bpm.vo.request.BsUserCheckReqVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;

public interface IBsFundApiProxyService {

    /**
     * 银证中心校验券商用户
     *
     * @param vo
     * @return
     */
    ResponseVO checkSecUserInfo(BsUserCheckReqVo vo);

    /**
     * 银证中心获取用户信息
     *
     * @param vo
     * @return
     */
    ResponseVO getBsSecUserInfo(BsUserCheckReqVo vo);

    /**
     * 银证转账-银证中心请求资金存入
     *
     * @param vo
     * @return
     */
    ResponseVO bsCashDeposit(BsCashDepositReqVo vo);

    /**
     * 银证中心获取交易日历
     *
     * @param vo
     * @return
     */
    ResponseVO getStkTrdCale(BsStkTrdCaleReqVo vo);
}

package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.vo.request.CashImageReqVo;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;

/**
 * @author Zhe.Xiao
 */
public interface ICashCommonService {

	/**
	 * 获取用户银行卡信息
	 * @param custId
	 * @return
	 */
	R getBankInfo(Long custId);

	/**
	 * 获取用户出入金权限
	 * @param custId
	 * @return
	 */
	R validataPurview(Long custId);

	/**
	 * 获取用户银证绑定状态
	 * @param custId
	 * @return
	 */
    R bsBindStatus(Long custId);

	/**
	 * 存取资金图片上传
	 * @param vo
	 * @return
	 */
	R<Kv> saveImg(CashImageReqVo vo);

	/**
	 * 获取图片资源
	 * @param custId
	 * @param imgIds
	 * @return
	 */
    R findAccImg(Long custId, String imgIds);
}

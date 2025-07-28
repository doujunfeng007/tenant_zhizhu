package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.IpoLoanInfoEntity;
import com.minigod.zero.trade.vo.IpoLoanInfoVO;

import java.util.Date;
import java.util.List;

/**
 * ipo垫资记录 服务类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IIpoLoanInfoService extends IService<IpoLoanInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoLoanInfo
	 * @return
	 */
	IPage<IpoLoanInfoVO> selectIpoLoanInfoPage(IPage<IpoLoanInfoVO> page, IpoLoanInfoVO ipoLoanInfo);

	/**
	 * 查找垫资未回滚记录
	 */
	List<IpoLoanInfoEntity> findWaitBackIpoLoanInfoList();

	/**
	 * 根据业务时间查找待回滚垫资记录
	 */
	IpoLoanInfoEntity findBackIpoLoanInfo(Date bizTime);

	/**
	 * 保存ipo垫资记录
	 */
	void saveIpoLoanInfo(IpoLoanInfoEntity loanInfo);

	/**
	 * 更新ipo垫资记录
	 */
	void updateIpoLoanInfo(IpoLoanInfoEntity loanInfo);

	/**
	 * 新股提交融资认购排队申请
	 * @param ipoVO
	 * @return
	 */

	R<Object> applySubQueue(IpoVO ipoVO);


	/**
	 * ipo今日待回滚垫资记录
	 * @return
	 */
	List<IpoLoanInfoEntity> findTodayWaitBackIpoLoanInfo();

	/**
	 * 撤销预约申购
	 * @param ipoVO
	 * @return
	 */
	R<Object> applyCancelQueue(IpoVO ipoVO);

	/**
	 * 融资排队申请
	 */
	void doQueueApply();

	/**
	 * 融资排队取消
	 */
	void doQueueCancel();
}

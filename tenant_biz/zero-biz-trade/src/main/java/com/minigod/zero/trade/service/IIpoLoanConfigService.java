package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoLoanConfigEntity;
import com.minigod.zero.trade.vo.IpoLoanConfigVO;

/**
 * ipo垫资配置 服务类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IIpoLoanConfigService extends IService<IpoLoanConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoLoanConfig
	 * @return
	 */
	IPage<IpoLoanConfigVO> selectIpoLoanConfigPage(IPage<IpoLoanConfigVO> page, IpoLoanConfigVO ipoLoanConfig);

	/**
	 * ipo垫资配置
	 */
	IpoLoanConfigEntity findIpoLoanConfig();

	/**
	 * 更新、获取ipo垫资配置
	 * @return
	 */
	IpoLoanConfigEntity updateAndGetIpoLoanConfig();

	/**
	 *  更新垫资剩余额度
	 * @param loanConfig
	 */
	void updateIpoLoanConfig(IpoLoanConfigEntity loanConfig);

	public double getIpoLoanAmount();
}

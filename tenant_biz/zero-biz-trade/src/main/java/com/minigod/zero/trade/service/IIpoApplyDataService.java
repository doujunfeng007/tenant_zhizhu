package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoApplyDataEntity;
import com.minigod.zero.trade.vo.IpoApplyDataVO;

import java.util.Date;
import java.util.List;

/**
 * IPO认购记录 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-07
 */
public interface IIpoApplyDataService extends IService<IpoApplyDataEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoApplyData
	 * @return
	 */
	IPage<IpoApplyDataVO> selectIpoApplyDataPage(IPage<IpoApplyDataVO> page, IpoApplyDataVO ipoApplyData);


	List<IpoApplyDataEntity> findIpoApplyList(String tradeAccount, Date searchBeginDate, Date searchEndDate, String[] statusArray);

	Integer saveIpoApplyData(IpoApplyDataEntity applyData);

	Integer updateCancelIpoApplyData(String clientId, String assetId);

	List<IpoApplyDataEntity> findIpoApplyList(IpoApplyDataEntity search);
}

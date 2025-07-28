package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.vo.BpmSecuritiesInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 证券客户资料表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IBpmSecuritiesInfoService extends IService<BpmSecuritiesInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmSecuritiesInfo
	 * @return
	 */
	IPage<BpmSecuritiesInfoVO> selectBpmSecuritiesInfoPage(IPage<BpmSecuritiesInfoVO> page, BpmSecuritiesInfoVO bpmSecuritiesInfo);

	/**
	 * 查询证券客户资料
	 *
	 * @param bpmSecuritiesInfo
	 * @return
	 */
	BpmSecuritiesInfoEntity selectBpmSecuritiesInfo(BpmSecuritiesInfoVO bpmSecuritiesInfo);

	/**
	 * 查询证券用户列表
	 *
	 * @param bpmSecuritiesInfo
	 * @return
	 */
	List<BpmSecuritiesInfoEntity> selectBpmSecuritiesInfoList(BpmSecuritiesInfoVO bpmSecuritiesInfo);

	/**
	 * 查询证券客户资料
	 *
	 * @param custIds
	 * @return
	 */
	List<BpmSecuritiesInfoEntity> selectBpmSecuritiesInfoByCustIds(List<Long> custIds);

	/**
	 * 查询证券客户资料
	 *
	 * @param custName
	 * @return
	 */
	List<BpmSecuritiesInfoEntity> selectBpmSecuritiesInfoByCustName(String custName);

	/**
	 * 查询证券客户资料
	 *
	 * @param custId
	 * @return
	 */
	BpmSecuritiesInfoEntity securitiesInfoByCustId(Long custId);

	/**
	 * 查询证券客户资料
	 *
	 * @param custName
	 * @return
	 */
	List<BpmSecuritiesInfoEntity> securitiesInfoLikeCustName(String custName);

	/**
	 * 根据手机号查询开户信息
	 *
	 * @param area  区号
	 * @param phone 手机号
	 * @return
	 */
	BpmSecuritiesInfoEntity securitiesInfoByPhone(String area, String phone);

	/**
	 * 更新客户级别
	 * 客户级别, 包括:1-Common 2-Important 3-VIP 4-PI 5-Capital
	 *
	 * @param clientLevel
	 * @param custId
	 */
	int updateClientLevelByCustId(Integer clientLevel, Long custId);
}

package com.minigod.zero.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.vo.BpmSecuritiesInfoVO;
import com.minigod.zero.bpm.vo.CacheAcctInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 证券客户资料表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface BpmSecuritiesInfoMapper extends BaseMapper<BpmSecuritiesInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmSecuritiesInfo
	 * @return
	 */
	List<BpmSecuritiesInfoVO> selectBpmSecuritiesInfoPage(IPage page, BpmSecuritiesInfoVO bpmSecuritiesInfo);

	/**
	 * 查询证券客户资料
	 *
	 * @param bpmSecuritiesInfo
	 * @return
	 */
    List<BpmSecuritiesInfoEntity> selectBpmSecuritiesInfo(BpmSecuritiesInfoVO bpmSecuritiesInfo);

	/**
	 * 查询客户账号信息
	 *
	 * @param custId
	 * @return
	 */
	CacheAcctInfoVO selectCustAcctInfo(Long custId);

	/**
	 * 根据交易账号查询
	 *
	 * @param tradeAccount
	 * @param custId
	 * @return
	 */
	CacheAcctInfoVO findByTradeAccountOrCustId(@Param("tradeAccount") String tradeAccount, @Param("custId") Long custId);

}

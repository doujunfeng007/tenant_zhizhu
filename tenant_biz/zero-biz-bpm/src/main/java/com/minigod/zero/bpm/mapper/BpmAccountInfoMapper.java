package com.minigod.zero.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.dto.BpmSecuritiesRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.vo.BpmAccountInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易账户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface BpmAccountInfoMapper extends BaseMapper<BpmAccountInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctInfo
	 * @return
	 */
	List<BpmAccountInfoVO> selectBpmAccountInfoPage(IPage page, BpmAccountInfoVO acctInfo);

	// 查询当前选中的账号（未销户）
	BpmTradeAcctRespDto selectCurrentAcctInfo(Long custId);

	/**
	 * 根据交易账号和基金账号查询账号信息
	 * @param tradeAccount
	 * @param fundAccount
	 * @return
	 */
	BpmTradeAcctRespDto selectAcctInfo(@Param("tradeAccount") String tradeAccount,@Param("fundAccount") String fundAccount);

	/**
	 * 获取公司户授权人信息 (未销户)
	 * @param custId 用户号
	 * @param tradeAccount 交易账号
	 * @return BpmSecuritiesRespDto
	 */
	BpmSecuritiesRespDto getCompanyUserRespDto(@Param("custId") Long custId, @Param("tradeAccount") String tradeAccount);
}

package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.BpmFundAcctInfoEntity;
import com.minigod.zero.bpm.vo.BpmFundAcctInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 基金账户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface BpmFundAcctInfoMapper extends BaseMapper<BpmFundAcctInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmFundAcctInfo
	 * @return
	 */
	List<BpmFundAcctInfoVO> selectBpmFundAcctInfoPage(IPage page, BpmFundAcctInfoVO bpmFundAcctInfo);


}

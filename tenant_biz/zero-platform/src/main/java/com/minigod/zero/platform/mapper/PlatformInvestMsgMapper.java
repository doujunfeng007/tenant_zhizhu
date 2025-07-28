package com.minigod.zero.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;

import java.util.List;

/**
 * 推送消息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-25
 */
public interface PlatformInvestMsgMapper extends BaseMapper<PlatformInvestMsgEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param investMsg
//	 * @return
//	 */
//	List<InvestMsgVO> selectInvestMsgPage(IPage page, InvestMsgVO investMsg);

	void saveInvestMsgs(List<PlatformInvestMsgEntity> investMsgList);


}

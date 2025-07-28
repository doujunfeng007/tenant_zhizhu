package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnOpenGiftRecordEntity;
import com.minigod.zero.manage.vo.request.OpenChannelSearchReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 开放渠道礼包记录表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 15:30:08
 */
@Mapper
public interface SnOpenGiftRecordMapper extends BaseMapper<SnOpenGiftRecordEntity> {

	/**
	 * 分页查询礼包记录
	 *
	 * @param page 分页参数
	 * @param userId 用户ID
	 * @param oprId 操作人员ID
	 * @return 分页数据
	 */
	IPage<SnOpenGiftRecordEntity> queryPage(IPage<SnOpenGiftRecordEntity> page,
											@Param("userId") Integer userId,
											@Param("oprId") Integer oprId);

	/**
	 * 获取开放渠道礼包入口记录
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	IPage<SnOpenGiftRecordEntity> queryOpenChannelGiftList(IPage page, @Param("searchReqVO") OpenChannelSearchReqVO searchReqVO);

	/**
	 * 查询用户的礼包记录
	 *
	 * @param userId 用户ID
	 * @return 礼包记录列表
	 */
	List<SnOpenGiftRecordEntity> queryByUserId(@Param("userId") Integer userId);

	/**
	 * 查询操作人员的发放记录
	 *
	 * @param oprId 操作人员ID
	 * @return 礼包记录列表
	 */
	List<SnOpenGiftRecordEntity> queryByOprId(@Param("oprId") Integer oprId);
}

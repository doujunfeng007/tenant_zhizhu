package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;
import com.minigod.zero.manage.vo.ChannelGiftVO;
import com.minigod.zero.manage.vo.request.GiftSearchReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 渠道礼包表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 14:52:08
 */
@Mapper
public interface SnChannelGiftMapper extends BaseMapper<SnChannelGiftEntity> {

	/**
	 * 分页查询渠道礼包
	 *
	 * @param page      分页参数
	 * @param channelId 渠道ID
	 * @param giftName  礼包名称
	 * @return 分页数据
	 */
	IPage<SnChannelGiftEntity> queryPage(IPage<SnChannelGiftEntity> page,
										 @Param("channelId") String channelId,
										 @Param("giftName") String giftName);

	/**
	 * 查询渠道礼包列表
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	IPage<ChannelGiftVO> queryPageChannelGift(IPage<ChannelGiftVO> page, @Param("searchReqVO") GiftSearchReqVO searchReqVO);

	/**
	 * 根据渠道ID查询礼包
	 *
	 * @param channelId 渠道ID
	 * @return 礼包信息
	 */
	SnChannelGiftEntity getByChannelId(@Param("channelId") String channelId);

	/**
	 * 更新礼包库存
	 *
	 * @param id    礼包ID
	 * @param count 更新数量(正数增加，负数减少)
	 * @return 影响行数
	 */
	int updateRemainCount(@Param("id") Long id, @Param("count") Integer count);

	/**
	 * 更新礼包状态
	 *
	 * @param id     礼包ID
	 * @param status 状态
	 * @return 影响行数
	 */
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}

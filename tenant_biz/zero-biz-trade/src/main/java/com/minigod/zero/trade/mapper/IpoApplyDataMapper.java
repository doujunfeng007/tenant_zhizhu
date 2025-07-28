package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoApplyDataEntity;
import com.minigod.zero.trade.vo.IpoApplyDataVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * IPO认购记录 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-07
 */
public interface IpoApplyDataMapper extends BaseMapper<IpoApplyDataEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoApplyData
	 * @return
	 */
	List<IpoApplyDataVO> selectIpoApplyDataPage(IPage page, IpoApplyDataVO ipoApplyData);

	/**
	 * 更新ipo认购记录-撤销状态
	 * @param clientId
	 * @param assetId
	 * @return
	 */
    Integer updateCancelIpoApplyData(@Param("clientId") String clientId, @Param("assetId") String assetId);
}

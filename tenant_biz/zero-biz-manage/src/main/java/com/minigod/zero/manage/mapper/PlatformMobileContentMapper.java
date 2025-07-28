package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.vo.SmsMessageRecord;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import com.minigod.zero.platform.vo.PlatformMobileContentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 短信内容信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface PlatformMobileContentMapper extends BaseMapper<PlatformMobileContentEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param platformMobileContent
	 * @return
	 */
	IPage<PlatformMobileContentEntity> selectPlatformMobileContentPage(@Param("page") IPage page, @Param("params") PlatformMobileContentVO platformMobileContent);

	List<PlatformMobileContentEntity> pageList(@Param("page") IPage<SmsMessageRecord> page,
											   @Param("startTime")String startTime,
											   @Param("endTime")String endTime,
											   @Param("phone")String phone,
											   @Param("tenantId")String tenantId, @Param("sendStatus") String sendStatus);
}

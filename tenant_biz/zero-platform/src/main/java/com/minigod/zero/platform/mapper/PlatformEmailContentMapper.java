package com.minigod.zero.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;

/**
 * 邮件内容信息表 Mapper 接口
 *
 * @author minigod
 * @since 2023-02-24
 */
public interface PlatformEmailContentMapper extends BaseMapper<PlatformEmailContentEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param informEmailContent
//	 * @return
//	 */
//	List<InformEmailContentVO> selectInformEmailContentPage(IPage page, InformEmailContentVO informEmailContent);

	void updateByMsgId(PlatformEmailContentEntity mobileContent);
}

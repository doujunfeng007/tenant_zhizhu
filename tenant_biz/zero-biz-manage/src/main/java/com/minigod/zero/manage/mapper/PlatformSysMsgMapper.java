package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;

import java.util.List;

/**
 * 系统通知信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
public interface PlatformSysMsgMapper extends BaseMapper<PlatformSysMsgEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param platformSysMsg
//	 * @return
//	 */
//	List<PlatformSysMsgVO> selectPlatformSysMsgPage(IPage page, PlatformSysMsgVO platformSysMsg);

	void saveSysMsgBatch(List<PlatformSysMsgEntity> sysMsgs);

}

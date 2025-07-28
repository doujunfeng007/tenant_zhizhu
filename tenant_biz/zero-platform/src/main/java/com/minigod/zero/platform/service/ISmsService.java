/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.zero.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.platform.entity.PlatformSms;
import com.minigod.zero.platform.vo.SmsVO;

/**
 * 短信配置表 服务类
 *
 * @author zsdp
 */
public interface ISmsService extends BaseService<PlatformSms> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param sms
	 * @return
	 */
	IPage<SmsVO> selectSmsPage(IPage<SmsVO> page, SmsVO sms);

	/**
	 * 提交oss信息
	 *
	 * @param oss
	 * @return
	 */
	boolean submit(PlatformSms oss);

	/**
	 * 启动配置
	 *
	 * @param id
	 * @return
	 */
	boolean enable(Long id);

}

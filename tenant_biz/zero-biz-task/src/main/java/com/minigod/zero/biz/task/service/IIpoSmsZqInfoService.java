package com.minigod.zero.biz.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoSmsZqInfoEntity;

/**
 * IPO已中签短信通知 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-02
 */
public interface IIpoSmsZqInfoService extends IService<IpoSmsZqInfoEntity> {


	/**
	 * 通过bean对象条件查询实体
	 *
	 * @param entity
	 * @return
	 */
	IpoSmsZqInfoEntity queryObjectByBean(IpoSmsZqInfoEntity entity);
}

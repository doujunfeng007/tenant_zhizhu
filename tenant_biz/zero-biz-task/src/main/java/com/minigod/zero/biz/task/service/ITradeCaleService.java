package com.minigod.zero.biz.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.mkt.entity.TradeCaleEntity;

/**
 * 交易日历 服务类
 *
 * @author 掌上智珠
 * @since 2022-11-17
 */
public interface ITradeCaleService extends IService<TradeCaleEntity> {


	void loadTrdCaleToRedis();
}

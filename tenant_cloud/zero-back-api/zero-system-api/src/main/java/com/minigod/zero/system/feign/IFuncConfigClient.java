/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.system.feign;

import com.minigod.zero.core.mp.support.ZeroPage;
import com.minigod.zero.system.entity.FuncConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 功能配置模块 Feign接口类
 *
 * @author ZSDP
 * @since 2024-07-23
 */
@FeignClient(
    value = "minigod-funcConfig"
)
public interface IFuncConfigClient {

    String API_PREFIX = "/client";
    String TOP = API_PREFIX + "/top";

    /**
     * 获取功能配置模块列表
     *
     * @param current   页号
     * @param size      页数
     * @return minigodPage
     */
    @GetMapping(TOP)
	ZeroPage<FuncConfig> top(@RequestParam("current") Integer current, @RequestParam("size") Integer size);

}

/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.system.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.mp.support.ZeroPage;
import com.minigod.zero.system.entity.FuncConfig;
import com.minigod.zero.system.service.IFuncConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 功能配置模块 Feign实现类
 *
 * @author ZSDP
 * @since 2024-07-23
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class FuncConfigClient implements IFuncConfigClient {

    private final IFuncConfigService funcConfigService;

    @Override
    @GetMapping(TOP)
    public ZeroPage<FuncConfig> top(Integer current, Integer size) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        IPage<FuncConfig> page = funcConfigService.page(Condition.getPage(query));
        return ZeroPage.of(page);
    }

}

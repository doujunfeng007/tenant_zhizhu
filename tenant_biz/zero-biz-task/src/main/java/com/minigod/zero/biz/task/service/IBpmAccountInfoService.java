package com.minigod.zero.biz.task.service;

import com.minigod.zero.biz.task.vo.BpmAccountInfoVo;

public interface IBpmAccountInfoService {
    BpmAccountInfoVo findOneByClientId(String clientId);
}

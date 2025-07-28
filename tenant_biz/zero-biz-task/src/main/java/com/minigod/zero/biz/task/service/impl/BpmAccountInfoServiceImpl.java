package com.minigod.zero.biz.task.service.impl;

import com.minigod.zero.biz.task.mapper.BpmAccountInfoMapper;
import com.minigod.zero.biz.task.service.IBpmAccountInfoService;
import com.minigod.zero.biz.task.vo.BpmAccountInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class BpmAccountInfoServiceImpl implements IBpmAccountInfoService {

    @Resource
    private BpmAccountInfoMapper bpmAccountInfoMapper;

    @Override
    public BpmAccountInfoVo findOneByClientId(String clientId) {
        return bpmAccountInfoMapper.findOneByClientId(clientId);
    }
}

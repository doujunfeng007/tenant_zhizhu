package com.minigod.zero.bpm.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.dto.PhillipProtocolSaveDTO;
import com.minigod.zero.bpm.entity.PhillipProtocolLog;
import com.minigod.zero.bpm.mapper.PhillipProtocolLogMapper;
import com.minigod.zero.bpm.service.PhillipProtocolLogService;
import com.minigod.zero.bpm.vo.PhillipProtocolVo;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 暗盘协议记录表 服务实现类
 * </p>
 *
 * @author zxw
 * @since 2023-10-07
 */
@Slf4j
@Service
public class PhillipProtocolLogServiceImpl extends ServiceImpl<PhillipProtocolLogMapper, PhillipProtocolLog> implements PhillipProtocolLogService {

    @Resource
    private ZeroRedis zeroRedis;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePhProtocol(PhillipProtocolSaveDTO saveDTO) {
        String key = "ph:protocol:"+ AuthUtil.getCustId();
        PhillipProtocolLog entity = this.lambdaQuery()
                .eq(PhillipProtocolLog::getCustId, AuthUtil.getCustId())
                .eq(PhillipProtocolLog::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .one();
        if (entity == null) {
            entity = new PhillipProtocolLog();
            entity.setId(IdUtil.getSnowflakeNextId());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setCustId(AuthUtil.getCustId());
        entity.setIsAgree(saveDTO.getIsAgree());
        entity.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(entity);
        zeroRedis.del(key);
    }

    @Override
    public PhillipProtocolVo findPhProtocolLog() {
        String key = "ph:protocol:"+ AuthUtil.getCustId();
        PhillipProtocolVo res = zeroRedis.get(key);
        if(res != null){
            return res;
        }
        res = new PhillipProtocolVo();
        res.setCustId(AuthUtil.getCustId());
        PhillipProtocolLog entity = this.lambdaQuery()
                .eq(PhillipProtocolLog::getCustId, AuthUtil.getCustId())
                .eq(PhillipProtocolLog::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .one();
        res.setIsAgree(entity == null ? "0" : entity.getIsAgree());
        zeroRedis.setEx(key, res, 60 * 60 * 24L);
        return res;
    }
}

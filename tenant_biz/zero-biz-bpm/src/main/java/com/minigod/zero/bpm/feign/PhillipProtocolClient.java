package com.minigod.zero.bpm.feign;

import com.minigod.zero.bpm.service.PhillipProtocolLogService;
import com.minigod.zero.bpm.dto.PhillipProtocolSaveDTO;
import com.minigod.zero.bpm.vo.PhillipProtocolVo;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zxw
 * @date 2023-10-07 11:10
 */
@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PhillipProtocolClient implements IPhillipProtocolClient {

    PhillipProtocolLogService phillipProtocolLogService;

    private RedisLockClient redisLockClient;

    @Override
    public R<Void> savePhProtocol(@RequestBody @Validated PhillipProtocolSaveDTO saveDTO) {
        String lockKey = "ph_protocol_" + AuthUtil.getCustId();
        try {
            if (redisLockClient.tryLock(lockKey, LockType.REENTRANT, 1, 2, TimeUnit.SECONDS)) {
                phillipProtocolLogService.savePhProtocol(saveDTO);
                return R.success();
            }
        } catch (Exception e) {
           log.error("签署暗盘协议失败", e);
        }finally {
            redisLockClient.unLock(lockKey, LockType.REENTRANT);
        }
        return R.fail();
    }

    @Override
    public R<PhillipProtocolVo> findPhProtocolLog() {
        return R.data(phillipProtocolLogService.findPhProtocolLog());
    }
}

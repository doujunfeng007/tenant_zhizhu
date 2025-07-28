package com.minigod.zero.bpm.openapi;

import com.minigod.zero.bpm.service.PhillipProtocolLogService;
import com.minigod.zero.bpm.dto.PhillipProtocolSaveDTO;
import com.minigod.zero.bpm.vo.PhillipProtocolVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 暗盘协议模块
 * @author zxw
 * @date 2023-10-07 14:42
 */
@Slf4j
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/ph")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PhillipProtocolController {

    PhillipProtocolLogService phillipProtocolLogService;

    RedisLockClient redisLockClient;

    @PostMapping("/save_protocol")
    public R<Void> savePhProtocol(@RequestBody @Validated PhillipProtocolSaveDTO saveDTO) {
        String lockKey = "ph_protocol_" + AuthUtil.getCustId();
        try {
            if (redisLockClient.tryLock(lockKey, LockType.REENTRANT, 1, 2, TimeUnit.SECONDS)) {
                phillipProtocolLogService.savePhProtocol(saveDTO);
                return R.success();
            }
        } catch (Exception e) {
            log.error("签署暗盘协议失败", e);
        } finally {
            redisLockClient.unLock(lockKey, LockType.REENTRANT);
        }
        return R.fail();
    }

    @GetMapping("/find_protocol")
    public R<PhillipProtocolVo> findPhProtocolLog() {
        return R.data(phillipProtocolLogService.findPhProtocolLog());
    }
}

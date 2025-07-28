package com.minigod.zero.bpmn.utils;

import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.tool.utils.SpringUtil;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LockUtil
 * @Description:
 * @Author chenyu
 * @Date 2024/4/3
 * @Version 1.0
 */
public abstract class BusinessLock {
    private RedisLockClient redisLockClient;
    private Boolean isWait = false;

    public BusinessLock() {
        this(true);
    }

    public BusinessLock(Boolean isWait) {
        this.isWait = isWait;
        this.redisLockClient = SpringUtil.getBean(RedisLockClient.class);
    }

    public void lock(String lockKey) {
        Boolean lockStatus = false;
        try {
            do {
                lockStatus = redisLockClient.tryLock(lockKey, LockType.REENTRANT, 1, 2, TimeUnit.SECONDS);
            } while (isWait && !lockStatus);

            if (lockStatus) {
                this.business();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lockStatus) {
                redisLockClient.unLock(lockKey, LockType.REENTRANT);
            }
        }
    }

    public abstract void business();

}

package com.minigod.zero.cust.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.cust.entity.AppFuncConfigEntity;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.mapper.AppFuncConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppFuncConfig {

    @Resource
    private AppFuncConfigMapper appFuncConfigMapper;

    /**
     * 初始化APP端功能配置
     */
	@EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Init App Func Config And Clean CustInfo Cache...");
		// 先清理Redis缓存
		Jwt2Util.getRedisTemplate().delete(TokenConstant.APP_FUNC_KEY);
		Jwt2Util.getRedisTemplate().delete(CustInfoEntity.class.getName().replace(".", ":"));
        this.reloadConfig();
    }

    /**
     * 重新加载消息到该类的Map缓存中
     */
    public void reloadConfig() {
		// 查询功能配置
        Map<String, String> localeMsgMap = loadAllConfig();;
		// 缓存到redis
		if (localeMsgMap != null && !localeMsgMap.isEmpty()) {
			Jwt2Util.getRedisTemplate().opsForHash().putAll(TokenConstant.APP_FUNC_KEY, localeMsgMap);
			log.info("App端功能配置初始化成功");
		} else {
			throw new RuntimeException("APP功能配置未初始化");
		}
    }
    /**
     * 加载所有的国际化消息资源
     *
     * @return
     */
    private Map<String, String> loadAllConfig() {
        // 从数据库中查询APP功能配置
		List<AppFuncConfigEntity> list = appFuncConfigMapper.selectList(Wrappers.<AppFuncConfigEntity>lambdaQuery()
			.eq(AppFuncConfigEntity::getStatus, CommonEnums.StatusEnum.YES.getCode())
			.eq(AppFuncConfigEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.eq(AppFuncConfigEntity::getConfigType, 2));
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().collect(Collectors.toMap(AppFuncConfigEntity::getFuncCode, AppFuncConfigEntity::getFuncPath));
		}
		return null;
    }

}

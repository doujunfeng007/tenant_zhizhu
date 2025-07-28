package com.minigod.zero.system.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.system.entity.I18nConfigEntity;
import com.minigod.zero.system.mapper.I18nConfigMapper;
import com.minigod.zero.system.service.II18nConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 国际化配置表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-08
 */
@Service
public class I18nConfigServiceImpl extends ServiceImpl<I18nConfigMapper, I18nConfigEntity> implements II18nConfigService {

    /**
     * 查询模块多语言配置
     * @param moduleName
     * @return
     */
    public List<I18nConfigEntity> selectI18nConfigByModule(String moduleName) {
        List<I18nConfigEntity> list = baseMapper.selectList(Wrappers.<I18nConfigEntity>lambdaQuery().eq(I18nConfigEntity::getSysModel, moduleName).eq(I18nConfigEntity::getIsDeleted, 0));
        return list;
    }

}

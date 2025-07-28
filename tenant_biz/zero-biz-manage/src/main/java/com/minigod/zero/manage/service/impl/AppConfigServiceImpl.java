package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.AppConfigEntity;
import com.minigod.zero.manage.mapper.AppConfigMapper;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.vo.AppConfigVO;
import com.minigod.zero.manage.dto.AppConfigSaveDTO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.manage.service.IAppConfigService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * APP配置 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Service
public class AppConfigServiceImpl extends BaseServiceImpl<AppConfigMapper, AppConfigEntity> implements IAppConfigService {

	@Resource
	private ICustInfoService custInfoService;


	@Override
	public IPage<AppConfigVO> selectAppConfigPage(IPage<AppConfigVO> page, AppConfigVO appConfig) {
		List<AppConfigVO> list = baseMapper.selectAppConfigPage(page, appConfig);
		List<Long> updateUserIds = null;
		if (null != list) {
			updateUserIds = list.stream().filter(item -> item.getUpdateUser() != null).map(BaseEntity::getUpdateUser).collect(Collectors.toList());
			Map<Long, User> updateUserMap = custInfoService.userInfoByIds(updateUserIds);
			if (MapUtils.isNotEmpty(updateUserMap)) {
				for (AppConfigVO appConfigVO : list) {
					appConfigVO.setUpdateUserName(updateUserMap.get(appConfigVO.getUpdateUser()).getName());
				}
			}
		}
		return page.setRecords(list);
	}

	@Override
	public R<Object> publish(String ids) {
		List<Long> arrIds = Func.toLongList(ids);
		for (Long id : arrIds) {
			AppConfigEntity entity = baseMapper.selectById(id);
			entity.setPublishTime(new Date());
			entity.setStatus(1);
			baseMapper.updateById(entity);
		}
		return R.success();
	}

	@Override
	public void save(AppConfigSaveDTO appConfig) {
		Date date = new Date();
		AppConfigEntity configEntity = null;
		if (appConfig.getId() != null) {
			configEntity = lambdaQuery().eq(AppConfigEntity::getId, appConfig.getId()).eq(AppConfigEntity::getIsDeleted, 0).one();
		}
		if (configEntity == null) {
			//不做唯一性处理,保存状态默认为未发布
//			long count = lambdaQuery().eq(AppConfigEntity::getItemKey, appConfig.getItemKey()).eq(AppConfigEntity::getIsDeleted, 0).count();
//			if(count > 0){
//				throw new RuntimeException("已存在对应的key,不能重复设置");
//			}
			configEntity = BeanUtil.copyProperties(appConfig, AppConfigEntity.class);
			configEntity.setCreateUser(AuthUtil.getUserId());
			configEntity.setCreateDept(Long.parseLong(AuthUtil.getDeptId()));
			configEntity.setCreateTime(date);
			configEntity.setIsDeleted(0);
			configEntity.setStatus(0);
		} else {
			BeanUtil.copyProperties(appConfig, configEntity);
			if (appConfig.getStatus()!=null){
				configEntity.setStatus(appConfig.getStatus());
			}
		}
		configEntity.setUpdateTime(date);
		configEntity.setUpdateUser(AuthUtil.getUserId());
		configEntity.setUpdateUserName(AuthUtil.getUserName());
		this.saveOrUpdate(configEntity);
	}

	@Override
	public R getDataFromUrl(String key) {
		LambdaQueryWrapper<AppConfigEntity> queryWrapper = new LambdaQueryWrapper<>();
		LambdaQueryWrapper<AppConfigEntity> query = queryWrapper.eq(AppConfigEntity::getItemKey, key)
			.eq(AppConfigEntity::getStatus,1)
			.eq(AppConfigEntity::getIsDeleted,0)
			.orderByDesc(AppConfigEntity::getCreateTime);
		//有多个的话.取最新的一个
		List<AppConfigEntity> appConfigEntities = this.getBaseMapper().selectList(query);
		if (CollectionUtil.isNotEmpty(appConfigEntities)){
			AppConfigEntity appConfigEntity = appConfigEntities.get(0);
			String configContent = appConfigEntity.getConfigContent();
			return R.data(configContent);
		}else {
			return R.fail("url未生效");
		}

	}


}

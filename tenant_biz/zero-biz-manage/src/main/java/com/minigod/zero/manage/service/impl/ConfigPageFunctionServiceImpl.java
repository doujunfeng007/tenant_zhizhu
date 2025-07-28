package com.minigod.zero.manage.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.manage.entity.ConfigPageFunctionEntity;
import com.minigod.zero.manage.entity.DiscoverIconEntity;
import com.minigod.zero.manage.entity.DiscoverRoleIconEntity;
import com.minigod.zero.manage.mapper.ConfigPageFunctionMapper;
import com.minigod.zero.manage.mapper.DiscoverIconMapper;
import com.minigod.zero.manage.mapper.DiscoverRoleIconMapper;
import com.minigod.zero.manage.service.IConfigPageFunctionService;
import com.minigod.zero.manage.vo.ConfigPageFunctionVO;
import com.minigod.zero.manage.vo.response.IconExtVO;
import com.minigod.zero.manage.vo.response.IndexVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.api.R;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置页面组件 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Service
public class ConfigPageFunctionServiceImpl extends BaseServiceImpl<ConfigPageFunctionMapper, ConfigPageFunctionEntity> implements IConfigPageFunctionService {

	@Resource
	private DiscoverIconMapper discoverIconMapper;
	@Resource
	private DiscoverRoleIconMapper discoverRoleIconMapper;

	@Override
	public IPage<ConfigPageFunctionVO> selectConfigPageFunctionPage(IPage<ConfigPageFunctionVO> page, ConfigPageFunctionVO ConfigPageFunction) {
		return page.setRecords(baseMapper.selectConfigPageFunctionPage(page, ConfigPageFunction));
	}

	@Override
	public List<ConfigPageFunctionEntity> getConfigPageFunctionList(Integer typeValue) {
		List<ConfigPageFunctionEntity> list = new LambdaQueryChainWrapper<>(baseMapper).eq(ConfigPageFunctionEntity::getFunctionType, typeValue)
			.orderByDesc(ConfigPageFunctionEntity::getGrade).list();
		return list;
	}

	@Override
	public List<IndexVO> getIndex() {
		List<IndexVO> list = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			Integer count = 0;
			List<IconExtVO> iconExtVOS = discoverIconMapper.getIconExtList(i, true);
			if (CollectionUtils.isNotEmpty(iconExtVOS)) {
				StringBuilder sb = new StringBuilder();
				for (IconExtVO iconExtVO : iconExtVOS) {
					sb.append(iconExtVO.getFunctionName()).append("，");
					count++;
				}
				IndexVO indexVO = new IndexVO();
				indexVO.setRoleId(i);
				indexVO.setIconCount(count);
				indexVO.setNames(sb.toString().substring(0, sb.toString().length() - 1));
				list.add(indexVO);
			} else {//如果对应角色的图标全部为不展示状态，界面仍需要显示一条记录，人工生成一条数据
				IndexVO indexVO = new IndexVO();
				indexVO.setRoleId(i);
				indexVO.setIconCount(0);
				indexVO.setNames("");
				list.add(indexVO);
			}
		}
		return list;
	}

	@Override
	public List<IconExtVO> getList(Integer roleId, Integer showType) {
		return discoverIconMapper.getIconExtList(roleId, true);
	}

	@Override
	public R<Object> updataDisplayStatus(Long id, boolean isDisplay) {
		DiscoverRoleIconEntity iconEntity = discoverRoleIconMapper.selectById(id);
		iconEntity.setIsDisplay(isDisplay);
		return R.data(discoverRoleIconMapper.updateById(iconEntity));
	}

	@Override
	public void sortIcons(Long[] intTemp) {
		DiscoverRoleIconEntity discoverRoleIconEntity = null;
		for (int i=0;i<intTemp.length;i++){
			discoverRoleIconEntity = discoverRoleIconMapper.selectById(intTemp[i]);
			discoverRoleIconEntity.setGrade(i+1);
			discoverRoleIconMapper.updateById(discoverRoleIconEntity);
		}
	}

	@Override
	public void saveDisplayStatusAndSort(Long id, Boolean isDisplay) {
		ConfigPageFunctionEntity configPageFunctionEntity = baseMapper.selectById(id);
		configPageFunctionEntity.setStatus(isDisplay?1:0);
		baseMapper.updateById(configPageFunctionEntity);
	}

	@Override
	public void sortConfigPageFunction(Long[] intTemp) {
		ConfigPageFunctionEntity configPageFunctionEntity = null;
		for(int i=0;i<intTemp.length;i++){//重新设置id对应的grade值
			configPageFunctionEntity = baseMapper.selectById(intTemp[i]);
			configPageFunctionEntity.setGrade(i+1);
			baseMapper.updateById(configPageFunctionEntity);
		}
	}

	@Override
	public List queryDiscoverIconList() {
		return discoverIconMapper.queryDiscoverIconList();
	}

	@Override
	public void updateDiscoverIcon(List<DiscoverIconEntity> discoverIconList) {
		for(DiscoverIconEntity discoverIcon:discoverIconList){
			DiscoverIconEntity dbVO = discoverIconMapper.selectById(discoverIcon.getId());
			dbVO.setFunctionName(discoverIcon.getFunctionName());
			dbVO.setName(discoverIcon.getName());
			if(StringUtils.isNotEmpty(discoverIcon.getUrlSmall())){
				dbVO.setUrlSmall(discoverIcon.getUrlSmall());
			}
			if(StringUtils.isNotEmpty(discoverIcon.getUrlBig())){
				dbVO.setUrlBig(discoverIcon.getUrlBig());
			}
			if(StringUtils.isNotEmpty(discoverIcon.getUrlAndroid())){
				dbVO.setUrlAndroid(discoverIcon.getUrlAndroid());
			}
			if(StringUtils.isNotEmpty(discoverIcon.getUrlIosBlack())){
				dbVO.setUrlIosBlack(discoverIcon.getUrlIosBlack());
			}
			if(StringUtils.isNotEmpty(discoverIcon.getUrlIosWhite())){
				dbVO.setUrlIosWhite(discoverIcon.getUrlIosWhite());
			}
			if(StringUtils.isNotEmpty(discoverIcon.getUrlAndroidBlack())){
				dbVO.setUrlAndroidBlack(discoverIcon.getUrlAndroidBlack());
			}
			if(StringUtils.isNotEmpty(discoverIcon.getUrlAndroidWhite())){
				dbVO.setUrlAndroidWhite(discoverIcon.getUrlAndroidWhite());
			}
			dbVO.setUrlPage(discoverIcon.getUrlPage());
			dbVO.setIpFilter(discoverIcon.getIpFilter());

			discoverIconMapper.updateById(dbVO);
		}
	}

	@Override
	public int updateSort(Long id) {
		baseMapper.updateSort(id);
		return 1;
	}


}

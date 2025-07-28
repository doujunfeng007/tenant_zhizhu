package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.AdInfoEntity;
import com.minigod.zero.manage.mapper.AdInfoMapper;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.vo.AdInfoVO;
import com.minigod.zero.manage.service.IAdInfoService;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 广告信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Slf4j
@Service
public class AdInfoServiceImpl extends BaseServiceImpl<AdInfoMapper, AdInfoEntity> implements IAdInfoService {
	@Resource
	private ICustInfoService custInfoService;
	@Value("${ad.user.can.see:155,28}")
	private String adUserSeeId;

	private List<Integer> lstAdUserId = new ArrayList<Integer>();

	@PostConstruct
	private void init() {
		String[] arrAdUserId = adUserSeeId.split(",");
		for (String userId : arrAdUserId) {
			lstAdUserId.add(Integer.valueOf(userId));
		}
	}

	@Override
	public IPage<AdInfoVO> selectAdInfoPage(IPage<AdInfoVO> page, AdInfoVO adInfo) {
		List<AdInfoVO> list = baseMapper.selectAdInfoPage(page, adInfo);
		List<Long> updateUserIds = null;
		if (null != list) {
			updateUserIds = list.stream().map(BaseEntity::getUpdateUser).filter(Objects::nonNull).collect(Collectors.toList());
			Map<Long, User> updateUserMap = custInfoService.userInfoByIds(updateUserIds);
			if (MapUtils.isNotEmpty(updateUserMap)) {
				for (AdInfoVO adInfoVO : list) {
					if (updateUserMap.get(adInfoVO.getUpdateUser())!=null){
						adInfoVO.setUpdateUserName(updateUserMap.get(adInfoVO.getUpdateUser()).getName());
					}else {
						adInfoVO.setUpdateUserName("管理员");
					}
				}
			}
		}
		return page.setRecords(list);
	}

	@Override
	public R<Object> submit(AdInfoVO adInfo) {
		try {
			if (null == adInfo.getId() && StringUtils.isEmpty(adInfo.getImg())) {
				return R.fail("请上传图片");
			}
			if (null == adInfo.getId() && 1 == adInfo.getIsShowInActivity()) {
				if (StringUtils.isEmpty(adInfo.getBananerImg())){
					return R.fail("请上传活动专栏图片");
				}
			}
			if (null == adInfo.getStartTime() || null == adInfo.getEndTime()) {
				return R.fail("请填写活动开始时间和结束时间");
			}
			Long count = findAdInfo(adInfo);
			if (count > 0) {
				return R.fail("有效广告信息优先级重复");
			}
			adInfo.setSubstartTime(adInfo.getStartTime());
			adInfo.setSubendTime(adInfo.getEndTime());
			if (null != adInfo.getChannelIds()) {
				String channels = StringUtils.join(adInfo.getChannelIds(), ",");
				if ("2".equals(adInfo.getShowType())) {
					adInfo.setWhiteChannel(channels);
				} else if ("3".equals(adInfo.getShowType())) {
					adInfo.setMaskChannel(channels);
				}
			}
			if (null != adInfo.getUserIds() && 0 != adInfo.getUserIds().length) {
				String userIdsString = StringUtils.join(adInfo.getUserIds(), ",");
				adInfo.setShowUser(userIdsString);
			}
			if (null != adInfo.getId()) {
				adInfo.setUpdateTime(new Date());
				adInfo.setUpdateUser(AuthUtil.getUserId());
				baseMapper.updateById(adInfo);
			} else {
				adInfo.setCreateUser(AuthUtil.getUserId());
				if(StringUtils.isNotEmpty(AuthUtil.getDeptId())){
					adInfo.setCreateDept(Long.parseLong(AuthUtil.getDeptId()));
				}
				adInfo.setCreateTime(new Date());
				adInfo.setUpdateTime(new Date());
				adInfo.setUpdateUser(AuthUtil.getUserId());
				adInfo.setStatus(1);
				baseMapper.insert(adInfo);
			}
			return R.success();
		} catch (Exception e) {
			return R.fail("提交失败:" + e.getMessage());
		}
	}

	private Long findAdInfo(AdInfoEntity adInfo) {
		Date date = new Date();
		LambdaQueryWrapper<AdInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AdInfoEntity::getIsDeleted, 0)
			.le(AdInfoEntity::getStartTime, date)
			.ge(AdInfoEntity::getEndTime, date)
			.eq(AdInfoEntity::getStatus, true)
			.eq(AdInfoEntity::getPriority, adInfo.getPriority())
			.eq(AdInfoEntity::getPositionCode, adInfo.getPositionCode())
			.eq(AdInfoEntity::getTenantId, AuthUtil.getTenantId());
		if (null != adInfo.getId()) {
			queryWrapper.ne(AdInfoEntity::getId, adInfo.getId());
		}
		return baseMapper.selectCount(queryWrapper);
	}

	@Override
	public List<AdInfoEntity> findByState(int status) {
		return baseMapper.selectList(new LambdaQueryWrapper<AdInfoEntity>().eq(AdInfoEntity::getStatus,1));
	}
}

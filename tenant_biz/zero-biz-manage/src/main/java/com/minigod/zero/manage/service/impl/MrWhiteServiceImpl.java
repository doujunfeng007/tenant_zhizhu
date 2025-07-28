package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.manage.entity.MrWhiteEntity;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.service.IMrWhiteService;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.manage.vo.MrWhiteVO;
import com.minigod.zero.manage.mapper.MrWhiteMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * MR白名单 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Service
public class MrWhiteServiceImpl extends BaseServiceImpl<MrWhiteMapper, MrWhiteEntity> implements IMrWhiteService {

	@Resource
	private ICustInfoService custInfoService;

	@Resource
	private ICustInfoClient custInfoClient;

	@Override
	public IPage<MrWhiteVO> selectMrWhitePage(IPage<MrWhiteVO> page, MrWhiteVO mrWhite) {
		List<MrWhiteVO> list = baseMapper.selectMrWhitePage(page, mrWhite);
		List<Long> updateUserIds = null;
		if (null != list) {
			updateUserIds = list.stream().filter(item -> item.getUpdateUser() != null).map(BaseEntity::getUpdateUser).collect(Collectors.toList());
			Map<Long, User> updateUserMap = custInfoService.userInfoByIds(updateUserIds);
			if (MapUtils.isNotEmpty(updateUserMap)) {
				for (MrWhiteVO mrWhiteVO : list) {
					mrWhiteVO.setUpdateUserName(updateUserMap.get(mrWhiteVO.getUpdateUser()).getName());
				}
			}
		}
		return page.setRecords(list);
	}

	@Override
	public R<Object> submit(MrWhiteVO mrWhite) {
		List<String> arrIds = Func.toStrList(";",mrWhite.getUserIds());
		Pattern pattern = Pattern.compile("^[\\d]+$");
		for (String userId : arrIds) {
			Matcher matcher = pattern.matcher(userId + "");
			if(!matcher.matches()){
				return R.fail("用户ID:" + userId + ",格式错误");
			}
			LambdaQueryWrapper<MrWhiteEntity> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(MrWhiteEntity::getUserId, userId);
			Long count = baseMapper.selectCount(queryWrapper);
			if(count != null && count > 0){
				return R.fail("用户ID:" + userId + ",已存在白名单");
			}
			List<CustInfoEntity> custInfoEntityList = custInfoClient.findByCustIds(Func.toLongList(userId));
			if(custInfoEntityList == null || custInfoEntityList.isEmpty()){
				return R.fail("用户ID:" + userId + ",不存在");
			}

			MrWhiteEntity entity = new MrWhiteEntity();
			entity.setCreateUser(AuthUtil.getUserId());
			entity.setCreateDept(Long.parseLong(AuthUtil.getDeptId()));
			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
			entity.setUserId(Integer.parseInt(userId));
			entity.setUpdateUser(AuthUtil.getUserId());
			entity.setUpdateUserName(AuthUtil.getUserName());
			entity.setStatus(1);
			baseMapper.insert(entity);
		}
		return R.success();
	}


}

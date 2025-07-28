package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.ReportLogEntity;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.mapper.ReportLogMapper;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.service.IReportLogService;
import com.minigod.zero.manage.vo.ReportLogVO;
import com.minigod.zero.user.entity.User;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * APP日志下载 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Service
public class ReportLogServiceImpl extends BaseServiceImpl<ReportLogMapper, ReportLogEntity> implements IReportLogService {

	@Resource
	private ICustInfoService custInfoService;

	@Override
	public IPage<ReportLogVO> selectReportLogPage(IPage<ReportLogVO> page, ReportLogVO reportLog) {
		List<ReportLogVO> list = baseMapper.selectReportLogPage(page, reportLog);
		List<Long> updateUserIds = null;
		if (null != list) {
			updateUserIds = list.stream().filter(item -> item.getUpdateUser() != null).map(BaseEntity::getUpdateUser).collect(Collectors.toList());
			Map<Long, User> updateUserMap = custInfoService.userInfoByIds(updateUserIds);
			if (MapUtils.isNotEmpty(updateUserMap)) {
				for (ReportLogVO reportLogVO : list) {
					reportLogVO.setUpdateUserName(updateUserMap.get(reportLogVO.getUpdateUser()).getName());
				}
			}
		}
		return page.setRecords(list);
	}
}

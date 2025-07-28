package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.ReportLogEntity;
import com.minigod.zero.manage.vo.ReportLogVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * APP日志下载 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public class ReportLogWrapper extends BaseEntityWrapper<ReportLogEntity, ReportLogVO>  {

	public static ReportLogWrapper build() {
		return new ReportLogWrapper();
 	}

	@Override
	public ReportLogVO entityVO(ReportLogEntity reportLog) {
	    ReportLogVO reportLogVO = new ReportLogVO();
    	if (reportLog != null) {
		    reportLogVO = Objects.requireNonNull(BeanUtil.copy(reportLog, ReportLogVO.class));

		    //User createUser = UserCache.getUser(reportLog.getCreateUser());
		    //User updateUser = UserCache.getUser(reportLog.getUpdateUser());
		    //reportLogVO.setCreateUserName(createUser.getName());
		    //reportLogVO.setUpdateUserName(updateUser.getName());
        }
		return reportLogVO;
	}


}

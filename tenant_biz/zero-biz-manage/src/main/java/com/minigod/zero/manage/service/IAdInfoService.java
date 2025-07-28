package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.AdInfoEntity;
import com.minigod.zero.manage.vo.AdInfoVO;
import com.minigod.zero.core.tool.api.R;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 广告信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface IAdInfoService extends BaseService<AdInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adInfo
	 * @return
	 */
	IPage<AdInfoVO> selectAdInfoPage(IPage<AdInfoVO> page, AdInfoVO adInfo);

	/**
	 * 保存或修改
	 * @param adInfo
	 * @return
	 */
    R<Object> submit(AdInfoVO adInfo);

	/**
	 * 根据状态查询列表
	 * @param status
	 * @return
	 */
	List<AdInfoEntity> findByState(int status);
}

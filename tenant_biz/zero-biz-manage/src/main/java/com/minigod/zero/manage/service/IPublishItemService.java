package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.PublishItemEntity;
import com.minigod.zero.manage.vo.request.PublishItemRequest;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 帮助中心目录配置 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface IPublishItemService extends IService<PublishItemEntity> {

	/**
	 * 查询主菜单或非主菜单
	 *
	 * @param id
	 * @param used true-排除常见问题菜单 false-包括常见问题菜单
	 * @return
	 */
	List<PublishItemEntity> findPublishItem(Long id, Boolean used);

	/**
	 * 查询指定菜单ID下的子菜单列表
	 *
	 * @param id
	 * @return
	 */
	List<PublishItemEntity> publishTree(Long id);

	/**
	 * 根据id查询菜单详情
	 *
	 * @param id
	 * @return
	 */
	PublishItemEntity findById(Long id);

	/**
	 * 保存/更新菜单
	 *
	 * @param publishItemRequest
	 * @return
	 */
	Long saveUpdate(PublishItemRequest publishItemRequest);

	/**
	 * 更改菜单状态
	 *
	 * @param publishItemRequest
	 */
	R updateStatus(PublishItemRequest publishItemRequest);

	/**
	 * 删除指定ID的菜单项
	 *
	 * @param ids
	 * @return
	 */
	boolean deleteByIds(List<Long> ids);
}

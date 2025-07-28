package com.minigod.zero.manage.service;

import com.minigod.zero.manage.vo.request.SensitiveWordLogSaveVo;

import java.util.ArrayList;

/**
 * @author 掌上智珠
 * @since 2023/7/25 9:54
 */
public interface ISensitiveWordLogService {
	/**
	 * 接收vo转换为实体类并批量保存
	 * @param list
	 */
	 boolean saveBatch(ArrayList<SensitiveWordLogSaveVo> list);
}

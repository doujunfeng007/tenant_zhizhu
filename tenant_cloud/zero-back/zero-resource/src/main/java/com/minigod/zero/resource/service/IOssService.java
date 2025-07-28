
package com.minigod.zero.resource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.resource.entity.Oss;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.resource.vo.OssVO;

/**
 * 服务类
 *
 * @author minigod
 */
public interface IOssService extends BaseService<Oss> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param oss
	 * @return
	 */
	IPage<OssVO> selectOssPage(IPage<OssVO> page, OssVO oss);

	/**
	 * 提交oss信息
	 *
	 * @param oss
	 * @return
	 */
	boolean submit(Oss oss);

	/**
	 * 启动配置
	 *
	 * @param id
	 * @return
	 */
	boolean enable(Long id);

}

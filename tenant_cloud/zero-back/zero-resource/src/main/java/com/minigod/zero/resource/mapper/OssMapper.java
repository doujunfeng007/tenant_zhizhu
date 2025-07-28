
package com.minigod.zero.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.resource.entity.Oss;
import com.minigod.zero.resource.vo.OssVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author minigod
 */
public interface OssMapper extends BaseMapper<Oss> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param oss
	 * @return
	 */
	List<OssVO> selectOssPage(IPage page, OssVO oss);

}

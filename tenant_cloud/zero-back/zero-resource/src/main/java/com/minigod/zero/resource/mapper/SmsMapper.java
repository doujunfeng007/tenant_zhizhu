
package com.minigod.zero.resource.mapper;

import com.minigod.zero.resource.vo.SmsVO;
import com.minigod.zero.resource.entity.Sms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 短信配置表 Mapper 接口
 *
 * @author minigod
 */
public interface SmsMapper extends BaseMapper<Sms> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param sms
	 * @return
	 */
	List<SmsVO> selectSmsPage(IPage page, SmsVO sms);

}

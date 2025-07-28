
package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.system.entity.Param;
import com.minigod.zero.system.mapper.ParamMapper;
import com.minigod.zero.system.service.IParamService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public String getValue(String paramKey) {
		Param param = this.getOne(Wrappers.<Param>query().lambda().eq(Param::getParamKey, paramKey));
		return param.getParamValue();
	}

}

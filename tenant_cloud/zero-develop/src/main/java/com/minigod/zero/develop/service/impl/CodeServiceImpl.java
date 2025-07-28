
package com.minigod.zero.develop.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.develop.entity.Code;
import com.minigod.zero.develop.service.ICodeService;
import com.minigod.zero.develop.mapper.CodeMapper;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class CodeServiceImpl extends BaseServiceImpl<CodeMapper, Code> implements ICodeService {

	@Override
	public boolean submit(Code code) {
		code.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
		return saveOrUpdate(code);
	}
}

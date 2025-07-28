
package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.system.entity.AuthClient;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@InterceptorIgnore(tenantLine = "true")
public interface AuthClientMapper extends BaseMapper<AuthClient> {

}

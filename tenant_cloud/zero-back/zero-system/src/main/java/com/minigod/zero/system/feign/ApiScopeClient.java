
package com.minigod.zero.system.feign;

import lombok.RequiredArgsConstructor;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.minigod.zero.core.secure.constant.PermissionConstant.permissionAllStatement;
import static com.minigod.zero.core.secure.constant.PermissionConstant.permissionStatement;

/**
 * 接口权限Feign实现类
 *
 * @author Chill
 */
@NonDS
@ApiIgnore
@RestController
@RequiredArgsConstructor
public class ApiScopeClient implements IApiScopeClient {

	private final JdbcTemplate jdbcTemplate;

	@Override
	@GetMapping(PERMISSION_PATH)
	public List<String> permissionPath(String roleId) {
		List<Long> roleIds = Func.toLongList(roleId);
		return jdbcTemplate.queryForList(permissionAllStatement(roleIds.size()), String.class, roleIds.toArray());
	}

	@Override
	@GetMapping(PERMISSION_CODE)
	public List<String> permissionCode(String permission, String roleId) {
		List<Object> args = new ArrayList<>(Collections.singletonList(permission));
		List<Long> roleIds = Func.toLongList(roleId);
		args.addAll(roleIds);
		return jdbcTemplate.queryForList(permissionStatement(roleIds.size()), String.class, args.toArray());
	}

}

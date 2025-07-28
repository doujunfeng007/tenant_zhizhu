package com.minigod.zero.system.dto;

import com.minigod.zero.system.entity.Tenant;
import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/27 16:08
 * @description：
 */
@Data
public class TenantSettingDTO extends Tenant {
	private Integer userSettingId;
	private Integer roleSettingId;
	private Integer postSettingId;
	private Integer deptSettingId;
	private List<Long> menuList;
}

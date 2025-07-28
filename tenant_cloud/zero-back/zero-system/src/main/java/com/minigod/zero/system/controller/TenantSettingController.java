package com.minigod.zero.system.controller;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.dto.TenantSettingDTO;
import com.minigod.zero.system.entity.ZeroDeptSetting;
import com.minigod.zero.system.entity.ZeroPostSetting;
import com.minigod.zero.system.entity.ZeroRoleSetting;
import com.minigod.zero.system.entity.ZeroUserSetting;
import com.minigod.zero.system.service.TenantSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/27 14:14
 * @description：
 */
@RestController
@RequestMapping("/tenant/setting")
public class TenantSettingController {
	@Autowired
	private TenantSettingService tenantSettingService;

	@PostMapping("/submit")
	public R submit(@RequestBody TenantSettingDTO tenantSetting) {
		return tenantSettingService.submit(tenantSetting);
	}

	@GetMapping("/detail")
	public R detail(String tenantId) {
		return tenantSettingService.initDetail(tenantId);
	}

	@GetMapping("/generate_tenant_id")
	public R generateTenantId(){
		return R.data(tenantSettingService.generateTenantId());
	}

	@GetMapping("/role/all")
	public R getRoleAll(){
		return tenantSettingService.getAllRole();
	}
	@GetMapping("/user/all")
	public R getUserAll(){
		return tenantSettingService.getAllUserAccount();
	}
	@GetMapping("/dept/all")
	public R getDeptAll(){
		return tenantSettingService.getAllDept();
	}

	@GetMapping("/post/all")
	public R getPostAll(){
		return tenantSettingService.getAllPost();
	}

	@PostMapping("/role")
	public R roleSettingSave(@RequestBody ZeroRoleSetting zeroRoleSetting) {
		return tenantSettingService.roleSettingSave(zeroRoleSetting);
	}

	@PutMapping("/role")
	public R roleSettingEdit(@RequestBody ZeroRoleSetting zeroRoleSetting){
		return tenantSettingService.roleSettingEdit(zeroRoleSetting);
	}

	@GetMapping("/role")
	public R roleQueryPage(Query query, String keyword){
		return tenantSettingService.roleQueryPage(Condition.getPage(query),keyword);
	}

	@GetMapping("/role/{roleSettingId}")
	public R roleSettingDetail(@PathVariable("roleSettingId") Integer roleSettingId){
		return tenantSettingService.roleSettingDetail(roleSettingId);
	}

	@DeleteMapping("/role/{roleSettingId}")
	public R roleSettingDeleted(@PathVariable("roleSettingId")Integer roleSettingId){
		return tenantSettingService.roleSettingDeleted(roleSettingId);
	}

	@PostMapping("/user")
	public R userSettingSave(@RequestBody ZeroUserSetting zeroUserSetting){
		return tenantSettingService.userSettingSave(zeroUserSetting);
	}

	@PutMapping("/user")
	public R userSettingEdit(@RequestBody ZeroUserSetting zeroUserSetting){
		return tenantSettingService.userSettingEdit(zeroUserSetting);
	}

	@GetMapping("/user")
	public R userSettingQueryPage(Query query,String keyword){
		return tenantSettingService.userSettingQueryPage(Condition.getPage(query),keyword);
	}

	@GetMapping("/user/{userSettingId}")
	public R userSettingDetail(@PathVariable("userSettingId") Integer userSettingId){
		return tenantSettingService.userSettingDetail(userSettingId);
	}

	@DeleteMapping("/user/{userSettingId}")
	public R userSettingDeleted(@PathVariable("userSettingId")Integer userSettingId){
		return tenantSettingService.userSettingDeleted(userSettingId);
	}

	@PostMapping("/post")
	public R postSettingSave(@RequestBody ZeroPostSetting zeroPostSetting){
		return tenantSettingService.postSettingSave(zeroPostSetting);
	}
	@PutMapping("/post")
	public R postSettingEdit(@RequestBody ZeroPostSetting zeroPostSetting){
		return tenantSettingService.postSettingEdit(zeroPostSetting);
	}
	@GetMapping("/post")
	public R postSettingQueryPage(Query query,String keyword){
		return tenantSettingService.postSettingQueryPage(Condition.getPage(query),keyword);
	}
	@GetMapping("/post/{postSettingId}")
	public R postSettingDetail(@PathVariable("postSettingId") Integer postSettingId){
		return tenantSettingService.postSettingDetail(postSettingId);
	}

	@PutMapping("/post/{postSettingId}")
	public R postSettingDeleted(@PathVariable("postSettingId")Integer postSettingId){
		return tenantSettingService.postSettingDeleted(postSettingId);
	}

	@PostMapping("/dept")
	public R deptSettingSave(@RequestBody ZeroDeptSetting zeroPostSetting){
		return tenantSettingService.deptSettingSave(zeroPostSetting);
	}
	@PutMapping("/dept")
	public R deptSettingEdit(@RequestBody ZeroDeptSetting zeroPostSetting){
		return tenantSettingService.deptSettingEdit(zeroPostSetting);
	}
	@GetMapping("/dept")
	public R deptSettingQueryPage(Query query,String keyword){
		return tenantSettingService.deptSettingQueryPage(Condition.getPage(query),keyword);
	}
	@GetMapping("/dept/{deptSettingId}")
	public R deptSettingDetail(@PathVariable("deptSettingId") Integer deptSettingId){
		return tenantSettingService.deptSettingDetail(deptSettingId);
	}

	@PutMapping("/dept/{deptSettingId}")
	public R deptSettingDeleted(@PathVariable("deptSettingId")Integer deptSettingId){
		return tenantSettingService.deptSettingDeleted(deptSettingId);
	}


}

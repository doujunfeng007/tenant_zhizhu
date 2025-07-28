package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.common.constant.TenantConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tenant.TenantId;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.system.cache.ParamCache;
import com.minigod.zero.system.common.GlobalExecutorService;
import com.minigod.zero.system.dto.TenantSettingDTO;
import com.minigod.zero.system.entity.*;
import com.minigod.zero.system.mapper.*;
import com.minigod.zero.system.service.IDictBizService;
import com.minigod.zero.system.service.TenantSettingService;
import com.minigod.zero.system.vo.TenantSettingVO;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.enums.SourceType;
import com.minigod.zero.user.feign.IUserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/27 14:15
 * @description：
 */
@Slf4j
@Service
public class TenantSettingServiceImpl implements TenantSettingService {

	@Autowired
	private TenantId tenantId;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private TopMenuMapper topMenuMapper;

	@Autowired
	private TopMenuSettingMapper topMenuSettingMapper;

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Autowired
	private TenantMapper tenantMapper;

	@Autowired
	private IDictBizService dictBizService;

	@Autowired
	private IUserClient userClient;

	@Autowired
	private ZeroRoleSettingMapper zeroRoleSettingMapper;

	@Autowired
	private ZeroUserSettingMapper zeroUserSettingMapper;

	@Autowired
	private ZeroPostSettingMapper zeroPostSettingMapper;

	@Autowired
	private ZeroDeptSettingMapper zeroDeptSettingMapper;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R submit(TenantSettingDTO tenantSetting) {
		//参数校验
		checkSubmitParam(tenantSetting);
		//租户信息
		Integer deptSettingId = tenantSetting.getDeptSettingId();
		ZeroDeptSetting deptSetting = zeroDeptSettingMapper.selectByPrimaryKey(deptSettingId);
		if (deptSetting == null) {
			throw new ZeroException("初始化失败，部门信息不存在！");
		}
		//部门信息
		Dept dept = new Dept();
		dept.setDeptCategory(1);
		dept.setSort(deptSetting.getSort());
		dept.setParentId(deptSetting.getParentId());
		dept.setTenantId(tenantSetting.getTenantId());
		dept.setParentId(ZeroConstant.TOP_PARENT_ID);
		dept.setDeptName(deptSetting.getDeptName());
		dept.setFullName(deptSetting.getFullName());
		dept.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
		dept.setAncestors(String.valueOf(ZeroConstant.TOP_PARENT_ID));
		deptMapper.insert(dept);
		//角色信息
		Integer roleSettingId = tenantSetting.getRoleSettingId();
		ZeroRoleSetting roleSetting = zeroRoleSettingMapper.selectByPrimaryKey(roleSettingId);
		if (roleSetting == null) {
			throw new ZeroException("初始化失败，角色信息不存在！");
		}
		Role role = new Role();
		role.setParentId(roleSetting.getParentId());
		role.setTenantId(tenantSetting.getTenantId());
		role.setParentId(ZeroConstant.TOP_PARENT_ID);
		role.setRoleName(roleSetting.getRoleName());
		role.setRoleAlias(roleSetting.getRoleAlias());
		role.setSort(roleSetting.getSort());
		role.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
		roleMapper.insert(role);
		//菜单信息
		List<Long> menuList = tenantSetting.getMenuList();
		List<Long> childMenuIdList = new ArrayList<>();
		getChildMenuIdList(menuList,childMenuIdList);
		menuList.addAll(childMenuIdList);
		List<RoleMenu> roleMenuList = new ArrayList<>();
		menuList.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(menuId);
			roleMenu.setRoleId(role.getId());
			roleMenuList.add(roleMenu);
		});
		roleMenuMapper.insertBatch(roleMenuList);
		//初始化头部菜单
		List<TopMenuSetting> topMenuList = topMenuMapper.selectByMenuIdList(menuList);
		Map<Long, List<TopMenuSetting>> groupedByAge = topMenuList.stream()
			.collect(Collectors.groupingBy(TopMenuSetting::getTopMenuId));

		groupedByAge.forEach((topMenuId, topMenuSettingList) -> {
			TopMenu topMenu = topMenuMapper.selectById(topMenuId);
			if (topMenu != null){
				TopMenu newTopMenu = new TopMenu();
				BeanUtils.copyProperties(topMenu,newTopMenu);
				newTopMenu.setId(null);
				newTopMenu.setTenantId(tenantSetting.getTenantId());
				topMenuMapper.insert(newTopMenu);

				Long newTopMenuId = newTopMenu.getId();
				topMenuSettingList.forEach(topMenuSetting -> {
					TopMenuSetting newTopMenuSetting = new TopMenuSetting();
					newTopMenuSetting.setTopMenuId(newTopMenuId);
					newTopMenuSetting.setMenuId(topMenuSetting.getMenuId());
					topMenuSettingMapper.insert(newTopMenuSetting);
				});
			}
		});
		//岗位信息
		Integer postSettingId = tenantSetting.getPostSettingId();
		ZeroPostSetting postSetting = zeroPostSettingMapper.selectByPrimaryKey(postSettingId);
		if (postSetting == null) {
			throw new ZeroException("初始化失败，岗位信息不存在！");
		}
		Post post = new Post();
		post.setTenantId(tenantSetting.getTenantId());
		post.setCategory(1);
		post.setPostCode(postSetting.getPostCode());
		post.setPostName(postSetting.getPostName());
		post.setSort(postSetting.getSort());
		postMapper.insert(post);
		//登录账号信息
		Integer userSettingId = tenantSetting.getUserSettingId();
		ZeroUserSetting userSetting = zeroUserSettingMapper.selectByPrimaryKey(userSettingId);
		if (userSetting == null) {
			throw new ZeroException("初始化失败，账号信息不存在！");
		}
		User user = new User();
		user.setName(userSetting.getName());
		user.setArea(userSetting.getArea());
		user.setPhone(userSetting.getPhone());
		user.setEmail(userSetting.getEmail());
		user.setAccount(userSetting.getAccount());
		user.setRealName(userSetting.getRealName());
		user.setTenantId(tenantSetting.getTenantId());
		// 获取参数配置的密码
		user.setPassword(TenantConstant.DEFAULT_PASSWORD);
		user.setRoleId(String.valueOf(role.getId()));
		user.setDeptId(String.valueOf(dept.getId()));
		user.setPostId(String.valueOf(post.getId()));
		user.setUserType(SourceType.WEB.getCategory());
		user.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
		R<Boolean> result = userClient.saveUser(user);
		if (!result.isSuccess() || !result.getData()) {
			log.error("用户信息初始化失败,具体原因:{}", result.getMsg());
			throw new ZeroException("用户信息初始化失败,具体原因:" + result.getMsg());
		}
		Tenant tenant = new Tenant();
		tenant.setTenantId(tenantSetting.getTenantId());
		tenant.setInitAccount(userSetting.getAccount());
		tenant.setUserSettingId(userSettingId);
		tenant.setRoleSettingId(roleSettingId);
		tenant.setDeptSettingId(deptSettingId);
		tenant.setPostSettingId(postSettingId);
		tenant.setMenuSettingId(menuList.stream().map(menuId -> Func.toStr(menuId)).collect(Collectors.joining(",")));
		BeanUtils.copyProperties(tenantSetting, tenant);
		tenant.setTenantName(tenantSetting.getTenantName());
		int accountNumber = Func.toInt(ParamCache.getValue(TenantConstant.ACCOUNT_NUMBER_KEY), TenantConstant.DEFAULT_ACCOUNT_NUMBER);
		tenant.setAccountNumber(accountNumber);
		tenant.setCreateDept(Long.valueOf(AuthUtil.getDeptId()));
		tenant.setCreateTime(new Date());
		tenant.setCreateUser(AuthUtil.getUserId());
		tenant.setStatus(1);
		tenant.setUpdateTime(new Date());
		tenant.setUpdateUser(AuthUtil.getUserId());
		tenantMapper.insert(tenant);
		GlobalExecutorService.getExecutor().execute(() -> {
			try {
				//字典初始
				LinkedList<DictBiz> dictBizs = new LinkedList<>();
				List<DictBiz> dictBizList = getDictBizs(tenantSetting.getTenantId(), dictBizs);
				dictBizService.batchInsert(dictBizList);
				log.info("字典初始化完成!");
			} catch (Exception e) {
				log.error("SAAS配置初始化,字典初始异常：{}", e);
				throw new RuntimeException("字典初始异常！！");
			}
		});
		return R.success();
	}


	private List<Long> getChildMenuIdList(List<Long> menuIdList,List<Long> childMenuIdList){
		List<Menu> menuList = menuMapper.selectByIdList(menuIdList);
		if (CollectionUtil.isEmpty(menuList)){
			return childMenuIdList;
		}
		childMenuIdList.addAll(menuList.stream().map(Menu::getId).collect(Collectors.toList()));
		menuIdList = menuList.stream().filter(menu->{
			return menu.getCategory() != 2;
		}).map(Menu::getId).collect(Collectors.toList());
		if (CollectionUtil.isEmpty(menuIdList)){
			return childMenuIdList;
		}else{
			return getChildMenuIdList(menuIdList,childMenuIdList);
		}
	}

	@Override
	public R initDetail(String tenantId) {
		Tenant tenant = tenantMapper.selectByTenantId(tenantId);
		if (tenant == null) {
			return R.fail("租户不存在");
		}
		TenantSettingVO tenantSetting = new TenantSettingVO();
		BeanUtils.copyProperties(tenant, tenantSetting);

		String menuIdStr = tenant.getMenuSettingId();
		if (!StringUtils.isEmpty(menuIdStr)){
			String[] menuIdArray = menuIdStr.split(",");
			List<Long> menuIdList = new ArrayList<>();
			if (menuIdArray != null){
				for (String menuId : menuIdArray){
					if (StringUtils.isEmpty(menuId)){
						continue;
					}
					menuIdList.add(Long.valueOf(menuId));
				}
			}
			tenantSetting.setMenuList(menuIdList);
		}
		return R.data(tenantSetting);
	}

	@Override
	public R generateTenantId() {
		String code = tenantId.generate();
		while (tenantMapper.selectByTenantId(code) != null) {
			code = tenantId.generate();
		}
		return R.data(code);
	}

	@Override
	public R getAllUserAccount() {
		return R.data(zeroUserSettingMapper.getAll());
	}

	@Override
	public R getAllRole() {
		return R.data(zeroRoleSettingMapper.getAll());
	}

	@Override
	public R getAllDept() {
		return R.data(zeroDeptSettingMapper.getAll());
	}

	@Override
	public R getAllPost() {
		return R.data(zeroPostSettingMapper.getAll());
	}

	@Override
	public R roleSettingSave(ZeroRoleSetting zeroRoleSetting) {
		if (StringUtils.isEmpty(zeroRoleSetting.getRoleAlias())) {
			throw new ZeroException("角色别名不能为空！");
		}
		if (StringUtils.isEmpty(zeroRoleSetting.getRoleName())) {
			throw new ZeroException("角色名称不能为空！");
		}
		if (zeroRoleSetting.getSort() == null) {
			throw new ZeroException("角色排序不能为空！");
		}
		zeroRoleSettingMapper.insertSelective(zeroRoleSetting);
		return R.success();
	}

	@Override
	public R roleSettingEdit(ZeroRoleSetting zeroRoleSetting) {
		if (zeroRoleSetting.getId() == null) {
			throw new ZeroException("角色id不能为空！");
		}
		if (StringUtils.isEmpty(zeroRoleSetting.getRoleAlias())) {
			throw new ZeroException("角色别名不能为空！");
		}
		if (StringUtils.isEmpty(zeroRoleSetting.getRoleName())) {
			throw new ZeroException("角色名称不能为空！");
		}
		if (zeroRoleSetting.getSort() == null) {
			throw new ZeroException("角色排序不能为空！");
		}
		zeroRoleSettingMapper.updateByPrimaryKeySelective(zeroRoleSetting);
		return R.success();
	}

	@Override
	public R roleQueryPage(IPage page, String keyword) {
		List<ZeroRoleSetting> list = zeroRoleSettingMapper.roleQueryPage(page, keyword);
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R roleSettingDetail(Integer roleSettingId) {
		if (roleSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		return R.data(zeroRoleSettingMapper.selectByPrimaryKey(roleSettingId));
	}

	@Override
	public R roleSettingDeleted(Integer roleSettingId) {
		if (roleSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		zeroRoleSettingMapper.deleteByPrimaryKey(roleSettingId);
		return R.success();
	}

	@Override
	public R userSettingSave(ZeroUserSetting zeroUserSetting) {
		if (StringUtils.isEmpty(zeroUserSetting.getAccount())) {
			throw new ZeroException("账号不能为空！");
		}
		if (StringUtils.isEmpty(zeroUserSetting.getName())) {
			throw new ZeroException("昵称不能为空！");
		}
		if (StringUtils.isEmpty(zeroUserSetting.getArea())
			|| StringUtils.isEmpty(zeroUserSetting.getPhone())) {
			throw new ZeroException("手机号不能为空！");
		}
		if (StringUtils.isEmpty(zeroUserSetting.getPassword())) {
			throw new ZeroException("初始密码不能为空！");
		}
		zeroUserSettingMapper.insertSelective(zeroUserSetting);
		return R.success();
	}

	@Override
	public R userSettingEdit(ZeroUserSetting zeroUserSetting) {
		if (zeroUserSetting.getId() == null) {
			throw new ZeroException("id不能为空！");
		}
		if (StringUtils.isEmpty(zeroUserSetting.getAccount())) {
			throw new ZeroException("账号不能为空！");
		}
		if (StringUtils.isEmpty(zeroUserSetting.getName())) {
			throw new ZeroException("昵称不能为空！");
		}
		if (StringUtils.isEmpty(zeroUserSetting.getArea())
			|| StringUtils.isEmpty(zeroUserSetting.getPhone())) {
			throw new ZeroException("手机号不能为空！");
		}
		if (StringUtils.isEmpty(zeroUserSetting.getPassword())) {
			throw new ZeroException("初始密码不能为空！");
		}
		zeroUserSettingMapper.updateByPrimaryKeySelective(zeroUserSetting);
		return R.success();
	}

	@Override
	public R userSettingQueryPage(IPage page, String keyword) {
		List<ZeroUserSetting> list = zeroUserSettingMapper.userSettingQueryPage(page, keyword);
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R userSettingDetail(Integer userSettingId) {
		if (userSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		return R.data(zeroUserSettingMapper.selectByPrimaryKey(userSettingId));
	}

	@Override
	public R userSettingDeleted(Integer userSettingId) {
		if (userSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		zeroUserSettingMapper.deleteByPrimaryKey(userSettingId);
		return R.success();
	}

	@Override
	public R postSettingSave(ZeroPostSetting zeroPostSetting) {
		if (StringUtils.isEmpty(zeroPostSetting.getPostCode())) {
			throw new ZeroException("岗位编号不能为空！");
		}
		if (StringUtils.isEmpty(zeroPostSetting.getPostName())) {
			throw new ZeroException("岗位名称不能为空！");
		}
//		if (zeroPostSetting.getCategory() == null){
//			throw new ZeroException("岗位类型不能为空！");
//		}
		if (zeroPostSetting.getSort() == null) {
			throw new ZeroException("排序不能为空！");
		}
		zeroPostSettingMapper.insertSelective(zeroPostSetting);
		return R.success();
	}

	@Override
	public R postSettingEdit(ZeroPostSetting zeroPostSetting) {
		if (zeroPostSetting.getId() == null) {
			throw new ZeroException("id不能为空！");
		}
		if (StringUtils.isEmpty(zeroPostSetting.getPostCode())) {
			throw new ZeroException("岗位编号不能为空！");
		}
		if (StringUtils.isEmpty(zeroPostSetting.getPostName())) {
			throw new ZeroException("岗位名称不能为空！");
		}
//		if (zeroPostSetting.getCategory() == null){
//			throw new ZeroException("岗位类型不能为空！");
//		}
		if (zeroPostSetting.getSort() == null) {
			throw new ZeroException("排序不能为空！");
		}
		zeroPostSettingMapper.updateByPrimaryKeySelective(zeroPostSetting);
		return R.success();
	}

	@Override
	public R postSettingQueryPage(IPage page, String keyword) {
		List<ZeroPostSetting> list = zeroPostSettingMapper.postSettingQueryPage(page, keyword);
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R postSettingDetail(Integer postSettingId) {
		if (postSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		return R.data(zeroPostSettingMapper.selectByPrimaryKey(postSettingId));
	}

	@Override
	public R postSettingDeleted(Integer postSettingId) {
		if (postSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		zeroPostSettingMapper.deleteByPrimaryKey(postSettingId);
		return R.success();
	}

	@Override
	public R deptSettingSave(ZeroDeptSetting zeroDeptSetting) {
		if (StringUtils.isEmpty(zeroDeptSetting.getDeptName())) {
			throw new ZeroException("部门名称不能为空！");
		}
		if (StringUtils.isEmpty(zeroDeptSetting.getFullName())) {
			throw new ZeroException("部门全称不能为空！");
		}
		if (zeroDeptSetting.getSort() == null) {
			throw new ZeroException("部门排序不能为空！");
		}
		zeroDeptSettingMapper.insertSelective(zeroDeptSetting);
		return R.success();
	}

	@Override
	public R deptSettingEdit(ZeroDeptSetting zeroDeptSetting) {
		if (zeroDeptSetting.getId() == null) {
			throw new ZeroException("部门id不能为空！");
		}
		if (StringUtils.isEmpty(zeroDeptSetting.getDeptName())) {
			throw new ZeroException("部门名称不能为空！");
		}
		if (StringUtils.isEmpty(zeroDeptSetting.getFullName())) {
			throw new ZeroException("部门全称不能为空！");
		}
		if (zeroDeptSetting.getSort() == null) {
			throw new ZeroException("部门排序不能为空！");
		}
		zeroDeptSettingMapper.updateByPrimaryKeySelective(zeroDeptSetting);
		return R.success();
	}

	@Override
	public R deptSettingQueryPage(IPage page, String keyword) {
		List<ZeroDeptSetting> list = zeroDeptSettingMapper.deptSettingQueryPage(page, keyword);
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R deptSettingDetail(Integer deptSettingId) {
		if (deptSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		return R.data(zeroDeptSettingMapper.selectByPrimaryKey(deptSettingId));
	}

	@Override
	public R deptSettingDeleted(Integer deptSettingId) {
		if (deptSettingId == null) {
			throw new ZeroException("id不能为空！");
		}
		zeroDeptSettingMapper.deleteByPrimaryKey(deptSettingId);
		return R.success();
	}

	/**
	 * 参数校验
	 *
	 * @param tenantSetting
	 */
	private void checkSubmitParam(TenantSettingDTO tenantSetting) {
		if (StringUtils.isEmpty(tenantSetting.getTenantId())) {
			throw new ZeroException("租户ID不能为空");
		}
		if (tenantSetting.getPostSettingId() == null) {
			throw new ZeroException("岗位ID不能为空");
		}
		if (tenantSetting.getRoleSettingId() == null) {
			throw new ZeroException("角色ID不能为空");
		}
		if (tenantSetting.getUserSettingId() == null) {
			throw new ZeroException("账号ID不能为空");
		}
		if (CollectionUtil.isEmpty(tenantSetting.getMenuList())) {
			throw new ZeroException("菜单ID不能为空");
		}
		if (tenantSetting.getTenantName() == null) {
			throw new ZeroException("机构名称不能为空");
		}

		Tenant tenant = tenantMapper.selectByTenantId(tenantSetting.getTenantId());
		if (tenant != null) {
			throw new ZeroException("租户ID已存在");
		}
	}

	private List<DictBiz> getDictBizs(String tenantId, LinkedList<DictBiz> dictBizs) {
		List<DictBiz> dictBizList = dictBizService.list(Wrappers.<DictBiz>query().lambda().eq(DictBiz::getParentId, ZeroConstant.TOP_PARENT_ID).eq(DictBiz::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
		dictBizList.forEach(dictBiz -> {
			Long oldParentId = dictBiz.getId();
			Long newParentId = IdWorker.getId();
			dictBiz.setId(newParentId);
			dictBiz.setTenantId(tenantId);
			dictBizs.add(dictBiz);
			recursionDictBiz(tenantId, oldParentId, newParentId, dictBizs);
		});
		return dictBizs;
	}

	private void recursionDictBiz(String tenantId, Long oldParentId, Long newParentId, LinkedList<DictBiz> dictBizs) {
		List<DictBiz> dictBizList = dictBizService.list(Wrappers.<DictBiz>query().lambda().eq(DictBiz::getParentId, oldParentId).eq(DictBiz::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
		dictBizList.forEach(dictBiz -> {
			Long oldSubParentId = dictBiz.getId();
			Long newSubParentId = IdWorker.getId();
			dictBiz.setId(newSubParentId);
			dictBiz.setTenantId(tenantId);
			dictBiz.setParentId(newParentId);
			dictBizs.add(dictBiz);
			recursionDictBiz(tenantId, oldSubParentId, newSubParentId, dictBizs);
		});
	}
}

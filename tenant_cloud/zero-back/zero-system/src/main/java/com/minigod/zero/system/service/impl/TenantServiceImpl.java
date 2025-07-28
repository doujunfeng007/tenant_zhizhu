
package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.system.cache.ParamCache;
import com.minigod.zero.system.entity.*;
import com.minigod.zero.system.mapper.TenantMapper;
import com.minigod.zero.system.service.*;
import com.minigod.zero.common.constant.TenantConstant;
import com.minigod.zero.core.cache.constant.CacheConstant;
import com.minigod.zero.system.entity.*;
import com.minigod.zero.system.service.*;
import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.user.enums.SourceType;
import com.minigod.zero.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tenant.TenantId;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.DesUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements ITenantService {

	private final TenantId tenantId;
	private final IRoleService roleService;
	private final IMenuService menuService;
	private final IDeptService deptService;
	private final IPostService postService;
	private final IRoleMenuService roleMenuService;
	private final IDictBizService dictBizService;
	private final ITenantPackageService tenantPackageService;
	private final IUserClient userClient;
	private final IFuncConfigService funcConfigService;

	@Override
	public IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant) {
		return page.setRecords(baseMapper.selectTenantPage(page, tenant));
	}

	@Override
	public Tenant getByTenantId(String tenantId) {
		return getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantId, tenantId));
	}

	@Override
	public Tenant getByTenantDomain(String tenantId, String domain) {
		return getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantId, tenantId).eq(Tenant::getDomainUrl, domain));
	}

	@Override
	public Tenant getByTenantDomain(String domain) {
		log.info("getByTenantDomain: domain={}", domain);
		return getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getDomainUrl, domain));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submitTenant(Tenant tenant) {
		if (Func.isEmpty(tenant.getId())) {
			List<Tenant> tenants = baseMapper.selectList(Wrappers.<Tenant>query().lambda().eq(Tenant::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
			List<String> codes = tenants.stream().map(Tenant::getTenantId).collect(Collectors.toList());
			String tenantId = getTenantId(codes);
			tenant.setTenantId(tenantId);
			// 获取参数配置的账号额度
			int accountNumber = Func.toInt(ParamCache.getValue(TenantConstant.ACCOUNT_NUMBER_KEY), TenantConstant.DEFAULT_ACCOUNT_NUMBER);
			tenant.setAccountNumber(accountNumber);
			// 新建租户对应的默认角色
			Role role = new Role();
			role.setTenantId(tenantId);
			role.setParentId(ZeroConstant.TOP_PARENT_ID);
			role.setRoleName("管理员");
			role.setRoleAlias("admin");
			role.setSort(2);
			role.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
			roleService.save(role);
			// 新建租户对应的角色菜单权限
			LinkedList<Menu> userMenus = new LinkedList<>();
			// 获取参数配置的默认菜单集合，逗号隔开
			List<String> menuCodes = Func.toStrList(ParamCache.getValue(TenantConstant.ACCOUNT_MENU_CODE_KEY));
			List<Menu> menus = getMenus((menuCodes.size() > 0 ? menuCodes : TenantConstant.MENU_CODES), userMenus);
			List<RoleMenu> roleMenus = new ArrayList<>();
			menus.forEach(menu -> {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenuId(menu.getId());
				roleMenu.setRoleId(role.getId());
				roleMenus.add(roleMenu);
			});
			roleMenuService.saveBatch(roleMenus);
			// 新建租户对应的默认部门
			Dept dept = new Dept();
			dept.setTenantId(tenantId);
			dept.setParentId(ZeroConstant.TOP_PARENT_ID);
			dept.setAncestors(String.valueOf(ZeroConstant.TOP_PARENT_ID));
			dept.setDeptName(tenant.getTenantName());
			dept.setFullName(tenant.getTenantName());
			dept.setDeptCategory(1);
			dept.setSort(2);
			dept.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
			deptService.save(dept);
			// 新建租户对应的默认岗位
			Post post = new Post();
			post.setTenantId(tenantId);
			post.setCategory(1);
			post.setPostCode("ceo");
			post.setPostName("首席执行官");
			post.setSort(1);
			postService.save(post);

			Post first_audit = new Post();
			first_audit.setTenantId(tenantId);
			first_audit.setCategory(1);
			first_audit.setPostCode("first_audit");
			first_audit.setPostName("初审岗");
			first_audit.setSort(2);
			postService.save(post);

			Post review_audit = new Post();
			review_audit.setTenantId(tenantId);
			review_audit.setCategory(1);
			review_audit.setPostCode("review_audit");
			review_audit.setPostName("复审岗");
			review_audit.setSort(3);
			postService.save(review_audit);

			Post risk = new Post();
			risk.setTenantId(tenantId);
			risk.setCategory(1);
			risk.setPostCode("risk");
			risk.setPostName("风控岗");
			risk.setSort(4);
			postService.save(risk);

			Post compliance = new Post();
			compliance.setTenantId(tenantId);
			compliance.setCategory(1);
			compliance.setPostCode("compliance");
			compliance.setPostName("合规岗");
			compliance.setSort(5);
			postService.save(compliance);

			Post finance = new Post();
			finance.setTenantId(tenantId);
			finance.setCategory(1);
			finance.setPostCode("finance");
			finance.setPostName("财务岗");
			finance.setSort(5);
			postService.save(finance);

			Post settle = new Post();
			settle.setTenantId(tenantId);
			settle.setCategory(1);
			settle.setPostCode("settle");
			settle.setPostName("结算岗");
			settle.setSort(5);
			postService.save(settle);

			Post service = new Post();
			service.setTenantId(tenantId);
			service.setCategory(1);
			service.setPostCode("service");
			service.setPostName("客服岗");
			service.setSort(5);
			postService.save(service);

			// 新建租户对应的默认业务字典
			LinkedList<DictBiz> dictBizs = new LinkedList<>();
			List<DictBiz> dictBizList = getDictBizs(tenantId, dictBizs);
			dictBizService.saveBatch(dictBizList);
			// 新建租户对应的默认管理用户
			User user = new User();
			user.setTenantId(tenantId);
			user.setName("admin");
			user.setRealName("admin");
			user.setAccount("admin");
			// 获取参数配置的密码
			String password = Func.toStr(ParamCache.getValue(TenantConstant.PASSWORD_KEY), TenantConstant.DEFAULT_PASSWORD);
			user.setPassword(password);
			user.setRoleId(String.valueOf(role.getId()));
			user.setDeptId(String.valueOf(dept.getId()));
			user.setPostId(String.valueOf(service.getId()));
			user.setBirthday(new Date());
			user.setSex(1);
			user.setUserType(SourceType.WEB.getCategory());
			user.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
			boolean temp = super.saveOrUpdate(tenant);
			R<Boolean> result = userClient.saveUser(user);
			if (!result.isSuccess()) {
				throw new ServiceException(result.getMsg());
			}
			return temp;
		} else {
			CacheUtil.clear(CacheConstant.SYS_CACHE, tenant.getTenantId());
			return super.saveOrUpdate(tenant);
		}
	}

	private List<FuncConfig> getFuncConfigs(String tenantId) {
		LinkedList<FuncConfig> funcConfigs = new LinkedList<>();
		List<FuncConfig> funcConfigList = funcConfigService.list(Wrappers.<FuncConfig>query().lambda().eq(FuncConfig::getParentId, ZeroConstant.TOP_PARENT_ID).eq(FuncConfig::getTenantId, ZeroConstant.ADMIN_TENANT_ID).eq(FuncConfig::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
		funcConfigList.forEach(funcConfig -> {
			Long oldParentId = funcConfig.getId();
			Long newParentId = IdWorker.getId();
			funcConfig.setId(newParentId);
			funcConfig.setTenantId(tenantId);
			funcConfigs.add(funcConfig);
			recursionFuncConfig(tenantId, oldParentId, newParentId, funcConfigs);
		});
		return funcConfigs;
	}

	private void recursionFuncConfig(String tenantId, Long oldParentId, Long newParentId, LinkedList<FuncConfig> funcConfigs) {
		List<FuncConfig> funcConfigList = funcConfigService.list(Wrappers.<FuncConfig>query().lambda().eq(FuncConfig::getParentId, oldParentId).eq(FuncConfig::getTenantId, ZeroConstant.ADMIN_TENANT_ID).eq(FuncConfig::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
		funcConfigList.forEach(funcConfig -> {
			Long oldSubParentId = funcConfig.getId();
			Long newSubParentId = IdWorker.getId();
			funcConfig.setId(newSubParentId);
			funcConfig.setTenantId(tenantId);
			funcConfig.setParentId(newParentId);
			funcConfigs.add(funcConfig);
			recursionFuncConfig(tenantId, oldSubParentId, newSubParentId, funcConfigs);
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeTenant(List<Long> ids) {
		List<String> tenantIds = this.list(Wrappers.<Tenant>query().lambda().in(Tenant::getId, ids))
			.stream().map(tenant -> Func.toStr(tenant.getTenantId())).distinct().collect(Collectors.toList());
		CacheUtil.clear(CacheConstant.SYS_CACHE, tenantIds);
		if (tenantIds.contains(ZeroConstant.ADMIN_TENANT_ID)) {
			throw new ServiceException("不可删除管理租户!");
		}
		boolean tenantTemp = this.deleteLogic(ids);
		R<Boolean> result = userClient.removeUser(StringUtil.join(tenantIds));
		if (!result.isSuccess()) {
			throw new ServiceException(result.getMsg());
		}
		return tenantTemp;
	}

	@Override
	public boolean setting(Integer accountNumber, Date expireTime, String ids) {
		List<String> tenantIds = this.list(Wrappers.<Tenant>query().lambda().in(Tenant::getId, ids))
			.stream().map(tenant -> Func.toStr(tenant.getTenantId())).distinct().collect(Collectors.toList());
		CacheUtil.clear(CacheConstant.SYS_CACHE, tenantIds);
		Func.toLongList(ids).forEach(id -> {
			Kv kv = Kv.create().set("accountNumber", accountNumber).set("expireTime", expireTime).set("id", id);
			String licenseKey = DesUtil.encryptToHex(JsonUtil.toJson(kv), TenantConstant.DES_KEY);
			update(
				Wrappers.<Tenant>update().lambda()
					.set(Tenant::getAccountNumber, accountNumber)
					.set(Tenant::getExpireTime, expireTime)
					.set(Tenant::getLicenseKey, licenseKey)
					.eq(Tenant::getId, id)
			);
		});
		return true;
	}

	@Override
	public TenantPackage getPackage(String tenantId) {
		Tenant tenant = this.getByTenantId(tenantId);
		return Optional.of(tenant)
			.filter(e -> Objects.nonNull(tenant.getPackageId()))
			.map(e -> tenantPackageService.getById(e.getPackageId()))
			.orElse(null);
	}

	private String getTenantId(List<String> codes) {
		String code = tenantId.generate();
		if (codes.contains(code)) {
			return getTenantId(codes);
		}
		return code;
	}

	private List<Menu> getMenus(List<String> codes, LinkedList<Menu> menus) {
		codes.forEach(code -> {
			Menu menu = menuService.getOne(Wrappers.<Menu>query().lambda().eq(Menu::getCode, code).eq(Menu::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
			if (menu != null) {
				menus.add(menu);
				recursionMenu(menu.getId(), menus);
			}
		});
		return menus;
	}

	private void recursionMenu(Long parentId, LinkedList<Menu> menus) {
		List<Menu> menuList = menuService.list(Wrappers.<Menu>query().lambda().eq(Menu::getParentId, parentId).eq(Menu::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
		menus.addAll(menuList);
		menuList.forEach(menu -> recursionMenu(menu.getId(), menus));
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

	@Override
	public List<Tenant> getListByTenantIds(List<String> tenantIds) {
		return baseMapper.selectList(Wrappers.<Tenant>query().lambda().in(Tenant::getTenantId, tenantIds));
	}

	@Override
	public Tenant getByTenantName(String tenantName) {
		return baseMapper.selectOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantName, tenantName));
	}

	@Override
	public List<Tenant> getLikeTenantName(String tenantName) {
		return baseMapper.selectList(Wrappers.<Tenant>query().lambda().like(Tenant::getTenantName, tenantName));
	}
}

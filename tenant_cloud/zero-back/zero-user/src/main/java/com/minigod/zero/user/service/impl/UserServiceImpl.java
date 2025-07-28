package com.minigod.zero.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.user.entity.*;
import com.minigod.zero.user.enums.SourceType;
import com.minigod.zero.user.excel.UserExcel;
import com.minigod.zero.user.utils.UserUtils;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.user.entity.*;
import com.minigod.zero.user.service.IUserOldPasswordService;
import lombok.AllArgsConstructor;
import com.minigod.zero.common.constant.CommonConstant;
import com.minigod.zero.common.constant.TenantConstant;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tenant.ZeroTenantProperties;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.*;
import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.cache.ParamCache;
import com.minigod.zero.system.cache.SysCache;
import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.system.enums.DictEnum;
import com.minigod.zero.system.feign.ISysClient;
import com.minigod.zero.user.cache.UserCache;
import com.minigod.zero.user.mapper.UserMapper;
import com.minigod.zero.user.service.IUserDeptService;
import com.minigod.zero.user.service.IUserOauthService;
import com.minigod.zero.user.service.IUserService;
import com.minigod.zero.user.vo.UserVO;
import com.minigod.zero.user.wrapper.UserWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	private static final String GUEST_NAME = "guest";

	private final IUserDeptService userDeptService;
	private final IUserOauthService userOauthService;
	private final ISysClient sysClient;
	private final ZeroTenantProperties tenantProperties;
	private final IUserOldPasswordService userOldPasswordService;
	private final ZeroRedis zeroRedis;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submit(User user) {
		if (StringUtil.isBlank(user.getTenantId())) {
			user.setTenantId(ZeroConstant.ADMIN_TENANT_ID);
		}
		String tenantId = user.getTenantId();
		Tenant tenant = SysCache.getTenant(tenantId);
		paramCheck(user);
		if (Func.isNotEmpty(tenant)) {
			Integer accountNumber = tenant.getAccountNumber();
			if (tenantProperties.getLicense()) {
				String licenseKey = tenant.getLicenseKey();
				String decrypt = DesUtil.decryptFormHex(licenseKey, TenantConstant.DES_KEY);
				accountNumber = JsonUtil.parse(decrypt, Tenant.class).getAccountNumber();
			}
			Long tenantCount = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId));
			if (accountNumber != null && accountNumber > 0 && accountNumber <= tenantCount) {
				throw new ServiceException("当前租户已到最大账号额度!");
			}
		}
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
			user.setUpdatePwd(1);
		}
		if (StringUtils.isBlank(user.getAccount())) {
			// 根据真实姓名自动生成拼音账号
			String account = UserUtils.generateAccount(user.getRealName());
			Integer count = baseMapper.getCounts(tenantId, account);
			if (count > 0) {
				account += String.format("%03d", count + 1);
			}
			user.setAccount(account);
		} else {
			Integer count = baseMapper.getCount(tenantId, user.getAccount());
			if (count > 0) {
				throw new ServiceException(StringUtil.format("登录账号 [{}] 已存在!", user.getAccount()));
			}
		}
		if(StringUtils.isBlank(user.getName())){
			user.setName(user.getRealName());
		}
		return save(user) && submitUserDept(user);
	}


	private void paramCheck(User user){
		String phone = user.getPhone();
		if (StringUtil.isNotBlank(phone)){
			User oldUser = baseMapper.selectByPhone(user.getTenantId(),phone,user.getArea());
			if (oldUser != null&& !oldUser.getId().equals(user.getId())){
				throw new ZeroException("手机号已被使用");
			}
		}
		String email = user.getEmail();
		if (StringUtil.isNotBlank(email)){
			User oldUser = baseMapper.selectByEmail(user.getTenantId(),email);
			if (oldUser != null && !oldUser.getId().equals(user.getId())){
				throw new ZeroException("邮箱已被使用");
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUser(User user) {
		String tenantId = user.getTenantId();
		paramCheck(user);
		Long userCount = baseMapper.selectCount(
			Wrappers.<User>query().lambda()
				.eq(User::getTenantId, tenantId)
				.eq(User::getAccount, user.getAccount())
				.notIn(User::getId, user.getId())
		);
		if (userCount > 0L) {
			throw new ServiceException(StringUtil.format("当前用户 [{}] 已存在!", user.getAccount()));
		}
		return updateUserInfo(user) && submitUserDept(user);
	}

	@Override
	public boolean updateUserInfo(User user) {
		user.setPassword(null);
		return updateById(user);
	}

	private boolean submitUserDept(User user) {
		List<Long> deptIdList = Func.toLongList(user.getDeptId());
		List<UserDept> userDeptList = new ArrayList<>();
		deptIdList.forEach(deptId -> {
			UserDept userDept = new UserDept();
			userDept.setUserId(user.getId());
			userDept.setDeptId(deptId);
			userDeptList.add(userDept);
		});
		userDeptService.remove(Wrappers.<UserDept>update().lambda().eq(UserDept::getUserId, user.getId()));
		return userDeptService.saveBatch(userDeptList);
	}

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId) {
		List<Long> deptIdList = SysCache.getDeptChildIds(deptId);
		return page.setRecords(baseMapper.selectUserPage(page, user, deptIdList, tenantId));
	}

	@Override
	public IPage<UserVO> selectUserSearch(UserVO user, Query query) {
		LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>query().lambda();
		String tenantId = AuthUtil.getTenantId();
		if (StringUtil.isNotBlank(tenantId)) {
			queryWrapper.eq(User::getTenantId, tenantId);
		}
		if (StringUtil.isNotBlank(user.getName())) {
			queryWrapper.like(User::getName, user.getName());
		}
		if (StringUtil.isNotBlank(user.getDeptName())) {
			String deptIds = SysCache.getDeptIdsByFuzzy(AuthUtil.getTenantId(), user.getDeptName());
			if (StringUtil.isNotBlank(deptIds)) {
				queryWrapper.and(wrapper -> {
					List<String> ids = Func.toStrList(deptIds);
					ids.forEach(id -> wrapper.like(User::getDeptId, id).or());
				});
			}
		}
		if (StringUtil.isNotBlank(user.getPostName())) {
			String postIds = SysCache.getPostIdsByFuzzy(AuthUtil.getTenantId(), user.getPostName());
			if (StringUtil.isNotBlank(postIds)) {
				queryWrapper.and(wrapper -> {
					List<String> ids = Func.toStrList(postIds);
					ids.forEach(id -> wrapper.like(User::getPostId, id).or());
				});
			}
		}
		IPage<User> pages = this.page(Condition.getPage(query), queryWrapper);
		return UserWrapper.build().pageVO(pages);
	}

	@Override
	public User userByAccount(String tenantId, String account) {
		return baseMapper.selectOne(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId).eq(User::getAccount, account).eq(User::getIsDeleted, ZeroConstant.DB_NOT_DELETED));
	}

	@Override
	public UserInfo userInfo(Long userId) {
		User user = baseMapper.selectById(userId);
		return buildUserInfo(user);
	}

	@Override
	public UserInfo userInfo(String tenantId, String username, SourceType sourceType) {
		User user = baseMapper.getUser(tenantId, username, sourceType.getCategory());
		return buildUserInfo(user);
	}

	private UserInfo buildUserInfo(User user) {
		if (ObjectUtil.isEmpty(user)) {
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			R<List<String>> result = sysClient.getRoleAliases(user.getRoleId());
			if (result.isSuccess()) {
				List<String> roleAlias = result.getData();
				userInfo.setRoles(roleAlias);
			}
		}
		// 根据每个用户平台，建立对应的detail表，通过查询将结果集写入到detail字段
		Kv detail = Kv.create();
		UserWeb userWeb = new UserWeb();
		UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, user.getId()));
		if (ObjectUtil.isNotEmpty(query)) {
			detail.set("ext", query.getUserExt());
		}
		userInfo.setDetail(detail);
		return userInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserInfo userInfo(UserOauth userOauth) {
		UserOauth uo = userOauthService.getOne(Wrappers.<UserOauth>query().lambda().eq(UserOauth::getUuid, userOauth.getUuid()).eq(UserOauth::getSource, userOauth.getSource()));
		UserInfo userInfo;
		if (Func.isNotEmpty(uo) && Func.isNotEmpty(uo.getUserId())) {
			userInfo = this.userInfo(uo.getUserId());
			userInfo.setOauthId(Func.toStr(uo.getId()));
		} else {
			userInfo = new UserInfo();
			if (Func.isEmpty(uo)) {
				userOauthService.save(userOauth);
				userInfo.setOauthId(Func.toStr(userOauth.getId()));
			} else {
				userInfo.setOauthId(Func.toStr(uo.getId()));
			}
			User user = new User();
			user.setAccount(userOauth.getUsername());
			user.setTenantId(userOauth.getTenantId());
			userInfo.setUser(user);
			userInfo.setRoles(Collections.singletonList(GUEST_NAME));
		}
		return userInfo;
	}

	@Override
	public User userInfoByCustId(Long custId) {
		return this.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getCustId, custId));
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		User user = new User();
		user.setRoleId(roleIds);
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
	}

	@Override
	public boolean resetPassword(String userIds) {
		User user = new User();
		user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		user.setUpdateTime(DateUtil.now());
		user.setUpdatePwd(1);
		boolean update = this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
		if(update){
			for (Long userId :  Func.toLongList(userIds)) {
				user = baseMapper.selectById(userId);
				//重置密码，清空账号错误次数
				zeroRedis.del(CacheNames.tenantKey(user.getTenantId(), CacheNames.USER_FAIL_KEY, user.getAccount()));
			}
		}
		return update;
	}

	@Override
	public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
		User user = getById(userId);
		/*if (!newPassword.equals(newPassword1)) {
			throw new ServiceException("请输入正确的确认密码!");
		}*/
		if (!user.getPassword().equals(DigestUtil.hex(oldPassword))) {
			throw new ServiceException("原密码不正确!");
		}
		return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.hex(newPassword)).set(User::getUpdatePwd,0).eq(User::getId, userId));
	}

	@Override
	public boolean removeUser(String userIds) {
		if (Func.contains(Func.toLongArray(userIds), AuthUtil.getUserId())) {
			throw new ServiceException("不能删除本账号!");
		}
		return deleteLogic(Func.toLongList(userIds));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importUser(List<UserExcel> data, Boolean isCovered) {
		data.forEach(userExcel -> {
			User user = Objects.requireNonNull(BeanUtil.copy(userExcel, User.class));
			// 设置用户平台
			user.setUserType(Func.toInt(DictCache.getKey(DictEnum.USER_TYPE, userExcel.getUserTypeName()), 1));
			// 设置部门ID
			user.setDeptId(Func.toStrWithEmpty(SysCache.getDeptIds(userExcel.getTenantId(), userExcel.getDeptName()), StringPool.EMPTY));
			// 设置岗位ID
			user.setPostId(Func.toStrWithEmpty(SysCache.getPostIds(userExcel.getTenantId(), userExcel.getPostName()), StringPool.EMPTY));
			// 设置角色ID
			user.setRoleId(Func.toStrWithEmpty(SysCache.getRoleIds(userExcel.getTenantId(), userExcel.getRoleName()), StringPool.EMPTY));
			// 设置租户ID
			if (!AuthUtil.isAdministrator() || StringUtil.isBlank(user.getTenantId())) {
				user.setTenantId(AuthUtil.getTenantId());
			}
			// 覆盖数据
			if (isCovered) {
				// 查询用户是否存在
				User oldUser = UserCache.getUser(userExcel.getTenantId(), userExcel.getAccount());
				if (oldUser != null && oldUser.getId() != null) {
					user.setId(oldUser.getId());
					this.updateUser(user);
					return;
				}
			}
			// 获取默认密码配置
			String initPassword = ParamCache.getValue(CommonConstant.DEFAULT_PARAM_PASSWORD);
			user.setPassword(initPassword);
			this.submit(user);
		});
	}

	@Override
	public List<UserExcel> exportUser(Wrapper<User> queryWrapper) {
		List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
		userList.forEach(user -> {
			user.setUserTypeName(DictCache.getValue(DictEnum.USER_TYPE, user.getUserType()));
			user.setRoleName(StringUtil.join(SysCache.getRoleNames(user.getRoleId())));
			user.setDeptName(StringUtil.join(SysCache.getDeptNames(user.getDeptId())));
			user.setPostName(StringUtil.join(SysCache.getPostNames(user.getPostId())));
		});
		return userList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerGuest(User user, Long oauthId) {
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		if (tenant == null || tenant.getId() == null) {
			throw new ServiceException("租户信息错误!");
		}
		UserOauth userOauth = userOauthService.getById(oauthId);
		if (userOauth == null || userOauth.getId() == null) {
			throw new ServiceException("第三方登陆信息错误!");
		}
		user.setRealName(user.getName());
		user.setAvatar(userOauth.getAvatar());
		user.setRoleId(StringPool.MINUS_ONE);
		user.setDeptId(StringPool.MINUS_ONE);
		user.setPostId(StringPool.MINUS_ONE);
		boolean userTemp = this.submit(user);
		userOauth.setUserId(user.getId());
		userOauth.setTenantId(user.getTenantId());
		boolean oauthTemp = userOauthService.updateById(userOauth);
		return (userTemp && oauthTemp);
	}
	@Override
	public boolean updatePlatform(Long userId, String userExt) {
		UserWeb userWeb = new UserWeb();
		UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, userId));
		if (ObjectUtil.isNotEmpty(query)) {
			userWeb.setId(query.getId());
		}
		userWeb.setUserId(userId);
		userWeb.setUserExt(userExt);
		return userWeb.insertOrUpdate();
	}

	@Override
	public UserVO platformDetail(User user) {
		User detail = baseMapper.selectOne(Condition.getQueryWrapper(user));
		UserVO userVO = UserWrapper.build().entityVO(detail);
		UserWeb userWeb = new UserWeb();
		UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, user.getId()));
		if (ObjectUtil.isNotEmpty(query)) {
			userVO.setUserExt(query.getUserExt());
		}
		return userVO;
	}

	@Override
	public List<User> userInfoByName(String name) {
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getName, name);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<User> userInfoByRealName(String realName) {
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getRealName, realName);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public boolean checkOldLoginPwd(String account, String password) {
		boolean check = userOldPasswordService.checkOldLoginPwd(account, password);
		if (check) {
			User user = new User();
			user.setPassword(DigestUtil.hex(password));
			user.setUpdateTime(new Date());
			baseMapper.update(user, Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
		}
		return check;
	}

	@Override
	public void updatePwdByCustId(Long custId,String password) {
		if (null==custId){
			return;
		}
		User user = this.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getCustId, custId));
		//不管账号状态如何,都给予更新
		if (null!=user){
			user.setPassword(DigestUtil.hex(RSANewUtil.decrypt(password)));
			user.setUpdateTime(DateUtil.now());
			user.setUpdatePwd(0);
			this.getBaseMapper().updateById(user);
			if (StringUtil.isNotBlank(user.getAccount())){
				userOldPasswordService.activeLoginPwd(user.getAccount());
			}
		}
	}

	@Override
	public UserInfo selectUserByPhone(String tenantId, String phone,String areaCode) {
		User user = baseMapper.selectByPhone(tenantId,phone,areaCode);
		if (user == null){
			return null;
		}
		UserInfo userInfo = buildUserInfo(user);
		return userInfo;
	}

	@Override
	public UserInfo selectUserByEmail(String tenantId, String email) {
		User user = baseMapper.selectByEmail(tenantId,email);
		if (user == null){
			return null;
		}
		UserInfo userInfo = buildUserInfo(user);
		return userInfo;
	}
}

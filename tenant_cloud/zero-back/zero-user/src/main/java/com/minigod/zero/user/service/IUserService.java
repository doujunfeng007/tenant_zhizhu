
package com.minigod.zero.user.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.entity.UserInfo;
import com.minigod.zero.user.entity.UserOauth;
import com.minigod.zero.user.enums.SourceType;
import com.minigod.zero.user.excel.UserExcel;
import com.minigod.zero.user.vo.UserVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.mp.support.Query;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增用户
	 *
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 修改用户
	 *
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	/**
	 * 修改用户基本信息
	 *
	 * @param user
	 * @return
	 */
	boolean updateUserInfo(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @param deptId
	 * @param tenantId
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId);

	/**
	 * 自定义分页
	 *
	 * @param user
	 * @param query
	 * @return
	 */
	IPage<UserVO> selectUserSearch(UserVO user, Query query);


	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param username
	 * @param sourceType
	 * @return
	 */
	UserInfo userInfo(String tenantId, String username, SourceType sourceType);

	/**
	 * 用户信息
	 *
	 * @param userOauth
	 * @return
	 */
	UserInfo userInfo(UserOauth userOauth);


	/**
	 * 用户信息
	 *
	 * @param custId
	 * @return
	 */
	User userInfoByCustId(Long custId);

	/**
	 * 根据账号获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User userByAccount(String tenantId, String account);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword);

	/**
	 * 删除用户
	 *
	 * @param userIds
	 * @return
	 */
	boolean removeUser(String userIds);

	/**
	 * 导入用户数据
	 *
	 * @param data
	 * @param isCovered
	 * @return
	 */
	void importUser(List<UserExcel> data, Boolean isCovered);

	/**
	 * 导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * 注册用户
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 配置用户平台
	 *
	 * @param userId
	 * @param userExt
	 * @return
	 */
	boolean updatePlatform(Long userId, String userExt);

	/**
	 * 用户详细信息
	 *
	 * @param user
	 * @return
	 */
	UserVO platformDetail(User user);

	/**
	 * 通过昵称查询用户信息
	 * @param name
	 * @return
	 */
	List<User> userInfoByName(String name);

	/**
	 * 通过真名查询用户信息
	 * @param name
	 * @return
	 */
	List<User> userInfoByRealName(String name);

	/**
	 * 校验用户旧密码
	 * @param account 用户ID
	 * @param password 登录密码
	 * @return
	 */
	boolean checkOldLoginPwd(String account, String password);

	/**
	 * 通过custId更新密码
	 * @param custId
	 * @return
	 */
	void updatePwdByCustId(Long custId,String password);

	UserInfo  selectUserByPhone(String tenantId,String phone,String areaCode);

	UserInfo  selectUserByEmail(String tenantId,String email);
}

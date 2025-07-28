
package com.minigod.zero.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.excel.UserExcel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @param deptIdList
	 * @param tenantId
	 * @return
	 */
	List<User> selectUserPage(IPage<User> page, @Param("user") User user, @Param("deptIdList") List<Long> deptIdList, @Param("tenantId") String tenantId);

	/**
	 * 获取用户
	 *
	 * @param tenantId
	 * @param username
	 * @return
	 */
	User getUser(@Param("tenantId") String tenantId, @Param("username") String username, @Param("userType") int userType);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(@Param("ew") Wrapper<User> queryWrapper);

	Integer getCount(@Param("tenantId") String tenantId, @Param("account") String account);

	Integer getCounts(@Param("tenantId") String tenantId, @Param("account") String account);


	User selectByPhone(@Param("tenantId") String tenantId, @Param("phone") String phone, @Param("area") String areaCode);

	User selectByEmail(@Param("tenantId") String tenantId, @Param("email") String email);
}

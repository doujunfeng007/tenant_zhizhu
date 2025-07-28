package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.ZeroRoleSetting;
import com.minigod.zero.system.entity.ZeroUserSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【zero_role_setting(角色初始化设置表)】的数据库操作Mapper
* @createDate 2024-09-27 14:07:44
* @Entity com.minigod.zero.system.entity.ZeroRoleSetting
*/
public interface ZeroRoleSettingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ZeroRoleSetting record);

    int insertSelective(ZeroRoleSetting record);

    ZeroRoleSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZeroRoleSetting record);

    int updateByPrimaryKey(ZeroRoleSetting record);

	List<ZeroRoleSetting> roleQueryPage(IPage page, @Param("keyword") String keyword);

	List<ZeroRoleSetting> getAll();
}

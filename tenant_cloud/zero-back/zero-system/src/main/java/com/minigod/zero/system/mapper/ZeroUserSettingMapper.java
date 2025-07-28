package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.ZeroUserSetting;

import java.util.List;

/**
* @author dell
* @description 针对表【zero_user_setting(用户初始化设置表)】的数据库操作Mapper
* @createDate 2024-09-27 14:07:44
* @Entity com.minigod.zero.system.entity.ZeroUserSetting
*/
public interface ZeroUserSettingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ZeroUserSetting record);

    int insertSelective(ZeroUserSetting record);

    ZeroUserSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZeroUserSetting record);

    int updateByPrimaryKey(ZeroUserSetting record);

	List<ZeroUserSetting> userSettingQueryPage(IPage page, String keyword);

	List<ZeroUserSetting> getAll();

}

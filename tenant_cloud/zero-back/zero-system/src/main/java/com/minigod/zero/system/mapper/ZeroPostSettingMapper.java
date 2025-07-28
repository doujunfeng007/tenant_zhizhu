package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.ZeroPostSetting;
import com.minigod.zero.system.entity.ZeroRoleSetting;
import com.minigod.zero.system.entity.ZeroUserSetting;

import java.util.List;

/**
* @author dell
* @description 针对表【zero_post_setting(岗位初始化设置表)】的数据库操作Mapper
* @createDate 2024-09-27 14:07:44
* @Entity com.minigod.zero.system.entity.ZeroPostSetting
*/
public interface ZeroPostSettingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ZeroPostSetting record);

    int insertSelective(ZeroPostSetting record);

    ZeroPostSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZeroPostSetting record);

    int updateByPrimaryKey(ZeroPostSetting record);

	List<ZeroPostSetting> postSettingQueryPage(IPage page, String keyword);

	List<ZeroPostSetting> getAll();

}

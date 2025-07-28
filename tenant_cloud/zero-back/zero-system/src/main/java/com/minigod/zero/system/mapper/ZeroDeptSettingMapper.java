package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.ZeroDeptSetting;

import java.util.List;

/**
* @author dell
* @description 针对表【zero_dept_setting(机构初始化设置表)】的数据库操作Mapper
* @createDate 2024-09-27 16:51:29
* @Entity com.minigod.zero.system.entity.ZeroDeptSetting
*/
public interface ZeroDeptSettingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ZeroDeptSetting record);

    int insertSelective(ZeroDeptSetting record);

    ZeroDeptSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZeroDeptSetting record);

    int updateByPrimaryKey(ZeroDeptSetting record);

	List<ZeroDeptSetting> deptSettingQueryPage(IPage page, String keyword);

	List<ZeroDeptSetting> getAll();
}

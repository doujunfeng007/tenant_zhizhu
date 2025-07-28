package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.query.OrganizationOpenInfoQuery;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eric
 * @description 针对表【organization_open_info(机构开户申请提交表)】的数据库操作Mapper
 * @createDate 2024-05-31 13:47:16
 */
public interface OrganizationOpenInfoMapper extends BaseMapper<OrganizationOpenInfoEntity> {
	int deleteByPrimaryKey(Long id);

	int insertSelective(OrganizationOpenInfoEntity record);

	OrganizationOpenInfoEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrganizationOpenInfoEntity record);

	int updateByPrimaryKey(OrganizationOpenInfoEntity record);

	List<OrganizationOpenInfoEntity> selectByPage(IPage page,  @Param("query") OrganizationOpenInfoQuery query);
}

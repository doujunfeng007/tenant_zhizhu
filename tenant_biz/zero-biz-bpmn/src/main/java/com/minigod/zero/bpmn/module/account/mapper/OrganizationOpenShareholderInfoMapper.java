package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.query.OrganizationOpenShareholderInfoQuery;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenShareholderInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构开户董事、股东、授权签署信息持久层
 *
 * @author eric
 * @date 2024-07-15 17:20:18
 */
@Mapper
public interface OrganizationOpenShareholderInfoMapper extends BaseMapper<OrganizationOpenShareholderInfoEntity> {
	int deleteByPrimaryKey(Long id);
	int batchDeleteByPrimaryKey(List<Long> ids);

	int insertSelective(OrganizationOpenShareholderInfoEntity record);

	OrganizationOpenShareholderInfoEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrganizationOpenShareholderInfoEntity record);

	int updateByPrimaryKey(OrganizationOpenShareholderInfoEntity record);

	int updateByOpenInfoId(@Param("openInfoId") Long openInfoId, @Param("custId") Long custId);

	List<OrganizationOpenShareholderInfoEntity> selectByOpenInfoId(@Param("openInfoId") Long openInfoId);

	List<OrganizationOpenShareholderInfoEntity> selectByPage(IPage page, @Param("query") OrganizationOpenShareholderInfoQuery query);
}

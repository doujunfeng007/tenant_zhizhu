package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.vo.MsgTemplateVO;
import com.minigod.zero.manage.vo.request.PlatformTemplateQueryVo;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper接口
 *
 * @author minigod
 */
public interface PlatformCommonTemplateMapper extends BaseMapper<PlatformCommonTemplateEntity> {

	IPage<MsgTemplateVO> getTemplateList(@Param("page") IPage page, @Param("platformTemplateQueryVo") PlatformTemplateQueryVo platformTemplateQueryVo);

	List<PlatformCommonTemplateEntity> findTemplateByParentTypeId(Long parentTypeId);

	Integer findTempMaxTempCode();

	void update(PlatformCommonTemplateEntity entity);

	List<PlatformCommonTemplateEntity> queryTemplatePage(IPage page,
														 @Param("keyword")String  keyword,
														 @Param("msgType")Integer msgType,
														 @Param("tenantId")String tenantId);

	PlatformCommonTemplateEntity selectByTemplateCode(@Param("templateCode")Integer templateCode,
												  @Param("tenantId")String tenantId);


	List<PlatformCommonTemplateEntity> selectTemplateByMsgType(@Param("tenantId")String tenantId,@Param("msgType")Integer msgType);
}

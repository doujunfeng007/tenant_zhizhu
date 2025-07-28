package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SensitiveWordLogEntity;
import com.minigod.zero.manage.vo.request.SensitiveWordRequestVo;
import com.minigod.zero.manage.vo.response.SensitiveWordLogRespDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author 掌上智珠
 * @since 2023/7/25 9:57
 */
public interface SensitiveWordLogMapper extends BaseMapper<SensitiveWordLogEntity> {

	IPage<SensitiveWordLogRespDto> selectSensitiveWordLogePage(IPage page, @Param("params") SensitiveWordRequestVo sensitiveWordRequestVo);
}

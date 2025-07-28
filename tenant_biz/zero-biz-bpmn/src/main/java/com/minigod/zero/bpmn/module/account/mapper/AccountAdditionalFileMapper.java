package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountAdditionalFileMapper extends BaseMapper<AccountAdditionalFileEntity> {

    List<AccountAdditionalFileVO> queryListByApplicationId(@Param("applicationId") String applicationId, @Param("type") Integer type);

    Integer deleteWithValidByAdditionalIds(@Param("additionalIds") List<String> additionalIds);

    void deleteByApplicationAndType(@Param("applicationId") String applicationId, @Param("type") Integer type);

    void deleteByApplicationAndTypeAndFileType(@Param("applicationId") String applicationId, @Param("type") Integer type,@Param("fileType")Integer fileType);

	List<AccountAdditionalFileEntity> queryListByType(@Param("userId") Long userId, @Param("type") Integer type);
}

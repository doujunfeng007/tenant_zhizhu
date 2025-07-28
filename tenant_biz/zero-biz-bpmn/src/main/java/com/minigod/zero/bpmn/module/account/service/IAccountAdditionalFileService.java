package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.bo.OpenSignImgBo;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalFileVO;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountAdditionalFileService extends BaseService<AccountAdditionalFileEntity> {

    List<AccountAdditionalFileVO> queryListByApplicationId(String applicationId, Integer type, Long userId);

    String uploadAdditionalFile(String applicationId, Integer type, Integer fileType, String remark, MultipartFile file);

    void deleteByApplicationAndTypeAndFileType(String applicationId, Integer type, Integer fileType);

    void deleteByApplicationAndType(String applicationId, Integer type);

	List<AccountAdditionalFileEntity> queryListByType(Integer type);


}

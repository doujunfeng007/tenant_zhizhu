package com.minigod.zero.bpmn.module.pi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.pi.dto.ProfessionalInvestorPIImageDTO;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIVoucherImageEntity;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIVoucherImageVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 专业投资者PI申请凭证图片服务类
 *
 * @author eric
 * @since 2024-05-07 19:21:09
 */
public interface IProfessionalInvestorPIVoucherImageService extends IService<ProfessionalInvestorPIVoucherImageEntity> {

	/**
	 * 查询指定PI审请记录的凭证图片
	 *
	 * @param applicationId
	 * @param imageTypes
	 * @return
	 */
	List<ProfessionalInvestorPIVoucherImageVO> queryByApplicationId(String applicationId, List<Integer> imageTypes);

	/**
	 * 上传凭证图片
	 *
	 * @param type
	 * @param file
	 * @return
	 */
	R<Kv> uploadImage(Integer type, MultipartFile file);

	/**
	 * 上传凭证图片
	 *
	 * @param imageDTO
	 * @return
	 */
	R<Kv> uploadImage(ProfessionalInvestorPIImageDTO imageDTO);

	/**
	 * 查询指定Ids的凭证图片
	 *
	 * @param ids
	 * @return
	 */
	List<ProfessionalInvestorPIVoucherImageEntity> queryByIds(List<String> ids);

}

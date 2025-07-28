package com.minigod.zero.bpmn.module.deposit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.deposit.dto.BankCardImageDTO;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardImageEntity;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardImageVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BankCardImageService extends IService<BankCardImageEntity> {
	/**
	 * 批量插入凭证图片
	 *
	 * @param list
	 * @return
	 */
	boolean batchInsert(List<BankCardImageEntity> list);

	/**
	 * 根据id集合查询凭证图片
	 *
	 * @param ids
	 * @return
	 */
	List<BankCardImageEntity> queryByIds(List<String> ids);

	/**
	 * 查询指定PI审请记录的凭证图片
	 *
	 * @param applicationId
	 * @return
	 */
	List<BankCardImageVO> queryByApplicationId(String applicationId);

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
	R<Kv> uploadImage(BankCardImageDTO imageDTO);
}

package com.minigod.zero.bpmn.module.deposit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.deposit.bo.SecImgBo;
import com.minigod.zero.bpmn.module.deposit.entity.SecAccImgEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecAccImgVO;
import com.minigod.zero.bpmn.module.deposit.vo.SecImgRespVo;

/**
 * 存取资金图片表 服务类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface ISecAccImgService extends IService<SecAccImgEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secAccImgVO
	 * @return
	 */
	IPage<SecAccImgVO> selectSecAccImgPage(IPage<SecAccImgVO> page, SecAccImgVO secAccImgVO);


    SecImgRespVo saveSecAccImg(SecImgBo params);

	List<SecImgRespVo> findSecAccImg(SecImgBo params);

	List<SecAccImgEntity> findSecAccImg(Long transactId);

	/**
	 * 更新 transactId
	 * @param transactId
	 * @param ids
	 */
	void batchUpdateImgTransactId(Long transactId,List<Long> ids);

	SecImgRespVo saveSecAccImg(MultipartFile multipartFile, Long custId);
}

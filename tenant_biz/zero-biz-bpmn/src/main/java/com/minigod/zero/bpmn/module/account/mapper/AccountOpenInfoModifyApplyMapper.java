package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpmn.module.account.dto.AccountOpenInfoModifyApplyPageDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoModifyApplyEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyApplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户开户资料修改申请记录表
 *
 * @author eric
 * @since 2024-08-05 10:57:02
 */
public interface AccountOpenInfoModifyApplyMapper extends BaseMapper<AccountOpenInfoModifyApplyEntity> {
	List<AccountOpenInfoModifyApplyVO> getOpenInfoModifyPge(Page page, @Param("applyPageDTO") AccountOpenInfoModifyApplyPageDTO applyPageDTO);
}

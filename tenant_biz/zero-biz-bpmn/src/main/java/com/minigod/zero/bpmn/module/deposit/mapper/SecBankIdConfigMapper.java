package com.minigod.zero.bpmn.module.deposit.mapper;

import com.minigod.zero.bpmn.module.deposit.entity.SecBankIdConfigEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecBankIdConfigVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 银行bankId配置表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface SecBankIdConfigMapper extends BaseMapper<SecBankIdConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secBankIdConfigVO
	 * @return
	 */
	List<SecBankIdConfigVO> selectSecBankIdConfigPage(IPage page, SecBankIdConfigVO secBankIdConfigVO);


}

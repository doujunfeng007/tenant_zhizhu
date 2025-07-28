package com.minigod.zero.bpmn.module.deposit.mapper;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositTypeEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositTypeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 入金方式管理配置表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface SecDepositTypeMapper extends BaseMapper<SecDepositTypeEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secDepositTypeVO
	 * @return
	 */
	List<SecDepositTypeVO> selectSecDepositTypePage(IPage page, SecDepositTypeVO secDepositTypeVO);


}

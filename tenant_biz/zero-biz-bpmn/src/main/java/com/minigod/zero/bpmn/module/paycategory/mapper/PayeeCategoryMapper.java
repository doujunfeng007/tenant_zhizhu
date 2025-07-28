package com.minigod.zero.bpmn.module.paycategory.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.paycategory.bo.PayCategoryQueryBO;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.paycategory.vo.PayCategoryQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【payee_category(收款人类别管理表/线上支付记录表)】的数据库操作Mapper
* @createDate 2024-07-30 10:36:48
* @Entity com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategory
*/
@Mapper
public interface PayeeCategoryMapper extends BaseMapper<PayeeCategoryEntity> {

	IPage<PayCategoryQueryVO> queryPageList(IPage<PayCategoryQueryVO> page,@Param("query") PayCategoryQueryBO queryBO);

	List<String> selOrderImgById(@Param("id") Long id);
}





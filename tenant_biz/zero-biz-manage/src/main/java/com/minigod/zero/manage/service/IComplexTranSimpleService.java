package com.minigod.zero.manage.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.ComplexSimpleLogEntity;
import com.minigod.zero.manage.vo.request.ComplexTranSimpleRequestVo;
import com.minigod.zero.manage.vo.response.ComplexTranSimpleResponseDto;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/7/17 18:09
 */
public interface IComplexTranSimpleService extends IService<ComplexSimpleLogEntity>{

	/**
	 * 分页查找
	 * @param requestVo
	 * @param query
	 * @return
	 */
	IPage<ComplexTranSimpleResponseDto> page(ComplexTranSimpleRequestVo requestVo, Query query);


	/**
	 * 添加
	 * @param complexTranSimpleRequestVo
	 */
	R save(ComplexTranSimpleRequestVo complexTranSimpleRequestVo);


	/**
	 * 修改状态
	 * @param complexTranSimpleRequestVo
	 * @return
	 */
	R changeStatus(ComplexTranSimpleRequestVo complexTranSimpleRequestVo);


	/**
	 * 编辑
	 * @param complexTranSimpleRequestVo
	 * @return
	 */
	R edit(ComplexTranSimpleRequestVo complexTranSimpleRequestVo);


	/**
	 * 转换
	 * @param type
	 * @param txt
	 * @return
	 */
	String trans(Integer type,String txt);


	/**
	 * 获取所有可用得敏感词
	 * @return
	 */
	List<ComplexSimpleLogEntity> findAll();

	/**
	 * 只使用配置的词语进行替换
	 * @param txt
	 * @return
	 */
	String configWordReplace(String txt);
}

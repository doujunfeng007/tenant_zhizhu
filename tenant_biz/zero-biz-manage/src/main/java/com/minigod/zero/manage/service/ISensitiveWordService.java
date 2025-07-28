package com.minigod.zero.manage.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.SensitiveWordEntity;
import com.minigod.zero.manage.vo.request.SensitiveWordRequestVo;
import com.minigod.zero.manage.vo.response.GpSensitiveWordDto;
import com.minigod.zero.manage.vo.response.SensitiveWordLogRespDto;
import com.minigod.zero.manage.vo.response.SensitiveWordRespDto;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.vo.request.SensitiveWordEditVo;

import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/7/17 18:09
 */
public interface ISensitiveWordService extends IService<SensitiveWordEntity>{
	/**
	 * 获取可用的词库
	 * @param scope
	 * @return
	 */
	List<String> getEnableLexicon(Integer scope);

	/**
	 * 敏感词库分页查找
	 * @param requestVo
	 * @param query
	 * @return
	 */
	IPage<SensitiveWordRespDto> page(SensitiveWordRequestVo requestVo, Query query);


	/**
	 * 判断该敏感词是否存在
	 * @param word
	 * @param scope
	 * @return
	 */
	SensitiveWordEntity getOneWord(String word, Integer scope);

	/**
	 * 添加敏感词
	 * @param sensitiveWordRequestVo
	 */
	R save(SensitiveWordRequestVo sensitiveWordRequestVo);


	/**
	 * 修改敏感词状态
	 * @param sensitiveWordEditVo
	 * @return
	 */
	R changeStatus(SensitiveWordEditVo sensitiveWordEditVo);


	/**
	 * 编辑敏感词
	 * @param sensitiveWordEditVo
	 * @return
	 */
	R editWord(SensitiveWordEditVo sensitiveWordEditVo);

	/**
	 * 将敏感词注释为指定符号
	 * @param checkWord
	 * @param scope
	 * @return
	 */
	String filterWord(String checkWord, Integer scope,Character replace);

	/**
	 * 获取有效的敏感词
	 * {“0005.HK”:List<"敏感词1","敏感词2">}
	 * @param scope
	 * @param source
	 * @return
	 */
	List<GpSensitiveWordDto> getGpSensitiveWordList(Integer scope, String source);

	/**
	 * 敏感词状态分页查询
	 * @param requestVo
	 * @param query
	 * @return
	 */
	IPage<SensitiveWordLogRespDto> detailPage(SensitiveWordRequestVo requestVo, Query query);
}

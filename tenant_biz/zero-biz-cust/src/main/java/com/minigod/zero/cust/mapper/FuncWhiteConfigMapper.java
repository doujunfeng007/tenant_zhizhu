package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.FuncWhiteConfigEntity;
import com.minigod.zero.cust.vo.FuncWhiteConfigVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 功能白名单启用配置 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface FuncWhiteConfigMapper extends BaseMapper<FuncWhiteConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param funcWhiteConfig
	 * @return
	 */
	List<FuncWhiteConfigVO> selectFuncWhiteConfigPage(IPage page, FuncWhiteConfigVO funcWhiteConfig);


}

package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.FuncWhiteListEntity;
import com.minigod.zero.cust.vo.FuncWhiteListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 功能白名单用户信息 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface FuncWhiteListMapper extends BaseMapper<FuncWhiteListEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param funcWhiteList
	 * @return
	 */
	List<FuncWhiteListVO> selectFuncWhiteListPage(IPage page, FuncWhiteListVO funcWhiteList);


}

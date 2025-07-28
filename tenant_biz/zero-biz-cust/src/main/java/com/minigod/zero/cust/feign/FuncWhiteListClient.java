package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.cust.feign.IFuncWhiteListClient;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.cust.apivo.req.FuncWhiteListQueryReq;
import com.minigod.zero.cust.entity.FuncWhiteListEntity;
import com.minigod.zero.cust.feign.IFuncWhiteListClient;
import com.minigod.zero.cust.service.IFuncWhiteListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.feign.FuncWhiteListClient
 * @Date: 2023年03月17日 15:17
 * @Description:
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
@Slf4j
public class FuncWhiteListClient implements IFuncWhiteListClient {

	@Resource
	IFuncWhiteListService funcWhiteListService;

	@Override
	public Page<FuncWhiteListEntity> queryPageFuncWhiteList(FuncWhiteListQueryReq funcWhiteListQueryReq, Integer current,Integer size) {
		LambdaQueryWrapper<FuncWhiteListEntity> queryWrapper = Wrappers.lambdaQuery();
		if(StringUtils.isNotEmpty(funcWhiteListQueryReq.getFuncId())){
			queryWrapper.eq(FuncWhiteListEntity::getFuncId,funcWhiteListQueryReq.getFuncId());
		}
		if(null !=funcWhiteListQueryReq.getCustId()){
			queryWrapper.eq(FuncWhiteListEntity::getCustId,funcWhiteListQueryReq.getCustId());
		}
		if(null !=funcWhiteListQueryReq.getStatus()){
			queryWrapper.eq(FuncWhiteListEntity::getStatus,funcWhiteListQueryReq.getStatus());
		}
		queryWrapper.orderByDesc(FuncWhiteListEntity::getCreateTime);
		Query query = new Query();
		query.setCurrent(current);
		query.setSize(size);
		IPage<FuncWhiteListEntity> page = funcWhiteListService.getBaseMapper().selectPage(Condition.getPage(query), queryWrapper);
		return new Page<FuncWhiteListEntity>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(page.getRecords());
	}

	@Override
	public List<FuncWhiteListEntity> queryFuncWhiteList(List<Long> ids, String funcId) {
		LambdaQueryWrapper<FuncWhiteListEntity> queryWrapper = Wrappers.lambdaQuery();
		if(CollectionUtil.isNotEmpty(ids)){
			queryWrapper.in(FuncWhiteListEntity::getCustId,ids);
		}
		if(StringUtils.isNotEmpty(funcId)){
			queryWrapper.eq(FuncWhiteListEntity::getFuncId,funcId);
		}
		return funcWhiteListService.getBaseMapper().selectList(queryWrapper);
	}

	@Override
	public boolean save(FuncWhiteListEntity funcWhiteList) {
		return funcWhiteListService.save(funcWhiteList);
	}

	@Override
	public boolean updateById(FuncWhiteListEntity funcWhiteList) {
		FuncWhiteListEntity funcWhiteListEntity =funcWhiteListService.getBaseMapper().selectById(funcWhiteList.getId());
		funcWhiteListEntity.setStatus(funcWhiteList.getStatus());
		return funcWhiteListService.updateById(funcWhiteListEntity);
	}
}

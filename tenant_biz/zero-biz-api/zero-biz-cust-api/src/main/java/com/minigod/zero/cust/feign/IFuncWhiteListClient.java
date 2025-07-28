package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.cust.apivo.req.FuncWhiteListQueryReq;
import com.minigod.zero.cust.entity.FuncWhiteListEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 客户信息表 Feign接口类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@FeignClient(
    value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface IFuncWhiteListClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/funcWhiteList";
	String QUERY_PAGE_WHITE = API_PREFIX + "/query_page";
	String QUERY_LIST = API_PREFIX + "/query_list";
	String ADD_WHITE = API_PREFIX + "/add";
	String UPDATE_WHITE = API_PREFIX + "/update";


	/**
	 * 分页查询
	 * @param funcWhiteListQueryReq
	 * @return
	 */
	@GetMapping(QUERY_PAGE_WHITE)
	Page<FuncWhiteListEntity> queryPageFuncWhiteList(@SpringQueryMap FuncWhiteListQueryReq funcWhiteListQueryReq,
													 @RequestParam(required = false) Integer current,
													 @RequestParam(required = false) Integer size);

	/**
	 *
	 * @param ids
	 * @param funcId
	 * @return
	 */
	@GetMapping(QUERY_LIST)
	List<FuncWhiteListEntity> queryFuncWhiteList(@RequestParam List<Long> ids,@RequestParam String funcId);

	/**
	 * 新增
	 * @param funcWhiteList
	 * @return
	 */
	@PostMapping(ADD_WHITE)
    boolean save(@RequestBody  FuncWhiteListEntity funcWhiteList);

	/**
	 * 修改
	 * @param funcWhiteList
	 * @return
	 */
	@PostMapping(UPDATE_WHITE)
	boolean updateById(@RequestBody FuncWhiteListEntity funcWhiteList);
}

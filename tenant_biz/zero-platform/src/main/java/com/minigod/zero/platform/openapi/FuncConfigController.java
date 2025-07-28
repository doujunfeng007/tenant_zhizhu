package com.minigod.zero.platform.openapi;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.system.cache.DictBizCache;
import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.cache.SysCache;
import com.minigod.zero.system.entity.Dict;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.entity.FuncConfig;
import com.minigod.zero.system.vo.FuncConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api")
@Validated
@Slf4j
@AllArgsConstructor
@Api("系统功能配置")
public class FuncConfigController {
    @ApiLog("查询功能配置")
    @ApiOperation("查询功能配置")
    @GetMapping("/func_config")
    public R<List<FuncConfigVO>> getFuncConfig() {
		String tenantId = "";
		HttpServletRequest request = WebUtil.getRequest();
		if (request != null) {
			tenantId = request.getHeader(TokenConstant.TENANT_KEY);
		}

		//调用业务字典服务，查询业务字典
        return R.data(SysCache.getFuncConfigByTenantId(tenantId));
		//return R.data(DictBizCache.getList(code));
    }
}

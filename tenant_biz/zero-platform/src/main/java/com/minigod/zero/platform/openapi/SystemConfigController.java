package com.minigod.zero.platform.openapi;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.system.cache.DictBizCache;
import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.entity.Dict;
import com.minigod.zero.system.entity.DictBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Api("系统字典配置接口")
public class SystemConfigController {
    @ApiLog("查询业务字典")
    @ApiOperation("查询业务字典")
    @GetMapping("/dict_biz_type/{code}")
    public R<List<DictBiz>> getDictBizType(@PathVariable("code") String code) {
		//判断是不是有缓存住户，没有租户，取header中的租户信息，有租户，取租户信息
        String tenantId = AuthUtil.getTenantId();
		String lang = WebUtil.getLanguage();
        HttpServletRequest request = WebUtil.getRequest();
        if (request != null) {
            tenantId = request.getHeader(TokenConstant.TENANT_KEY);
        }
        log.info("业务字典查询->当前租户:{}",tenantId);
		log.info("业务字典查询->当前语言:{}",lang);
		log.info("业务字典查询->字典key:{}",code);
		//调用业务字典服务，查询业务字典
        return R.data(DictBizCache.getListByTenantId(tenantId,code));
    }
    @ApiLog("查询系统字典")
    @ApiOperation("查询系统字典")
    @GetMapping("/dict_system_type/{code}")
    public R<List<Dict>> getDictSystemType(@PathVariable("code") String code) {
        return R.data(DictCache.getList(code));
    }
}

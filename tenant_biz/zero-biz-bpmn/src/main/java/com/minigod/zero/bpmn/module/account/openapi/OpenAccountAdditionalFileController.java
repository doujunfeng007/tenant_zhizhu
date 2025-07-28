package com.minigod.zero.bpmn.module.account.openapi;

import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalFileService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *  开户附件控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX+"/account/accountAdditionalFile")
@Api(value = "", tags = "")
public class OpenAccountAdditionalFileController extends ZeroController {

	private final IAccountAdditionalFileService account_additional_fileService;

	/**
	 *
	 * @param type 2 w8协议   3自我证明
	 * @return
	 */
	@ApiOperation("查询用户开户相关附件列表")
	@GetMapping("/list")
	public R<List<AccountAdditionalFileEntity>> list(@ApiParam("文件类型")
													 @RequestParam(value = "type", required = false) Integer type) {

		return R.data(account_additional_fileService.queryListByType(type));
	}



}

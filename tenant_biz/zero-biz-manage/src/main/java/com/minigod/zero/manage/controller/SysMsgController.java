package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.manage.service.IPlatformSysMsgService;
import com.minigod.zero.manage.utils.FileReadUtil;
import com.minigod.zero.platform.dto.SendSysMsgReqDTO;
import com.minigod.zero.platform.dto.SysMsgSearchReqDTO;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.enums.InformEnum;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * OMS系统通知
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/sysMsg")
@RequiredArgsConstructor
@Slf4j
public class SysMsgController {

	private final IPlatformSysMsgService platformSysMsgService;

	/**
	 * 查询系统通知列表
	 *
	 * @return
	 */
	@ApiOperation("查询系统通知列表")
	@GetMapping("/list")
	public R<Object> sysmsgList(SysMsgSearchReqDTO sysMsgSearchReqDTO, Query query) {
		R<Object> rt = R.success();
		IPage<PlatformSysMsgEntity> page = null;
		try {
			page = platformSysMsgService.getSysMsgList(sysMsgSearchReqDTO, query);
		} catch (Exception e) {
			log.error("查询系统通知列表异常：", e);
			R.fail(ResultCode.INTERNAL_ERROR);
		}
		rt.setData(page);
		return rt;
	}

	/**
	 * 保存并发送系统通知消息
	 *
	 * @return
	 */
	@ApiOperation("保存并发送系统通知消息")
	@PostMapping("/sendMsgSaveUpdate")
	public R<Object> confirmSysMsg(@RequestParam(value = "file", required = false) MultipartFile file,
								   SendSysMsgReqDTO sendSysMsgReqDTO) {
		try {
			if (StringUtils.isBlank(sendSysMsgReqDTO.getTitle())) {
				return R.fail("标题为空");
			}
			if (StringUtils.isBlank(sendSysMsgReqDTO.getContent())) {
				return R.fail("通知内容为空");
			}
			if (sendSysMsgReqDTO.getSendWay() == null) {
				return R.fail("推送方式为空");
			}
			if (sendSysMsgReqDTO.equals(Constants.SysMsgContants.SYS_MSG_SENDWAY_GIVINGTIME) && sendSysMsgReqDTO.getSendTime() == null) {
				return R.fail("推送时间为空");
			}

			// 是否通过文件导入
			if (file != null && !file.isEmpty()) {
				List<Long> lstUserIds = FileReadUtil.getUserIdsFromFile(file);
				if (CollectionUtils.isEmpty(lstUserIds)) {
					return R.fail("导入文件内容为空");
				} else {
					Long[] arr = new Long[lstUserIds.size()];
					sendSysMsgReqDTO.setUserIds(lstUserIds.toArray(arr));
					platformSysMsgService.saveUpdateAndSendSysMsg(sendSysMsgReqDTO);
				}
			} else {
				if (sendSysMsgReqDTO.getMsgGroup().equals(InformEnum.MsgGroupEnum.PERSON.getTypeCode()) && sendSysMsgReqDTO.getUserIds() == null) {
					return R.fail("请选择用户");
				}
				// 保存并发送数据
				platformSysMsgService.saveUpdateAndSendSysMsg(sendSysMsgReqDTO);
			}
			return R.success();
		} catch (Exception e) {
			log.error(" 发送通知验证失败：", e);
			return R.fail(ResultCode.INTERNAL_ERROR.getCode(), "操作失败");
		}
	}

	/**
	 * 系统通知新增修改页面
	 *
	 * @return
	 */
	@ApiOperation("系统通知新增修改页面")
	@GetMapping("/saveUpdateUI")
	public R<Object> sysMsgAddWeb(Long id) {
		//系统通知内容数据
		PlatformSysMsgEntity sysMsg = null;
		if (id != null) {
			sysMsg = platformSysMsgService.findSysMsg(id);
		}

//		//系统通知只有一级目录，寻找类型为3(系统通知类型)的一级目录
//		//通知模板数据
//		List<PlatformTemplateTypeEntity> tempList = platformTemplateTypeService.findInformTempTypeList(InformEnum.BusTypeEnum.SYS.getTypeCode() , 0);

//		return R.data(Kv.create().set("sysMsg", sysMsg).set("tempList", tempList));
		return R.data(sysMsg);
	}
}

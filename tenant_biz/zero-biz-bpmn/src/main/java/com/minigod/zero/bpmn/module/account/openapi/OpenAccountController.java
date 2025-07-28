package com.minigod.zero.bpmn.module.account.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.account.bo.*;
import com.minigod.zero.bpmn.module.account.service.IAccountBackReasonService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.service.IOpenAccountOnlineService;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.common.bo.OcrReqParams;
import com.minigod.zero.bpmn.module.common.bo.QueryAddressCode;
import com.minigod.zero.bpmn.module.common.service.AddressService;
import com.minigod.zero.bpmn.module.common.vo.address.Province;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api")
@Validated
@Slf4j
@AllArgsConstructor
@Api("开户接口")
public class OpenAccountController {

	private final IAccountOpenInfoService accountOpenInfoService;
    private final IOpenAccountOnlineService openAccountOnlineService;
    private final IAccountBackReasonService accountBackReasonService;
    private final IDictBizClient iDictBizClient;
    private final AddressService addressService;

    /**
     * 获取用户开户进度
     */
    @ApiLog("获取开户进度")
    @ApiOperation("获取开户进度")
    @PostMapping("/get_open_progress")
    public R<OpenUserInfoVo> getOpenProgress(@RequestBody CommonReqVO<OpenProgressBo> bo) {
        return R.data(openAccountOnlineService.getOpenProgress(bo.getParams()));
    }

    @ApiLog("获取开户缓存数据")
    @ApiOperation("获取开户缓存")
    @PostMapping("/get_cache_data")
    public R<OpenCacheDataVo> getCacheCnInfo(@RequestBody CommonReqVO<OpenCacheDataBo> bo) {
        return R.data(openAccountOnlineService.getCacheData(bo.getParams()));
    }

    @ApiLog("保存开户缓存数据")
    @ApiOperation("保存缓存数据")
    @PostMapping("/save_cache_info")
    public R saveCacheCnInfo(@RequestBody CommonReqVO<OpenCacheInfoBo> bo) {
        openAccountOnlineService.saveOrUpdateCacheInfoStep(bo.getParams());
        return R.success();
    }

    /**
     * 提交开户
     */
    @ApiLog("提交开户资料")
    @ApiOperation("提交开户资料")
    @PostMapping("/submit_open_info")
    public R submitOpenInfo(@RequestBody CommonReqVO<OpenInfoBo> bo) {
        openAccountOnlineService.submitOpenInfo(bo.getParams());
        return R.success();
    }

    @ApiLog("查询业务字典")
    @ApiOperation("查询业务字典")
    @GetMapping("/dict_data_type/{code}")
    public R<List<DictBiz>> getDictDataType(@PathVariable("code") String code) {
        return iDictBizClient.getList(code);
    }

    @ApiLog("查开户退回原因")
    @ApiOperation("查开户退回原因")
    @GetMapping("/backReason/{applicationId}")
    public R<List<AccountBackReasonVO>> backReasonList(@PathVariable("applicationId") String applicationId) {
        return R.data(accountBackReasonService.selectByApplicationId(applicationId));
    }

    @ApiLog("查询地址列表")
    @ApiOperation("查询地址列表")
    @PostMapping("/getAddressList")
    public R<List<Province>> sysAddressService(@RequestBody CommonReqVO<QueryAddressCode> bo) {
        return R.data(addressService.getAddressList(bo.getParams()));
    }

    @ApiLog("保存用户开户图片数据")
    @ApiOperation("保存用户开户图片数据")
    @PostMapping("/save_cache_img")
    public R saveCacheCnImg(@RequestBody CommonReqVO<OpenImgBo> bo) {
        OpenImgVo openImgVo = openAccountOnlineService.saveCacheImg(bo.getParams());
        return R.data(openImgVo);
    }

    /**
     * 保存用户开户视频数据
     */
    @ApiLog("保存用户开户视频数据")
    @ApiOperation("保存用户开户视频数据")
    @PostMapping("/save_cache_video")
    public R saveDepositHkVideo(@RequestPart("location")String location,
                                @RequestPart("type")String type,
                                @RequestPart("file")MultipartFile file,
                                HttpServletRequest request) {
        try {
            OpenVideoBo params = new OpenVideoBo();
            params.setLocation(location);
            params.setType(type);
            params.setFile(file);
            OpenVideoVo res = openAccountOnlineService.saveCacheVideo(params);
            return R.data(res);
        } catch (Exception e) {
            log.error("上传视频失败:", e);
            return R.fail("上传视频失败");
        }
    }
    /**
     * ocr识别
     */
	@ApiLog(value = "ocr识别")
    @PostMapping("/ocr_by_card_type")
    public R ocrByCardType(@RequestBody OcrReqParams params) {
		Object rt = openAccountOnlineService.ocrByCardType(params);
		return R.data(rt);
    }

	/**
	 * 获取开户协议状态
	 * @param type  2 w8协议    3 个人证明协议
	 * @return
	 */
	@GetMapping("/agreement_status")
	public R agreementStatus(@RequestParam("type") Integer type) {
		return accountOpenInfoService.agreementStatus(type);
	}

	@ApiLog("上传用户签名图片数据")
	@ApiOperation(value = "上传用户签名图片数据", notes = "传入openImgBo对象")
	@ApiOperationSupport(order = 3)
	@PostMapping("/save_sign_img")
	public R saveSignImg(@RequestBody CommonReqVO<OpenSignImgBo> bo) {
		return accountOpenInfoService.saveSignImg(bo.getParams());
	}

	/**
	 * 确认协议
	 * @param type    3 个人证明协议
	 * @return
	 */
	@ApiOperation("确认协议")
	@PostMapping("/confirmationProtocol/{type}")
	public R confirmationProtocol(@PathVariable("type") Integer type) {
		return accountOpenInfoService.confirmationProtocol(type);
	}
}

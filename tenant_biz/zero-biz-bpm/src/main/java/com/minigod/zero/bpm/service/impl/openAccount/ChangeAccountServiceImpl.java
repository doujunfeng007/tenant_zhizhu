package com.minigod.zero.bpm.service.impl.openAccount;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minigod.zero.bpm.constant.BpmConstants;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.ChangeSecuritiesDto;
import com.minigod.zero.bpm.entity.AcctChangeImageEntity;
import com.minigod.zero.bpm.entity.AcctChangeInfoTempEntity;
import com.minigod.zero.bpm.entity.AcctOpenImageEntity;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.helper.SecUserInfoChangeHelper;
import com.minigod.zero.bpm.service.IAcctChangeImageService;
import com.minigod.zero.bpm.service.IAcctChangeInfoTempService;
import com.minigod.zero.bpm.service.IAcctOpenImageService;
import com.minigod.zero.bpm.service.IBpmSecuritiesInfoService;
import com.minigod.zero.bpm.utils.ImageUtils;
import com.minigod.zero.bpm.vo.*;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.service.openAccount.IChangeAccountService;
import com.minigod.zero.bpm.vo.*;
import com.minigod.zero.bpm.vo.request.OpenAccountImgUpdateVo;
import com.minigod.zero.bpm.vo.request.SecUserInfoChangeCallbackProtocol;
import com.minigod.zero.bpm.vo.request.SecUserInfoChangeProtocol;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpm.vo.response.SecUserInfoChangeResp;
import com.minigod.zero.bpm.vo.response.SecuritiesUserInfoFullResp;
import com.minigod.zero.cms.feign.oms.ISensitiveWordClient;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChangeAccountServiceImpl implements IChangeAccountService {

	@Resource
	private IAcctChangeInfoTempService changeInfoTempService;

	@Resource
	private IAcctChangeImageService acctChangeImageService;

	@Resource
	private IAcctOpenImageService acctOpenImageService;

	@Resource
	private ISensitiveWordClient sensitiveWordClient;

	@Resource
	private IOssClient ossClient;

	@Value("${bpm.api.url:'http://10.9.68.165:6426/bpm'}")
	private String bpmApiUrl;

	@Autowired
	private IBpmSecuritiesInfoService bpmSecuritiesInfoService;


	@Override
	public R<AcctChangeInfoTempVO> getSecuritiesTemp(Long custId, ChangeSecuritiesDto dto) {

		if(null == dto || null == dto.getStep()) return R.fail(ResultCode.PARAM_VALID_ERROR);

		AcctChangeInfoTempVO changeTemp = null;
		LambdaQueryWrapper<AcctChangeInfoTempEntity> changeTempWrapper = new LambdaQueryWrapper<>();
		changeTempWrapper.eq(AcctChangeInfoTempEntity::getCustId, custId);
		changeTempWrapper.eq(AcctChangeInfoTempEntity::getStep,dto.getStep());
		List<AcctChangeInfoTempEntity> changeTempList = changeInfoTempService.list(changeTempWrapper);
		if(CollectionUtil.isEmpty(changeTempList)) {
			try{
				changeTemp = getUserInfoByBpm(custId, dto.getStep(),false);
			}catch (Exception e) {
				log.error("getUserInfoByBpm error",e);
				return R.fail();
			}
			return R.data(changeTemp);
		}

		Map<Integer, List<AcctChangeInfoTempEntity>> tempCollect = changeTempList.stream().collect(Collectors.groupingBy(AcctChangeInfoTempEntity::getCheckStatus));
		Boolean check1 = !CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED)) && tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED).size() > 1;
		Boolean check2 = !CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED)) && tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED).size() > 1;
		Boolean check3 = !CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL)) && tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL).size() > 1;
		// 客户正在修改中的数据不能超过一条
		if (check1 || check2 || check3) {
			log.error("查询证券资料缓存失败，客户当前修改中的数据不止一条：custId:" + custId + "  step:" + dto.getStep());
			return R.fail("查询客户资料失败");
		}
		// 客户不能同时存在未提交、已提交、已拒绝的数据
		if (!CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED))
			&& !CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED))
			&& !CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL))) {
			log.error("客户不能同时存在未提交、已提交、已拒绝的数据：custId:" + custId + "  step:" + dto.getStep());
			return R.fail("查询客户资料失败");
		}

		if (!CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED))) {
			List<AcctChangeInfoTempEntity> list = tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED);
			AcctChangeInfoTempEntity temp = list.get(0);
			temp.setImgList(JSONObject.toJSONString(getImgList(temp.getId().longValue()),SerializerFeature.WriteNonStringValueAsString));
			return R.data(BeanUtil.copyProperties(temp,AcctChangeInfoTempVO.class));
		}

		if (!CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED))) {
			List<AcctChangeInfoTempEntity> list = tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED);
			AcctChangeInfoTempEntity temp = list.get(0);
			temp.setImgList(JSONObject.toJSONString(getImgList(temp.getId().longValue()),SerializerFeature.WriteNonStringValueAsString));
			return R.data(BeanUtil.copyProperties(temp,AcctChangeInfoTempVO.class));
		}

		if (!CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL))) {
			List<AcctChangeInfoTempEntity> list = tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL);
			AcctChangeInfoTempEntity temp = list.get(0);
			temp.setImgList(JSONObject.toJSONString(getImgList(temp.getId()),SerializerFeature.WriteNonStringValueAsString));
			return R.data(BeanUtil.copyProperties(temp,AcctChangeInfoTempVO.class));
		}

		AcctChangeInfoTempEntity checkChangetmp = null;
		if (!CollectionUtils.isEmpty(tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS))
			&& tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS).size() > 1) {
			// 多次修改,按照id降序排序，取第一条
			List<AcctChangeInfoTempEntity> listSorted = tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS).stream().sorted(Comparator.comparing(AcctChangeInfoTempEntity::getId).reversed()).collect(Collectors.toList());
			checkChangetmp = listSorted.get(0);
		} else {
			// 只修改过一次
			List<AcctChangeInfoTempEntity> list = tempCollect.get(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS);
			checkChangetmp = list.get(0);
		}

		AcctChangeInfoTempEntity newTemp = new AcctChangeInfoTempEntity();
		newTemp.setCreateTime(new Date());
		newTemp.setCheckStatus(0);
		newTemp.setStep(checkChangetmp.getStep());
		newTemp.setCustId(checkChangetmp.getCustId());
		// 身份资料声明，前后的描述相反，，并且前端提交的对象，和查询展示的对象是不一样的，直接将保存的提交对象返回，前端无法展示，需要转换
		if(dto.getStep() == 6){
			if(StringUtil.isNotBlank(checkChangetmp.getJsonInfo())){
				JSONObject rootObject = JSONObject.parseObject(checkChangetmp.getJsonInfo());
				String otherDisclosureList = rootObject.getString("otherDisclosureList");
				if(StringUtil.isNotBlank(otherDisclosureList)){
					List<OpenAccountOtherDisclosureProtocol> otherDisclosure = JSON.parseArray(otherDisclosureList, OpenAccountOtherDisclosureProtocol.class);
					for(int i = 0;i < otherDisclosure.size(); i++){
						otherDisclosure.get(i).setDisclosureIsTrue(Math.abs((otherDisclosure.get(i).getDisclosureIsTrue() - 1)));
					}
					SecuritiesUserInfoFullResp securitiesUserFullInfo = new SecuritiesUserInfoFullResp();
					securitiesUserFullInfo.setOtherDisclosure(otherDisclosure);
					SecUserInfoChangeResp secUserInfoChange = SecUserInfoChangeHelper.getChangeVO(6, securitiesUserFullInfo);
					newTemp.setJsonInfo(JSON.toJSONString(secUserInfoChange));
				}else{
					newTemp.setJsonInfo(checkChangetmp.getJsonInfo());
				}
			}
		}else{
			newTemp.setJsonInfo(checkChangetmp.getJsonInfo());
		}
		changeInfoTempService.getBaseMapper().insert(newTemp);

		List<AcctChangeImageVO> imgs = getImgList(checkChangetmp.getId());
		if (CollectionUtil.isNotEmpty(imgs)) {
			newTemp.setImgList(JSONObject.toJSONString(imgs,SerializerFeature.WriteNonStringValueAsString));
		}
		return R.data(BeanUtil.copyProperties(newTemp,AcctChangeInfoTempVO.class));
	}

	@Override
	public R updSecuritiesTemp(Long custId, ChangeSecuritiesDto dto) {
		if(null == dto || null == dto.getId() || StringUtil.isBlank(dto.getInfo()) || "{}".equals(dto.getInfo()))
			return R.fail(ResultCode.PARAM_VALID_ERROR);

		LambdaQueryWrapper<AcctChangeInfoTempEntity> changeWrapper = new LambdaQueryWrapper<>();
		changeWrapper.eq(AcctChangeInfoTempEntity::getId, dto.getId());
		AcctChangeInfoTempEntity tempEntity = changeInfoTempService.getOne(changeWrapper);
		if(null == tempEntity || custId.intValue() != tempEntity.getCustId().intValue()) return R.fail(ResultCode.NONE_DATA);

		if(StringUtils.isNotEmpty(dto.getImgList())) {
			tempEntity.setImgList(dto.getImgList());
		}

		/**
		 * 拦截json里面的敏感词  familyName,familyNameSpell,givenName,givenNameSpell
		 * 为什么要添加 |  符号 ,在敏感词的工具类中增加了对特殊符号的判断,但是没有增加|。其此是封装成一次,不用多次调用接口
		 */
		StringBuilder checkWord = new StringBuilder();
		JSONObject jsonObject = JSON.parseObject(dto.getInfo());
		if(jsonObject!=null){
			if (StringUtil.isNotBlank(jsonObject.getString("familyName"))){
				checkWord.append(jsonObject.getString("familyName")).append("|");
			}
			if (StringUtil.isNotBlank(jsonObject.getString("familyNameSpell"))){
				checkWord.append(jsonObject.getString("familyNameSpell")).append("|");
			}
			if (StringUtil.isNotBlank(jsonObject.getString("givenName"))){
				checkWord.append(jsonObject.getString("givenName")).append("|");
			}
			if (StringUtil.isNotBlank(jsonObject.getString("givenNameSpell"))){
				checkWord.append(jsonObject.getString("givenNameSpell")).append("|");
			}

			//如果数据不为空,那么执行拦截
			if (StringUtil.isNotBlank(checkWord.toString())){
				R<Boolean> check = sensitiveWordClient.check(checkWord.toString(), 0);
				//如果获取结果成功
				if (R.isSuccess(check)){
					if (check.getData()!=null&&check.getData()){
						log.info("用户id{}修改资料时检测出敏感词：{}", custId,checkWord.toString());
						return R.fail("请详细检查你的名字信息,存在违规名称");
					}
				}
			}
		}

		tempEntity.setJsonInfo(dto.getInfo());
		tempEntity.setUpdateTime(new Date());
		tempEntity.setPushRecved("0");
		tempEntity.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED);
		changeInfoTempService.updateById(tempEntity);
		return R.success();
	}

	@Override
	public R removeImg(Long custId, ChangeSecuritiesDto dto) {
		if(null == dto || null == dto.getId()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		LambdaQueryWrapper<AcctChangeImageEntity> changeWrapper = new LambdaQueryWrapper<>();
		changeWrapper.eq(AcctChangeImageEntity::getId,dto.getId());
		acctChangeImageService.removeById(dto.getId());
		return R.success();
	}

	@Override
	public R<AcctChangeInfoTempVO> getSecuritiesInfoReal(Long custId, ChangeSecuritiesDto dto) {
		if(null == dto || null == dto.getStep()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		AcctChangeInfoTempVO changeTemp =  getUserInfoByBpm(custId, dto.getStep(),true);
		return R.data(changeTemp);
	}

	@Override
	public R<AcctChangeInfoTempVO> getSecuritiesCheckStatus(Long custId, ChangeSecuritiesDto dto) {
		if(null == dto || null == dto.getStep()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		LambdaQueryWrapper<AcctChangeInfoTempEntity> changeWrapper = new LambdaQueryWrapper<>();
		changeWrapper.eq(AcctChangeInfoTempEntity::getCustId,custId);
		changeWrapper.eq(AcctChangeInfoTempEntity::getStep,dto.getStep());
		changeWrapper.last("limit 1");
		changeWrapper.orderByDesc(AcctChangeInfoTempEntity::getId);
		AcctChangeInfoTempEntity entity = changeInfoTempService.getOne(changeWrapper);
		AcctChangeInfoTempVO vo = new AcctChangeInfoTempVO();
		if(null == entity) {
			vo.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED);
		} else {
			vo.setCheckStatus(entity.getCheckStatus());
			vo.setAppointmentId(entity.getAppointmentId());
			vo.setChangeResult(entity.getChangeResult());
			vo.setErrCnt(entity.getErrCnt());
			vo.setId(entity.getId());
		}
		vo.setCustId(custId);
		vo.setStep(dto.getStep());

		return R.data(vo);
	}

	@Override
	public R<Boolean> getSecuritiesChangingStatus(Long custId) {
		LambdaQueryWrapper<AcctChangeInfoTempEntity> changeWrapper = new LambdaQueryWrapper<>();
		changeWrapper.eq(AcctChangeInfoTempEntity::getCustId,custId);
		changeWrapper.eq(AcctChangeInfoTempEntity::getCheckStatus,BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED);
		AcctChangeInfoTempEntity entity = changeInfoTempService.getOne(changeWrapper);
		if(null != entity) R.data(true);
		return R.data(false);
	}

	@Override
	public R resetCheckStatus(Long custId, ChangeSecuritiesDto dto) {
		if(null == dto || null == dto.getId()) return R.fail(ResultCode.PARAM_VALID_ERROR);

		LambdaQueryWrapper<AcctChangeInfoTempEntity> changeWrapper = new LambdaQueryWrapper<>();
		changeWrapper.eq(AcctChangeInfoTempEntity::getId,dto.getId());
 		AcctChangeInfoTempEntity entity = changeInfoTempService.getOne(changeWrapper);
		if(null == entity || custId.intValue() != entity.getCustId().intValue()) return R.fail(ResultCode.NONE_DATA);

		if (BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL != entity.getCheckStatus()) {
			return R.fail(ResultCode.NONE_DATA);
		}
		entity.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED);
		entity.setPushRecved("0");
		entity.setUpdateTime(new Date());
		entity.setChangeResult("");
		changeInfoTempService.updateById(entity);

		return R.success();
	}

	@Override
	public R<Kv> changeSaveImg(CaCertificationDto dto) {
		Long custId = AuthUtil.getCustId();

		Long startTime = System.currentTimeMillis();
		if (null == dto ||  StringUtil.isBlank(dto.getImageLocation()) ||  StringUtil.isBlank(dto.getImageLocationType())
			|| StringUtil.isBlank(dto.getImgBase64()) || null == dto.getChangeId() ) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		}
		String location = dto.getImageLocation();
		String type = dto.getImageLocationType();
		Long changeId = dto.getChangeId();
		log.info("changeAccountService.changeSaveImg param：" + "custId:" + custId + ",location:" + location+ ",type:" + type+ ",changeId:" + changeId);
		LambdaQueryWrapper<AcctChangeInfoTempEntity> changeWrapper = new LambdaQueryWrapper<>();
		changeWrapper.eq(AcctChangeInfoTempEntity::getId,changeId);
		AcctChangeInfoTempEntity entity = changeInfoTempService.getOne(changeWrapper);
		if(null == entity || entity.getCheckStatus() == BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED
			|| entity.getCheckStatus() == BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		}

		String uuid = UUID.fastUUID().toString();
		byte[] b = ImageUtils.base64StringToImage(dto.getImgBase64());
		String realFileName = uuid + ".jpg";
		if(null == b || null == realFileName) return R.fail(ResultCode.NONE_DATA);

		MultipartFile file = FileUtil.getMultipartFile(realFileName,b);

		R<ZeroFile> ossResp = ossClient.uploadMinioFile(file,file.getOriginalFilename());
		ZeroFile zeroFile = ossResp.getData();

		if(null == ossResp || null == ossResp.getData() || StringUtil.isBlank(zeroFile.getLink())) return R.fail(ResultCode.NONE_DATA);

		LambdaQueryWrapper<AcctChangeImageEntity> imageWrapper = new LambdaQueryWrapper<>();
		imageWrapper.eq(AcctChangeImageEntity::getImageLocation,location);
		imageWrapper.eq(AcctChangeImageEntity::getImageLocationType,type);
		imageWrapper.eq(AcctChangeImageEntity::getCustId,custId);
		imageWrapper.eq(AcctChangeImageEntity::getChangeId,changeId);
		imageWrapper.last("limit 1");
		AcctChangeImageEntity imageEntity = acctChangeImageService.getOne(imageWrapper);
		String id = null;
		if(null == imageEntity) {
			AcctChangeImageEntity changeEntity = new AcctChangeImageEntity();
			changeEntity.setCreateTime(new Date());
			changeEntity.setCustId(custId);
			changeEntity.setImageLocation(Integer.parseInt(location));
			changeEntity.setImageLocationType(Integer.parseInt(type));
			changeEntity.setImgPath(zeroFile.getLink());
			changeEntity.setChangeId(changeId);
			acctChangeImageService.save(changeEntity);

			id = changeEntity.getId().toString();
		} else {
			imageEntity.setImgPath(zeroFile.getLink());
			imageEntity.setCreateTime(new Date());
			imageEntity.setChangeId(changeId);
			acctChangeImageService.updateById(imageEntity);

			id = imageEntity.getId().toString();
		}

		log.info("上传图片耗时：" + (System.currentTimeMillis() - startTime));
		//String fileName = custId + "_" + type + System.currentTimeMillis() + ".jpg";
		return R.data(Kv.create().set("path",zeroFile.getLink()).set("imgId",id));
	}

	@Override
	public R executeChangeAccount() {

		LambdaQueryWrapper<AcctChangeInfoTempEntity> infoWrapper = new LambdaQueryWrapper<>();
		infoWrapper.eq(AcctChangeInfoTempEntity::getCheckStatus,BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED);
		infoWrapper.and(wrapper -> wrapper.isNull(AcctChangeInfoTempEntity::getPushRecved).or().eq(AcctChangeInfoTempEntity::getPushRecved,"0"));
		List<AcctChangeInfoTempEntity> infoList = changeInfoTempService.list(infoWrapper);
		if(CollectionUtil.isNotEmpty(infoList)) {
			for(AcctChangeInfoTempEntity temp : infoList) {
				if(null != temp.getErrCnt() && temp.getErrCnt() >= 2) continue;
				try{
					// 数据处理
					SecUserInfoChangeProtocol protocol = dataHandle(temp);
					requestBpm(protocol,temp);
				}catch (Exception e) {
					log.error("智珠id"+temp.getCustId()+"提交线上修改资料申请失败",e);
					saveErrorInfo(temp, "提交线上修改资料申请失败");
				}
			}
		}

		return R.success();
	}

	@Override
	public R changeAccountCheckStatus(SecUserInfoChangeCallbackProtocol protocol) {
 		if(null == protocol || StringUtils.isBlank(protocol.getApplicationId()) || null == protocol.getCheckStatus())
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		// 检查预约号是否存在
		LambdaQueryWrapper<AcctChangeInfoTempEntity> infoWrapper = new LambdaQueryWrapper<>();
		infoWrapper.eq(AcctChangeInfoTempEntity::getAppointmentId,protocol.getApplicationId());
		AcctChangeInfoTempEntity entity = changeInfoTempService.getOne(infoWrapper);

		if(null == entity) return R.fail(ResultCode.NONE_DATA);
		entity.setUpdateTime(new Date());
		if(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS == protocol.getCheckStatus()) entity.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS);

		if(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL == protocol.getCheckStatus()) entity.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL);


		if(4 == protocol.getCheckStatus()) {
			entity.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL);
			entity.setChangeResult(protocol.getBackReason());

			//TODO  APP推送通知客户
		}
		changeInfoTempService.updateById(entity);

		return R.success();
	}

	@Override
	public R changeAccountImage(OpenAccountImgUpdateVo proto) {

		// bpm 回调中台，图片新增
		if(null == proto || StringUtils.isBlank(proto.getApplicationId()))
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		LambdaQueryWrapper<AcctChangeInfoTempEntity> infoWrapper = new LambdaQueryWrapper<>();
		infoWrapper.eq(AcctChangeInfoTempEntity::getAppointmentId,proto.getApplicationId());
		AcctChangeInfoTempEntity entity = changeInfoTempService.getOne(infoWrapper);
		if(null == entity) {
			entity = new AcctChangeInfoTempEntity();
			entity.setCustId(proto.getUserId().longValue());
			entity.setAppointmentId(proto.getApplicationId());
			entity.setCreateTime(new Date());
		} else {
			entity.setUpdateTime(new Date());
		}
		// 审核状态置为2-已通过
		entity.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS);
		//资金账号升级修改客户资料
		entity.setStep(null);
		if(null == entity.getId()) {
			changeInfoTempService.save(entity);
		} else {
			changeInfoTempService.updateById(entity);
		}
		//FileOperaterUtil.createFileItem(proto.getI)
		return null;
	}

	@Override
	public SecuritiesUserInfoFullResp getSecuritiesFromBpm(Long custId) {
		SecuritiesUserInfoFullResp request = new SecuritiesUserInfoFullResp();
		request.setUserId(custId);
		return getSecUserInfoFromBpm(request);
	}

	private String requestBpm(SecUserInfoChangeProtocol protocol, AcctChangeInfoTempEntity temp) {
		// 请求接口
		String result = null;
		String server = bpmApiUrl + "/proxy/secUserInfoChange/changeApplication";
		String errMsg = "";
		HashMap<String, Object> hashMap = Maps.newHashMap();
		hashMap.put("params", protocol);
		try {
			log.info("****************************线上修改资料申请任务开始******************************");
			log.info(protocol.getUserId()+"调用cubp传入线上修改资料参数："+JSONObject.toJSONString(protocol));
			result = HttpUtil.post(server, JSONObject.toJSONString(hashMap), 20000);
			log.info("线上修改资料任务，返回结果："+result);
			log.info("****************************线上修改资料申请任务结束******************************");
			if (StringUtils.isNotBlank(result)) {
				ResponseVO res = JSONObject.parseObject(result, ResponseVO.class);
				if (res.getCode() == 0) {
					// 调用成功，更新记录的申请id，更新时间，推送情况
					String applicationId = res.getResult().toString();
					temp.setAppointmentId(applicationId);
					temp.setUpdateTime(new Date());
					temp.setPushRecved("1");
					changeInfoTempService.updateById(temp);
				} else {
					log.error("线上修改资料提交失败：" + protocol.getUserId() + "," + res.getMessage());
					saveErrorInfo(temp, res.getMessage());

				}
			} else {
				log.error("连接服务器异常,返回结果为null");
				saveErrorInfo(temp, "连接CUBP服务器异常,返回结果为null");
			}
		} catch (Exception e) {
			log.error("连接服务器异常", e);
			saveErrorInfo(temp,"连接CUBP服务器异常");
		}

		return result;
	}

	private void saveErrorInfo(AcctChangeInfoTempEntity temp, String errorMsg){
		temp.setUpdateTime(new Date());
		if(null == temp.getErrCnt()) temp.setErrCnt(0);
		if(temp.getErrCnt() < 1) {
			Integer errCnt = temp.getErrCnt();
			temp.setErrCnt(errCnt + 1);
		}
		changeInfoTempService.updateById(temp);
	}


	private SecUserInfoChangeProtocol dataHandle(AcctChangeInfoTempEntity temp) {
		SecUserInfoChangeResp tempVO = JSONObject.parseObject(temp.getJsonInfo(), SecUserInfoChangeResp.class);
		SecUserInfoChangeProtocol protocol = new SecUserInfoChangeProtocol();
		protocol.setStep(temp.getStep());
		String jsonInfo = temp.getJsonInfo();
		SecUserInfoChangeResp changeTempVO = JSONObject.parseObject(jsonInfo, SecUserInfoChangeResp.class);
		// 拷贝实体对象至protocol
		BeanUtils.copyProperties(changeTempVO,protocol);
		protocol.setUserId(temp.getCustId().intValue());

		if (temp.getStep() == 1) {
			if(StringUtils.isNotBlank(changeTempVO.getIdCardValue())) protocol.setIdNo(changeTempVO.getIdCardValue());
			// 姓名
			// 证件图片
			// 图片处理
			List<OpenAccountImageInfoProtocol> imgInfo = imgHandle(temp);
			protocol.setOpenAccountImagesInfo(imgInfo);
		}
		if (temp.getStep() == 2 || temp.getStep() == 7 || temp.getStep() == 8) {
			// 地址处理
			addressInfoHandle(tempVO, protocol);

			//增加地址证明图片
			List<OpenAccountImageInfoProtocol> imgInfo = imgHandle(temp);
			protocol.setOpenAccountImagesInfo(imgInfo);
		}
		if (temp.getStep() == 3) {
			// 职业信息与财务状况
			// 资产凭证图片
			// 图片处理
			List<OpenAccountImageInfoProtocol> imgInfo = imgHandle(temp);
			protocol.setOpenAccountImagesInfo(imgInfo);
			// 财产种类
			List<OpenAccountPropertyTypeProtocol> propertyTypeInfo = propertyHandle(tempVO);
			protocol.setPropertyTypeList(propertyTypeInfo);
			//财富来源
		}
		if (temp.getStep() == 4) {
			// 投资目标

			// 图片处理
			List<OpenAccountImageInfoProtocol> imgInfo = imgHandle(temp);
			protocol.setOpenAccountImagesInfo(imgInfo);
		}
		if (temp.getStep() == 5) {
			// 税务信息
			List<OpenAccountTaxationInfoProtocol> taxInfo = taxInfoHandle(tempVO);
			protocol.setTaxationInfoList(taxInfo);
		}
		protocol.setClientName(protocol.getFamilyName()+protocol.getGivenName());
		protocol.setClientNameSpell(protocol.getFamilyNameSpell()+" "+protocol.getGivenNameSpell());
		return protocol;
	}

	private List<OpenAccountTaxationInfoProtocol> taxInfoHandle(SecUserInfoChangeResp tempVO) {
		ArrayList<OpenAccountTaxationInfoProtocol> list = Lists.newArrayList();

		if (StringUtils.isNotEmpty(tempVO.getDefaultCountry())&&StringUtils.isNotEmpty(tempVO.getDefaultPrivacyNum())) {
			OpenAccountTaxationInfoProtocol protocol = new OpenAccountTaxationInfoProtocol();
			protocol.setTaxCountry(tempVO.getDefaultCountry());
			protocol.setHasTaxNumber(1);
			protocol.setTaxNumber(tempVO.getDefaultPrivacyNum());
			list.add(protocol);
		}

		if (StringUtils.isNotEmpty(tempVO.getCountry1())) {
			OpenAccountTaxationInfoProtocol protocol = new OpenAccountTaxationInfoProtocol();
			protocol.setTaxCountry(tempVO.getCountry1());
			if (StringUtils.isNotEmpty(tempVO.getOfferPrivacy1()) && "1".equals(tempVO.getOfferPrivacy1())) {
				protocol.setHasTaxNumber(1);
				protocol.setTaxNumber(tempVO.getCanPrivacyNum1());
			}else {
				protocol.setHasTaxNumber(0);
				protocol.setReasonType(tempVO.getNoOfferPrivacy1());
				protocol.setReasonDesc(tempVO.getReasonDesc1());
			}
			list.add(protocol);
		}

		if (StringUtils.isNotEmpty(tempVO.getCountry2())) {
			OpenAccountTaxationInfoProtocol protocol = new OpenAccountTaxationInfoProtocol();
			protocol.setTaxCountry(tempVO.getCountry2());
			if (StringUtils.isNotEmpty(tempVO.getOfferPrivacy2()) && "1".equals(tempVO.getOfferPrivacy2())) {
				protocol.setHasTaxNumber(1);
				protocol.setTaxNumber(tempVO.getCanPrivacyNum2());
			}else {
				protocol.setHasTaxNumber(0);
				protocol.setReasonType(tempVO.getNoOfferPrivacy2());
				protocol.setReasonDesc(tempVO.getReasonDesc2());
			}
			list.add(protocol);
		}

		if (StringUtils.isNotEmpty(tempVO.getCountry3())) {
			OpenAccountTaxationInfoProtocol protocol = new OpenAccountTaxationInfoProtocol();
			protocol.setTaxCountry(tempVO.getCountry3());
			if (StringUtils.isNotEmpty(tempVO.getOfferPrivacy3()) && "1".equals(tempVO.getOfferPrivacy3())) {
				protocol.setHasTaxNumber(1);
				protocol.setTaxNumber(tempVO.getCanPrivacyNum3());
			}else {
				protocol.setHasTaxNumber(0);
				protocol.setReasonType(tempVO.getNoOfferPrivacy3());
				protocol.setReasonDesc(tempVO.getReasonDesc3());
			}
			list.add(protocol);
		}

		if (StringUtils.isNotEmpty(tempVO.getCountry4())) {
			OpenAccountTaxationInfoProtocol protocol = new OpenAccountTaxationInfoProtocol();
			protocol.setTaxCountry(tempVO.getCountry4());
			if (StringUtils.isNotEmpty(tempVO.getOfferPrivacy4()) && "1".equals(tempVO.getOfferPrivacy4())) {
				protocol.setHasTaxNumber(1);
				protocol.setTaxNumber(tempVO.getCanPrivacyNum4());
			}else {
				protocol.setHasTaxNumber(0);
				protocol.setReasonType(tempVO.getNoOfferPrivacy4());
				protocol.setReasonDesc(tempVO.getReasonDesc4());
			}
			list.add(protocol);
		}

		return list;
	}

	private List<OpenAccountPropertyTypeProtocol> propertyHandle(SecUserInfoChangeResp changeInfo) {

		ArrayList<OpenAccountPropertyTypeProtocol> list = Lists.newArrayList();
		if (changeInfo.getBankCheck()!=null&&changeInfo.getBankCheck()) {
			OpenAccountPropertyTypeProtocol protocol = new OpenAccountPropertyTypeProtocol();
			protocol.setPropertyType(1);
			protocol.setPropertyAmount(changeInfo.getBankValue());
			list.add(protocol);
		}
		if (changeInfo.getStockCheck()!=null&&changeInfo.getStockCheck()) {
			OpenAccountPropertyTypeProtocol protocol = new OpenAccountPropertyTypeProtocol();
			protocol.setPropertyType(2);
			protocol.setPropertyAmount(changeInfo.getStockValue());
			list.add(protocol);
		}
		if (changeInfo.getRealCheck()!=null&&changeInfo.getRealCheck()) {
			OpenAccountPropertyTypeProtocol protocol = new OpenAccountPropertyTypeProtocol();
			protocol.setPropertyType(3);
			protocol.setPropertyAmount(changeInfo.getRealValue());
			list.add(protocol);
		}

		return list;
	}

	private void addressInfoHandle(SecUserInfoChangeResp tempVO, SecUserInfoChangeProtocol protocol ) {

		if (protocol.getStep() == 7 || protocol.getStep() == 2) {
			List<Map> list = JSONObject.parseArray(tempVO.getFamilyCity(), Map.class);
			if(null != list && list.size() > 2) {
				Map map = list.get(0);
				protocol.setFamilyProvinceName(String.valueOf(map.get("text")));
				map = list.get(1);
				protocol.setFamilyCityName(String.valueOf(map.get("text")));
				map = list.get(2);
				protocol.setFamilyCountyName(String.valueOf(map.get("text")));
				String s = tempVO.getFamilyAddressNum() == null ? "" : tempVO.getFamilyAddressNum();
				protocol.setFamilyDetailAddress(tempVO.getFamilyAddressDetail() + " " + s);

				// 现实住址：国家地区处理,拼接地址处理
				if ("1".equals(tempVO.getFamilyAddressRadio())) {
					// 中国大陆
					protocol.setFamilyRepublicName("CHN");
					protocol.setFamilyAddress(protocol.getFamilyProvinceName() + protocol.getFamilyCityName() + protocol.getFamilyCountyName() + protocol.getFamilyDetailAddress().replace(" ", ""));
					// 直辖市处理(拼接地址不带省份)
					if (protocol.getFamilyCityName().contains(protocol.getFamilyProvinceName())) {
						protocol.setFamilyAddress(protocol.getFamilyCityName() + protocol.getFamilyCountyName() + protocol.getFamilyDetailAddress().replace(" ", ""));
					}
				}
				if ("2".equals(tempVO.getFamilyAddressRadio())) {
					// 中国香港
					protocol.setFamilyRepublicName("HKG");
					// 拼接地址不带省份
					protocol.setFamilyAddress(protocol.getFamilyCityName() + protocol.getFamilyCountyName() + protocol.getFamilyDetailAddress().replace(" ", ""));
				}
				if ("3".equals(tempVO.getFamilyAddressRadio())) {
					// 当前端选择其他地区时
					protocol.setFamilyRepublicName(tempVO.getFamilyOhterCountry());
					// 拼接地址处理
					protocol.setFamilyAddress(tempVO.getFamilyRepublicName() + protocol.getFamilyProvinceName() + protocol.getFamilyCityName() + protocol.getFamilyCountyName() + protocol.getFamilyDetailAddress().replace(" ", ""));
					// 当前端选择其他地区时又选择其他地区
					if ("OTH".equals(tempVO.getFamilyOhterCountry())) {
						protocol.setFamilyRepublicName("OTH");
						protocol.setOtherFamilyRepublic(tempVO.getOtherFamilyRepublic());
						protocol.setFamilyAddress(tempVO.getOtherFamilyRepublic() + protocol.getFamilyProvinceName() + protocol.getFamilyCityName() + protocol.getFamilyCountyName() + protocol.getFamilyDetailAddress().replace(" ", ""));
					}
				}
			}
		}

		if (protocol.getStep() == 8 || protocol.getStep() == 2) {
			List<Map> list = JSONObject.parseArray(tempVO.getContactAddressCity(), Map.class);
			Map map = list.get(0);
			protocol.setContactProvinceName(String.valueOf(map.get("text")));
			map = list.get(1);
			protocol.setContactCityName(String.valueOf(map.get("text")));
			map = list.get(2);
			protocol.setContactCountyName(String.valueOf(map.get("text")));
			String s = tempVO.getContactAddressNum() == null ? "" : tempVO.getContactAddressNum();
			protocol.setContactDetailAddress(tempVO.getContactAddressDetail() + " " + s);

			// 通讯住址：国家地区处理、拼接地址处理
			if ("1".equals(tempVO.getContactAddressRadio())) {
				// 中国大陆
				protocol.setContactRepublicName("CHN");
				protocol.setContactAddress(protocol.getContactProvinceName() + protocol.getContactCityName() + protocol.getContactCountyName() + protocol.getContactDetailAddress().replace(" ", ""));
				// 直辖市处理(拼接地址不带省份)
				if (protocol.getContactCityName().contains(protocol.getContactProvinceName())) {
					protocol.setContactAddress(protocol.getContactCityName() + protocol.getContactCountyName() + protocol.getContactDetailAddress().replace(" ", ""));
				}
			}
			if ("2".equals(tempVO.getContactAddressRadio())) {
				// 中国香港
				protocol.setContactRepublicName("HKG");
				// 拼接地址不带省份
				protocol.setContactAddress(protocol.getContactCityName() + protocol.getContactCountyName() + protocol.getContactDetailAddress().replace(" ", ""));
			}
			if ("3".equals(tempVO.getContactAddressRadio())) {
				// 当前端选择其他地区时
				protocol.setContactRepublicName(tempVO.getContactOhterCountry());
				// 拼接地址处理
				protocol.setContactAddress(tempVO.getContactRepublicName() + protocol.getContactProvinceName() + protocol.getContactCityName() + protocol.getContactCountyName() + protocol.getContactDetailAddress().replace(" ", ""));
				// 当前端选择其他地区时又选择其他地区
				if ("OTH".equals(tempVO.getContactOhterCountry())) {
					protocol.setContactRepublicName("OTH");
					protocol.setOtherContactRepublic(tempVO.getOhterContactRepublic());
					protocol.setContactAddress(tempVO.getOhterContactRepublic() + protocol.getContactProvinceName() + protocol.getContactCityName() + protocol.getContactCountyName() + protocol.getContactDetailAddress().replace(" ", ""));
				}
			}
			if ("4".equals(tempVO.getContactAddressRadio())) {
				// 通讯地址同现时地址
				protocol.setContactRepublicName(protocol.getFamilyRepublicName());
				protocol.setOtherContactRepublic(protocol.getOtherFamilyRepublic());
				protocol.setContactAddress(protocol.getFamilyAddress());
			}
		}

	}

	private  List<OpenAccountImageInfoProtocol> imgHandle(AcctChangeInfoTempEntity temp) {
		LambdaQueryWrapper<AcctChangeImageEntity> imageWrapper = new LambdaQueryWrapper<>();
		imageWrapper.eq(AcctChangeImageEntity::getChangeId,temp.getId());
		List<AcctChangeImageEntity> imageList = acctChangeImageService.list(imageWrapper);
		if(CollectionUtil.isEmpty(imageList)) return null;
		ArrayList<OpenAccountImageInfoProtocol> imageInfoProtocols = Lists.newArrayList();
		for(AcctChangeImageEntity imageEntity : imageList) {
			OpenAccountImageInfoProtocol imageInfoProtocol = new OpenAccountImageInfoProtocol();
			imageInfoProtocol.setImageLocation(imageEntity.getImageLocation().intValue());
			imageInfoProtocol.setImageLocationType(imageEntity.getImageLocationType().intValue());
			imageInfoProtocol.setImageUrl(imageEntity.getImgPath());
			imageInfoProtocols.add(imageInfoProtocol);
		}
		return imageInfoProtocols;
	}



	private AcctChangeInfoTempVO getUserInfoByBpm(Long custId, Integer step,boolean real){
		SecuritiesUserInfoFullResp request = new SecuritiesUserInfoFullResp();
		request.setUserId(custId);
		SecuritiesUserInfoFullResp securitiesUserFullInfo = getSecUserInfoFromBpm(request);
		if(null == securitiesUserFullInfo) return null;
		SecUserInfoChangeResp userInfo = SecUserInfoChangeHelper.getChangeVO(step, securitiesUserFullInfo);
		AcctChangeInfoTempVO changeTemp = new AcctChangeInfoTempVO();
		changeTemp.setStep(step);
		changeTemp.setJsonInfo(JSONObject.toJSONString(userInfo));
		changeTemp.setCustId(custId);
		changeTemp.setCreateTime(new Date());

		if(!real) {
			changeTemp.setCheckStatus(BpmConstants.SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED);
			// 缓存数据，方便下次查询
			changeInfoTempService.getBaseMapper().insert(changeTemp);
			// 从cust 获取开户时上传的图片
			String imgList = null;
			// 新增地址证明，地址证明相关的步骤也要查询图片，地址证明相关步骤 2，7，8
			if (BpmConstants.STEP_5 != step && BpmConstants.STEP_6 != step) {
				imgList = getImgListOpenAccount(custId, step, changeTemp.getId());
			}
			changeTemp.setImgList(imgList);
		}

		return changeTemp;
	}

	private String getImgListOpenAccount(Long custId, Integer step,Long changeId){
		String imgListJonStr = null;
		List<AcctChangeImageEntity> secImgList = new ArrayList<>();

		LambdaQueryWrapper<AcctOpenImageEntity> openImageWrapper = new LambdaQueryWrapper<>();
		openImageWrapper.eq(AcctOpenImageEntity::getCustId, custId);
		List<AcctOpenImageEntity> imgs = acctOpenImageService.list(openImageWrapper);
		if(CollectionUtil.isNotEmpty(imgs)) {
			for(AcctOpenImageEntity img : imgs) {
				AcctChangeImageEntity updImg = new AcctChangeImageEntity();
				BeanUtil.copyProperties(img,updImg);
				updImg.setChangeId(changeId);
				updImg.setId(null);
				secImgList.add(updImg);
			}
 		}
		if (CollectionUtils.isEmpty(secImgList)) {
			return imgListJonStr;
		}
		List<AcctChangeImageVO> imgVOList = imgFilter(secImgList, step);
		if(null != imgVOList && imgVOList.size() >0) {
			imgVOList.stream().map(d -> {d.setImgPath(null);d.setChangeTag(true);return d;}).collect(Collectors.toList());
			imgListJonStr = JSONObject.toJSONString(imgVOList,SerializerFeature.WriteNonStringValueAsString);
		}

		return imgListJonStr;
	}


	private  List<AcctChangeImageVO> getImgList(Long changeId) {
		List<AcctChangeImageVO> list = new ArrayList<>();
		LambdaQueryWrapper<AcctChangeImageEntity> changeImageWrapper = new LambdaQueryWrapper<>();
		changeImageWrapper.eq(AcctChangeImageEntity::getChangeId, changeId);
		List<AcctChangeImageEntity> imgs = acctChangeImageService.list(changeImageWrapper);
		if(CollectionUtil.isNotEmpty(imgs)) {
			for(AcctChangeImageEntity entity : imgs) {
				AcctChangeImageVO vo = new AcctChangeImageVO();
				BeanUtil.copyProperties(entity,vo);
				//get_securities_temp 返回，区别图片来至true: 修改资料/ false :开户
				vo.setChangeTag(true);
				list.add(vo);
			}
			list.stream().map(d -> {d.setImgPath(null);return d;}).collect(Collectors.toList());
		}
		return list;
	}

	private List<AcctChangeImageVO> imgFilter(List<AcctChangeImageEntity> list, Integer step) {
//        String[] typesAll = {"101", "102", "103", "104", "105", "601", "602", "603", "604", "605"};
		String[] types1 = {BpmConstants.ImgLocationTypeEnum.TYP_101.value, BpmConstants.ImgLocationTypeEnum.TYP_102.value,
			BpmConstants.ImgLocationTypeEnum.TYP_103.value, BpmConstants.ImgLocationTypeEnum.TYP_104.value,
			BpmConstants.ImgLocationTypeEnum.TYP_105.value,BpmConstants.ImgLocationTypeEnum.TYP_107.value,BpmConstants.ImgLocationTypeEnum.TYP_108.value};
		String[] types3 = {BpmConstants.ImgLocationTypeEnum.TYP_604.value};
		String[] types4 = {BpmConstants.ImgLocationTypeEnum.TYP_601.value, BpmConstants.ImgLocationTypeEnum.TYP_602.value, BpmConstants.ImgLocationTypeEnum.TYP_603.value};
		String[] types2 = {BpmConstants.ImgLocationTypeEnum.TYP_801.value};
		List<AcctChangeImageVO> listHandled = Lists.newArrayList();
		for (AcctChangeImageEntity img : list) {

			boolean saveFlag = false;

			// locationType过滤
			if (BpmConstants.STEP_1 == step) {
				// 证件照
				if (Arrays.asList(types1).contains(img.getImageLocationType().toString())) {
					saveFlag = true;
				}
			}
			if (BpmConstants.STEP_3 == step) {
				// 职业信息与财务状况
				if (Arrays.asList(types3).contains(img.getImageLocationType().toString())) {
					saveFlag = true;
				}
			}
			if (BpmConstants.STEP_4 == step) {
				// 投资
				if (Arrays.asList(types4).contains(img.getImageLocationType().toString())) {
					saveFlag = true;
				}
			}
			if (BpmConstants.STEP_2 == step || BpmConstants.STEP_7 == step || BpmConstants.STEP_8 == step) {
				// 住址证明
				if (Arrays.asList(types2).contains(img.getImageLocationType().toString())) {
					saveFlag = true;
				}
			}
			if(saveFlag) {
				if(null != img.getId()) {
					acctChangeImageService.getBaseMapper().updateById(img);
				} else {
					acctChangeImageService.getBaseMapper().insert(img);
				}
				listHandled.add(BeanUtil.copyProperties(img,AcctChangeImageVO.class));
			}
		}

		return listHandled;
	}


	private SecuritiesUserInfoFullResp getSecUserInfoFromBpm(SecuritiesUserInfoFullResp condition) {

		BpmSecuritiesInfoEntity securitiesInfo = bpmSecuritiesInfoService.securitiesInfoByCustId(condition.getUserId());
		if (securitiesInfo == null){
			return new SecuritiesUserInfoFullResp();
		}
		SecuritiesUserInfoFullResp securitiesUserFullInfo = new SecuritiesUserInfoFullResp();
		BeanUtils.copyProperties(securitiesInfo,securitiesUserFullInfo);
		return securitiesUserFullInfo;
	}
}

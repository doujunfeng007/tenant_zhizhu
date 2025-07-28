package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.mapper.PlatformCommonTemplateMapper;
import com.minigod.zero.manage.service.IPlatformCommonTemplateService;
import com.minigod.zero.manage.vo.MsgTemplateVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;
import com.minigod.zero.platform.enums.EmailChannel;
import com.minigod.zero.platform.enums.MsgType;
import com.minigod.zero.platform.enums.PushChannel;
import com.minigod.zero.platform.enums.SmsChannelType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlatformCommonTemplateServiceImpl extends BaseServiceImpl<PlatformCommonTemplateMapper, PlatformCommonTemplateEntity> implements IPlatformCommonTemplateService {

	private final PlatformCommonTemplateMapper platformCommonTemplateMapper;


	@Override
	public R<IPage<MsgTemplateVO>> getTemplateList(IPage page, String keyword, Integer msgType) {
		List<PlatformCommonTemplateEntity> list = platformCommonTemplateMapper.queryTemplatePage(page,keyword,msgType,AuthUtil.getTenantId());
		if (CollectionUtils.isEmpty(list)){
			return R.data(page);
		}
		List<MsgTemplateVO> pageList = new ArrayList<>();
		list.stream().forEach(template->{
			MsgTemplateVO templateVO = new MsgTemplateVO();
			BeanUtils.copyProperties(template,templateVO);

			Integer templateType = template.getMsgType();
			if (MsgType.SMS.getCode() == templateType){
				templateVO.setChannelName(SmsChannelType.Tencent.getName());
			}
			if (MsgType.EMAIL.getCode() == templateType){
				templateVO.setChannelName(EmailChannel.SEND_CLOUD.getName());
			}
			if (MsgType.PUSH.getCode() == templateType){
				templateVO.setChannelName(PushChannel.JG.getName());
			}
			/*if(template.getContent() != null ){
				Document document = new Document(template.getContent());

				templateVO.setContent(document.text());
			}
			if(template.getContentEn() != null){
				Document document = new Document(template.getContentEn());
				templateVO.setContentEn(document.text());
			}
			if(template.getContentHant() != null ){
				Document document = new Document(template.getContentHant());
				templateVO.setContentHant(document.text());
			}*/
			String statusName = null;
			if (template.getStatus() == 1 ){
				statusName = "可用";
			}else{
				statusName = "不可用";
			}
			templateVO.setStatusName(statusName);
			templateVO.setTempCodeHant(template.getTempCodeHant());
			pageList.add(templateVO);
		});
		page.setRecords(pageList);
		return R.data(page);
	}

	@Override
	public R<PlatformCommonTemplateEntity> getTemplateDetail(Long id) {
		return R.data(platformCommonTemplateMapper.selectById(id));
	}

	@Override
	public R addTemplate(PlatformCommonTemplateEntity template) {
		//参数校验
		paramCheck(template);
		if (template.getMsgType() != MsgType.SMS.getCode()){
			template.setTempCodeHans(template.getTempCode().toString());
			template.setTempCodeHant(template.getTempCode().toString());
			template.setTempCodeEn(template.getTempCode().toString());
		}
		platformCommonTemplateMapper.insert(template);
		return R.success();
	}

	@Override
	public R updateTemplate(PlatformCommonTemplateEntity template) {
		if (template.getId() == null){
			throw new ZeroException("修改失败，id不能为空");
		}
		paramCheck(template);
		platformCommonTemplateMapper.updateById(template);
		return R.success();
	}

	@Override
	public R deleteTemplate(Long id) {
		if (id == null){
			throw new ZeroException("删除失败，id不能为空");
		}
		PlatformCommonTemplateEntity templateEntity = new PlatformCommonTemplateEntity();
		templateEntity.setId(id);
		templateEntity.setIsDeleted(1);
		platformCommonTemplateMapper.updateById(templateEntity);
		return R.success();
	}

	@Override
	public R selectTemplateByMsgType(Integer msgType) {
		return R.data(platformCommonTemplateMapper.selectTemplateByMsgType(AuthUtil.getTenantId(),msgType));
	}


	private void paramCheck(PlatformCommonTemplateEntity template){
		if (template.getTempCode() == null){
			throw new ZeroException("模板编号 不能为空");
		}
		if (StringUtils.isEmpty(template.getTitle())){
			throw new ZeroException("标题 不能为空");
		}
		if (StringUtils.isEmpty(template.getContent())){
			throw new ZeroException("模板内容 不能为空");
		}
		PlatformCommonTemplateEntity templateEntity =
			platformCommonTemplateMapper.selectByTemplateCode(template.getTempCode(),AuthUtil.getTenantId());
		if (templateEntity != null && template.getId() == null){
			throw new ZeroException("模板编号已存在");
		}
		if (MsgType.SMS.getCode() == template.getMsgType()){
			if (template.getTempCodeHans() == null){
				throw new ZeroException("第三方模板编号不能为空");
			}
		}
		//可用
		template.setStatus(1);
		template.setTenantId(AuthUtil.getTenantId());
		if (template.getId() == null){
			template.setCreateTime(new Date());
			template.setCreateUser(AuthUtil.getUserId());
			template.setCreateUserName(AuthUtil.getUserName());
		}else{
			template.setUpdateTime(new Date());
			template.setUpdateUser(AuthUtil.getUserId());
		}
	}

}

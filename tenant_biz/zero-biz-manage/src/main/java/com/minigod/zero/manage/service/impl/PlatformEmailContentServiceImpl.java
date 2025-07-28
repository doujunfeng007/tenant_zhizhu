package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.manage.mapper.PlatformEmailContentMapper;
import com.minigod.zero.manage.service.IPlatformEmailContentService;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.utils.PlatformUtil;
import com.minigod.zero.platform.vo.PlatformEmailContentVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.minigod.zero.manage.comm.EamilFilterCode.EAMIL_FILTER_CODE;

/**
 * 短信内容信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Service
@RequiredArgsConstructor
public class PlatformEmailContentServiceImpl extends BaseServiceImpl<PlatformEmailContentMapper, PlatformEmailContentEntity> implements IPlatformEmailContentService {


	@Override
	public IPage<PlatformEmailContentVO> selectPlatformEmailContentPage(IPage<PlatformEmailContentEntity> page, PlatformEmailContentVO platformEmailContent) {
		if (StringUtils.isNotBlank(platformEmailContent.getSendDate())) {
			Date sendDateStart = DateUtil.parseDate(platformEmailContent.getSendDate());
			Date sendDateEnd = DateUtil.parseDate(platformEmailContent.getSendDate() + " 23:59:59");
			platformEmailContent.setSendDateStart(sendDateStart);
			platformEmailContent.setSendDateEnd(sendDateEnd);
		}
		IPage<PlatformEmailContentVO> platformEmailContentPage = baseMapper.selectPlatformEmailContentPage(page, platformEmailContent);

		List<PlatformEmailContentVO> records = platformEmailContentPage.getRecords();
		for (PlatformEmailContentVO vo : records) {
			String maskedContent = vo.getContent();

			if (Arrays.stream(EAMIL_FILTER_CODE).anyMatch(code -> code.equals(String.valueOf(vo.getTempCode())))){
				maskedContent = (maskedContent.replaceAll("密码：\\d{6}", "密码：******"));
				maskedContent = (maskedContent.replaceAll("验证码：\\d{6}", "验证码：******"));
				if (vo.getTempCode()==EmailTemplate.OPEN_SUB_ACCOUNT.getCode()) {
					maskedContent = (maskedContent.replaceAll(" \\d{6}", " ******"));
				}
			}
			vo.setContent(maskedContent);
			vo.setContent(PlatformUtil.getDataMaskContent(vo.getContent()));
		}

		return platformEmailContentPage;
	}


}

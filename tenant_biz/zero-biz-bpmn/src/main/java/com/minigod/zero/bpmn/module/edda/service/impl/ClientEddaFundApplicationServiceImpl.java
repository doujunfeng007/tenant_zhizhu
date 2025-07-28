package com.minigod.zero.bpmn.module.edda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.bpmn.module.dbs.DbsEddaFundBusinessService;
import com.minigod.zero.bpmn.module.deposit.bo.HistoryRecordBo;
import com.minigod.zero.bpmn.module.edda.bo.ClientEddaFundListBO;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.mapper.ClientEddaFundApplicationMapper;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaFundApplicationService;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author dell
* @description 针对表【client_edda_fund_application(DBS edda入金流水表)】的数据库操作Service实现
* @createDate 2024-05-10 15:43:22
*/
@Slf4j
@Service
@AllArgsConstructor
public class ClientEddaFundApplicationServiceImpl extends ServiceImpl<ClientEddaFundApplicationMapper, ClientEddaFundApplicationEntity>
    implements ClientEddaFundApplicationService{

	/**
	 * 后台审批入金申请
	 * @param applicationId
	 * @return
	 */
	@Override
	public R approval(String applicationId) {
		if (StringUtils.isEmpty(applicationId)){
			throw new ZeroException("审核失败，applicationId不能为空");
		}
		ClientEddaFundApplicationEntity clientEddaInfoApplication = baseMapper.selectOne(Wrappers.<ClientEddaFundApplicationEntity>lambdaQuery()
			.eq(ClientEddaFundApplicationEntity::getApplicationId, applicationId));
		if(clientEddaInfoApplication == null){
			return R.fail("数据不存在");
		}
		if (SystemCommonEnum.EDDABankStateTypeEnum.UN_COMMNIT.getValue() != clientEddaInfoApplication.getBankState()){
			return R.fail("数据状态异常");
		}
		//edda定时任务扫描获取任务
		int update = baseMapper.update(null, Wrappers.<ClientEddaFundApplicationEntity>lambdaUpdate()
			.set(ClientEddaFundApplicationEntity::getBankState, SystemCommonEnum.EDDABankStateTypeEnum.AUTHORIZING.getValue())
			.eq(ClientEddaFundApplicationEntity::getApplicationId, applicationId));
		if(update == 1){
			return R.success("审批成功");
		}
		return R.fail("数据异常");
	}

	@Override
	public IPage<ClientEddaFundApplicationEntity> queryPage(ClientEddaFundListBO bo, Query query) {
		LambdaQueryWrapper<ClientEddaFundApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getApplicationId()), ClientEddaFundApplicationEntity::getApplicationId, bo.getApplicationId());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getClientId()), ClientEddaFundApplicationEntity::getClientId, bo.getClientId());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getFundAccount()), ClientEddaFundApplicationEntity::getFundAccount, bo.getFundAccount());
		queryWrapper.like(ObjectUtil.isNotEmpty(bo.getMobile()), ClientEddaFundApplicationEntity::getMobile, bo.getMobile());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getEName()), ClientEddaFundApplicationEntity::getEName, bo.getEName());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getCName()), ClientEddaFundApplicationEntity::getCName, bo.getCName());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getBankState()), ClientEddaFundApplicationEntity::getBankState, bo.getBankState());
		queryWrapper.gt(ObjectUtil.isNotEmpty(bo.getApplicationStartTime()), ClientEddaFundApplicationEntity::getApplicationTime, bo.getApplicationStartTime());

		if (ObjectUtil.isNotEmpty(bo.getKeyworld())) {
			queryWrapper.eq(ClientEddaFundApplicationEntity::getClientId,  bo.getKeyworld())
				.or().eq(ClientEddaFundApplicationEntity::getEName, bo.getKeyworld())
				.or().eq(ClientEddaFundApplicationEntity::getEName, bo.getKeyworld())
				.or().eq(ClientEddaFundApplicationEntity::getMobile, bo.getKeyworld());
		}
		queryWrapper.lt(ObjectUtil.isNotEmpty(bo.getApplicationEndTime()), ClientEddaFundApplicationEntity::getApplicationTime, bo.getApplicationEndTime());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getEmail()), ClientEddaFundApplicationEntity::getEmail, bo.getEmail());
		queryWrapper.orderByDesc(ClientEddaFundApplicationEntity::getCreateTime);
		return super.page(Condition.getPage(query), queryWrapper);
	}


	/**
	 * 查询需要上送给银行的edda入金数据
	 * @return
	 */
	@Override
	public List<ClientEddaFundApplicationEntity> queryNotSendBankFundList() {
		List<ClientEddaFundApplicationEntity> list = baseMapper.selectList(Wrappers.<ClientEddaFundApplicationEntity>lambdaQuery()
			.eq(ClientEddaFundApplicationEntity::getBankState, SystemCommonEnum.EDDABankStateTypeEnum.UN_COMMNIT.getValue()));
		return list;
	}
}





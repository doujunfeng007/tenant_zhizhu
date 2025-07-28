package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.deposit.bo.DepositBankInfoBo;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositBankEntity;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositBankMapper;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositBankService;
import com.minigod.zero.bpmn.module.deposit.vo.*;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 入金银行配置表 服务实现类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Service
public class SecDepositBankServiceImpl extends ServiceImpl<SecDepositBankMapper, SecDepositBankEntity> implements ISecDepositBankService {

    @Override
    public IPage<SecDepositBankVO> selectSecDepositBankPage(IPage<SecDepositBankVO> page, SecDepositBankVO secDepositBankVO) {
        return page.setRecords(baseMapper.selectSecDepositBankPage(page, secDepositBankVO));
    }

    @Override
    public List<DepositBankRespVO> findDepositBankSetting(DepositBankInfoBo params) {

		String tenantId = ObjectUtil.isNotEmpty(AuthUtil.getTenantId())? AuthUtil.getTenantId() : "000000";
		LambdaQueryWrapper<SecDepositBankEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(ObjectUtil.isNotEmpty(params.getBankType()), SecDepositBankEntity::getBankType, params.getBankType())
                .eq(ObjectUtil.isNotEmpty(params.getMoneyType()), SecDepositBankEntity::getMoneyType, params.getMoneyType())
                .eq(StringUtil.isNotBlank(params.getKey()), SecDepositBankEntity::getRemitBankCode, params.getKey())
                //.eq(SecDepositBankEntity::getTenantId, AuthUtil.getTenantId())
                .eq(SecDepositBankEntity::getTenantId, tenantId)
                .eq(SecDepositBankEntity::getIsShow, 1)
                .orderByAsc(SecDepositBankEntity::getSort);
		if (ObjectUtil.isNotEmpty(params.getSupportType())) {
			lambdaQueryWrapper.apply("FIND_IN_SET({0}, support_type)", params.getSupportType());
		}



        List<DepositBankRespVO> list = baseMapper.selectList(lambdaQueryWrapper).stream().map(entityClass -> convertDepositBankRespVO(entityClass)).collect(Collectors.toList());
        return list;
    }

    /**
     * 转换 DepositBankRespVO
     *
     * @param bank
     * @return
     */
    private DepositBankRespVO convertDepositBankRespVO(SecDepositBankEntity bank) {
        DepositBankRespVO respVO = new DepositBankRespVO();
        respVO.setId(bank.getId());
        respVO.setCode(bank.getSort());
        respVO.setAppBanklogo(bank.getAppIcon());
        respVO.setPcBanklogo(bank.getPcIcon());
        respVO.setName(bank.getRemitBankName());
        respVO.setKey(bank.getRemitBankCode());
        respVO.setDepositCertImg(bank.getDepositCertImg());

        respVO.setDescFee(bank.getChargeMoney());
        respVO.setDescTime(bank.getTimeArrival());
        respVO.setDescTimeTips(bank.getTimeArrivalRemark());
        respVO.setSupportType(bank.getSupportType());
        respVO.setBgColor(bank.getBgColor());
        respVO.setShadowColor(bank.getShadowColor());

        respVO.setMaxAmt(bank.getMaxAmt());
        respVO.setMoneyType(bank.getMoneyType());
        respVO.setBankType(bank.getBankType());


        DepositBankToAccountVO depositBankToAccountVO = new DepositBankToAccountVO();
        depositBankToAccountVO.setHkd(bank.getReceiptAccountPayeeHkd());
        depositBankToAccountVO.setHkdRemark(bank.getReceiptAccountPayeeHkdRemark());
        depositBankToAccountVO.setUsd(bank.getReceiptAccountPayeeUsd());
        depositBankToAccountVO.setUsdRemark(bank.getReceiptAccountPayeeUsdRemark());

        DepositBankInfoVO depositBankInfoVO = new DepositBankInfoVO();
        depositBankInfoVO.setDepositToAccount(depositBankToAccountVO);
        depositBankInfoVO.setCode(bank.getReceiptBankCode());
        depositBankInfoVO.setBankName(bank.getReceiptBankName());
        depositBankInfoVO.setAccountName(bank.getReceiptAccountName());
        depositBankInfoVO.setAccountNameRemark(bank.getReceiptAccountNameRemark());
        depositBankInfoVO.setSwiftCode(bank.getSwiftCode());
        depositBankInfoVO.setDepositToBankAddress(bank.getReceiptBankAddress());
        depositBankInfoVO.setDescTimeRemark(bank.getTimeArrivalRemark());
        depositBankInfoVO.setDepositUserAddress(bank.getReceiptPayeeAddress());
        depositBankInfoVO.setBankNameEN(bank.getReceiptBankNameSpell());
        depositBankInfoVO.setAccountType(bank.getAccountType());
        respVO.setBankInfo(depositBankInfoVO);

        DepositBankTipsLinkdsVO depositBankTipsLinkdsVO = new DepositBankTipsLinkdsVO();
        depositBankTipsLinkdsVO.setAppWyHkd(bank.getAppWyGuideUrlHkd());
        depositBankTipsLinkdsVO.setAppWyUsd(bank.getAppWyGuideUrlUsd());
        depositBankTipsLinkdsVO.setPcWyHkd(bank.getPcWyGuideUrlHkd());
        depositBankTipsLinkdsVO.setPcWyUsd(bank.getPcWyGuideUrlUsd());
        depositBankTipsLinkdsVO.setGt("");
        respVO.setTipsLinks(depositBankTipsLinkdsVO);
        respVO.setBankIdQuick(bank.getBankIdQuick());

		DepositBankFpsInfoVO depositBankFpsInfoVO = new DepositBankFpsInfoVO();

		depositBankFpsInfoVO.setAccountFps(bank.getAccountFps());
		depositBankFpsInfoVO.setReceiptBankCodeFps(bank.getReceiptBankCodeFps());
		depositBankFpsInfoVO.setReceiptBankNameFps(bank.getReceiptBankNameFps());
		depositBankFpsInfoVO.setChargeMoneyFps(bank.getChargeMoneyFps());
		depositBankFpsInfoVO.setChargeMoneyRemarkFps(bank.getChargeMoneyRemarkFps());
		depositBankFpsInfoVO.setTimeArrivalFps(bank.getTimeArrivalFps());
		depositBankFpsInfoVO.setTimeArrivalRemarkFps(bank.getTimeArrivalRemarkFps());
		depositBankFpsInfoVO.setAppGuideUrlFps(bank.getAppGuideUrlFps());
		depositBankFpsInfoVO.setPcGuideUrlFps(bank.getPcGuideUrlFps());
		depositBankFpsInfoVO.setDepositCertImgFps(bank.getDepositCertImgFps());
		depositBankFpsInfoVO.setReceiptAccountNameFps(bank.getReceiptAccountNameFps());
		depositBankFpsInfoVO.setReceiptBankNoFps(bank.getReceiptBankNoFps());
		respVO.setDepositBankFpsInfoVO(depositBankFpsInfoVO);

		return respVO;
    }

    /**
     * 转换 SecDepositTypeVO
     * @param entity
     * @return
     */
    private SecDepositTypeVO convertSecDepositTypeVO(SecDepositBankEntity entity) {
        SecDepositTypeVO VO = new SecDepositTypeVO();
        VO.setDepositType(1);
        VO.setDescFee(entity.getChargeMoneyFps());
        VO.setDescFeeRemark(entity.getChargeMoneyRemarkFps());
        VO.setBankName(entity.getReceiptBankNameFps());
        VO.setBankCode(entity.getRemitBankCode());
        VO.setAccountName(entity.getReceiptAccountNameFps());
        VO.setAccountNameRemark("");
        VO.setDescTime(entity.getTimeArrivalFps());
        VO.setDescTimeRemark(entity.getTimeArrivalRemarkFps());
        VO.setAccountFPS(entity.getAccountFps());
        VO.setReceiptBankNoFps(entity.getReceiptBankNoFps());
        VO.setSwiftCode(entity.getSwiftCode());
        VO.setReceiptBankAddress(entity.getReceiptBankAddress());

        DepositBankTipsLinkdsVO tipsLinkdsVO = new DepositBankTipsLinkdsVO();
        tipsLinkdsVO.setAppWyHkd(entity.getAppGuideUrlFps());
        tipsLinkdsVO.setPcWyHkd(entity.getPcGuideUrlFps());
        VO.setTipsLinks(tipsLinkdsVO);

        if (StringUtils.isNotBlank(entity.getDepositCertImgFps())) {
            VO.setDepositCertImg(entity.getDepositCertImgFps());
        } else {
            VO.setDepositCertImg("");
        }
        return VO;
    }

    @Override
    public List<SecDepositTypeVO> findDepositTypeSetting(DepositBankInfoBo params) {
		String tenantId = ObjectUtil.isNotEmpty(AuthUtil.getTenantId())? AuthUtil.getTenantId() : "000000";

		LambdaQueryWrapper<SecDepositBankEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(ObjectUtil.isNotEmpty(params.getBankType()), SecDepositBankEntity::getBankType, params.getBankType())
                .eq(StringUtil.isNotBlank(params.getKey()), SecDepositBankEntity::getRemitBankCode, params.getKey())
                .eq(StringUtil.isNotBlank(params.getSupportType()), SecDepositBankEntity::getSupportType, params.getSupportType())
                .eq(SecDepositBankEntity::getTenantId, tenantId)
                .eq(SecDepositBankEntity::getIsShow, 1)
                .orderByAsc(SecDepositBankEntity::getSort);
        List<SecDepositTypeVO> list = baseMapper.selectList(lambdaQueryWrapper).stream().map(entityClass -> convertSecDepositTypeVO(entityClass)).collect(Collectors.toList());
        return list;
    }
}

package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.cust.apivo.req.FindCustByPhonesReq;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.service.ICustInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户信息表 Feign实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CustInfoClient implements ICustInfoClient {

    private final ICustInfoService custInfoService;

    @Override
    @GetMapping(USER_INFO_BY_USER_ID)
    public CustInfoEntity userInfoByUserId(Long userId) {
        return custInfoService.getById(userId);
    }

    @Override
    @GetMapping(FIND_BY_CUSTIDS)
    public List<CustInfoEntity> findByCustIds(List<Long> custIds) {
        if (CollectionUtil.isEmpty(custIds)) {
            return Collections.emptyList();
        }
        return new LambdaQueryChainWrapper<>(custInfoService.getBaseMapper())
                .in(BaseEntity::getId, custIds)
                .eq(BaseEntity::getStatus, 1)
                .eq(BaseEntity::getIsDeleted, 0)
                .list();
    }

    @Override
    public Map<String, Long> findCustByPhones(FindCustByPhonesReq req) {
        List<String> phones = req.getPhones();

        if (CollectionUtil.isEmpty(phones)) {
            return Collections.emptyMap();
        }

        Map<String, String> phoneAreaCodeMap = new HashMap<>(phones.size());
        for (String phone : phones) {
            if (phone.contains("-")) {
                String phoneNos[] = phone.trim().replace("+", "").split("-");
                phoneAreaCodeMap.put(phoneNos[1], phoneNos[0]);
            } else {
                phoneAreaCodeMap.put(phone.trim(), null);
            }
        }
        // 	TODO 是否需要区分客户类型
        List<CustInfoEntity> custInfoList = new LambdaQueryChainWrapper<>(custInfoService.getBaseMapper())
                .in(CustInfoEntity::getCellPhone, phoneAreaCodeMap.keySet())
                .eq(BaseEntity::getStatus, 1)
                .eq(BaseEntity::getIsDeleted, 0)
                .select(BaseEntity::getId, CustInfoEntity::getCellPhone, CustInfoEntity::getAreaCode)
                .list();

        return custInfoList.stream()
                .filter(o -> (StringUtils.isBlank(o.getAreaCode()) && phoneAreaCodeMap.get(o.getCellPhone()) == null) || (StringUtils.isNotBlank(o.getAreaCode()) && o.getAreaCode().replace("+", "").equals(phoneAreaCodeMap.get(o.getCellPhone()))))
                .collect(Collectors.toMap(o -> o.getCellPhone(), BaseEntity::getId, (v1, v2) -> v2));
    }

    @Override
    @GetMapping(ALL_CUST_ID)
    public List<Long> allCustId() {
        return new LambdaQueryChainWrapper<>(custInfoService.getBaseMapper())
                .select(CustInfoEntity::getId)
                .list()
                .stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
    }


    @Override
    public List<Long> findCustInfoInChannels(String[] channelIds) {
        return new LambdaQueryChainWrapper<>(custInfoService.getBaseMapper())
                .eq(CustInfoEntity::getStatus, 1)
                .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .in(CustInfoEntity::getCustChannel, channelIds)
                .select(CustInfoEntity::getId)
                .list()
                .stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findUserInfoNotInChannels(String[] channelIds) {
        return new LambdaQueryChainWrapper<>(custInfoService.getBaseMapper())
                .eq(CustInfoEntity::getStatus, 1)
                .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .notIn(CustInfoEntity::getCustChannel, channelIds)
                .select(CustInfoEntity::getId)
                .list()
                .stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<CustInfoEntity> allCustInfo(List<Long> custIds) {
        LambdaQueryWrapper<CustInfoEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.select(CustInfoEntity::getId, CustInfoEntity::getCellEmail, CustInfoEntity::getCellPhone, CustInfoEntity::getAreaCode);
        queryWrapper.eq(CustInfoEntity::getStatus, 1);
        queryWrapper.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode());
        queryWrapper.isNotNull(CustInfoEntity::getId);

        if (CollectionUtil.isNotEmpty(custIds)) {
            queryWrapper.in(BaseEntity::getId, custIds);
        }

        return custInfoService.getBaseMapper().selectList(queryWrapper);
    }

    @Override
    @GetMapping(UPDATE_CUST_DEVICE_CHANNEL)
    public void updateChannel(Long custId, String channel) {
        custInfoService.updateChannel(custId, channel);
    }

	@Override
	public void updateCustpwdById(Long custId, String password,Integer changeType) {
		custInfoService.updatePwdByCustId(custId,password,changeType);
	}

	@Override
	public R updateEmailById(Long custId, String email) {
		return custInfoService.updateEmailById(custId,email);
	}

	@Override
	public List<CustInfoEntity> selectCustInfoListByIds(List<Long> custIds) {
		return custInfoService.selectCustInfoListByIds(custIds);
	}

	@Override
	public List<CustInfoEntity> selectCustInfoListByCellPhone(String cellPhone) {
		return custInfoService.selectCustInfoListByCellPhone(cellPhone);
	}
}

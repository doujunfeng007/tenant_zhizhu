package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.customer.back.service.ICustomerAgreementInfoService;
import com.minigod.zero.customer.entity.CustomerAgreementInfoEntity;
import com.minigod.zero.customer.mapper.CustomerAgreementInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 大账户协议信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Slf4j
@Service
public class CustomerAgreementInfoServiceImpl extends ServiceImpl<CustomerAgreementInfoMapper, CustomerAgreementInfoEntity>
    implements ICustomerAgreementInfoService {

    @Override
    public CustomerAgreementInfoEntity getByCustomerAndAccount(Long custId, String accountId) {
        LambdaQueryWrapper<CustomerAgreementInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomerAgreementInfoEntity::getCustId, custId)
               .eq(CustomerAgreementInfoEntity::getAccountId, accountId)
               .eq(CustomerAgreementInfoEntity::getIsDeleted, 0);
        return this.getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAgreementStatus(CustomerAgreementInfoEntity entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        return this.updateById(entity);
    }
}

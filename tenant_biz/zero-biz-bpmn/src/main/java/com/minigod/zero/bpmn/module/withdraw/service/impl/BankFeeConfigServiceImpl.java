package com.minigod.zero.bpmn.module.withdraw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.withdraw.bo.BankFeeConfigBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankFeeConfig;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.BankFeeConfigMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IBankFeeConfigService;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 取款手续费Service业务层处理
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BankFeeConfigServiceImpl implements IBankFeeConfigService {

    private final BankFeeConfigMapper baseMapper;

    /**
     * 新建 取款手续费
     * @param bo BankFeeConfigBo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long commitApply(BankFeeConfigBo bo) {
        // 手续费不为空 大于0
        if(null != bo.getChargeFee() && BigDecimal.ZERO.compareTo(bo.getChargeFee()) > 0){
            log.info("新增取款手续费,手续费不能小于0, data:{}", bo);
            throw new ServiceException("取款手续费不能小于0!");
        }

        // 验证取款方式
        SystemCommonEnum.TransferTypeEnum transferTypeEnum = SystemCommonEnum.TransferTypeEnum.getEnum(bo.getTransferType());
        if(null == transferTypeEnum){
            log.info("新增取款手续费不支持的取款方式, data:{}", bo);
            throw new ServiceException("不支持的取款方式");
        }
        // 检测费用是否已存在
        BankFeeConfigBo queryBo = new BankFeeConfigBo();
        queryBo.setBankCode(bo.getBankCode());
        queryBo.setTransferType(bo.getTransferType());
        queryBo.setCcy(bo.getCcy());
        Long iCount = queryCount(queryBo);
        if(iCount != null && iCount > 0){
            log.info("新增取款手续费记录已存在, , data:{}", bo);
            throw new ServiceException("新增取款手续费记录已存在");
        }
        BankFeeConfig add = BeanUtil.toBean(bo, BankFeeConfig.class);
        // 手续费为空 默认为零
        if(bo.getChargeFee() == null){
            add.setChargeFee(BigDecimal.ZERO);
        }
        add.setTransferTypeDesc(transferTypeEnum.getName());
        add.setActive(SystemCommonEnum.YesNo.YES.getIndex());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        log.info("新增取款手续费记录:{} {}", bo, flag ? "成功" : "失败");

        return add.getId();
    }

    /**
     * 修改取款手续费
     *
     * @param bo 取款手续费
     * @return 结果
     */
    @Override
    public Long updateFeeConfig(BankFeeConfigBo bo) {
        if(bo.getId() == null){
            log.info("修改取款手续费流水号不能为空, data:{}", bo);
            throw new ServiceException("流水号不能为空。");
        }
        // 手续费不为空 大于0
        if(null != bo.getChargeFee() && BigDecimal.ZERO.compareTo(bo.getChargeFee()) > 0){
            log.info("新增取款手续费,手续费不能小于0, data:{}", bo);
            throw new ServiceException("取款手续费不能小于0。");
        }
        // 查询记录
        BankFeeConfig feeConfigVo = queryById(bo.getId());
        if(feeConfigVo == null){
            log.info("取款手续费记录不存在, , data:{}", bo);
            throw new ServiceException("取款手续费记录不存在。");
        }
        // 验证取款方式
        SystemCommonEnum.TransferTypeEnum transferTypeEnum = SystemCommonEnum.TransferTypeEnum.getEnum(bo.getTransferType());
        if(null == transferTypeEnum){
            log.info("修改取款手续费不支持的取款方式, data:{}", bo);
            throw new ServiceException("不支持的取款方式。");
        }
        // 检测费用是否已存在
        BankFeeConfigBo queryBo = new BankFeeConfigBo();
        queryBo.setBankCode(bo.getBankCode());
        queryBo.setTransferType(bo.getTransferType());
        queryBo.setCcy(bo.getCcy());
        Long iCount = queryCount(queryBo);
        if(iCount != null && iCount > 1){
            log.info("修改取款手续费存在多条相同手续费记录, data:{}", bo);
            throw new ServiceException("存在多条相同手续费记录, 请重新调整。");
        }
        BankFeeConfig update = BeanUtil.toBean(bo, BankFeeConfig.class);
        // 手续费为空 默认为零
        if(bo.getChargeFee() == null){
            update.setChargeFee(BigDecimal.ZERO);
        }
        update.setTransferTypeDesc(transferTypeEnum.getName());
        boolean flag = baseMapper.updateById(update) > 0;
        if (flag) {
            bo.setId(update.getId());
        }
        log.info("修改取款手续费记录:{} {}", bo, flag ? "成功" : "失败");
        return update.getId();
    }

    /**
     * 查询取款手续费
     *
     * @param id 取款手续费主键
     * @return 取款手续费
     */
    @Override
    public BankFeeConfig queryById(Long id){
        return baseMapper.selectById(id);
    }

    /**
     * 查询取款手续费列表
     * @param pageQuery
     * @param bo 取款手续费
     * @return
     */
    @Override
    public IPage<BankFeeConfig> queryPageList(IPage pageQuery,BankFeeConfigBo bo) {
        LambdaQueryWrapper<BankFeeConfig> lqw = buildQueryWrapper(bo);
        IPage<BankFeeConfig> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    /**
     * 查询取款手续费列表
     *
     * @param bo 取款手续费
     * @return 取款手续费
     */
    @Override
    public List<BankFeeConfig> queryList(BankFeeConfigBo bo) {
        LambdaQueryWrapper<BankFeeConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }

	/**
	 * 银行列表 去重 取code
	 * @param bo
	 * @return
	 */
	@Override
    public List<BankFeeConfig> bankList(BankFeeConfigBo bo) {
        LambdaQueryWrapper<BankFeeConfig> lqw = buildQueryWrapper(bo);
		lqw.groupBy(BankFeeConfig::getBankCode);
        return baseMapper.selectList(lqw);
    }

    /**
     * 查询手续费银行列表
     */
    public List<BankFeeConfig> queryBankList(BankFeeConfigBo bo){
        LambdaQueryWrapper<BankFeeConfig> lqw = buildQueryBankListWrapper(bo);
        return baseMapper.selectList(lqw);
    }

    /**
     * 查询银行信息记录数
     *
     * @param bo 银行信息记录
     * @return 银行信息记录
     */
    public Long queryCount(BankFeeConfigBo bo) {
        LambdaQueryWrapper<BankFeeConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectCount(lqw);
    }

    private LambdaQueryWrapper<BankFeeConfig> buildQueryWrapper(BankFeeConfigBo bo) {
        LambdaQueryWrapper<BankFeeConfig> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getBankCode()), BankFeeConfig::getBankCode, bo.getBankCode());
        lqw.like(StringUtil.isNotBlank(bo.getBankName()), BankFeeConfig::getBankName, bo.getBankName());
        lqw.eq(bo.getTransferType() != null, BankFeeConfig::getTransferType, bo.getTransferType());
        lqw.eq(StringUtil.isNotBlank(bo.getTransferTypeDesc()), BankFeeConfig::getTransferTypeDesc, bo.getTransferTypeDesc());
        lqw.eq(StringUtil.isNotBlank(bo.getCcy()), BankFeeConfig::getCcy, bo.getCcy());
        lqw.eq(bo.getChargeFee() != null, BankFeeConfig::getChargeFee, bo.getChargeFee());
        lqw.eq(bo.getActive() != null, BankFeeConfig::getActive, bo.getActive());
        return lqw;
    }

    private LambdaQueryWrapper<BankFeeConfig> buildQueryBankListWrapper(BankFeeConfigBo bo) {
        QueryWrapper<BankFeeConfig> queryWrapper = new QueryWrapper<BankFeeConfig>();
        LambdaQueryWrapper<BankFeeConfig> lqw = queryWrapper.select("DISTINCT bank_code, bank_name").lambda();
        lqw.eq(StringUtil.isNotBlank(bo.getBankCode()), BankFeeConfig::getBankCode, bo.getBankCode());
        lqw.like(StringUtil.isNotBlank(bo.getBankName()), BankFeeConfig::getBankName, bo.getBankName());
        lqw.eq(bo.getTransferType() != null, BankFeeConfig::getTransferType, bo.getTransferType());
        lqw.eq(StringUtil.isNotBlank(bo.getTransferTypeDesc()), BankFeeConfig::getTransferTypeDesc, bo.getTransferTypeDesc());
        lqw.eq(StringUtil.isNotBlank(bo.getCcy()), BankFeeConfig::getCcy, bo.getCcy());
        lqw.eq(bo.getChargeFee() != null, BankFeeConfig::getChargeFee, bo.getChargeFee());
        lqw.eq(bo.getActive() != null, BankFeeConfig::getActive, bo.getActive());
        lqw.ne(true, BankFeeConfig::getBankCode, "");
        lqw.orderByAsc(BankFeeConfig::getBankCode);
        return lqw;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(BankFeeConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public boolean deleteWithValidByIds(List<String> ids, boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}

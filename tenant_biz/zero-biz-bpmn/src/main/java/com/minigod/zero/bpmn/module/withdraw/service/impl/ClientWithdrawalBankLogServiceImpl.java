package com.minigod.zero.bpmn.module.withdraw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.minigod.zero.bpmn.module.withdraw.bo.ClientWithdrawalBankLogBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientWithdrawalBankLog;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientWithdrawalBankLogMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IClientWithdrawalBankLogService;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 收款银行登记操作日志Service业务层处理
 *
 * @author zsdp
 * @date 2023-04-09
 */
@RequiredArgsConstructor
@Service
public class ClientWithdrawalBankLogServiceImpl implements IClientWithdrawalBankLogService {

    private final ClientWithdrawalBankLogMapper baseMapper;


    /**
     * 添加用户银行卡操作日志
     *
     * @param custCode
     * @param swiftCode
     * @param optType
     * @param channel
     * @param filename
     * @return
     */
    @Override
    public boolean insertBankLog(String custCode, String swiftCode, String optType, String channel, String filename) {
        // 记录操作日志
        ClientWithdrawalBankLogBo clientWithdrawalBankLog = new ClientWithdrawalBankLogBo();
        clientWithdrawalBankLog.setOprcode(custCode);
        clientWithdrawalBankLog.setOprtype(optType);
        clientWithdrawalBankLog.setChannel(channel);
        clientWithdrawalBankLog.setFileName(filename);
        clientWithdrawalBankLog.setOprtime(new Date());
        return insertByBo(clientWithdrawalBankLog);
    }

    /**
     * 查询收款银行登记操作日志
     *
     * @param id 收款银行登记操作日志主键
     * @return 收款银行登记操作日志
     */
    @Override
    public ClientWithdrawalBankLog queryById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 查询收款银行登记操作日志列表
     *
     * @param bo        收款银行登记操作日志
     * @param pageQuery
     * @return 收款银行登记操作日志
     */
    @Override
    public IPage<ClientWithdrawalBankLog> queryPageList(ClientWithdrawalBankLogBo bo, IPage pageQuery) {
        LambdaQueryWrapper<ClientWithdrawalBankLog> lqw = buildQueryWrapper(bo);
        IPage<ClientWithdrawalBankLog> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    /**
     * 查询收款银行登记操作日志列表
     *
     * @param bo 收款银行登记操作日志
     * @return 收款银行登记操作日志
     */
    @Override
    public List<ClientWithdrawalBankLog> queryList(ClientWithdrawalBankLogBo bo) {
        LambdaQueryWrapper<ClientWithdrawalBankLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }

    private LambdaQueryWrapper<ClientWithdrawalBankLog> buildQueryWrapper(ClientWithdrawalBankLogBo bo) {
        LambdaQueryWrapper<ClientWithdrawalBankLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getOprcode()), ClientWithdrawalBankLog::getOprcode, bo.getOprcode());
        lqw.eq(bo.getOprtime() != null, ClientWithdrawalBankLog::getOprtime, bo.getOprtime());
        lqw.eq(StringUtil.isNotBlank(bo.getOprtype()), ClientWithdrawalBankLog::getOprtype, bo.getOprtype());
        lqw.eq(StringUtil.isNotBlank(bo.getChannel()), ClientWithdrawalBankLog::getChannel, bo.getChannel());
        lqw.like(StringUtil.isNotBlank(bo.getFileName()), ClientWithdrawalBankLog::getFileName, bo.getFileName());
        return lqw;
    }

    /**
     * 新增收款银行登记操作日志
     *
     * @param bo 收款银行登记操作日志
     * @return 结果
     */
    @Override
    public Boolean insertByBo(ClientWithdrawalBankLogBo bo) {
        ClientWithdrawalBankLog add = BeanUtil.toBean(bo, ClientWithdrawalBankLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改收款银行登记操作日志
     *
     * @param bo 收款银行登记操作日志
     * @return 结果
     */
    @Override
    public Boolean updateUserBo(ClientWithdrawalBankLogBo bo) {
        ClientWithdrawalBankLog update = BeanUtil.toBean(bo, ClientWithdrawalBankLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ClientWithdrawalBankLog entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除收款银行登记操作日志
     *
     * @param ids 需要删除的收款银行登记操作日志主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}

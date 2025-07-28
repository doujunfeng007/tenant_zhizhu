package com.minigod.zero.bpmn.module.withdraw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientBankInfo;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientBankInfoMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IClientBankInfoService;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 银行信息记录Service业务层处理
 *
 * @author zsdp
 * @date 2023-03-24
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ClientBankInfoServiceImpl extends ServiceImpl<ClientBankInfoMapper, ClientBankInfo> implements IClientBankInfoService {


    /**
     * 新建 香港银行信息
     *
     * @param bo ClientBankInfoBo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long commitApply(ClientBankInfoBo bo) {
        // 校验BankCode是否存在
        ClientBankInfoBo queryBo = new ClientBankInfoBo();
        queryBo.setBankCode(bo.getBankCode());
        Long iCount = queryCount(queryBo);
        if (iCount != null && iCount > 0) {
            log.info("新增银行信息已存在, bankCode:{}", bo.getBankCode());
            throw new ServiceException("新增失败，银行代码已存在。");
        }
        ClientBankInfo add = BeanUtil.toBean(bo, ClientBankInfo.class);
        add.setCreateTime(new Date());
        add.setCreateUser(AuthUtil.getUserId());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        log.info("新增银行信息记录:{} {}", bo, flag ? "成功" : "失败");
        return add.getId();
    }

    /**
     * 查询银行信息记录
     *
     * @param id 银行信息记录主键
     * @return 银行信息记录
     */
    @Override
    public ClientBankInfo queryById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 查询银行信息记录列表
     *
     * @param bo 银行信息记录
     * @return 银行信息记录
     */
    @Override
    public IPage<ClientBankInfo> queryPageList(IPage pageQuery, ClientBankInfoBo bo) {
        LambdaQueryWrapper<ClientBankInfo> lqw = buildQueryWrapper(bo);
        IPage<ClientBankInfo> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    /**
     * 查询银行信息记录列表
     *
     * @param bo 银行信息记录
     * @return 银行信息记录
     */
    @Override
    public List<ClientBankInfo> queryList(ClientBankInfoBo bo) {
        LambdaQueryWrapper<ClientBankInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }

    /**
     * 查询银行信息记录数
     *
     * @param bo 银行信息记录
     * @return 银行信息记录
     */
    public Long queryCount(ClientBankInfoBo bo) {
        LambdaQueryWrapper<ClientBankInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectCount(lqw);
    }

    /**
     * 查询银行信息记录
     *
     * @param bo 银行信息记录
     * @return 银行信息记录
     */
    @Override
    public ClientBankInfo queryEntity(ClientBankInfoBo bo) {
        LambdaQueryWrapper<ClientBankInfo> lqw = buildQueryWrapper(bo);
        List<ClientBankInfo> lstData = baseMapper.selectList(lqw);
        ClientBankInfo clientBankInfo = null;
        if (null != lstData && lstData.size() > 0) {
            clientBankInfo = lstData.get(0);
        }
        return clientBankInfo;
    }

    private LambdaQueryWrapper<ClientBankInfo> buildQueryWrapper(ClientBankInfoBo bo) {
        LambdaQueryWrapper<ClientBankInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getBankCode()), ClientBankInfo::getBankCode, bo.getBankCode());
        lqw.like(StringUtil.isNotBlank(bo.getBankName()), ClientBankInfo::getBankName, bo.getBankName());
        lqw.eq(StringUtil.isNotBlank(bo.getBankNameEn()), ClientBankInfo::getBankNameEn, bo.getBankNameEn());
        lqw.eq(StringUtil.isNotBlank(bo.getSwiftCode()), ClientBankInfo::getSwiftCode, bo.getSwiftCode());
        lqw.eq(StringUtil.isNotBlank(bo.getBankId()), ClientBankInfo::getBankId, bo.getBankId());
        lqw.eq(bo.getBankAreaType() != null, ClientBankInfo::getBankAreaType, bo.getBankAreaType());
        lqw.eq(StringUtil.isNotBlank(bo.getWithdrawType()), ClientBankInfo::getWithdrawType, bo.getWithdrawType());
        lqw.eq(bo.getStatus() != null, ClientBankInfo::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增银行信息记录
     *
     * @param bo 银行信息记录
     * @return 结果
     */
    @Override
    public Boolean insertByBo(ClientBankInfoBo bo) {
        ClientBankInfo add = BeanUtil.toBean(bo, ClientBankInfo.class);
        add.setCreateTime(new Date());
        add.setCreateUser(AuthUtil.getUserId());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        log.info("新增银行信息记录:{} {}", bo, flag ? "成功" : "失败");
        return flag;
    }

    /**
     * 修改银行信息记录
     *
     * @param bo 银行信息记录
     * @return 结果
     */
    @Override
    public Boolean updateByBo(ClientBankInfoBo bo) {
        ClientBankInfo update = BeanUtil.toBean(bo, ClientBankInfo.class);
        update.setUpdateUser(AuthUtil.getUserId());
        update.setUpdateTime(new Date());
        validEntityBeforeSave(update);
        boolean flag = baseMapper.updateById(update) > 0;
        log.info("修改银行信息记录:{} {}", bo, flag ? "成功" : "失败");
        return flag;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ClientBankInfo entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除银行信息记录
     *
     * @param ids 需要删除的银行信息记录主键
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

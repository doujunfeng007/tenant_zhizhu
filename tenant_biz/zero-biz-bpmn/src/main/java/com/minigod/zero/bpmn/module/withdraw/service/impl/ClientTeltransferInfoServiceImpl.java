package com.minigod.zero.bpmn.module.withdraw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientTeltransferInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientTeltransferInfo;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientTeltransferInfoMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IClientTeltransferInfoService;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 客户电汇取款信息Service业务层处理
 *
 * @author chenyu
 * @date 2023-04-06
 */
@RequiredArgsConstructor
@Service
public class ClientTeltransferInfoServiceImpl implements IClientTeltransferInfoService {

    private final ClientTeltransferInfoMapper baseMapper;

    /**
     * 查询客户电汇取款信息
     *
     * @param id 客户电汇取款信息主键
     * @return 客户电汇取款信息
     */
    @Override
    public ClientTeltransferInfo queryById(Long id){
        return baseMapper.selectById(id);
    }

    /**
     * 查询客户电汇取款信息列表
     *
     * @param bo 客户电汇取款信息
     * @return 客户电汇取款信息
     */
    @Override
    public IPage<ClientTeltransferInfo> queryPageList(ClientTeltransferInfoBo bo, IPage pageQuery) {
        LambdaQueryWrapper<ClientTeltransferInfo> lqw = buildQueryWrapper(bo);
        IPage<ClientTeltransferInfo> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    /**
     * 查询客户电汇取款信息列表
     *
     * @param bo 客户电汇取款信息
     * @return 客户电汇取款信息
     */
    @Override
    public List<ClientTeltransferInfo> queryList(ClientTeltransferInfoBo bo) {
        LambdaQueryWrapper<ClientTeltransferInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }

    /**
     * 查询客户电汇取款信息列表
     *
     * @param bo 客户电汇取款信息
     * @return 客户电汇取款信息
     */
    @Override
    public ClientTeltransferInfo queryEntity(ClientTeltransferInfoBo bo) {
        LambdaQueryWrapper<ClientTeltransferInfo> lqw = buildQueryWrapper(bo);
        lqw.last("limit 1");
        ClientTeltransferInfo lstData = baseMapper.selectOne(lqw);
        return lstData;
    }

    /**
     * 查询客户电汇取款信息列表
     *
     * @param applicationId 流水号
     * @return 客户出金申请
     */
    @Override
    public ClientTeltransferInfo queryByApplicationId(String applicationId){
        ClientTeltransferInfoBo queryBo = new ClientTeltransferInfoBo();
        queryBo.setApplicationId(applicationId);
        LambdaQueryWrapper<ClientTeltransferInfo> lqw = buildQueryWrapper(queryBo);
        return baseMapper.selectOne(lqw);
    }

    private LambdaQueryWrapper<ClientTeltransferInfo> buildQueryWrapper(ClientTeltransferInfoBo bo) {
        LambdaQueryWrapper<ClientTeltransferInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getApplicationId()), ClientTeltransferInfo::getApplicationId, bo.getApplicationId());
        lqw.eq(StringUtil.isNotBlank(bo.getClientId()), ClientTeltransferInfo::getClientId, bo.getClientId());
        lqw.eq(StringUtil.isNotBlank(bo.getFundAccount()), ClientTeltransferInfo::getFundAccount, bo.getFundAccount());
        lqw.eq(StringUtil.isNotBlank(bo.getBankCode()), ClientTeltransferInfo::getBankCode, bo.getBankCode());
        lqw.like(StringUtil.isNotBlank(bo.getBankName()), ClientTeltransferInfo::getBankName, bo.getBankName());
        lqw.eq(StringUtil.isNotBlank(bo.getBankAcct()), ClientTeltransferInfo::getBankAcct, bo.getBankAcct());
        lqw.eq(StringUtil.isNotBlank(bo.getNationality()), ClientTeltransferInfo::getNationality, bo.getNationality());
        lqw.eq(bo.getProvinceId() != null, ClientTeltransferInfo::getProvinceId, bo.getProvinceId());
        lqw.like(StringUtil.isNotBlank(bo.getProvinceName()), ClientTeltransferInfo::getProvinceName, bo.getProvinceName());
        lqw.eq(bo.getCityId() != null, ClientTeltransferInfo::getCityId, bo.getCityId());
        lqw.like(StringUtil.isNotBlank(bo.getCityName()), ClientTeltransferInfo::getCityName, bo.getCityName());
        lqw.eq(StringUtil.isNotBlank(bo.getSwiftCode()), ClientTeltransferInfo::getSwiftCode, bo.getSwiftCode());
        lqw.eq(StringUtil.isNotBlank(bo.getIsVisible()), ClientTeltransferInfo::getIsVisible, bo.getIsVisible());
        lqw.eq(StringUtil.isNotBlank(bo.getCountry()), ClientTeltransferInfo::getCountry, bo.getCountry());
        lqw.eq(StringUtil.isNotBlank(bo.getAddress()), ClientTeltransferInfo::getAddress, bo.getAddress());
        return lqw;
    }

    /**
     * 新增客户电汇取款信息
     *
     * @param bo 客户电汇取款信息
     * @return 结果
     */
    @Override
    public Boolean insertByBo(ClientTeltransferInfoBo bo) {
        ClientTeltransferInfo add = BeanUtil.toBean(bo, ClientTeltransferInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改客户电汇取款信息
     *
     * @param bo 客户电汇取款信息
     * @return 结果
     */
    @Override
    public Boolean updateUserBo(ClientTeltransferInfoBo bo) {
        ClientTeltransferInfo update = BeanUtil.toBean(bo, ClientTeltransferInfo.class);
        validEntityBeforeSave(update);
        update.setUpdateTime(new Date());
        update.setUpdateUser(AuthUtil.getUserId());
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ClientTeltransferInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户电汇取款信息
     *
     * @param ids 需要删除的客户电汇取款信息主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}

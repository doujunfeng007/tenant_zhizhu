package com.minigod.zero.bpmn.module.withdraw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;



import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;

import java.util.List;

/**
 * 公司付款银行信息Mapper接口
 *
 * @author chenyu
 * @date 2023-04-06
 */
public interface BankPayingMapper extends BaseMapper<BankPaying> {

    /**
     * 查询公司付款银行信息
     *
     * @param id 公司付款银行信息主键
     * @return 公司付款银行信息
     */
    public BankPaying selectBankPayingByid(Long id);

    /**
     * 查询公司付款银行信息列表
     *
     * @param bankPaying 公司付款银行信息
     * @return 公司付款银行信息集合
     */
    public List<BankPaying> selectBankPayingList(BankPaying bankPaying);

    /**
     * 新增公司付款银行信息
     *
     * @param bankPaying 公司付款银行信息
     * @return 结果
     */
    public int insertBankPaying(BankPaying bankPaying);

    /**
     * 修改公司付款银行信息
     *
     * @param bankPaying 公司付款银行信息
     * @return 结果
     */
    public int updateBankPaying(BankPaying bankPaying);

    /**
     * 删除公司付款银行信息
     *
     * @param id 公司付款银行信息主键
     * @return 结果
     */
    public int deleteBankPayingByid(Long id);

    /**
     * 批量删除公司付款银行信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankPayingByids(String[] ids);

}

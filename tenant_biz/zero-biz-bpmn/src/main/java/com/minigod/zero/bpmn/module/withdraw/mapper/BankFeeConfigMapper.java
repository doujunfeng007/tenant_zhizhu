package com.minigod.zero.bpmn.module.withdraw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;



import com.minigod.zero.bpmn.module.withdraw.entity.BankFeeConfig;

import java.util.List;

/**
 * 取款手续费Mapper接口
 *
 * @author chenyu
 * @date 2023-04-06
 */
public interface BankFeeConfigMapper extends BaseMapper<BankFeeConfig> {

    /**
     * 查询取款手续费
     *
     * @param id 取款手续费主键
     * @return 取款手续费
     */
    public BankFeeConfig selectBankFeeConfigByid(Long id);

    /**
     * 查询取款手续费列表
     *
     * @param bankFeeConfig 取款手续费
     * @return 取款手续费集合
     */
    public List<BankFeeConfig> selectBankFeeConfigList(BankFeeConfig bankFeeConfig);

    /**
     * 新增取款手续费
     *
     * @param bankFeeConfig 取款手续费
     * @return 结果
     */
    public int insertBankFeeConfig(BankFeeConfig bankFeeConfig);

    /**
     * 修改取款手续费
     *
     * @param bankFeeConfig 取款手续费
     * @return 结果
     */
    public int updateBankFeeConfig(BankFeeConfig bankFeeConfig);

    /**
     * 删除取款手续费
     *
     * @param id 取款手续费主键
     * @return 结果
     */
    public int deleteBankFeeConfigByid(Long id);

    /**
     * 批量删除取款手续费
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankFeeConfigByids(String[] ids);

}

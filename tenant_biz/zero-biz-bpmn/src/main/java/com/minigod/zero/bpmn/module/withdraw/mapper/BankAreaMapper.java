package com.minigod.zero.bpmn.module.withdraw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;



import com.minigod.zero.bpmn.module.withdraw.entity.BankArea;

import java.util.List;

/**
 * 区域Mapper接口
 *
 * @author chenyu
 * @date 2023-04-20
 */
public interface BankAreaMapper extends BaseMapper<BankArea> {

    /**
     * 查询区域
     *
     * @param id 区域主键
     * @return 区域
     */
    public BankArea selectBankAreaByid(Long id);

    /**
     * 查询区域列表
     *
     * @param bankArea 区域
     * @return 区域集合
     */
    public List<BankArea> selectBankAreaList(BankArea bankArea);

    /**
     * 新增区域
     *
     * @param bankArea 区域
     * @return 结果
     */
    public int insertBankArea(BankArea bankArea);

    /**
     * 修改区域
     *
     * @param bankArea 区域
     * @return 结果
     */
    public int updateBankArea(BankArea bankArea);

    /**
     * 删除区域
     *
     * @param id 区域主键
     * @return 结果
     */
    public int deleteBankAreaByid(Long id);

    /**
     * 批量删除区域
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankAreaByids(String[] ids);

}

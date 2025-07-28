package com.minigod.zero.bpmn.module.withdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.BankAreaBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankArea;

/**
 * 区域Service接口
 *
 * @author chenyu
 * @date 2023-04-20
 */
public interface IBankAreaService {

    IPage<BankArea> queryPageList(IPage<BankArea> page, BankAreaBo bo);
}

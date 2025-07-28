package com.minigod.zero.bpmn.module.withdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoOsBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientBankInfoOs;

import java.util.List;

/**
 * @ClassName: ClientBankInfoOsService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/28
 * @Version 1.0
 */
public interface ClientBankInfoOsService extends IService<ClientBankInfoOs> {


    int batchInsert(List<ClientBankInfoOs> list);

    IPage<ClientBankInfoOs> queryPageList(IPage page, ClientBankInfoOsBo bo);

    List<ClientBankInfoOs> queryList(ClientBankInfoOsBo bo);
}

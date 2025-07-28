package com.minigod.zero.bpmn.module.fundTrans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransQuota;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransQuotaVo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.ClientFundTransQuotaBo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 *  划拨额度Service接口
 *
 * @author zsdp
 * @date 2024-12-25
 */
public interface IClientFundTransQuotaService extends IService<ClientFundTransQuota> {

    /**
     * 查询 划拨额度
     *
     * @param id  划拨额度主键
     * @return  划拨额度
     */
    ClientFundTransQuotaVo queryById(Long id);

    /**
     * 查询 划拨额度列表
     *
     * @param bo  划拨额度
     * @return  划拨额度集合
     */
	IPage<ClientFundTransQuotaVo> queryPageList(ClientFundTransQuotaBo bo, IPage pageQuery);

    /**
     * 查询 划拨额度列表
     *
     * @param bo  划拨额度
     * @return  划拨额度集合
     */
    List<ClientFundTransQuotaVo> queryList(ClientFundTransQuotaBo bo);

    /**
     * 修改 划拨额度
     *
     * @param  bo  划拨额度
     * @return 结果
     */
    Boolean insertByBo(ClientFundTransQuotaBo bo);

    /**
     * 修改 划拨额度
     *
     * @param  bo  划拨额度
     * @return 结果
     */
    Boolean updateByBo(ClientFundTransQuotaBo bo);

    /**
     * 校验并批量删除 划拨额度信息
     *
     * @param ids 需要删除的 划拨额度主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


	/**
	 * 返回额度
	 * @param withdrawBusinessType
	 * @param depositBusinessType
	 * @param currency
	 * @return
	 */
	BigDecimal queryQuota(String withdrawBusinessType,String depositBusinessType,String currency);
}

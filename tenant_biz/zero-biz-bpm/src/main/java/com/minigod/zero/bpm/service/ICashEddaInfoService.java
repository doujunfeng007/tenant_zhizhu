package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.vo.request.CashEddaInfoReqVo;
import com.minigod.zero.bpm.vo.request.EddaInfoCallProtocol;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpm.entity.CashEddaInfoEntity;
import com.minigod.zero.bpm.vo.CashEddaInfoVO;
import com.minigod.zero.core.tool.api.R;

/**
 * edda申请流水表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashEddaInfoService extends IService<CashEddaInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashEddaInfo
	 * @return
	 */
	IPage<CashEddaInfoVO> selectCashEddaInfoPage(IPage<CashEddaInfoVO> page, CashEddaInfoVO cashEddaInfo);

	/**
	 * 保存edda 授权数据
	 * @param custId
	 * @param params
	 * @return
	 */
	R saveEddaBank(Long custId, CashEddaInfoReqVo params);

	/**
	 * 失败的edda授权记录重试
	 * @param custId
	 * @param params
	 * @return
	 */
	R eddaBankRetry(Long custId, CashEddaInfoReqVo params);

	/**
	 * edda前端用户修改
	 * @param custId
	 * @param params
	 * @return
	 */
	R updateEddaBankInfo(Long custId, CashEddaInfoReqVo params);

	/**
	 * eDDA前端用户删除
	 * @param custId
	 * @param params
	 * @return
	 */
	R deleteEddaBank(Long custId, CashEddaInfoReqVo params);

	/**
	 * eDDA绑定列表查询
	 * @param custId
	 * @param params
	 * @return
	 */
	R getEddaBankInfo(Long custId, CashEddaInfoReqVo params);

	/**
	 * edda状态更新
	 * @param protocol
	 * @return
	 */
	ResponseVO updateEddaStatus(EddaInfoCallProtocol protocol);

	/**
	 * 更新edda授权记录
	 * @param eddaInfo
	 * @return
	 */
	int updateEddaInfo(CashEddaInfoEntity eddaInfo);

	/**
	 * 执行客户edda数据上送任务
	 */
	R executeClientEddaInfoJob();
}

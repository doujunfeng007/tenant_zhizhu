package com.minigod.zero.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.apivo.req.CustInfoQueryReq;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.vo.CustContactVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface CustInfoMapper extends BaseMapper<CustInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param custInfo
	 * @return
	 */
	List<CustInfoEntity> selectCustInfoPage(IPage page, @Param("custInfo") CustInfoQueryReq custInfo);

	/**
	 * 获取客户信息
	 * @param custId
	 * @return
	 */
	CustInfo getCustInfo(Long custId);

	/**
	 * 查询客户账号联系方式
	 *
	 * @param custId
	 * @return
	 */
	CustContactVO getCustContactInfo(Long custId,String tradeAccount);

	/**
	 * 查询客户账号联系方式
	 *
	 * @param custId
	 * @return
	 */
	CustContactVO getCustContactInfo2fa(Long custId,String tradeAccount);

	void esopAcctBind(String tradeAccount, Long custId);

	void esopCustBind(Long custId, Long esopCust);

	Integer checkCustBindIsExit(String tradeAccount);

}

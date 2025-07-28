package com.minigod.zero.cust.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.entity.CustInvestorStmtEntity;
import com.minigod.zero.cust.vo.ConfigTemp;
import com.minigod.zero.cust.vo.request.UserInvestorStmtReqVo;

import java.util.List;
import java.util.Map;

/**
 * 投资者声明信息（美股） 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustInvestorStmtService extends BaseService<CustInvestorStmtEntity> {

	/**
	 * 查询所有问题
	 *
	 * @return
	 */
	List<ConfigTemp> findQuestions();

	/**
	 * 是否弹出行情声明
	 *
	 * @param investorStmtType
	 * @return
	 */
	boolean isInvestorStmt(Integer investorStmtType);

	/**
	 * 根据用户号查询投资者声明信息（美股）
	 * @param custId
	 * @return
	 */
	CustInvestorStmtEntity findUserInvestorStmt(Long custId);

	/**
	 * 当前事情是否包含在设定的时间中
	 */
	boolean isSetTimeContainCurDate();

	/**
	 * 是否专业投资者
	 *
	 * @return
	 */
	boolean isIsInvestor();

	/**
	 * 查询是否需要弹出行情声明
	 *
	 * @param reqVo
	 * @return
	 */
	R isInvestorStmt(UserInvestorStmtReqVo reqVo);

	/**
	 * 查询补充资料
	 * @param reqVo
	 * @return
	 */
	R findInvestorStmt(UserInvestorStmtReqVo reqVo);

	/**
	 * 保存补充资料
	 * @param reqVo
	 * @return
	 */
	R saveInvestorStmt(UserInvestorStmtReqVo reqVo);

	/**
	 * 更新专业投资者
	 *
	 * @param userInvestorStmt
	 */
	void updateInvestorStmt(CustInvestorStmtEntity userInvestorStmt);

	/**
	 * 查询属性列表
	 *
	 * @return
	 */
	Map<String, List<ConfigTemp>> findInvestorStmtConf();

	/**
	 * 是否专业投资者-行情套餐 -1 没做过 1是 0 -否
	 *
	 * @return
	 */
	Integer isInvestorPackage();

}

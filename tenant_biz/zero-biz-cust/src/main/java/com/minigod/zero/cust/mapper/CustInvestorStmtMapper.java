package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.CustInvestorStmtEntity;
import com.minigod.zero.cust.vo.CustInvestorStmtVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 投资者声明信息（美股） Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface CustInvestorStmtMapper extends BaseMapper<CustInvestorStmtEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param custInvestorStmt
	 * @return
	 */
	List<CustInvestorStmtVO> selectCustInvestorStmtPage(@Param("page") IPage page, @Param("params") CustInvestorStmtVO custInvestorStmt);


}

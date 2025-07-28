package com.minigod.zero.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.data.entity.SecDepositFunds;
import com.minigod.zero.data.dto.DepositAndWithdrawalReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.minigod.zero.data.vo.DepositAndWithdrawalFundsReportVO;

import java.util.List;

/**
 * @author dell
 * @description 针对表【sec_deposit_funds(存入资金表)】的数据库操作Mapper
 * @createDate 2024-09-27 09:40:55
 * @Entity com.minigod.zero.entity.SecDepositFunds
 */
@Mapper
public interface SecDepositFundsMapper {

	/**
	 * 根据主键删除存入资金表
	 *
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 插入存入资金
	 *
	 * @param record
	 * @return
	 */
	int insert(SecDepositFunds record);

	/**
	 * 插入存入资金表
	 *
	 * @param record
	 * @return
	 */
	int insertSelective(SecDepositFunds record);

	/**
	 * 根据主键查询存入资金表
	 *
	 * @param id
	 * @return
	 */
	SecDepositFunds selectByPrimaryKey(Long id);

	/**
	 * 根据主键更新存入资金表
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(SecDepositFunds record);

	/**
	 * 更新存入资金表
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(SecDepositFunds record);

	/**
	 * 分页查询出入金报表数据
	 *
	 * @param page
	 * @param depositAndWithdrawalReportDTO
	 * @return
	 */
	List<DepositAndWithdrawalFundsReportVO> queryReportPage(IPage<DepositAndWithdrawalFundsReportVO> page,
															@Param("depositAndWithdrawalReportDTO") DepositAndWithdrawalReportDTO depositAndWithdrawalReportDTO);
}

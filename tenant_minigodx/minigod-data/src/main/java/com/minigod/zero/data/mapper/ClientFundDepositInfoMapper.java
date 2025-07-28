package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientFundDepositInfo;
import com.minigod.zero.data.vo.ClientDepositStatVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dell
 * @description 针对表【client_fund_deposit_info(客户入金申请信息表)】的数据库操作Mapper
 * @createDate 2024-09-26 16:13:17
 * @Entity com.minigod.zero.report.entity.ClientFundDepositInfo
 */
@Mapper
public interface ClientFundDepositInfoMapper {

	int deleteByPrimaryKey(Long id);

	int insert(ClientFundDepositInfo record);

	int insertSelective(ClientFundDepositInfo record);

	ClientFundDepositInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ClientFundDepositInfo record);

	int updateByPrimaryKey(ClientFundDepositInfo record);

	/**
	 * 统计所有币种入金总额
	 *
	 * @return 各币种入金统计结果
	 */
	ClientDepositStatVO getDepositStats();
}

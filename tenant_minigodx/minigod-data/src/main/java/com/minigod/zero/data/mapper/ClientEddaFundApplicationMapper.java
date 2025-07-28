package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientEddaFundApplication;
import org.apache.ibatis.annotations.Mapper;

/**
* @author dell
* @description 针对表【client_edda_fund_application(DBS edda入金流水表)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientEddaFundApplication
*/
@Mapper
public interface ClientEddaFundApplicationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientEddaFundApplication record);

    int insertSelective(ClientEddaFundApplication record);

    ClientEddaFundApplication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientEddaFundApplication record);

    int updateByPrimaryKey(ClientEddaFundApplication record);

	/**
	 * 统计EDDA入金申请笔数
	 */
	Long countEDDADepositApply();
}

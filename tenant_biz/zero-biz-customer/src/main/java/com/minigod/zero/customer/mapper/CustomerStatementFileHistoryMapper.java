package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.dto.StatementHistoryListDTO;
import com.minigod.zero.customer.entity.CustomerStatementFileHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.vo.StatementHistoryListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_statement_file_history】的数据库操作Mapper
* @createDate 2024-08-27 13:58:46
* @Entity com.minigod.zero.customer.entity.CustomerStatementFileHistoryEntity
*/
public interface CustomerStatementFileHistoryMapper extends BaseMapper<CustomerStatementFileHistoryEntity> {

	List<StatementHistoryListVO> customerStatementList(@Param("page") IPage<StatementHistoryListVO> page, @Param("statementHistoryListDTO") StatementHistoryListDTO statementHistoryListDTO);


}





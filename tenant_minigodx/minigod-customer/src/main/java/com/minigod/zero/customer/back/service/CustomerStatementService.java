package com.minigod.zero.customer.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.StatementFileListDTO;
import com.minigod.zero.customer.dto.StatementHistoryListDTO;
import com.minigod.zero.customer.dto.StatementListDTO;
import com.minigod.zero.customer.vo.StatementHistoryListVO;
import com.minigod.zero.customer.vo.StatementHistoryStatisticsVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.customer.back.service.CustomerStatementService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 10:40
 * @Version: 1.0
 */
public interface CustomerStatementService {
	R getTabulateStatistics(StatementListDTO statementListDTO);

	R sendStatementEmail(List<StatementFileListDTO> list);

	R<IPage<StatementHistoryListVO>> customerStatementhistory(IPage<StatementHistoryListVO> page, StatementHistoryListDTO statementHistoryListDTO);


	R<List<StatementHistoryStatisticsVO>> getHistoryStatistics(StatementHistoryListDTO statementHistoryListDTO);

	List<StatementHistoryListVO> getStatementHistoryListVOS(IPage<StatementHistoryListVO> page, StatementHistoryListDTO statementHistoryListDTO);

	void downFlile(StatementListDTO statementListDTO, HttpServletResponse response);
}

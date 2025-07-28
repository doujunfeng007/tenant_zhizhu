package com.minigod.zero.manage.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.manage.entity.SimuSysConfigEntity;
import com.minigod.zero.manage.entity.SimulateAccountFilterEntity;
import com.minigod.zero.manage.entity.SimulateCompetitionEntity;
import com.minigod.zero.manage.entity.SimulateCompetitionFilterEntity;
import com.minigod.zero.manage.vo.simulatetrade.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模拟交易
 */
@DS("simulate")
public interface SimulateTradeMapper extends BaseMapper<BaseEntity> {

    List<SimulateCompetitionVO> listSimulateCompetitionIdName();

    List<SimulateCompetitionEntity> listSimulateCompetition(@Param("page") IPage page, @Param("params") SimulateCompetitionSearchVO params);

    SimulateCompetitionEntity getSimulateCompetition(Long id);

    boolean updateSimulateCompetition(SimulateCompetitionEntity param);

    Long addSimulateCompetition(SimulateCompetitionEntity param);

    Long selectSimulateCompetitionLastId();

    String getSysConfigKeyVal(@Param("moduleName") String moduleName,@Param("keyName") String keyName);

    int updateSysConfigKeyVal(@Param("moduleName") String moduleName,@Param("keyName") String keyName,@Param("keyValue") String keyValue);

    int addSysConfigKeyVal(List<SimuSysConfigEntity> list);

    List<CompetitionRankDataVO> listRankData(@Param("page") IPage page, @Param("params") SimulateCompetitionSearchVO param);

    List<CompetitionPositionDataVO> listPositionData(@Param("page") IPage page, @Param("params") SimulateCompetitionSearchVO param);

    List<CompetitionPositionDataVO> listPositionDataByIds(List<Long> ids);

    List<CompetitionRankDataVO> listRankHistoryData(@Param("page") IPage page, @Param("params") SimulateCompetitionSearchVO param);

    List<CompetitionEntrustOrderVO> listEntrustOrder(@Param("page") IPage page, @Param("params") SimulateCompetitionSearchVO param);

    List<CompetitionEntrustOrderVO> listEntrustOrderByIds(List<Long> ids);

    Long getSimulateAccoundId(@Param("competitionId") Long competitionId, @Param("userId") Long userId);

    boolean saveSimulateCompetitionFilter(List<SimulateCompetitionFilterEntity> list);

    boolean updateSimulateAccountState(@Param("competitionId") Long competitionId, @Param("state") Integer state);

    boolean updateSimulateAccountStateInUserIds(@Param("competitionId") Long competitionId, @Param("userIds") List<Long> userIds, @Param("state")  Integer state);

    boolean updateSimulateAccountStateNotInUserIds(@Param("competitionId") Long competitionId, @Param("userIds") List<Long> userIds, @Param("state")  Integer state);

    boolean updateSimulateAccountTradeCount(@Param("competitionId") Long competitionId, @Param("tradeCount") Integer tradeCount);

	List<SimulateAccountFilterVO> listSimulateAccountFilter(@Param("page") IPage page, @Param("params") SimulateCompetitionSearchVO param);

    int deleteSimulateCompetitionFilterById(Long competitionId);

	void updateHoldStock(@Param("list") List<CompetitionPositionDataVO> dataList, @Param("limit") int size);

	void saveSimulateAccountFilter(List<SimulateAccountFilterEntity> list);

	List<SimulateAccountFilterEntity> simulateAccountFilterList(@Param("page") IPage page, @Param("params") SimulateCompetitionSearchVO param);

	List<SimulateAccountFilterEntity> simulateAccountFilterListByIds(List<Long> ids);

	List<SimulateAccountFilterEntity> simulateAccountFilterListByUserIds(List<Long> userIds);

	void deleteSimulateAccountFilter(List<Long> ids);

	void updateSimulateAccountFilterValid(List<Long> userIds);
}

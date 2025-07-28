package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.entity.ManualDepositRecord;
import com.minigod.zero.customer.vo.ManualDepositRecordVO;

import java.util.List;

/**
* @author dell
* @description 针对表【manual_deposit_record】的数据库操作Mapper
* @createDate 2024-07-17 10:34:06
* @Entity com.minigod.zero.customer.entity.ManualDepositRecord
*/
public interface ManualDepositRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ManualDepositRecord record);

    int insertSelective(ManualDepositRecord record);

    ManualDepositRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ManualDepositRecord record);

    int updateByPrimaryKey(ManualDepositRecord record);

	List<ManualDepositRecordVO> queryList(IPage<ManualDepositRecordVO> page, String keyword, String startTime, String endTime);
}

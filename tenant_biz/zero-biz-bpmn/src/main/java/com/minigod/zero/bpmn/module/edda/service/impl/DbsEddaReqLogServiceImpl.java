package com.minigod.zero.bpmn.module.edda.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.edda.entity.DbsEddaReqLogEntity;
import com.minigod.zero.bpmn.module.edda.service.DbsEddaReqLogService;
import com.minigod.zero.bpmn.module.edda.mapper.DbsEddaReqLogMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【dbs_edda_req_Log】的数据库操作Service实现
* @createDate 2024-05-13 16:07:05
*/
@Slf4j
@Service
@AllArgsConstructor
public class DbsEddaReqLogServiceImpl extends ServiceImpl<DbsEddaReqLogMapper, DbsEddaReqLogEntity>
    implements DbsEddaReqLogService{

}




